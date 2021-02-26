package com.itcd.angelfund.mars.delegate;

import com.itcd.angelfund.mars.MockData;
import com.itcd.angelfund.mars.connector.RoomDao;
import com.itcd.angelfund.mars.model.base.RoomStatus;
import com.itcd.angelfund.mars.model.base.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
@MockBean(RoomDao.class)
public class RoomDelegateTest {
    @Autowired
    private RoomDao roomDao;

    private RoomDelegate delegate2Test;

    @BeforeEach
    public void setup() {
        delegate2Test = new RoomDelegate(roomDao);
    }

    @Test
    public void getRoomByIdFound() {
        Mockito.doReturn(MockData.mockRoomInfo()).when(roomDao).getRoomById(1);
        delegate2Test.getRoomById(1).subscribe(roomInfo -> {
            Assertions.assertEquals(1, roomInfo.getId());
            Assertions.assertEquals("City_Value", roomInfo.getCity());
        });
    }

    @Test
    public void getRoomByIdNotFound() {
        Mockito.doReturn(null).when(roomDao).getRoomById(2);
        StepVerifier.create(delegate2Test.getRoomById(2)).verifyComplete();
    }

    @Test
    public void updateRoomStatus() {
        Mockito.doReturn(1).when(roomDao).createRoomStatus(Mockito.any(RoomStatus.class));
        final RoomStatus roomStatus = new RoomStatus();
        roomStatus.setRoomId(1);
        roomStatus.setStatus(Status.AVAILABLE);
        final Integer entryUpdated = delegate2Test.createRoomStatus(roomStatus);
        Assertions.assertEquals(1, entryUpdated);
    }

    @Test
    public void updateRoomStatusFailed() {
        Mockito.doThrow(DataIntegrityViolationException.class).when(roomDao).createRoomStatus(Mockito.any(RoomStatus.class));
        final RoomStatus roomStatus = new RoomStatus();
        roomStatus.setRoomId(2);
        roomStatus.setStatus(Status.UNKNOWN);
        Assertions.assertThrows(IllegalArgumentException.class, () -> delegate2Test.createRoomStatus(roomStatus));
    }
}
