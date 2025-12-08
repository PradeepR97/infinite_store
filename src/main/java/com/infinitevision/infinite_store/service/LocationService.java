package com.infinitevision.infinite_store.service;

import com.infinitevision.infinite_store.dto.LocationResponse;
import com.infinitevision.infinite_store.dto.ManualLocationRequest;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class LocationService {

    private final RestTemplate restTemplate = new RestTemplate();


    public LocationResponse getLocation() {
        log.info("Detecting location via IP API...");
        String url = "https://ipapi.co/json/";

        try {
            String response = restTemplate.getForObject(url, String.class);
            log.debug("IP API Response: {}", response);

            JSONObject json = new JSONObject(response);
            LocationResponse location = new LocationResponse();
            location.setIp(json.optString("ip"));
            location.setCity(json.optString("city"));
            location.setRegion(json.optString("region"));
            location.setCountry(json.optString("country"));
            location.setLatitude(json.optDouble("latitude"));
            location.setLongitude(json.optDouble("longitude"));
            location.setPincode(json.optString("postal"));  // Zepto-style
            location.setAddress(json.optString("city") + ", " + json.optString("region"));

            log.info("Location detected successfully: {}", location);
            return location;

        } catch (Exception e) {
            log.error("Error detecting location", e);
            throw new RuntimeException("AUTO_DETECT_FAILED");
        }
    }


    public LocationResponse saveManualLocation(ManualLocationRequest request) {
        log.info("Saving manual location: {}", request);

        LocationResponse location = new LocationResponse();
        location.setHomeNumber(request.getHomeNumber());
        location.setStreet(request.getStreet());
        location.setLandmark(request.getLandmark());
        location.setPincode(request.getPincode());
        location.setPhoneNumber(request.getPhoneNumber());

        // Combine full address for display
        String fullAddress = (request.getHomeNumber() != null ? request.getHomeNumber() + ", " : "") +
                (request.getStreet() != null ? request.getStreet() + ", " : "") +
                (request.getLandmark() != null ? request.getLandmark() + ", " : "") +
                (request.getPincode() != null ? request.getPincode() : "");
        location.setAddress(fullAddress);

        return location;
    }

}
