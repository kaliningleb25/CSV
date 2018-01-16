import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.IOException;
import java.util.List;

class NumberOfSecondsToAverage {
    int getAverage(String userId, String url) throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        FillRecordsFromCsv fillRecordsFromCsv = new FillRecordsFromCsv();
        List<Record> records = fillRecordsFromCsv.csvToBean();
        int avg;
        int sum = 0;
        int count = 0;

        for (Record record : records) {
            if (record.getUserId().equals(userId) && record.getUrl().equals(url)) {
                sum += record.getNumOfSec();
                count++;
            }
        }

        avg = sum / count;

        return avg;
    }
}
