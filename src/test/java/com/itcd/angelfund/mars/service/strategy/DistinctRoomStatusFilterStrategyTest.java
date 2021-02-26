package com.itcd.angelfund.mars.service.strategy;

import com.itcd.angelfund.mars.model.base.RoomStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.List;

public class DistinctRoomStatusFilterStrategyTest extends AbstractOccupationStrategyTest {
    final DistinctRoomStatusFilterStrategy strategy2Test = new DistinctRoomStatusFilterStrategy();

    @Test
    public void testApply() {
        final List<RoomStatus> result = strategy2Test.apply(mockData);
        Assertions.assertEquals(3, result.size());
        Assertions.assertEquals(BigInteger.valueOf(1), result.get(0).getId());
        Assertions.assertEquals(BigInteger.valueOf(5), result.get(1).getId());
        Assertions.assertEquals(BigInteger.valueOf(8), result.get(2).getId());
    }
}
