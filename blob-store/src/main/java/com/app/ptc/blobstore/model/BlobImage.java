package com.app.ptc.blobstore.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Entity to capture image details,
 */
@Entity
@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BlobImage {

    @Id
    @GeneratedValue
    private Long id;

    private String fileNameWithAbsolutePath;

    private String imageUri;

}
