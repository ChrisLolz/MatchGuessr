package com.chris.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.chris.backend.services.AdminService;

@Configuration
@EnableScheduling
public class SchedulerConfig {

    @Autowired
    private AdminService adminService;

    @Scheduled(fixedDelay = 5*60*1000)
    public void refreshLeagues() {
        adminService.setLeague("PL");
        adminService.setLeague("PD");
        adminService.setLeague("SA");
        adminService.setLeague("BL1");
        adminService.setLeague("FL1");
    }
}
