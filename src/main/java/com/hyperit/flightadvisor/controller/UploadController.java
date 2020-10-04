package com.hyperit.flightadvisor.controller;

import com.hyperit.flightadvisor.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("upload")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class UploadController {

    private final UploadService uploadService;

    @PostMapping("airports")
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadAirports(@RequestParam("file") MultipartFile file) {
        uploadService.uploadAirports(file);
    }

    @PostMapping("routes")
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadRoutes(@RequestParam("file") MultipartFile file) {
        uploadService.uploadRoutes(file);
    }
}


