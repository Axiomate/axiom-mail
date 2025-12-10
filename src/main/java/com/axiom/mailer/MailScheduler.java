package com.axiom.mailer; // Original Package

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.axiom.mailer.config.TaskMonitoringService;
import com.axiom.mailer.service.BulkMailService;

import jakarta.annotation.PostConstruct;

@Component
public class MailScheduler {

    private final BulkMailService mailService;
    
    private final TaskMonitoringService monitoring;

    // Inject only the service needed for the task
    public MailScheduler(BulkMailService mailService,TaskMonitoringService monitoring) { 
        this.mailService = mailService;
        this.monitoring = monitoring;
        
        System.out.println("MailScheduler initialized.");
    }
    
    // Both tasks will now be correctly registered with the scheduler defined in SchedulerConfig
    @Scheduled(cron = "0 40 8 * * *", zone = "Asia/Dubai")
    public void runDailyEmailTask() {
        System.out.println("Running scheduled email task at " + java.time.LocalDateTime.now());
        mailService.sendAllCompanyMails();
    }

	@PostConstruct
	public void checkTasks() {
		System.out.println("[SCHEDULER] Number of scheduled tasks: "
				+ monitoring.getScheduledTaskCount());

	}
}