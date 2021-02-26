package com.itcd.angelfund.mars.service.strategy;

import com.itcd.angelfund.mars.model.base.RoomStatus;
import com.itcd.angelfund.mars.model.base.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
public class UnknownStatusPatchStrategy {
    final private LocalDateTime startFrom;
    final private LocalDateTime endAt;
    final private int intervalInMinutes;

    public UnknownStatusPatchStrategy(
            final LocalDateTime startFrom,
            final LocalDateTime endAt,
            final int intervalInMinutes) {
        this.startFrom = startFrom;
        this.endAt = endAt;
        this.intervalInMinutes = intervalInMinutes;
    }

    public List<RoomStatus> apply(final List<RoomStatus> original) {
        if(CollectionUtils.isEmpty(original)) {
            return Collections.emptyList();
        } else {
            final LocalDateTime currentTime = LocalDateTime.now();
            final LinkedList<RoomStatus> revisedList = new LinkedList<>(original);

            final RoomStatus firstStatus = revisedList.getFirst();
            if(startFrom.plusMinutes(intervalInMinutes).isBefore(firstStatus.getUpdateTimestamp())) {
                final RoomStatus unknownStatusBeforeAll = new RoomStatus();
                unknownStatusBeforeAll.setId(BigInteger.ZERO);
                unknownStatusBeforeAll.setRoomId(firstStatus.getRoomId());
                unknownStatusBeforeAll.setStatus(Status.UNKNOWN);
                unknownStatusBeforeAll.setUpdateTimestamp(startFrom);
                revisedList.addFirst(unknownStatusBeforeAll);
                log.warn("lack of first status, putting unknown, start time[{}] is early than first status update time [{}]",
                        startFrom, firstStatus.getUpdateTimestamp());
            }

            final RoomStatus lastStatus = revisedList.getLast();
            if(endAt.minusMinutes(intervalInMinutes).isAfter(lastStatus.getUpdateTimestamp())) {
                final RoomStatus unknownStatusAfterAll = new RoomStatus();
                unknownStatusAfterAll.setId(BigInteger.ZERO);
                unknownStatusAfterAll.setRoomId(lastStatus.getRoomId());
                unknownStatusAfterAll.setStatus(Status.UNKNOWN);
                unknownStatusAfterAll.setUpdateTimestamp(lastStatus.getUpdateTimestamp());
                revisedList.add(unknownStatusAfterAll);
                log.warn("lack of last status, putting unknown, end time[{}] is later than last status update time [{}] minus interval [{}] minutes",
                        endAt, lastStatus.getUpdateTimestamp(), intervalInMinutes);
            }

            if(endAt.isAfter(currentTime)) {
                final RoomStatus futureUnknownStatus = new RoomStatus();
                futureUnknownStatus.setId(BigInteger.ZERO);
                futureUnknownStatus.setRoomId(lastStatus.getRoomId());
                futureUnknownStatus.setStatus(Status.UNKNOWN);
                futureUnknownStatus.setUpdateTimestamp(currentTime);
                revisedList.add(futureUnknownStatus);
                log.warn("end time [{}] is after current time [{}]", endAt, currentTime);
            }

            return revisedList;
        }
    }
}
