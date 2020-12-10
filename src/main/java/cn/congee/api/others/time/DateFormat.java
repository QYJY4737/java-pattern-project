package cn.congee.api.others.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 * @Author: yang
 * @Date: 2020-12-11 6:56
 */
public class DateFormat {

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 日期格式转时间戳
     * 1607642404209
     * @param date
     * @return
     */
    public static long dateToTimeStamp(Date date){
        try{
            String dateTime = "2021-01-01 00:00:00";
            date = date != null ? date : simpleDateFormat.parse(dateTime);
            long time = date.getTime();
            System.out.println("日期格式转时间戳: " + time);
            return time;
        }catch(ParseException pe){
            pe.printStackTrace();
        }
        return Long.valueOf(null);
    }

    /**
     * 时间戳转为日期格式
     * 2020-12-11 07:20:04
     * @param timestamp
     * @return
     */
    public static Date timestampToDate(String timestamp){
        timestamp = timestamp != null ? timestamp : "1606752000000";
        long time = Long.parseLong(timestamp);
        Date date = new Date();
        date.setTime(time);
        System.out.println("时间戳转为日期格式: " + simpleDateFormat.format(date));
        return date;
    }

    /**
     * 获取当前时间的日期(年月日)
     * 2020-12-11
     * @return
     */
    public static LocalDate getCurrentDate(){
        return LocalDateTime.now().toLocalDate();
        //return LocalDate.of(2021, Month.JANUARY, 1);
    }


    /**
     * 获取当前时间的日期(时分秒)
     * 23:59:59
     * @return
     */
    public static LocalTime getCurrentTime(int hour, int minute, int second){
        return LocalTime.of(hour, minute, second);
    }

    public static void main(String[] args) {
        //long timeStamp = dateToTimeStamp(new Date());
        //timestampToDate(String.valueOf(timeStamp));
        //getLocalDateTime();
        LocalTime currentTime = getCurrentTime(23, 59, 59);
        System.out.println(currentTime);
    }
}
