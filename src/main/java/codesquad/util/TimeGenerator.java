package codesquad.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeGenerator {

  public static String getFormatTime(Date date){

    return new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss")
        .format(date);
  }
}
