package com.app.ptc.workerservice.model;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Application Configuration.
 */
@Configuration
@PropertySource("classpath:application.yaml")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationProperties {

    @Value("${blob-store.host}")
    private String blobServiceHost;

    @Value("${blob-store.host}")
    private String blobServicePort;
}
