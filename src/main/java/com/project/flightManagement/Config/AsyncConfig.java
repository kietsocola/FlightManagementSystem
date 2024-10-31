package com.project.flightManagement.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class AsyncConfig {

    @Bean(name = "mailTaskExecutor")
    public Executor mailTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);  // Số luồng cơ bản
        executor.setMaxPoolSize(20);   // Số luồng tối đa
        executor.setQueueCapacity(500); // Độ dài hàng đợi
        executor.setThreadNamePrefix("MailThread-");
        executor.initialize();
        return executor;
    }
}