package de.cispa.se.mexcounter;

import com.opencsv.CSVWriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MEXCountWriter {

    private final CSVWriter csvWriter;

    public MEXCountWriter(String outputFilePath) throws IOException {
        File outputFile = new File(outputFilePath);
        outputFile.getAbsoluteFile().getParentFile().mkdirs();
        csvWriter = new CSVWriter(new BufferedWriter(new FileWriter(outputFile)));
        csvWriter.writeNext(new String[]{"method", "executions"}, false);
    }

    public void writeResultsToFile() {
        CounterState.METHOD_COUNTERS.forEach((method, hitCount) ->
                csvWriter.writeNext(new String[]{method, hitCount.toString()}, false)
        );
        csvWriter.flushQuietly();
    }
}
