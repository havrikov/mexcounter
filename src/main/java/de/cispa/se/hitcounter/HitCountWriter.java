package de.cispa.se.hitcounter;

public class HitCountWriter {

    private final String outputFile;

    public HitCountWriter(String outputFile) {
        // todo bootstrap CSV output file
        this.outputFile = outputFile;
    }

    public void writeResultsToFile() {
        System.out.println("Writing into " + outputFile);
        CounterState.METHOD_COUNTERS.forEach((method, hitCount) ->
            System.out.println(method + ": " + hitCount)
        );
    }
}
