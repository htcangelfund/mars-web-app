package com.itcd.angelfund.mars.service;

import com.itcd.angelfund.mars.delegate.FloorDelegate;
import com.itcd.angelfund.mars.delegate.RoomDelegate;
import com.itcd.angelfund.mars.model.base.RoomStatus;
import com.itcd.angelfund.mars.model.combination.FloorDetail;
import com.itcd.angelfund.mars.model.combination.FloorOccupationReport;
import com.itcd.angelfund.mars.model.combination.RoomCurrentStatus;
import com.itcd.angelfund.mars.service.strategy.CurrentOccupationRateCalculationStrategy;
import com.itcd.angelfund.mars.service.strategy.ObsoletedStatusPatchStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FloorService {

    private FloorDelegate floorDelegate;
    private RoomDelegate roomDelegate;

    public FloorService(final FloorDelegate floorDelegate, final RoomDelegate roomDelegate) {
        this.floorDelegate = floorDelegate;
        this.roomDelegate = roomDelegate;
    }

    public Flux<FloorDetail> getFloorsSummary() {
        return floorDelegate.getFloors();
    }

    public Flux<FloorDetail> getFloors() {
        return roomDelegate.getRoomsGroupByFloor().map(floorDetail -> {
            floorDetail.getRooms().forEach(
                    roomCurrentStatus -> new ObsoletedStatusPatchStrategy().apply(roomCurrentStatus)
            );
            return floorDetail;
        });
    }

    public Mono<FloorDetail> getFloorsById(String id) {
        return floorDelegate.getFloorById(id);
    }

    public Mono<FloorOccupationReport> getFloorCurrentOccupationReport(String floorId) {
        return roomDelegate.getRoomsWithCurrentStatusGroupByFloor(floorId)
                .switchIfEmpty(Mono.defer(() -> Mono.empty()))
                .map(floorDetail -> {
                    FloorOccupationReport floorOccupationReport = new FloorOccupationReport();
                    floorOccupationReport.setFloorDetail(floorDetail);
                    final List<RoomCurrentStatus> roomCurrentStatusList = floorDetail.getRooms().stream().collect(Collectors.toList());
                    final List<RoomStatus> statusList = roomCurrentStatusList.stream().map(
                            roomCurrentStatus -> new ObsoletedStatusPatchStrategy().apply(roomCurrentStatus)
                    ).collect(Collectors.toList());
                    floorOccupationReport.setUtilizeRate(new CurrentOccupationRateCalculationStrategy().apply(statusList));
                    return floorOccupationReport;
        });
    }
}
