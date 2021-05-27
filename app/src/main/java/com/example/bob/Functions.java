package com.example.bob;

import java.util.Calendar;

public class Functions {
    public static String wishMe() {
        String s = "";
        Calendar c = Calendar.getInstance();
        int time = c.get(Calendar.HOUR_OF_DAY);

        if (time >= 0 && time < 4) {
            s = "Good Morning, I think it's too early, better have some rest";
        } else if (time >= 4 && time <12){
            s = "Good Morning";
        } else if (time >= 12 && time < 16) {
            s = "Good Afternoon";
        } else if (time >= 16 && time < 22) {
            s = "Good Evening";
        } else if (time >= 22 && time < 24) {
            s = "Good evening, I think it's too late buddy... take rest";
        }

        return s;
    }
}
