package com.axiom.mailer.config;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class TaskVerificationListener implements ApplicationListener<ContextRefreshedEvent> {

    private final TaskMonitoringService monitoring;

    public TaskVerificationListener(TaskMonitoringService monitoring) {
        this.monitoring = monitoring;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // This runs AFTER the context is fully loaded and all scheduling is complete
        int taskCount = monitoring.getScheduledTaskCount();

        if (taskCount >= 1) {
            System.out.println("✅ [SCHEDULER SUCCESS] Found " + taskCount + " scheduled tasks.");
        } else {
            System.err.println("❌ [SCHEDULER FAILURE] Only found " + taskCount + " tasks. Check configurations.");
        }
    }
}
