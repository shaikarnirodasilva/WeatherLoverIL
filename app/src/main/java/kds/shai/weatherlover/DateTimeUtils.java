package kds.shai.weatherlover;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {
    //https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html
    public static String getTime(Date d) {
        SimpleDateFormat f = new SimpleDateFormat("hh:mm a");
        String s = f.format(d);
        return s;
    }

}
