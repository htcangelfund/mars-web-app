package com.itcd.angelfund.mars.controller;

import com.itcd.angelfund.mars.model.Constant;
import com.itcd.angelfund.mars.model.base.RoomInfo;
import com.itcd.angelfund.mars.model.combination.RoomCurrentStatus;
import com.itcd.angelfund.mars.model.base.Status;
import com.itcd.angelfund.mars.model.combination.RoomOccupationReport;
import com.itcd.angelfund.mars.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalTime;

@Slf4j
@RestController
@RequestMapping("/rooms")
public class RoomController {
    private final RoomService roomService;

    public RoomController(final RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping(value = "/{roomId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<RoomInfo> getRoomInfoById(
            @PathVariable final Integer roomId, ServerHttpResponse response) {
        return roomService.getRoomById(roomId).switchIfEmpty(Mono.defer(() -> {
            response.setStatusCode(HttpStatus.NOT_FOUND);
            return Mono.empty();
        }));
    }

    @GetMapping(value = "/{roomId}/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<RoomCurrentStatus> getRoomStatus(
            @PathVariable final Integer roomId,
            ServerHttpResponse response
    ) {
        return roomService.getRoomCurrentStatusById(roomId).switchIfEmpty(Mono.defer(() -> {
            response.setStatusCode(HttpStatus.NO_CONTENT);
            return Mono.empty();
        }));
    }

    @PostMapping(value = "/{roomId}/status", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateRoomStatus(
            @PathVariable final Integer roomId,
            @RequestBody final Integer status,
            ServerHttpResponse response
            ) {
        try {
            roomService.updateRoomStatus(roomId, Status.valueOf(status));
            response.setStatusCode(HttpStatus.CREATED);
        } catch (IllegalArgumentException illegalArgumentException) {
            response.setStatusCode(HttpStatus.BAD_REQUEST);
        }
    }
    
    @CrossOrigin(origins = "http://139.224.70.36:8080")
    @GetMapping(value = "/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<RoomCurrentStatus> getRoomsCurrentStatus(ServerHttpResponse response) {
        return roomService.getRoomsWithCurrentStatus().switchIfEmpty(Flux.defer(() -> {
            response.setStatusCode(HttpStatus.NO_CONTENT);
            return Flux.empty();
        }));
    }

    @GetMapping(value = "/{roomId}/status/report", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<RoomOccupationReport> getRoomStatusReport(
            @PathVariable Integer roomId,
            @RequestParam(required = false) @DateTimeFormat(pattern = Constant.LOCAL_DATE_FORMAT) final LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = Constant.LOCAL_TIME_FORMAT) final LocalTime fromTime,
            @RequestParam(required = false) @DateTimeFormat(pattern = Constant.LOCAL_DATE_FORMAT) final LocalDate toDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = Constant.LOCAL_TIME_FORMAT) final LocalTime toTime,
            ServerHttpResponse response) {
        return roomService.getRoomOccupationReport(roomId, fromDate, fromTime, toDate, toTime)
                .switchIfEmpty(Flux.defer(() -> {
                    response.setStatusCode(HttpStatus.NO_CONTENT);
                    return Flux.empty();
                }));
    }
}
