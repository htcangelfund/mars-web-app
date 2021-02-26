package com.itcd.angelfund.mars.controller;

import com.itcd.angelfund.mars.model.combination.FloorDetail;
import com.itcd.angelfund.mars.model.combination.FloorOccupationReport;
import com.itcd.angelfund.mars.service.FloorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/floors")
public class FloorController {
    private FloorService floorService;
    public FloorController(FloorService floorService) {
        this.floorService = floorService;
    }

    @GetMapping(value = "/summary", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<FloorDetail> getFloorsSummary(ServerHttpResponse response) {
        return floorService.getFloorsSummary().switchIfEmpty(Flux.defer(() -> {
            response.setStatusCode(HttpStatus.NO_CONTENT);
            return Flux.empty();
        }));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<FloorDetail> getFloors(ServerHttpResponse response) {
        return floorService.getFloors().switchIfEmpty(Flux.defer(() -> {
            response.setStatusCode(HttpStatus.NO_CONTENT);
            return Flux.empty();
        }));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Mono<FloorDetail> getFloorsById(@PathVariable String id, ServerHttpResponse response) {
        return floorService.getFloorsById(id).switchIfEmpty(Mono.defer(() -> {
            response.setStatusCode(HttpStatus.NOT_FOUND);
            return Mono.empty();
        }));
    }

    @GetMapping(value = "/{id}/report", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<FloorOccupationReport> getReportByFloorId(@PathVariable String id, ServerHttpResponse response) {
        return floorService.getFloorCurrentOccupationReport(id).switchIfEmpty(Mono.defer(() -> {
            response.setStatusCode(HttpStatus.NO_CONTENT);
            return Mono.empty();
        }));
    }
}
