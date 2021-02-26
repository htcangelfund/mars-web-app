package com.itcd.angelfund.mars.delegate;

import com.itcd.angelfund.mars.connector.FloorDao;
import com.itcd.angelfund.mars.model.combination.FloorDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class FloorDelegate {

    @Autowired
    private FloorDao floorDao;

    public Flux<FloorDetail> getFloors() {
        List<FloorDetail> floorDetailList = floorDao.getFloors();
        return CollectionUtils.isEmpty(floorDetailList) ? Flux.empty() : Flux.fromIterable(floorDetailList);
    }

    public Mono<FloorDetail> getFloorById(final String id) {
        FloorDetail floorDetail = floorDao.getFloorById(id);
        return Objects.isNull(floorDetail) ? Mono.empty() : Mono.just(floorDetail);
    }
}
