package com.itcd.angelfund.mars.service.strategy;

import com.itcd.angelfund.mars.model.base.RoomStatus;
import com.itcd.angelfund.mars.model.base.Status;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractOccupationStrategyTest {
    protected final LocalDateTime baseLine = LocalDateTime.of(2020, 03, 04,05, 06,07,890123456);

    protected List<RoomStatus> mockData = new LinkedList() {{
        final RoomStatus roomStatus1 = new RoomStatus();
        roomStatus1.setId(BigInteger.valueOf(1));
        roomStatus1.setRoomId(1);
        roomStatus1.setStatus(Status.AVAILABLE);
        roomStatus1.setUpdateTimestamp(baseLine.plusMinutes(2));
        add(roomStatus1);

        final RoomStatus roomStatus2 = new RoomStatus();
        roomStatus2.setId(BigInteger.valueOf(2));
        roomStatus2.setRoomId(1);
        roomStatus2.setStatus(Status.AVAILABLE);
        roomStatus2.setUpdateTimestamp(baseLine.plusMinutes(4));
        add(roomStatus2);

        final RoomStatus roomStatus3 = new RoomStatus();
        roomStatus3.setId(BigInteger.valueOf(3));
        roomStatus3.setRoomId(1);
        roomStatus3.setStatus(Status.AVAILABLE);
        roomStatus3.setUpdateTimestamp(baseLine.plusMinutes(6));
        add(roomStatus3);

        final RoomStatus roomStatus4 = new RoomStatus();
        roomStatus4.setId(BigInteger.valueOf(4));
        roomStatus4.setRoomId(1);
        roomStatus4.setStatus(Status.AVAILABLE);
        roomStatus4.setUpdateTimestamp(baseLine.plusMinutes(8));
        add(roomStatus4);

        final RoomStatus roomStatus5 = new RoomStatus();
        roomStatus5.setId(BigInteger.valueOf(5));
        roomStatus5.setRoomId(1);
        roomStatus5.setStatus(Status.OCCUPIED);
        roomStatus5.setUpdateTimestamp(baseLine.plusMinutes(10));
        add(roomStatus5);

        final RoomStatus roomStatus6 = new RoomStatus();
        roomStatus6.setId(BigInteger.valueOf(6));
        roomStatus6.setRoomId(1);
        roomStatus6.setStatus(Status.OCCUPIED);
        roomStatus6.setUpdateTimestamp(baseLine.plusMinutes(12));
        add(roomStatus6);

        final RoomStatus roomStatus7 = new RoomStatus();
        roomStatus7.setId(BigInteger.valueOf(7));
        roomStatus7.setRoomId(1);
        roomStatus7.setStatus(Status.OCCUPIED);
        roomStatus7.setUpdateTimestamp(baseLine.plusHours(1));
        add(roomStatus7);

        final RoomStatus roomStatus8 = new RoomStatus();
        roomStatus8.setId(BigInteger.valueOf(8));
        roomStatus8.setRoomId(1);
        roomStatus8.setStatus(Status.UNKNOWN);
        roomStatus8.setUpdateTimestamp(baseLine.plusMinutes(14));
        add(roomStatus8);
    }};
}
