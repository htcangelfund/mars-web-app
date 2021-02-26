package com.itcd.angelfund.mars.service.strategy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalTime;

public class OccupationRateCalculationStrategyTest extends AbstractOccupationStrategyTest {
    private OccupationRateCalculationStrategy strategy2Test = new OccupationRateCalculationStrategy(LocalTime.of(5, 0), LocalTime.of(6,0));

    @Test
    public void testApply() {
        //2 out of 8
        Assertions.assertEquals(BigDecimal.valueOf(0.25).setScale(4, RoundingMode.HALF_UP), strategy2Test.apply(mockData));
    }
}
