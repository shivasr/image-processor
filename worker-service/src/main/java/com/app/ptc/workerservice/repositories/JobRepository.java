package com.app.ptc.workerservice.repositories;

import com.app.ptc.workerservice.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository to manage CRUD operations for Job.
 */
public interface JobRepository extends JpaRepository<Job, Long> {
}
