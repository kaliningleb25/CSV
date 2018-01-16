import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.TimeUnit;

class TimeStampToDate {
    private final static int SECONDS_IN_DAY = 86400;
    private long timeStamp;

    TimeStampToDate(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    String timeStampToDateString() {
        Date date = Date.from(Instant.ofEpochSecond(timeStamp));
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        return sdf.format(date);
    }

    private Date timeStampToDateDate() throws ParseException {
        Date date = Date.from(Instant.ofEpochSecond(timeStamp));
        SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
        dateFormatGmt.setTimeZone(TimeZone.getTimeZone("UTC"));

        //Local time zone
        SimpleDateFormat dateFormatLocal = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");

        //Time in GMT
        return dateFormatLocal.parse( dateFormatGmt.format(date) );
    }

    boolean hasNextDay(Record record) throws ParseException {
        Date date = new TimeStampToDate(record.getTime()).timeStampToDateDate();
        Date dateNext = Date.from(Instant.from(Instant.ofEpochSecond(timeStamp)));

        // real time in UTC
        String dayStart = String.valueOf(date.getDate());
       // System.out.println("dayStart = " + dayStart);

        // Day when user finish session
        String dayFinish = String.valueOf(nextDayDate(record, dateNext, "dd"));
       // System.out.println("dayFinish = " + dayFinish);

        // If user finish session on the next day - return true
        return !(dayStart.equals(dayFinish));
    }

    private static String nextDayDate(Record record, Date date, String format) {
        int numOfSec = record.getNumOfSec();
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        // Get date when user finish session
        date.setSeconds(date.getSeconds() + numOfSec);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        return sdf.format(date);
    }


    private int getSecondsInFirstDay(Date date) {
        int secInFstDay = SECONDS_IN_DAY - (date.getHours() * 3600 + date.getMinutes() * 60 + date.getSeconds());

        return secInFstDay;
    }

    private int getSecondsInSecondDay(Record record, Date date) {
        int secInSndDay = getSecondsInFirstDay(date) - record.getNumOfSec();

        if (secInSndDay < 0)
            secInSndDay *= -1;

        return secInSndDay;
    }


    List<NewRecord> makeListForFewDays(Record record) throws ParseException {
        List<NewRecord> listForFewDays = new ArrayList<>();
        TimeStampToDate time = new TimeStampToDate(record.getTime());
        Date date = time.timeStampToDateDate();
        Date dateOriginal = Date.from(Instant.from(Instant.ofEpochSecond(timeStamp)));

        listForFewDays.add(new NewRecord(time.timeStampToDateString(), record.getUserId(), record.getUrl(), time.getSecondsInFirstDay(date)));
        listForFewDays.add(new NewRecord(nextDayDate(record, dateOriginal, "dd-MM-yyyy"), record.getUserId(), record.getUrl(), time.getSecondsInSecondDay(record, date)));

        return listForFewDays;
    }

}
