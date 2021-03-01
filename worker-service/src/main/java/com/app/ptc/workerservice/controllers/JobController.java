package com.app.ptc.workerservice.controllers;

import com.app.ptc.workerservice.model.ApplicationProperties;
import com.app.ptc.workerservice.model.Job;
import com.app.ptc.workerservice.repositories.JobRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * API to manage jobs.
 */
@RestController
@RequestMapping("/api/v1/job")
public class JobController {

    Logger logger = LoggerFactory.getLogger(JobController.class);
    private final JobRepository repository;

    private final ApplicationProperties properties;

    /**
     * Constructor.
     *
     * @param repository
     * @param properties
     */
    public JobController(JobRepository repository, ApplicationProperties properties) {
        this.repository = repository;
        this.properties = properties;
    }

    /**
     * GET /{id}/status
     *
     * API to get status of a job.
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}/status")
    public ResponseEntity<String> getStatus(@PathVariable("id") Long id) {
        Job job = Job.builder()
                .Id(id)
                .jobStatus(Job.JobStatus.RUNNING)
                .build();

        return ResponseEntity.ok(job.getJobStatus().toString());

    }

    /**
     * POST /api/v1/job
     *
     * API to submit image processing jobs.
     *
     * @param job
     * @return
     */
    @PostMapping
    public ResponseEntity<Job> processImage(@RequestBody Job job) {
        logger.info("Received image processing request.");

        job.setJobStatus(Job.JobStatus.RUNNING);
        job = repository.save(job);

        logger.info("Received image processing request with job Id {}", job.getId());
        return ResponseEntity.ok(job);
    }
}
