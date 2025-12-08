package com.infinitevision.infinite_store.controller;

import com.infinitevision.infinite_store.dto.ApiResponse;
import com.infinitevision.infinite_store.dto.LocationResponse;
import com.infinitevision.infinite_store.dto.ManualLocationRequest;
import com.infinitevision.infinite_store.service.LocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/location")
@Slf4j
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }


    @GetMapping("/detect")
    public ResponseEntity<ApiResponse<LocationResponse>> detectLocation() {
        log.info("Request received: /location/detect");
        try {
            LocationResponse location = locationService.getLocation();
            return ResponseEntity.ok(ApiResponse.success("Location detected successfully", location));
        } catch (Exception e) {
            log.error("AUTO_DETECT_FAILED", e);
            return ResponseEntity.ok(ApiResponse.error("AUTO_DETECT_FAILED", 500));
        }
    }


    @PostMapping("/manual")
    public ResponseEntity<ApiResponse<LocationResponse>> manualLocation(
            @RequestBody ManualLocationRequest request) {

        log.info("Request received: /location/manual with payload: {}", request);

        if (request.getPincode() == null || request.getPincode().isEmpty()) {
            log.warn("Manual location failed: PINCODE_REQUIRED");
            return ResponseEntity.badRequest()
                    .body(ApiResponse.failure("PINCODE_REQUIRED", 400));
        }

        LocationResponse location = locationService.saveManualLocation(request);
        log.info("Manual location saved successfully: {}", location);

        return ResponseEntity.ok(ApiResponse.success("Location saved successfully", location));
    }

}
