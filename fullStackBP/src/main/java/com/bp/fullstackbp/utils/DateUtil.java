package com.bp.fullstackbp.utils;

import org.apache.commons.lang3.time.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
  private static final String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";
  private static final String SIMPLE_DATE_FORMAT = "dd/MM/yyyy";

  private static final DateFormat df = new SimpleDateFormat(DATE_FORMAT);


  public static Date parseDate(String date) throws ParseException {
    try {
      return DateUtils.parseDate(date, DATE_FORMAT);
    } catch (ParseException e) {
      return DateUtils.parseDate(date, SIMPLE_DATE_FORMAT);
    }

  }

  public static String formatDate(Date date) {
    return df.format(date);
  }

}
