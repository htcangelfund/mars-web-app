package com.itcd.angelfund.mars;

import com.itcd.angelfund.mars.model.base.*;
import com.itcd.angelfund.mars.model.combination.FloorDetail;
import com.itcd.angelfund.mars.model.combination.RoomCurrentStatus;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Collections;

public class MockData {
    public static RoomInfo mockRoomInfo() {
        final RoomInfo roomInfo = new RoomInfo();
        roomInfo.setId(1);
        roomInfo.setCountry("Country_Value");
        roomInfo.setCity("City_Value");
        roomInfo.setLocation("Location_Value");
        roomInfo.setFloor("Floor_Value");
        roomInfo.setName("Name_Value");
        roomInfo.setRemark("Remark_Value");
        roomInfo.setUpdateTimestamp(LocalDateTime.of(2020, 1, 12, 2, 2, 47));
        return roomInfo;
    }

    public static RoomStatus mockRoomStatus() {
        final RoomStatus roomStatus = new RoomStatus();
        roomStatus.setId(BigInteger.TEN);
        roomStatus.setRoomId(1);
        roomStatus.setStatus(Status.AVAILABLE);
        roomStatus.setUpdateTimestamp(LocalDateTime.of(2020, 1, 12, 2, 2, 49));
        return roomStatus;
    }

    public static FloorDetail mockFloorDetail() {
        final RoomCurrentStatus roomCurrentStatus = new RoomCurrentStatus();
        roomCurrentStatus.setRoom(MockData.mockRoomInfo());
        roomCurrentStatus.setCurrentStatus(MockData.mockRoomStatus());

        final FloorDetail floorDetail = new FloorDetail();
        floorDetail.setCountry(roomCurrentStatus.getRoom().getCountry());
        floorDetail.setCity(roomCurrentStatus.getRoom().getCity());
        floorDetail.setLocation(roomCurrentStatus.getRoom().getLocation());
        floorDetail.setFloor(roomCurrentStatus.getRoom().getFloor());
        floorDetail.setRooms(Collections.singletonList(roomCurrentStatus));

        return floorDetail;
    }
}
