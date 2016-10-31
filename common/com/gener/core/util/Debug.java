package com.gener.core.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.logging.Log;

public final class Debug
{
  private static final int toStringFormatWidth = 16;

  private static String formatWithSpaces(String s)
  {
    StringBuffer sb = new StringBuffer(s);
    if (s.length() < 16) {
      for (int i = 0; i < 16 - s.length(); i++) {
        sb.append(" ");
      }
      return sb.toString();
    }

    return sb.substring(0, 16);
  }

  private static boolean isNull(List results, int row, String columnName)
  {
    Object o = getObject(results, row, columnName);
    return o == null;
  }

  private static Object getObject(List results, int irow, String columnName)
  {
    if (irow >= results.size()) {
      return null;
    }
    Map row = (HashMap)results.get(irow);
    return row.get(columnName.toLowerCase());
  }

  private static String getString(List results, int row, String columnName)
  {
    Object o = getObject(results, row, columnName);
    if (o == null) {
      return "";
    }
    if ((o instanceof BigDecimal)) {
      BigDecimal b = (BigDecimal)o;
      return b.toString();
    }
    if ((o instanceof Integer)) {
      return ((Integer)o).toString();
    }

    String s = (String)o;
    return s;
  }

  public static String toString(List results)
  {
    int size = results.size();
    if (size < 1) {
      return "Empty!";
    }
    StringBuffer out = new StringBuffer("\n");
    Map tmpRow = (HashMap)results.get(0);
    Iterator it = tmpRow.keySet().iterator();
    List columnNames = new ArrayList();
    while (it.hasNext()) {
      columnNames.add(it.next());
    }
    for (int col = 0; col < columnNames.size(); col++) {
      String formattedColName = formatWithSpaces((String)columnNames.get(col));
      out.append(formattedColName);
    }
    out.deleteCharAt(out.length() - 2);
    int len = out.length();
    out.append("\n");
    for (int i = 0; i < len - 1; i++) {
      out.append("-");
    }
    out.append("\n");
    for (int irow = 0; irow < size; irow++) {
      for (int col = 0; col < columnNames.size(); col++) {
        String formattedColName = null;
        String columnName = (String)columnNames.get(col);
        if (isNull(results, irow, columnName)) {
          formattedColName = formatWithSpaces("NULL");
        }
        else {
          formattedColName = formatWithSpaces(getString(results, irow, columnName));
        }
        out.append(formattedColName);
      }
      out.deleteCharAt(out.length() - 2);
      out.append("\n");
    }
    return out.toString();
  }

  public static void debug(Log logger, String msg)
  {
    if (logger.isDebugEnabled())
      logger.debug("[AP]" + msg);
  }

  public static void info(Log logger, String msg)
  {
    if (logger.isInfoEnabled())
      logger.info("[AP]" + msg);
  }

  public static void error(Log logger, String msg)
  {
    logger.error("[AP]" + msg);
  }

  public static void exception(Log logger, String msg, Exception ex)
  {
    logger.error("[AP-EX]" + msg, ex);
  }

  public static void exception(Log logger, Exception ex)
  {
    logger.error("[AP-EX]", ex);
  }
}

/* Location:           D:\workfolder\tjhmfms\WEB-INF\lib\hmfmsframework.jar
 * Qualified Name:     hmfms.util.Debug
 * JD-Core Version:    0.6.0
 */