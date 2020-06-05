/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.core.src4.utils;

import java.util.Calendar;
import java.util.Date;

import pasa.cbentley.core.src4.ctx.UCtx;

/**
 * 
 * This class is for those unlucky people stuck without LocalDate class.
 * This is assumed for all src4 modules.
 * <br>
 * 
 * une minute =      60 000 millis
 * une heure =      360 000 millis
 * un jour =      8 640 000 millis
 * un an      3 153 600 000
 * 
 * To generate current day?
 * 
 * @author cbentley
 *
 */
public class DateUtils {

   public static final long MILLIS_IN_A_DAY     = 86400000;

   public static final long MILLIS_IN_A_HOUR    = 3600000;

   public static final long MILLIS_IN_A_MINUTE  = 60000;

   public static final int  MINUTES_IN_A_DAY    = 60 * 24;

   public static final int  MINUTES_IN_A_MONTH  = 60 * 24 * 31;

   public static final int  MINUTES_IN_A_YEAR   = 60 * 24 * 31 * 12;

   public static final int  MINUTES_IN_A_YEAR_2 = 60 * 24 * 31 * 12 * 2;

   public static final int  MINUTES_IN_A_YEAR_3 = 60 * 24 * 31 * 12 * 3;

   private final UCtx       uc;

   public DateUtils(UCtx uc) {
      this.uc = uc;
   }

   public static String getPeriodDayHourMin(long inter) {
      if (inter == 0)
         return "0";

      long days = inter / (24 * MILLIS_IN_A_HOUR);
      long h = inter / MILLIS_IN_A_HOUR;
      long left = inter % MILLIS_IN_A_HOUR;
      long min = left / 60000;

      String s = "";
      if (h != 0) {
         if (min == 0) {
            s = h + "h ";
         } else {
            s = h + "h " + min;
         }
      } else {
         s = min + " mins";
      }
      if (days > 2) {
         s += "(" + days + " days)";
      }
      return s;
   }

   /**
    * Compute Nice string for a period of time in milliseconds.
    * Granularity is seconds/minutes with hours.
    * <br>
    * <br>
    * 
    * @param inter
    * @return
    */
   public static String getPeriodHourMinSec(long inter) {
      if (inter == 0)
         return "0";
      if (inter < 60000) {
         return (inter / 1000) + " secs";
      }
      long h = inter / MILLIS_IN_A_HOUR;
      long left = inter % MILLIS_IN_A_HOUR;
      long min = left / 60000;
      if (h != 0) {
         if (min == 0) {
            return h + "h ";
         } else {
            return h + "h " + min;
         }
      } else {
         return min + " mins";
      }
   }

   /**
    * string with only day month and year
    * @param date
    * @return
    */
   public static String formatDate(long date) {
      String s = new Date(date).toString();
      int lspace = s.lastIndexOf(' ');
      int firstspace = s.indexOf(' ');
      int secondSpace = s.indexOf(' ', firstspace + 1);
      int thirdSpace = s.indexOf(' ', secondSpace + 1);
      return s.substring(firstspace, thirdSpace + 1) + s.substring(lspace);
   }

   /**
    * A minute date.
    * @param date
    * @return
    */
   public static String getDslashMslashYsmall(int date) {
      Calendar cal = Calendar.getInstance();
      cal.setTime(new Date(getLongDateFromDateMin(date)));
      String s = String.valueOf(cal.get(Calendar.DAY_OF_MONTH)) + "/";
      s = s + String.valueOf(cal.get(Calendar.MONTH) + 1) + "/";
      s = s + String.valueOf(cal.get(Calendar.YEAR) - 2000);
      return s;
   }

   /**
    * Creates a Date added with minutes
    * @param past
    * @param minutes
    * @return
    */
   public static long fromMinutes(long past, int minutes) {
      long date = past + (minutes * 60000);
      return date;
   }

   /**
    * Input is a date formatted 1/2/2007 or 1/2/07 or 1/2/7
    *<br>
    *<br>
    * Does NOT support 1/8/99 for 1/8/1999 but will be 1/8/2099
    * <br>
    * <br>
    * @param date
    * @return the long date + 1 hour, 1 minute and 1 second
    */
   public static long getDateFromDslashMslashY(String date) {
      int index = date.indexOf('/');
      String days = date.substring(0, index);
      int day = Integer.parseInt(days);
      int mindex = date.indexOf('/', index + 1);
      String month = date.substring(index + 1, mindex);
      int mon = Integer.parseInt(month);
      String year = date.substring(mindex + 1);
      int iyear = Integer.parseInt(year);
      if (iyear < 2000) {
         iyear = iyear + 2000;
      }
      Calendar cal = Calendar.getInstance();
      //reset the calendar
      cal.set(Calendar.HOUR, 6);
      cal.set(Calendar.MILLISECOND, 0);
      cal.set(Calendar.SECOND, 0);
      cal.set(Calendar.MINUTE, 0);
      cal.set(Calendar.DAY_OF_MONTH, day);
      cal.set(Calendar.MONTH, mon - 1);
      cal.set(Calendar.YEAR, iyear);
      return cal.getTime().getTime();
   }

   /**
    * the number of human days between past and future
    * so if past is 10/2/2006 and future is 12/2/2006
    * method returns 2
    * @param past
    * @param future
    * @return negative if future is the past, and past the future
    */
   public static int getDays(long past, long future) {
      past = trimToDay(past);
      future = trimToDay(future);
      long interval = future - past;
      int numberOfDays = (int) (interval / DateUtils.MILLIS_IN_A_DAY);
      return numberOfDays;
   }

   /**
    * Gets a long date which is now by days number in the past.
    * <br>
    * <br>
    * 
    * @param daysFromNow
    * @return
    */
   public long getDaysFromNow(long now, int daysFromNow) {
      return now - (daysFromNow * MILLIS_IN_A_DAY);
   }

   /**
    * The number of days in this month
    * <br>
    * @param date
    * @return
    */
   public static int getDaysInMonth(long date) {
      Calendar cal = Calendar.getInstance();
      cal.setTime(new Date(date));
      switch (cal.get(Calendar.MONTH)) {
         case Calendar.FEBRUARY: {
            if (isLeapYear(cal.get(Calendar.YEAR)))
               return 29;
            else
               return 28;
         }
         case Calendar.APRIL:
            return 30;
         case Calendar.JUNE:
            return 30;
         case Calendar.SEPTEMBER:
            return 30;
         case Calendar.NOVEMBER:
            return 30;
      }
      return 31;
   }

   /**
    * Returns a nice string expressing the number of days or minutes between date in the past
    * and now
    * @param date
    * @param now
    * @return
    */
   public static String getDaysNazad(long date, long now) {
      long diff = now - date;
      if (diff < MILLIS_IN_A_DAY) {
         int hour = 0;
         while ((diff = diff - MILLIS_IN_A_HOUR) > MILLIS_IN_A_HOUR) {
            hour++;
         }
         int min = 0;
         while ((diff = diff - MILLIS_IN_A_MINUTE) > MILLIS_IN_A_MINUTE) {
            min++;
         }
         String s = getNumber(hour, "hour");
         if (s != "")
            s += " & ";
         return s + getNumber(min, "minute");

      }
      int ndays = (int) (diff / MILLIS_IN_A_DAY);
      if (60 < ndays) {
         int year = 0;
         while (ndays > 356) {
            year++;
            ndays -= 356;
         }
         int month = 0;
         while (ndays > 31) {
            month++;
            ndays -= 31;
         }
         String s = "";
         if (year == 1) {
            s += "1 year";
         }
         if (year > 1) {
            s += year + " years";
         }
         boolean hasmonth = true;
         String smonth = "";
         if (month == 0) {
            hasmonth = false;
         } else if (month == 1)
            smonth += "1 month";
         else
            smonth += month + " months";

         if (hasmonth) {
            if (year == 0)
               return smonth + " ago";
            return s += " & " + smonth + " ago";
         } else {
            return s + " ago";
         }
      } else {
         if (ndays == 1)
            return "yesterday";
         if (ndays == 0)
            return "today";
         else
            return ndays + " days ago";
      }
   }

   public static String getDslashMslashYFromDay(int intDateDay) {
      return getDslashMslashY(getLongDateFromDateDay(intDateDay));
   }

   public static String getDslashMslashYFromMin(int intDateMin) {
      return getDslashMslashY(getLongDateFromDateMin(intDateMin));
   }

   public static String getDslashMslashYslashHourslashMinFromMin(int intDateMin) {
      return getDslashMslashYslashHourslashMin(getLongDateFromDateMin(intDateMin));
   }

   /**
    * Gets 21/12/2012 template
    * <br>
    * <br>
    * 
    * @param date
    * @return
    */
   public static String getDslashMslashY(long date) {
      Calendar cal = Calendar.getInstance();
      cal.setTime(new Date(date));
      String s = String.valueOf(cal.get(Calendar.DAY_OF_MONTH)) + "/";
      s = s + String.valueOf(cal.get(Calendar.MONTH) + 1) + "/";
      s = s + String.valueOf(cal.get(Calendar.YEAR));
      return s;
   }

   public static String getDslashMslashYslashHourslashMin(long date) {
      Calendar cal = Calendar.getInstance();
      cal.setTime(new Date(date));
      String s = String.valueOf(cal.get(Calendar.DAY_OF_MONTH)) + "/";
      s = s + String.valueOf(cal.get(Calendar.MONTH) + 1) + "/";
      s = s + String.valueOf(cal.get(Calendar.YEAR)) + "/";
      s = s + String.valueOf(cal.get(Calendar.HOUR)) + "/";
      s = s + String.valueOf(cal.get(Calendar.MINUTE));

      return s;
   }

   /**
    * the last day of the month for that dat
    * <br>
    * 
    * @param date
    * @return
    */
   public static long getEndOfMonthDay(long date) {
      Calendar cal = Calendar.getInstance();
      cal.setTime(new Date(date));
      int day = cal.get(Calendar.DAY_OF_MONTH);
      int lastday = getDaysInMonth(date);
      long newd = date + (lastday - day) * MILLIS_IN_A_DAY;
      return newd;
   }

   public static long getEndOfWeekDay(long date) {
      Calendar cal = Calendar.getInstance();
      cal.setTime(new Date(date));
      int dw = cal.get(Calendar.DAY_OF_WEEK);
      int add = 0;
      switch (dw) {
         case Calendar.SUNDAY:
            break;
         case Calendar.TUESDAY:
            add = 5;
            break;
         case Calendar.WEDNESDAY:
            add = 4;
            break;
         case Calendar.THURSDAY:
            add = 3;
            break;
         case Calendar.FRIDAY:
            add = 2;
            break;
         case Calendar.SATURDAY:
            add = 1;
            break;
         case Calendar.MONDAY:
            add = 6;
            break;
      }
      return date + (add * MILLIS_IN_A_DAY);
   }

   public static long getEndOfYearDay(long date) {
      Calendar cal = Calendar.getInstance();
      cal.setTime(new Date(date));
      int year = cal.get(Calendar.YEAR);
      Calendar ncal = Calendar.getInstance();
      ncal.set(Calendar.YEAR, year);
      ncal.set(Calendar.MONTH, Calendar.DECEMBER);
      ncal.set(Calendar.DAY_OF_MONTH, 31);
      return ncal.getTime().getTime();
   }

   /**
    * 
    * @param year
    * @param month [0-11] based
    * @param day
    * @param hour
    * @param minute
    * @return
    */
   public static int getIntDate(int year, int month, int day, int hour, int minute) {
      Calendar cal = Calendar.getInstance();
      cal.set(Calendar.YEAR, year);
      cal.set(Calendar.MONTH, month);
      cal.set(Calendar.DAY_OF_MONTH, day);
      cal.set(Calendar.HOUR, hour);
      cal.set(Calendar.MINUTE, minute);
      return getIntDateMinute(cal.getTime().getTime());
   }

   /**
    * Shortens a long date by removing milliseconds and seconds. keeping only minutes.
    * <br>
    * <br>
    * 
    * @param date
    * @return
    * @throw RuntimeException if long cannot fit int
    */
   public static int getIntDateMinute(long date) {
      long d = (int) (date / MILLIS_IN_A_MINUTE);
      int md = (int) d;
      if (d == md)
         return md;
      else
         throw new RuntimeException("Date Truncation Failed");
   }

   /**
    * Number of days since computer reference date.
    * <br>
    * <br>
    * 
    * @param intDate minute date
    * @return
    */
   public static int getIntDateDayFromMin(int intDate) {
      int d = (int) (intDate / MINUTES_IN_A_DAY);
      int modulo = intDate % MINUTES_IN_A_DAY;
      return d;
   }

   /**
    * returns a Date with a DAY granularity
    * @param date
    * @return
    */
   public static int getIntDateDayFromLong(long date) {
      int d = (int) (date / MILLIS_IN_A_DAY);
      return d;
   }

   public static int getIntDateMinFromDayDate(int dayDate) {
      return dayDate * MINUTES_IN_A_DAY;
   }

   public static long getLongDateFromDateMin(int date) {
      return ((long) (date)) * MILLIS_IN_A_MINUTE;
   }

   public static long getLongDateFromDateDay(int dayDate) {
      return ((long) (dayDate)) * MILLIS_IN_A_DAY;
   }

   /**
    * Returns the number of minutes between two milli dates.
    * <br>
    * <br>
    * 
    * @param past
    * @param future
    * @return
    */
   public static int getMinutes(long past, long future) {
      return (int) (future - past) / 60000;
   }

   public static String getMonth(int v) {
      switch (v) {
         case Calendar.JANUARY:
            return "January";
         case Calendar.FEBRUARY:
            return "February";
         case Calendar.MARCH:
            return "March";
         case Calendar.APRIL:
            return "April";
         case Calendar.MAY:
            return "May";
         case Calendar.JUNE:
            return "June";
         case Calendar.JULY:
            return "July";
         case Calendar.AUGUST:
            return "August";
         case Calendar.SEPTEMBER:
            return "September";
         case Calendar.OCTOBER:
            return "October";
         case Calendar.NOVEMBER:
            return "November";
         case Calendar.DECEMBER:
            return "December";

         default:
            return "null";
      }
   }

   /**
    * interprets the value for a month
    * @param value
    * @return -1 if impossible, 1 for January, 12 for December
    */
   public int getMonth(String value) {
      StringUtils stru = uc.getStrU();
      if (stru.isSimilar("january", value))
         return 1;
      else if (stru.isSimilar("february", value))
         return 2;
      else if (stru.isSimilar("march", value))
         return 3;
      else if (stru.isSimilar("april", value))
         return 4;
      else if (stru.isSimilar("may", value))
         return 5;
      else if (stru.isSimilar("june", value))
         return 6;
      else if (stru.isSimilar("july", value))
         return 7;
      else if (stru.isSimilar("august", value))
         return 8;
      else if (stru.isSimilar("september", value))
         return 9;
      else if (stru.isSimilar("october", value))
         return 10;
      else if (stru.isSimilar("november", value))
         return 11;
      else if (stru.isSimilar("december", value))
         return 12;
      return -1;
   }

   private static String getNumber(int num, String msg) {
      switch (num) {
         case 0:
            return "";
         case 1:
            return "1 " + msg;
         default:
            return num + " " + msg + "s";
      }
   }

   public static String getWeekDay(int v) {
      switch (v) {
         case Calendar.SUNDAY:
            return "Sunday";
         case Calendar.TUESDAY:
            return "Tuesday";
         case Calendar.WEDNESDAY:
            return "Wednesday";
         case Calendar.THURSDAY:
            return "Thursday";
         case Calendar.FRIDAY:
            return "Friday";
         case Calendar.SATURDAY:
            return "Saturday";
         case Calendar.MONDAY:
            return "Monday";
         default:
            return "null";
      }
   }

   public static boolean isLeapYear(int yr) {
      return (yr % 400 == 0) || ((yr % 4 == 0) && (yr % 100 != 0));
   }

   public static boolean isSameMonth(long l, long l2) {
      Calendar cal1 = Calendar.getInstance();
      cal1.setTime(new Date(l));
      Calendar cal2 = Calendar.getInstance();
      cal2.setTime(new Date(l2));
      return cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
   }

   public static boolean isSameWeek(long l, long l2) {
      Calendar cal1 = Calendar.getInstance();
      cal1.setTime(new Date(l));
      Calendar cal2 = Calendar.getInstance();
      cal2.setTime(new Date(l2));
      if (cal1.get(Calendar.YEAR) != cal2.get(Calendar.YEAR))
         return false;
      if (cal1.get(Calendar.MONTH) != cal2.get(Calendar.MONTH))
         return false;
      int day1 = cal1.get(Calendar.DAY_OF_MONTH);
      int day2 = cal2.get(Calendar.DAY_OF_MONTH);
      if (day1 == day2)
         return true;
      else {
         int diff = day1 - day2;
         if (diff > 0) {
            if (diff > 7) {
               return false;
            } else {
               return true;
            }
         }
      }
      return false;
   }

   public static boolean isSameYear(long l, long l2) {
      Calendar cal1 = Calendar.getInstance();
      cal1.setTime(new Date(l));
      Calendar cal2 = Calendar.getInstance();
      cal2.setTime(new Date(l2));
      return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
   }

   /**
    * Set Hours, Minutes and Seconds to zero 
    */
   public static long trimToDay(long date) {
      Calendar cal = Calendar.getInstance();
      cal.setTime(new Date(date));
      cal.set(Calendar.MILLISECOND, 0);
      cal.set(Calendar.SECOND, 0);
      cal.set(Calendar.MINUTE, 0);
      cal.set(Calendar.HOUR, 0);
      return cal.getTime().getTime();
   }

   public static long trimToHourMinSec(long date) {
      Calendar cal = Calendar.getInstance();
      cal.setTime(new Date(date));
      cal.set(Calendar.MONTH, 0);
      cal.set(Calendar.YEAR, 0);

      return cal.getTime().getTime();
   }

   /**
    * Returns only the seconds and deciseconds as in
    * <br>
    * <br>
    * @param data
    * @return
    */
   public static int getIntSecDeci(long data) {
      return (int) (data / 100);
   }

   /**
    * String such as 11,4 seconds.
    * <br>
    * <br>
    * When param is 1001, that's 100 seconds and 1 deci 
    * @param decisecs
    * @return
    */
   public static String getPeriodSecDeci(int decisecs) {
      String val = String.valueOf(decisecs);
      if (val.length() == 0) {
         return "0";
      } else if (val.length() == 1) {
         return val;
      } else {

      }
      String deci = val.substring(val.length() - 1, val.length());
      String secs = val.substring(0, val.length() - 1);
      return secs + "," + deci;
   }

   /**
    * Input is milliseconds.
    * 
    * @param diff
    * @return
    */
   public static int getIntMinSec(long diff) {
      int seconds = (int) diff / 1000;
      return seconds;
   }
}
