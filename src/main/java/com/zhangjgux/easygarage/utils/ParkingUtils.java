package com.zhangjgux.easygarage.utils;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class ParkingUtils {

    public double getCost(Timestamp begin, Timestamp end, double normalPrice, double latePrice) {
        long diff = end.getTime() - begin.getTime();
        if (diff / 3600 <= 5) {
            return diff / 3600 * normalPrice;
        }
        return (diff - 5 * 3600) * latePrice + 5 * 3600 * normalPrice;
    }
}
