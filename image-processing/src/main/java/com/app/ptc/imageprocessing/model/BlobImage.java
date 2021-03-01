package com.app.ptc.imageprocessing.model;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BlobImage {

    private Long id;

    private String fileNameWithAbsolutePath;

    private String imageUri;

}
