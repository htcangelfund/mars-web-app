package com.itcd.angelfund.mars.model.combination;

import lombok.Data;

import java.util.List;

@Data
public class LocationDetail {
    private String country;
    private String city;
    private String location;
    private List<RoomCurrentStatus> rooms;
}
