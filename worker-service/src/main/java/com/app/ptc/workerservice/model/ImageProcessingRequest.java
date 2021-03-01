package com.app.ptc.workerservice.model;

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

    private String md5;

    private String content;
}
