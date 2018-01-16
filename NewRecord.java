import java.util.Date;

public class NewRecord {
    private String time;
    private String userId;
    private String url;
    private int numOfSec;


    NewRecord(String time, String userId, String url, int numOfSec) {
        this.time = time;
        this.userId = userId;
        this.url = url;
        this.numOfSec = numOfSec;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
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
