package com.axiom.mailer.config; // New Package/Class

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@EnableScheduling // Make sure scheduling is enabled here
public class SchedulerConfig {

    // Define the official TaskScheduler bean once
    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(10);
        scheduler.setThreadNamePrefix("scheduled-task-");
        // Initialization is done automatically by Spring, but we can keep it
        scheduler.initialize(); 
        return scheduler;
    }
}