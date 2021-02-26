package com.itcd.angelfund.mars.connector;

import com.itcd.angelfund.mars.model.base.*;
import com.itcd.angelfund.mars.model.combination.FloorDetail;
import com.itcd.angelfund.mars.model.combination.LocationDetail;
import com.itcd.angelfund.mars.model.combination.RoomCurrentStatus;
import com.itcd.angelfund.mars.model.combination.RoomHistoricalStatus;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomDao {
    FloorDetail getRoomsWithCurrentStatusGroupByFloor(String floorId);
    LocationDetail getRoomsWithCurrentStatusGroupByLocation(String locationId);

    RoomInfo getRoomById(Integer roomId);

    Integer createRoomStatus(RoomStatus roomStatus) throws DataIntegrityViolationException;

    List<RoomCurrentStatus> getRoomsWithCurrentStatus();

    RoomCurrentStatus getRoomCurrentStatusById(Integer roomId);

    List<RoomHistoricalStatus> getRoomWithHistoricalStatusByRoomId(
            @Param("roomId") Integer roomId,
            @Param("localDateTimeRangeParam") LocalDateTimeRangeParam localDateTimeRangeParam);

    List<FloorDetail> getRoomsGroupByFloor();
}
