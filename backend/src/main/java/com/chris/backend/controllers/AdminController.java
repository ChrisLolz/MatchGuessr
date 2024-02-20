package com.chris.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chris.backend.services.AdminService;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping("/PL")
    public void setPremierLeague() {
        adminService.setLeague("PL");
    }

    @PostMapping("/PD")
    public void setLaLiga() {
        adminService.setLeague("PD");
    }

    @PostMapping("/SA")
    public void setSerieA() {
        adminService.setLeague("SA");
    }

    @PostMapping("/BL1")
    public void setBundesliga() {
        adminService.setLeague("BL1");
    }

    @PostMapping("/FL1")
    public void setLigue1() {
        adminService.setLeague("FL1");
    }

    @PostMapping()
    public void setAllLeagues() {
        adminService.setLeague("PL");
        adminService.setLeague("PD");
        adminService.setLeague("SA");
        adminService.setLeague("BL1");
        adminService.setLeague("FL1");
    }
}
