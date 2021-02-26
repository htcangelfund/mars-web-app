package com.itcd.angelfund.mars.service.strategy;

import com.itcd.angelfund.mars.model.base.RoomStatus;
import com.itcd.angelfund.mars.model.base.Status;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class DistinctRoomStatusFilterStrategy {

    public List<RoomStatus> apply(final List<RoomStatus> original) {
        if(CollectionUtils.isEmpty(original)) {
            return Collections.emptyList();
        } else {
            final List<RoomStatus> distinctList = new ArrayList<>();
            final Iterator<RoomStatus> roomStatusIterator = original.iterator();
            RoomStatus currentStatus = roomStatusIterator.next();
            distinctList.add(currentStatus);
            while (roomStatusIterator.hasNext()) {
                RoomStatus nextStatus = roomStatusIterator.next();
                if(nextStatus.getStatus() != currentStatus.getStatus()) {
                    distinctList.add(nextStatus);
                }
                currentStatus = nextStatus;
            }
            if(currentStatus.getUpdateTimestamp().isBefore(LocalDateTime.now().minusMinutes(10))) {
                currentStatus.setStatus(Status.UNKNOWN);
            }
            return distinctList;
        }
    }
}
