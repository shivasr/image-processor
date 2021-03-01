package com.app.ptc.blobstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring boot application to store Blobs.
 */
@SpringBootApplication
public class BlobStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlobStoreApplication.class, args);
    }

}
