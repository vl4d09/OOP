
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelp {

    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    public static String formatDate(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(new Date(timestamp));
    }
}