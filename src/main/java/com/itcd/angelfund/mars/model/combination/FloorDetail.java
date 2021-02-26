package com.itcd.angelfund.mars.model.combination;

import lombok.Data;

import java.util.List;

@Data
public class FloorDetail {
    private String country;
    private String city;
    private String location;
    private String floor;
    private List<RoomCurrentStatus> rooms;
}
