package com.itcd.angelfund.mars.service.strategy;

import com.itcd.angelfund.mars.model.base.RoomStatus;
import com.itcd.angelfund.mars.model.base.Status;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class CurrentOccupationRateCalculationStrategy {

    public BigDecimal apply(final List<RoomStatus> roomStatusList) {
        if (CollectionUtils.isEmpty(roomStatusList)) {
            return null;
        } else {
            final Long occupationCount = roomStatusList
                    .stream()
                    .filter(roomStatus ->
                            Status.OCCUPIED.equals(roomStatus.getStatus()))
                    .count();
            return BigDecimal.valueOf(occupationCount).divide(BigDecimal.valueOf(roomStatusList.size()), 4, RoundingMode.HALF_EVEN);
        }
    }
}
