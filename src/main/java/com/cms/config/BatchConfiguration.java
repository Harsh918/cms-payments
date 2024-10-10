package com.cms.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import com.cms.entity.Transaction;
import com.cms.repository.TransactionRepository;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    private final TransactionRepository transactionRepository;
    private final EntityManagerFactory entityManagerFactory;

    public BatchConfiguration(TransactionRepository transactionRepository, EntityManagerFactory entityManagerFactory) {
        this.transactionRepository = transactionRepository;
        this.entityManagerFactory = entityManagerFactory;
    }

    @Bean
    public TaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setMaxPoolSize(12);
        executor.setCorePoolSize(8);
        executor.setQueueCapacity(15);
        executor.initialize();
        return executor;
    }

    @Bean
    public Job asyncJob(JobRepository jobRepository, Step step) {
        return new JobBuilder("asyncJob", jobRepository)
                .start(step)
                .build();
    }

    @Bean
    public Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step", jobRepository)
                .<Transaction, Transaction>chunk(10)
                .reader(transactionItemReader())
                .processor(transactionItemProcessor())
                .writer(transactionItemWriter())
                .taskExecutor(threadPoolTaskExecutor())
                .throttleLimit(10)
                .transactionManager(transactionManager)
                .build();
    }

    @Bean
    public ItemReader<Transaction> transactionItemReader() {
        return new JpaPagingItemReaderBuilder<Transaction>()
                .name("transactionItemReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(10)
                .queryString("SELECT t FROM Transaction t")
                .build();
    }

    @Bean
    public ItemProcessor<Transaction, Transaction> transactionItemProcessor() {
        return transaction -> {
            transaction.setStatus("Processed");
            return transaction;
        };
    }

    @Bean
    public ItemWriter<Transaction> transactionItemWriter() {
        return new JpaItemWriterBuilder<Transaction>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }
}
