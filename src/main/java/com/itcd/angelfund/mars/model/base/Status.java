package com.itcd.angelfund.mars.model.base;

public enum Status {
    OCCUPIED, AVAILABLE, UNKNOWN;

    public static Status valueOf(Integer value) {
        Status status = Status.UNKNOWN;
        if(Integer.valueOf(0).equals(value)) {
            status = Status.AVAILABLE;
        } else if(Integer.valueOf(1).equals(value)) {
            status = Status.OCCUPIED;
        }
        return status;
    }
}
