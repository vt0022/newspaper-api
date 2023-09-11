package com.intern.newspaper_api.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimestampConversion {
    public String convert(String s) {
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            String[] parts = s.split(" GMT\\+\\d+");
            Date parsedDate = inputDateFormat.parse(parts[0]);
            s = outputDateFormat.format(parsedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return s;
    }
}
