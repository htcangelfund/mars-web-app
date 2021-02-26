package com.itcd.angelfund.mars.service;

import com.itcd.angelfund.mars.MockData;
import com.itcd.angelfund.mars.delegate.RoomDelegate;
import com.itcd.angelfund.mars.model.base.RoomStatus;
import com.itcd.angelfund.mars.model.base.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
@MockBean(RoomDelegate.class)
public class RoomServiceTest {
    @Autowired
    private RoomDelegate roomDelegate;

    private RoomService service2Test;

    @BeforeEach
    public void setup() {
    	System.out.println(roomDelegate.toString());
        service2Test = new RoomService(roomDelegate);
    }

    @Test
    public void getRoomByIdFound() {
        Mockito.doReturn(Mono.just(MockData.mockRoomInfo())).when(roomDelegate).getRoomById(1);
        service2Test.getRoomById(1).subscribe(roomInfo -> {
            Assertions.assertEquals(1, roomInfo.getId());
            Assertions.assertEquals("City_Value", roomInfo.getCity());
        });
    }

    @Test
    public void getRoomByIdNotFound() {
        Mockito.doReturn(Mono.empty()).when(roomDelegate).getRoomById(2);
        StepVerifier.create(service2Test.getRoomById(2)).verifyComplete();
    }

    @Test
    public void updateRoomStatus() {
        Mockito.doReturn(1).when(roomDelegate).createRoomStatus(Mockito.any(RoomStatus.class));
        service2Test.updateRoomStatus(1, Status.AVAILABLE);
    }

    @Test
    public void updateRoomStatusFailed() {
        Mockito.doThrow(IllegalArgumentException.class).when(roomDelegate).createRoomStatus(Mockito.any(RoomStatus.class));
        Assertions.assertThrows(IllegalArgumentException.class, () -> service2Test.updateRoomStatus(2, Status.UNKNOWN));
    }


}
