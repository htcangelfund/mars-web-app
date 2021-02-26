package com.itcd.angelfund.mars.connector;

import com.itcd.angelfund.mars.model.base.*;
import com.itcd.angelfund.mars.model.combination.FloorDetail;
import com.itcd.angelfund.mars.model.combination.RoomCurrentStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.sql.SQLException;
import java.util.List;

@ActiveProfiles("test")
@SpringBootTest
public class RoomDaoTest {
    
    @Autowired
    private RoomDao roomDao;

    @Test
    public void testGetRoomByIdFound() {
        final RoomInfo roomInfo = roomDao.getRoomById(1);
        Assertions.assertNotNull(roomInfo);
        Assertions.assertEquals(1, roomInfo.getId());
        Assertions.assertEquals("China", roomInfo.getCountry());
        Assertions.assertEquals("Guangzhou", roomInfo.getCity());
        Assertions.assertEquals("TKHOT2", roomInfo.getLocation());
        Assertions.assertEquals("24F", roomInfo.getFloor());
        Assertions.assertEquals("CR24.4", roomInfo.getName());
        Assertions.assertEquals("Special testing room 24.4 for MARS PoC", roomInfo.getRemark());
    }

    @Test
    public void testGetRoomByIdNotFound() {
        RoomInfo roomInfo = roomDao.getRoomById(5);
        Assertions.assertNull(roomInfo);
    }

    @Test
    public void testGetRoomCurrentStatus() throws SQLException {
        RoomCurrentStatus roomCurrentStatus = roomDao.getRoomCurrentStatusById(1);
        Assertions.assertNotNull(roomCurrentStatus);
        Assertions.assertEquals(1, roomCurrentStatus.getRoom().getId());
        Assertions.assertEquals(Status.OCCUPIED, roomCurrentStatus.getCurrentStatus().getStatus());
    }

    @Test
    public void testCreateRoomStatus() throws SQLException {
        final RoomStatus roomStatus = new RoomStatus();
        roomStatus.setRoomId(1);
        roomStatus.setStatus(Status.AVAILABLE);
        Assertions.assertEquals(1, roomDao.createRoomStatus(roomStatus));
    }

    @Test
    public void testCreateRoomStatusFailed() {
        final RoomStatus roomStatus = new RoomStatus();
        roomStatus.setRoomId(5);
        roomStatus.setStatus(Status.UNKNOWN);
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> roomDao.createRoomStatus(roomStatus));
    }

    @Test
    public void testGetRoomsCurrentStatus() {
        final List<RoomCurrentStatus> roomCurrentStatusList = roomDao.getRoomsWithCurrentStatus();
        Assertions.assertNotNull(roomCurrentStatusList);
        Assertions.assertEquals(4, roomCurrentStatusList.size());
        Assertions.assertEquals(4, roomCurrentStatusList.get(3).getRoom().getId());
        Assertions.assertEquals("China", roomCurrentStatusList.get(3).getRoom().getCountry());
        Assertions.assertEquals("Guangzhou", roomCurrentStatusList.get(3).getRoom().getCity());
        Assertions.assertEquals("TKHOT2", roomCurrentStatusList.get(3).getRoom().getLocation());
        Assertions.assertEquals("25F", roomCurrentStatusList.get(3).getRoom().getFloor());
        Assertions.assertEquals("CR25.5", roomCurrentStatusList.get(3).getRoom().getName());
        Assertions.assertEquals("Special testing room 25.5 for MARS PoC", roomCurrentStatusList.get(3).getRoom().getRemark());
        Assertions.assertEquals(4, roomCurrentStatusList.get(3).getCurrentStatus().getRoomId());
        Assertions.assertEquals(Status.UNKNOWN, roomCurrentStatusList.get(3).getCurrentStatus().getStatus());
    }


    @Test
    public void testGetRoomHistoricalStatus() {

    }

    @Test
    public void testGetByFloor() {
        List<FloorDetail> floorDetailList = roomDao.getRoomsGroupByFloor();
        Assertions.assertNotNull(floorDetailList);
        Assertions.assertEquals(2, floorDetailList.size());
        Assertions.assertEquals("China", floorDetailList.get(1).getCountry());
        Assertions.assertEquals("Guangzhou", floorDetailList.get(1).getCity());
        Assertions.assertEquals("TKHOT2", floorDetailList.get(1).getLocation());
        Assertions.assertEquals("25F", floorDetailList.get(1).getFloor());
        Assertions.assertEquals(2, floorDetailList.get(1).getRooms().size());
        Assertions.assertEquals("China", floorDetailList.get(1).getRooms().get(1).getRoom().getCountry());
        Assertions.assertEquals("Guangzhou", floorDetailList.get(1).getRooms().get(1).getRoom().getCity());
        Assertions.assertEquals("TKHOT2", floorDetailList.get(1).getRooms().get(1).getRoom().getLocation());
        Assertions.assertEquals("25F", floorDetailList.get(1).getRooms().get(1).getRoom().getFloor());
        Assertions.assertEquals(Status.UNKNOWN, floorDetailList.get(1).getRooms().get(1).getCurrentStatus().getStatus());
    }
}
