package com.bw.movie.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author : FangShiKang
 * email : fangshikang@outlook.com
 * desc :       时间转换工具类
 */
public class DateUtils {

    private static SimpleDateFormat mSimpleDateFormat = null;

    //获取系统时间
    public static String getCurrentDate() {
        Date d = new Date();
        mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return mSimpleDateFormat.format(d);
    }

    /*时间戳转换成字符窜*/
    public static String getDateToString(long time) {
        Date d = new Date(time);
        mSimpleDateFormat = new SimpleDateFormat("MM-dd", Locale.getDefault());
        return mSimpleDateFormat.format(d);
    }
    /*时间戳转换成字符窜*/
    public static String getDateToStrings(long time) {
        Date d = new Date(time);
        mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return mSimpleDateFormat.format(d);
    }

    /*将字符串转为时间戳*/
    public static long getStringToDate(String time) {
        mSimpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒SSS毫秒", Locale.getDefault());
        Date date = new Date();
        try {
            date = mSimpleDateFormat.parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }
}