package com.itcd.angelfund.mars.model.base;

import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
public class RoomStatus {
    private BigInteger id;
    private Integer roomId;
    private Status status;
    private LocalDateTime updateTimestamp;
}
