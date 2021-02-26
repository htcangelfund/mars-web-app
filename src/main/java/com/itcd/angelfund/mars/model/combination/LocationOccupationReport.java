package com.itcd.angelfund.mars.model.combination;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LocationOccupationReport {
    private LocationDetail locationDetail;
    private BigDecimal utilizeRate;
}
