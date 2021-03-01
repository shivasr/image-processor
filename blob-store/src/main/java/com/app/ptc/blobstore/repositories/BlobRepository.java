package com.app.ptc.blobstore.repositories;

import com.app.ptc.blobstore.model.BlobImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

/**
 * Repository to handle CRUD operation.
 */
@Component
public interface BlobRepository extends JpaRepository<BlobImage, Long> {
}
