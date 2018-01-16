import com.opencsv.bean.CsvBindByPosition;

public class Record {
    @CsvBindByPosition(position = 0)
    private long time;

    @CsvBindByPosition(position = 1)
    private String userId;

    @CsvBindByPosition(position = 2)
    private String url;

    @CsvBindByPosition(position = 3)
    private int numOfSec;


    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getNumOfSec() {
        return numOfSec;
    }

    public void setNumOfSec(int numOfSec) {
        this.numOfSec = numOfSec;
    }

    @Override
    public String toString() {
        return "Record{" +
                "time=" + time +
                ", userId='" + userId + '\'' +
                ", url='" + url + '\'' +
                ", numOfSec=" + numOfSec +
                '}';
    }
}
