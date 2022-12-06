package com.zhangjgux.easygarage.utils;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class TimeUtils {

    public static Timestamp timeToTimestamp (String readableTime) {
        readableTime = readableTime.trim();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date dt;
        try {
            dt = df.parse(readableTime);
        } catch (ParseException e) {
            throw new RuntimeException("Wrong Format!");
        }
        Timestamp timestamp = new Timestamp(dt.getTime());
        return timestamp;
    }

    public static boolean timeEqual(Timestamp a, Timestamp b) {
        if (a.getTime() == b.getTime()) {
            return true;
        }
        return false;
    }
}
