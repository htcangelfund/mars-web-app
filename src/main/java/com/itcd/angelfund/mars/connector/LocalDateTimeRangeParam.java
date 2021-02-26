package com.itcd.angelfund.mars.connector;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LocalDateTimeRangeParam {
    private LocalDateTime from;
    private LocalDateTime to;


    public LocalDateTimeRangeParam(LocalDateTime from, LocalDateTime to) {
        this.from = from;
        this.to = to;

        if(this.from.isAfter(this.to)) {
            throw new IllegalArgumentException("Invalid date range");
        }
    }
}
