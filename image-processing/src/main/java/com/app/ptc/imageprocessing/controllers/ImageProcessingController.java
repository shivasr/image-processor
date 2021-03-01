package com.app.ptc.imageprocessing.controllers;

import com.app.ptc.imageprocessing.exception.ApplicationException;
import com.app.ptc.imageprocessing.model.*;
import com.app.ptc.imageprocessing.util.JWTUtil;
import org.apache.tomcat.util.buf.HexUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

@RestController
@RequestMapping("/")
public class ImageProcessingController {

    Logger logger = LoggerFactory.getLogger(ImageProcessingController.class);

    private final RestTemplate restTemplate = new RestTemplate();

    public static final String WORKER_JOB_API = "http://%s:%s/api/v1/job";
    public static final String WORKER_STATUS_API = "http://%s:%s/api/v1/job/%d/status";
    public static final String BLOB_STORE_API = "http://%s:%s/api/v1/blob";

    private final ApplicationProperties properties;

    public ImageProcessingController(ApplicationProperties properties) {
        this.properties = properties;
    }

    @PostMapping
    public ResponseEntity<Job> processImage(HttpServletRequest request, @RequestBody ImageProcessingRequest ipRequest) {

        Job job;

        try {
            String header = request.getHeader("Authorization");
            User user = JWTUtil.authenticateAndRetrieveUserDetails(header);

            verifyMD5(ipRequest);

            BlobImage blobImage = sendToBlobStore(ipRequest);

            job = Job.builder()
                    .clientId(user.getClientId())
                    .tenantId(user.getTenantId())
                    .imageLocation(blobImage.getImageUri())
                    .build();

            Job createdJob = sendToWorkerService(job);
            return ResponseEntity.ok(createdJob);
        } catch (ApplicationException e) {
            return ResponseEntity.badRequest().body(Job.builder()
                    .errorMessage("Job not created! Error: " + e.getMessage())
                    .build());
        }
    }

    /**
     * Get /1/status
     * @param id
     * @return
     */
    @GetMapping("/{id}/status")
    public ResponseEntity<String> getStatus(@PathVariable("id") Long id) {
        String host = properties.getWorkerServiceHost();
        String port = properties.getWorkerServicePort();
        String serverUrl = String.format(WORKER_STATUS_API, host, port, id);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(serverUrl, String.class);

        if (response.getStatusCode() != HttpStatus.OK)
            return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(response.getBody());
    }

    /**
     *
     * @param request
     * @return
     * @throws ApplicationException
     */
    private BlobImage sendToBlobStore(ImageProcessingRequest request) throws ApplicationException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ImageProcessingRequest> requestEntity =
                new HttpEntity<>(request, headers);

        String host = properties.getBlobStoreHost();
        String port = properties.getBlobStorePort();

        String serverUrl = String.format(BLOB_STORE_API, host, port);
        logger.info("Blob store URL is {}", serverUrl);

        ResponseEntity<BlobImage> response = restTemplate
                .postForEntity(serverUrl, requestEntity, BlobImage.class);

        if (response.getStatusCode() != HttpStatus.OK)
            throw new ApplicationException("Worker service failed!");

        return response.getBody();
    }

    /**
     * Call worker service
     *
     * @param job
     * @return
     * @throws ApplicationException
     */
    private Job sendToWorkerService(Job job) throws ApplicationException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Job> requestEntity =
                new HttpEntity<>(job, headers);

        String host = properties.getWorkerServiceHost();
        String port = properties.getWorkerServicePort();

        String serverUrl = String.format(WORKER_JOB_API, host, port);

        logger.info("Worker service URL is {}", serverUrl);

        ResponseEntity<Job> response = restTemplate
                .postForEntity(serverUrl, requestEntity, Job.class);

        if (response.getStatusCode() != HttpStatus.OK)
            throw new ApplicationException("Worker service failed!");

        return response.getBody();
    }

    /**
     * To Verify the checksum computed from the binary data received with checksum value in the JSON.
     *
     * @param ipRequest
     * @throws ApplicationException
     */
    private void verifyMD5(ImageProcessingRequest ipRequest) throws ApplicationException {
        // Get MD5 Digest Instance
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new ApplicationException(e.getMessage());
        }

        // Update the binaries obtained from the JSON and verify
        md.update(Base64Utils.decodeFromString(ipRequest.getContent()));

        byte[] digest = md.digest();
        String myChecksum = HexUtils.toHexString(digest).toUpperCase(Locale.ROOT);

        if(!myChecksum.equals(ipRequest.getMD5().toUpperCase(Locale.ROOT)))
            throw new ApplicationException("MD5 Digest does not match the contents.");
    }
}
