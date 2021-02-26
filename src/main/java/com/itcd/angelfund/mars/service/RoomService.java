package com.itcd.angelfund.mars.service;

import com.itcd.angelfund.mars.delegate.RoomDelegate;
import com.itcd.angelfund.mars.model.base.*;
import com.itcd.angelfund.mars.model.combination.RoomCurrentStatus;
import com.itcd.angelfund.mars.model.combination.RoomOccupationReport;
import com.itcd.angelfund.mars.service.strategy.DistinctRoomStatusFilterStrategy;
import com.itcd.angelfund.mars.service.strategy.ObsoletedStatusPatchStrategy;
import com.itcd.angelfund.mars.service.strategy.UnknownStatusPatchStrategy;
import com.itcd.angelfund.mars.service.strategy.OccupationRateCalculationStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

@Slf4j
@Service
public class RoomService {

    private RoomDelegate roomDelegate;

    public RoomService(final RoomDelegate roomDelegate) {
        this.roomDelegate = roomDelegate;
    }

    public Mono<RoomInfo> getRoomById(Integer id) {
        return roomDelegate.getRoomById(id);
    }

    public Mono<RoomCurrentStatus> getRoomCurrentStatusById(Integer id) {
        return roomDelegate.getRoomCurrentStatusById(id).map(roomCurrentStatus -> {
            roomCurrentStatus.setCurrentStatus(new ObsoletedStatusPatchStrategy().apply(roomCurrentStatus));
            return roomCurrentStatus;
        });
    }

    public void updateRoomStatus(Integer roomId, Status newStatus) {
        final RoomStatus statusEntry = new RoomStatus();
        statusEntry.setRoomId(roomId);
        statusEntry.setStatus(newStatus);

        roomDelegate.createRoomStatus(statusEntry);
    }

    public Flux<RoomCurrentStatus> getRoomsWithCurrentStatus() {
        return roomDelegate.getRoomsWithCurrentStatus();
    }

    public Flux<RoomOccupationReport> getRoomOccupationReport(Integer roomId, LocalDate fromDate, LocalTime fromTime, LocalDate toDate, LocalTime toTime) {
        if (Objects.isNull(fromDate) && Objects.isNull(toDate)) {
            toDate = LocalDate.now();
        }

        if (Objects.isNull(fromDate)) {
            fromDate = toDate.minusDays(1);
        }

        if (Objects.isNull(toDate)) {
            toDate = fromDate.plusDays(1);
        }

        final LocalDateTime fromDateTime = LocalDateTime.of(fromDate, Objects.isNull(fromTime) ? LocalTime.MIN : fromTime);
        final LocalDateTime toDateTime = LocalDateTime.of(toDate, Objects.isNull(toTime) ? LocalTime.MAX : toTime);

        return roomDelegate.getRoomWithHistoricalStatusByRoomId(roomId,fromDateTime, toDateTime)
                .map(roomHistoricalStatus -> {
                    final RoomOccupationReport roomOccupationReport = new RoomOccupationReport();
                    roomOccupationReport.setRoom(roomHistoricalStatus.getRoom());
                    roomOccupationReport.setRoomStatusList(
                            new DistinctRoomStatusFilterStrategy().apply(
                                    new UnknownStatusPatchStrategy(fromDateTime, toDateTime, 3).apply(roomHistoricalStatus.getRoomStatusList())
                            )
                    );
                    roomOccupationReport.setUtilizeRate(new OccupationRateCalculationStrategy(
                            LocalTime.of(9, 0), LocalTime.of(18, 0)
                    ).apply(roomHistoricalStatus.getRoomStatusList()));
                    return roomOccupationReport;
                });
    }
}
