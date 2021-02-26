package com.itcd.angelfund.mars.delegate;

import com.itcd.angelfund.mars.connector.LocalDateTimeRangeParam;
import com.itcd.angelfund.mars.connector.RoomDao;
import com.itcd.angelfund.mars.model.base.*;
import com.itcd.angelfund.mars.model.combination.FloorDetail;
import com.itcd.angelfund.mars.model.combination.LocationDetail;
import com.itcd.angelfund.mars.model.combination.RoomCurrentStatus;
import com.itcd.angelfund.mars.model.combination.RoomHistoricalStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class RoomDelegate {

    private final RoomDao roomDao;

    public RoomDelegate(final RoomDao roomDao) {
        this.roomDao = roomDao;
    }

    public Mono<FloorDetail> getRoomsWithCurrentStatusGroupByFloor(String floorId) {
        final FloorDetail floorDetail = roomDao.getRoomsWithCurrentStatusGroupByFloor(floorId);
        return Objects.isNull(floorDetail) ? Mono.empty() : Mono.just(floorDetail);
    }

    public Mono<LocationDetail> getRoomsWithCurrentStatusGroupByLocation(String locationId) {
        final LocationDetail locationDetail = roomDao.getRoomsWithCurrentStatusGroupByLocation(locationId);
        return Objects.isNull(locationDetail) ? Mono.empty() : Mono.just(locationDetail);
    }

    public Mono<RoomInfo> getRoomById(Integer roomId) {
        final RoomInfo room = roomDao.getRoomById(roomId);
        return Objects.isNull(room) ? Mono.empty() : Mono.just(room);
    }

    public Mono<RoomCurrentStatus> getRoomCurrentStatusById(Integer roomId) {
        final RoomCurrentStatus roomCurrentStatus = roomDao.getRoomCurrentStatusById(roomId);
        return Objects.isNull(roomCurrentStatus) ? Mono.empty() : Mono.just(roomCurrentStatus);
    }

    public Integer createRoomStatus(RoomStatus roomStatus) {
        try {
            return roomDao.createRoomStatus(roomStatus);
        } catch (DataAccessException dataAccessException) {
            log.error("failed to update room ["+ roomStatus.getId() + "] with status [" + roomStatus.getStatus() + "]",
                    dataAccessException);
            throw new IllegalArgumentException();
        }
    }

    public Flux<RoomCurrentStatus> getRoomsWithCurrentStatus() {
        final List<RoomCurrentStatus> roomCurrentStatusList = roomDao.getRoomsWithCurrentStatus();
        return CollectionUtils.isEmpty(roomCurrentStatusList) ? Flux.empty() : Flux.fromIterable(roomCurrentStatusList);
    }

    public Flux<RoomHistoricalStatus> getRoomWithHistoricalStatusByRoomId(Integer roomId, LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        List<RoomHistoricalStatus> roomHistoricalStatusList = roomDao.getRoomWithHistoricalStatusByRoomId(roomId, new LocalDateTimeRangeParam(fromDateTime, toDateTime));
        return CollectionUtils.isEmpty(roomHistoricalStatusList) ? Flux.empty() : Flux.fromIterable(roomHistoricalStatusList);
    }

    public Flux<FloorDetail> getRoomsGroupByFloor() {
        List<FloorDetail> floorDetails = roomDao.getRoomsGroupByFloor();
        return CollectionUtils.isEmpty(floorDetails) ? Flux.empty() : Flux.fromIterable(floorDetails);
    }
}
