import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import org.apache.commons.collections4.CollectionUtils;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FillRecordsFromCsv {

    List<Record> csvToBean() throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        List<Record> records = new CsvToBeanBuilder(new FileReader("file1.csv"))
                .withType(Record.class).withSeparator(',').build().parse();


        return records;
    }

    private void workWithRecords(List<Record> records) throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException, ParseException {
        NumberOfSecondsToAverage sec = new NumberOfSecondsToAverage();

        List<NewRecord> newRecordList = new ArrayList<>();
        List<NewRecord> newRecordListForFewDays = new ArrayList<>();
        List<String> userIdListForRemove = new ArrayList<>();
        List<String> urlListForRemove = new ArrayList<>();

        List<String> userIdList = new ArrayList<>();
        List<String> urlList = new ArrayList<>();

        for (Record record : records) {
            System.out.println(record);
        }


        for (Record record : records)
            userIdListForRemove.add(record.getUserId());

        for (Record record : records)
            urlListForRemove.add(record.getUrl());

        System.out.println(userIdListForRemove);
        System.out.println(urlListForRemove);


        for (Record record : records) {
            TimeStampToDate time = new TimeStampToDate(record.getTime());
            String userId = record.getUserId();
            String url = record.getUrl();


            if (time.hasNextDay(record)) {
                newRecordListForFewDays.addAll(time.makeListForFewDays(record));
            }

            else if (userIdList.contains(userId) && urlList.contains(url)) {

                for (int i = 0; i < userIdListForRemove.size(); i++) {
                    if (userIdListForRemove.get(i).equals(userId) && urlListForRemove.get(i).equals(url)) {
                        newRecordList.remove(i);
                        userIdListForRemove.remove(i);
                        urlListForRemove.remove(i);
                        break;
                    }
                }


                    newRecordList.add(new NewRecord(time.timeStampToDateString(),
                            record.getUserId(),
                            record.getUrl(),
                            sec.getAverage(userId, url)));
            } else {

                newRecordList.add(new NewRecord(time.timeStampToDateString(),
                        record.getUserId(),
                        record.getUrl(),
                        record.getNumOfSec()));


            }

            userIdList.add(record.getUserId());
            urlList.add(record.getUrl());
        }

        newRecordList.addAll(newRecordListForFewDays);

        System.out.println();
        for (NewRecord newRecord : newRecordList) {
            System.out.println(newRecord);
        }

        System.out.println();
        System.out.println();
        System.out.println();
        for (NewRecord newRecord : newRecordListForFewDays) {
            System.out.println(newRecord);
        }
    }

    public static void main(String[] args) throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException, ParseException {
        FillRecordsFromCsv fillRecordsFromCsv = new FillRecordsFromCsv();
        List<Record> recordList = fillRecordsFromCsv.csvToBean();

        fillRecordsFromCsv.workWithRecords(recordList);

    }
}
