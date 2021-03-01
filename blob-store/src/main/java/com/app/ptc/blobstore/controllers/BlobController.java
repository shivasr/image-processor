package com.app.ptc.blobstore.controllers;

import com.app.ptc.blobstore.model.ApplicationProperties;
import com.app.ptc.blobstore.model.BlobError;
import com.app.ptc.blobstore.model.BlobImage;
import com.app.ptc.blobstore.model.BlobStoreRequest;
import com.app.ptc.blobstore.repositories.BlobRepository;
import com.google.common.collect.ImmutableMap;
import org.apache.tomcat.util.buf.HexUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/blob")
public class BlobController {
    Logger logger = LoggerFactory.getLogger(BlobController.class);

    private final ApplicationProperties properties;

    private final BlobRepository repository;

    private final Map<String, String> extensionToMediaTypeMap =
                                        ImmutableMap.of("svg", MediaType.IMAGE_PNG_VALUE,
                                                        "jpg", MediaType.IMAGE_JPEG_VALUE,
                                                        "gif", MediaType.IMAGE_GIF_VALUE);

    private static byte[] imageContents = HexUtils.fromHexString("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");
    /**
     * Constructor
     *
     * @param properties
     * @param repository
     */
    public BlobController(ApplicationProperties properties, BlobRepository repository) {
        this.properties = properties;
        this.repository = repository;
    }

    /**
     * GET /api/v1/blob/{id}
     *
     * API to download image.
     *
     * @param response
     * @param id
     * @throws IOException
     */
    @RequestMapping("/{id}")
    @GetMapping
    public void downloadPDFResource(HttpServletResponse response,
                                    @PathVariable("id") Long id) throws IOException {
        try {
            logger.info(String.format("Got a request to download %s", id));

            Optional<BlobImage> blobImageOptional = repository.findById(id);
            BlobImage blob = blobImageOptional.orElseThrow(() -> new IllegalArgumentException("File not found!"));
            logger.info(String.format("The request translated to the file by name: %s", blob.getFileNameWithAbsolutePath()));
            Path file = Paths.get(blob.getFileNameWithAbsolutePath());

            String contentType = MediaType.ALL_VALUE;

            logger.info(String.format(String.format("Content Type would be %s", contentType)));

            if (Files.exists(file)) {
                response.setContentType(contentType);
                response.addHeader("Content-Disposition", "attachment; filename=" + blob.getFileNameWithAbsolutePath());
                try {
                    response.getOutputStream().write(imageContents);
                    response.getOutputStream().flush();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());

            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * POST /api/v1/blob
     *
     * API to upload image.
     * @param request
     * @return
     */

    @PostMapping
    public ResponseEntity<BlobImage> saveImage(@RequestBody BlobStoreRequest request) {
        logger.info("Got a request to save an image: {}", request.getMd5());
        BlobImage blob = BlobImage.builder()
                .build();
        blob = repository.save(blob);

        String storageLocation = properties.getStorageLocation();
        String uploadDir = storageLocation + "/" + blob.getId();

        blob.setImageUri(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/blob/{id}")
                .buildAndExpand(blob.getId()).toUriString());

        blob.setFileNameWithAbsolutePath(uploadDir);

        repository.save(blob);
        return ResponseEntity.ok(blob);
    }
}
