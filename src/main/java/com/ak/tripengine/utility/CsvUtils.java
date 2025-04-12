package com.ak.tripengine.utility;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.util.List;

import com.ak.tripengine.model.Tap;
import com.ak.tripengine.model.Trip;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.extern.java.Log;

@Log
public class CsvUtils {

    private CsvUtils() {
        // Private constructor to prevent instantiation
    }

    static public List<Tap> readTapsFromCsv(String fileName) throws IOException {
        FileReader reader = new FileReader(fileName);
        CsvToBean<Tap> csvToBean =
                new CsvToBeanBuilder<Tap>(reader).withType(Tap.class).withIgnoreLeadingWhiteSpace(true).build();

        return csvToBean.parse();
    }

    public static void writeTripsToCsv(List<Trip> trips, String fileName)  {
        Writer writer = null;
        try {
             writer = new FileWriter(Path.of(fileName).toString());

            StatefulBeanToCsv<Trip> sbc =
                    new StatefulBeanToCsvBuilder<Trip>(writer).withQuotechar('\"')
                            .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                            .build();
            writer.write(Trip.header);
            writer.write("\n");
            sbc.write(trips);
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException |
                 IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                   log.warning(e.getMessage());
                }
            }
        }
    }

}
