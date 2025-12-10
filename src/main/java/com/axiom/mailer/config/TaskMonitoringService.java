package com.axiom.mailer.config;

import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskHolder;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;

@Component
public class TaskMonitoringService implements SchedulingConfigurer {

    private ScheduledTaskRegistrar taskRegistrar;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        // Spring calls this method to hand us the Registrar it uses internally.
        this.taskRegistrar = taskRegistrar; 
    }

    // Public method to check the task count at runtime
    public int getScheduledTaskCount() {
        if (taskRegistrar == null) {
            return 0;
        }
        // ScheduledTaskHolder is the correct interface to retrieve the tasks
        ScheduledTaskHolder holder = (ScheduledTaskHolder) taskRegistrar;
        
        // This method returns the Set of all registered tasks
        return holder.getScheduledTasks().size(); 
    }
}
