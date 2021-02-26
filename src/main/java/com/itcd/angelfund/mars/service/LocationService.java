package com.itcd.angelfund.mars.service;

import com.itcd.angelfund.mars.delegate.RoomDelegate;
import com.itcd.angelfund.mars.model.base.RoomStatus;
import com.itcd.angelfund.mars.model.base.Status;
import com.itcd.angelfund.mars.model.combination.LocationOccupationReport;
import com.itcd.angelfund.mars.model.combination.RoomCurrentStatus;
import com.itcd.angelfund.mars.service.strategy.CurrentOccupationRateCalculationStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LocationService {

    private RoomDelegate roomDelegate;

    public LocationService (RoomDelegate roomDelegate) {
        this.roomDelegate = roomDelegate;
    }

    public Mono<LocationOccupationReport> getLocationCurrentOccupationReport(String locationId) {
        return roomDelegate.getRoomsWithCurrentStatusGroupByLocation(locationId)
                .switchIfEmpty(Mono.defer(() -> Mono.empty()))
                .map(locationDetail -> {
                    final LocationOccupationReport locationOccupationReport = new LocationOccupationReport();
                    locationOccupationReport.setLocationDetail(locationDetail);
                    final List<RoomCurrentStatus> roomCurrentStatusList = locationDetail.getRooms().stream().collect(Collectors.toList());
                    final List<RoomStatus> statusList = roomCurrentStatusList.stream().map(roomCurrentStatus -> {
                        if(roomCurrentStatus.getCurrentStatus().getUpdateTimestamp().isBefore(LocalDateTime.now().minusHours(1))) {
                            log.warn("Room [{}] has a far too early lastUpdateStatus[{}] compare to current time [{}], setting status to UNKNOWN",
                                    roomCurrentStatus.getRoom().getId(),
                                    roomCurrentStatus.getCurrentStatus().getUpdateTimestamp(),
                                    LocalDateTime.now()
                            );
                            roomCurrentStatus.getCurrentStatus().setStatus(Status.UNKNOWN);
                        }
                        return roomCurrentStatus.getCurrentStatus();
                    }).collect(Collectors.toList());
                    locationOccupationReport.setUtilizeRate(new CurrentOccupationRateCalculationStrategy().apply(statusList));
                    return locationOccupationReport;
                });
    }
}
