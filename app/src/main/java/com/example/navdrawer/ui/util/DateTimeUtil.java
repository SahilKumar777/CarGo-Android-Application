package com.example.navdrawer.ui.util;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtil {
     static public String dateToStrDate(Date d){
         SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy", Locale.ENGLISH);
         return sdf.format(d.clone());
     }

     static public String dateToStrTime(Date d){

         return (new SimpleDateFormat("hh:mm a", Locale.ENGLISH)).format(d.clone());
     }

     static public Date strToDate(String d,String t){
         SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy hh:mm a", Locale.ENGLISH);
         return sdf.parse(d+" "+t, new ParsePosition(0));

     }

     static public Date addHrsToDate(Date date, int hours) {
         Calendar calendar = Calendar.getInstance();
         calendar.setTime(date);
         calendar.add(Calendar.HOUR_OF_DAY, hours);
         return calendar.getTime();
     }
}
