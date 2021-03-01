package com.app.ptc.blobstore.model;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Application Properties
 */
@Configuration
@PropertySource("classpath:application.yaml")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
/**
 *
 */
public class ApplicationProperties {
    @Value("${blob.location}")
    private String storageLocation;

}
