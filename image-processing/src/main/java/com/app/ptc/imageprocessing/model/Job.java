package com.app.ptc.imageprocessing.model;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Job {

    private Long Id;

    private String jobStatus;

    private String tenantId;

    private String clientId;

    private String imageLocation;

    private String errorMessage;
}
