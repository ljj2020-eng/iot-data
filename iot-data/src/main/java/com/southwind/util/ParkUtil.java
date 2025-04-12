package com.southwind.util;

import cn.hutool.core.date.DateTime;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ParkUtil {

    public static String getNumber(String faceBase){
        String number = "";
        switch (faceBase){
            case "/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDAAMCAgMCAgMDAwMEAwMEBQgFBQQE":
                number = "冀FQ019T";
                break;
            case "/9j/4AAQSkZJRgABAQEAYABgAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsL":
                number = "浙C39BN0";
                break;
            case "/9j/4AAQSkZJRgABAgEAAQABAAD/4RhVRXhpZgAATU0AKgAAAAgABwESAAMA":
                number = "苏E9761T";
                break;
        }
        return number;
    }

    public static Map<String,Integer> parkPay(Date inTime,Date outTime,Integer freeDuration,Integer chargePrice,Integer maxCharge){
        long time = outTime.getTime() - inTime.getTime();
        long minutes = TimeUnit.MILLISECONDS.toMinutes(time);
        if(minutes <= freeDuration){
            return null;
        }
        long hour = minutes / 60;
        if(minutes % 60 > 0) hour++;
        int amount = (int) hour*chargePrice;
        if(amount > maxCharge) amount = maxCharge;
        Map<String,Integer> map = new HashMap<>();
        map.put("hour", (int)hour);
        map.put("amount", amount);
        return map;
    }

    public static void main(String[] args) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = format.parse("2023-12-16 12:05:57");
        Date date2 = format.parse("2023-12-16 13:06:16");
        parkPay(date1, date2, 30, 3,100);
    }
}
