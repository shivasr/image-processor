package com.app.ptc.imageprocessing.model;

import lombok.*;

/**
 *
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageProcessingRequest {
    private String encoding;

    private String MD5;

    private String content;

    private String tenantId;

    private String clientId;

}
