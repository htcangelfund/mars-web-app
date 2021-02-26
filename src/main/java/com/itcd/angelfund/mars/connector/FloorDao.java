package com.itcd.angelfund.mars.connector;

import com.itcd.angelfund.mars.model.combination.FloorDetail;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FloorDao {
    List<FloorDetail> getFloors();
    FloorDetail getFloorById(String id);
}
