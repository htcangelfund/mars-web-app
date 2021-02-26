package com.itcd.angelfund.mars.model.combination;

import com.itcd.angelfund.mars.model.base.RoomInfo;
import com.itcd.angelfund.mars.model.base.RoomStatus;
import lombok.Data;

import java.util.List;

@Data
public class RoomHistoricalStatus {
    private RoomInfo room;
    private List<RoomStatus> roomStatusList;
}
