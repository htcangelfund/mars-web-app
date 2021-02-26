package com.itcd.angelfund.mars.model.combination;

import com.itcd.angelfund.mars.model.base.RoomInfo;
import com.itcd.angelfund.mars.model.base.RoomStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class RoomOccupationReport {
    private RoomInfo room;
    private BigDecimal utilizeRate;
    private List<RoomStatus> roomStatusList;
}
