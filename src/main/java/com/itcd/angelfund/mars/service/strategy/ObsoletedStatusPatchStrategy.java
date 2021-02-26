package com.itcd.angelfund.mars.service.strategy;

import com.itcd.angelfund.mars.model.base.RoomStatus;
import com.itcd.angelfund.mars.model.base.Status;
import com.itcd.angelfund.mars.model.combination.RoomCurrentStatus;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
public class ObsoletedStatusPatchStrategy {

    public RoomStatus apply(final RoomCurrentStatus originalRoomCurrentStatus) {
        if(originalRoomCurrentStatus.getCurrentStatus().getUpdateTimestamp().isBefore(LocalDateTime.now().minusHours(1))) {
            log.warn("Room [{}] has a far too early lastUpdateStatus[{}] compare to current time [{}], setting status to UNKNOWN",
                    originalRoomCurrentStatus.getRoom().getId(),
                    originalRoomCurrentStatus.getCurrentStatus().getUpdateTimestamp(),
                    LocalDateTime.now()
            );
            originalRoomCurrentStatus.getCurrentStatus().setStatus(Status.UNKNOWN);
        }
        return originalRoomCurrentStatus.getCurrentStatus();
    }
}
