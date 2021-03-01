package com.app.ptc.blobstore.model;

import lombok.*;

/**
 * Request object to store image.
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlobStoreRequest {
    private String encoding;

    private String md5;

    private String content;

    private Long jobId;
}
