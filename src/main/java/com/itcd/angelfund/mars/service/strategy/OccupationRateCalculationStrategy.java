package com.itcd.angelfund.mars.service.strategy;

import com.itcd.angelfund.mars.model.base.RoomStatus;
import com.itcd.angelfund.mars.model.base.Status;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalTime;
import java.util.List;

public class OccupationRateCalculationStrategy {
    final private LocalTime officeHourFrom;
    final private LocalTime officeHourTo;

    public OccupationRateCalculationStrategy(final LocalTime officeHourFrom, final LocalTime officeHourTo) {
        this.officeHourFrom = officeHourFrom;
        this.officeHourTo = officeHourTo;
    }

    public BigDecimal apply(final List<RoomStatus> roomStatusList) {
        if (CollectionUtils.isEmpty(roomStatusList)) {
            return null;
        } else {
            final Long occupationCount = roomStatusList
                    .stream()
                    .filter(roomStatus ->
                            Status.OCCUPIED.equals(roomStatus.getStatus())
                                    && roomStatus.getUpdateTimestamp().toLocalTime().isAfter(officeHourFrom)
                                    && roomStatus.getUpdateTimestamp().toLocalTime().isBefore(officeHourTo))
                    .count();
            return BigDecimal.valueOf(occupationCount).divide(BigDecimal.valueOf(roomStatusList.size()), 4, RoundingMode.HALF_EVEN);
        }
    }
}
