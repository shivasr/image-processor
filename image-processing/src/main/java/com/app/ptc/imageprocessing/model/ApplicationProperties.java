package com.app.ptc.imageprocessing.model;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.yaml")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationProperties {
    @Value("${worker-service.host}")
    private String workerServiceHost;

    @Value("${worker-service.port}")
    private String workerServicePort;

    @Value("${blob-store.host}")
    private String blobStoreHost;

    @Value("${blob-store.port}")
    private String blobStorePort;
}
