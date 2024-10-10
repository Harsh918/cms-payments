package com.cms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/batch")
public class BatchController {

    private static final Logger logger = LoggerFactory.getLogger(BatchController.class);
    
    private final JobLauncher jobLauncher;
    private final Job asyncJob;

    public BatchController(JobLauncher jobLauncher, Job asyncJob) {
        this.jobLauncher = jobLauncher;
        this.asyncJob = asyncJob;
    }

    @PostMapping("/execute")
    public ResponseEntity<String> executeBatchJob() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(asyncJob, jobParameters);
            return ResponseEntity.ok("Batch job has been invoked successfully.");
        } catch (Exception e) {
            logger.error("Batch job failed", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Batch job failed: " + e.getMessage());
        }
    }
}
