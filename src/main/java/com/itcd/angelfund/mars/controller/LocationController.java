package com.itcd.angelfund.mars.controller;

import com.itcd.angelfund.mars.model.combination.LocationOccupationReport;
import com.itcd.angelfund.mars.service.LocationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/location")
public class LocationController {
    private LocationService locationService;
    public LocationController (LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping(value = "/{id}/report", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<LocationOccupationReport> getFloorStatusReportByFloorId(
            @PathVariable String id, ServerHttpResponse response) {
        return locationService.getLocationCurrentOccupationReport(id).switchIfEmpty(Mono.defer(() -> {
            response.setStatusCode(HttpStatus.NO_CONTENT);
            return Mono.empty();
        }));
    }
}
