package com.reviewsystem.hotel.service.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/staffs")
public class StaffController {
    @GetMapping
    public ResponseEntity<List<String>> getDefaultStaffs() {
        List<String> staffList = Arrays.asList("Paul", "Nitin", "Ram", "Krishna");
        return ResponseEntity.ok(staffList);
    }
}
