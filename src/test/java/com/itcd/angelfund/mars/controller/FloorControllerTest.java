package com.itcd.angelfund.mars.controller;

import com.itcd.angelfund.mars.MockData;
import com.itcd.angelfund.mars.model.combination.FloorDetail;
import com.itcd.angelfund.mars.model.base.Status;
import com.itcd.angelfund.mars.service.FloorService;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@MockBeans({@MockBean(FloorService.class)})
public class FloorControllerTest {

    @Autowired
    private FloorService floorService;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void getFloorDetails() {
        Mockito.doReturn(Flux.just(MockData.mockFloorDetail())).when(floorService).getFloors();

        webTestClient.get()
                .uri("/floors")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(FloorDetail.class).hasSize(1)
                .consumeWith(response -> {
                    Assertions.assertEquals("Floor_Value", response.getResponseBody().get(0).getFloor());
                    Assertions.assertEquals(Status.AVAILABLE, response.getResponseBody().get(0).getRooms().get(0).getCurrentStatus().getStatus());
                });
    }
}
