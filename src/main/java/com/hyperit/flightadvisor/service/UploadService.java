package com.hyperit.flightadvisor.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Service for data upload operations.
 */
public interface UploadService {

    /**
     * Upload airports.
     *
     * @param file File with airports.
     */
    void uploadAirports(MultipartFile file);

    /**
     * Upload routes.
     *
     * @param file File with routes.
     */
    void uploadRoutes(MultipartFile file);
}
