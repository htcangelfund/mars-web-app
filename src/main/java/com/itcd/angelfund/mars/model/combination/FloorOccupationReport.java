package com.itcd.angelfund.mars.model.combination;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FloorOccupationReport {
    private FloorDetail floorDetail;
    private BigDecimal utilizeRate;
}
