package com.itcd.angelfund.mars.controller;

import com.itcd.angelfund.mars.MockData;
import com.itcd.angelfund.mars.model.base.*;
import com.itcd.angelfund.mars.model.combination.RoomCurrentStatus;
import com.itcd.angelfund.mars.service.RoomService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@MockBeans({@MockBean(RoomService.class)})
public class RoomControllerTest {

    @Autowired
    private RoomService roomService;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void testGetRoomInfoFound() {
        Mockito.doReturn(Mono.just(MockData.mockRoomInfo())).when(roomService).getRoomById(1);

        webTestClient.get()
                .uri("/rooms/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(RoomInfo.class)
                .consumeWith(response -> {
                    Assertions.assertEquals(1, response.getResponseBody().getId());
                    Assertions.assertEquals("Country_Value", response.getResponseBody().getCountry());
                    Assertions.assertEquals("City_Value", response.getResponseBody().getCity());
                    Assertions.assertEquals("Location_Value", response.getResponseBody().getLocation());
                    Assertions.assertEquals("Floor_Value", response.getResponseBody().getFloor());
                    Assertions.assertEquals("Name_Value", response.getResponseBody().getName());
                    Assertions.assertEquals("Remark_Value", response.getResponseBody().getRemark());
                    Assertions.assertEquals(LocalDateTime.of(2020, 1, 12, 2, 2, 47), response.getResponseBody().getUpdateTimestamp());
                });
    }

    @Test
    public void testGetRoomInfoNotFound() {
        Mockito.doReturn(Mono.empty()).when(roomService).getRoomById(2);

        webTestClient.get()
                .uri("/rooms/2")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void testUpdateRoomStatusSuccessful() {
        Mockito.doNothing().when(roomService).updateRoomStatus(1, Status.OCCUPIED);

        webTestClient.post()
                .uri("/rooms/1/status")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(1)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    public void testUpdateRoomStatusUnexpectedStatus() {
        webTestClient.post()
                .uri("/rooms/2/status")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("ITCD")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    public void testUpdateRoomStatusFailed() {
        Mockito.doThrow(new IllegalArgumentException()).when(roomService).updateRoomStatus(2, Status.AVAILABLE);

        webTestClient.post()
                .uri("/rooms/2/status")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(0)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    public void testGetAllRoomStatusFound() {
        final RoomCurrentStatus roomCurrentStatus = new RoomCurrentStatus();
        roomCurrentStatus.setRoom(MockData.mockRoomInfo());
        roomCurrentStatus.setCurrentStatus(MockData.mockRoomStatus());

        Mockito.doReturn(Flux.just(roomCurrentStatus)).when(roomService).getRoomsWithCurrentStatus();

        webTestClient.get()
                .uri("/rooms/status")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(RoomCurrentStatus.class)
                .hasSize(1)
                .consumeWith(response -> {
                    Assertions.assertEquals(1, response.getResponseBody().get(0).getRoom().getId());
                    Assertions.assertEquals(Status.AVAILABLE, response.getResponseBody().get(0).getCurrentStatus().getStatus());
                });
    }

    @Test
    public void testGetAllRoomStatusNoContent() {
        Mockito.doReturn(Flux.empty()).when(roomService).getRoomsWithCurrentStatus();

        webTestClient.get()
                .uri("/rooms/status")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNoContent();
    }
}
