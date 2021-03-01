package com.app.ptc.workerservice.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Class to capture job details.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Job {

    public static enum JobStatus {
        RUNNING,
        SUCCESS,
        FAILED
    };

    @Id
    @GeneratedValue
    private Long Id;

    private JobStatus jobStatus;

    private String tenantId;

    private String clientId;

    private String imageLocation;
}
