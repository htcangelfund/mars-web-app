package com.itcd.angelfund.mars.model.combination;

import com.itcd.angelfund.mars.model.base.RoomInfo;
import com.itcd.angelfund.mars.model.base.RoomStatus;
import lombok.Data;

@Data
public class RoomCurrentStatus {
    private RoomInfo room;
    private RoomStatus currentStatus;
}
