package com.itcd.angelfund.mars.model.base;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RoomInfo {
    private Integer id;
    private String country;
    private String city;
    private String location;
    private String floor;
    private String name;
    private String remark;
    private LocalDateTime updateTimestamp;
}
