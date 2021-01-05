package de.cispa.se.mexcounter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CounterState {
    public static final Map<String, Integer> METHOD_COUNTERS = new ConcurrentHashMap<>(1000);

    private CounterState() {
        // no instances
    }

    public static void report(String methodName) {
        METHOD_COUNTERS.merge(methodName, 1, Integer::sum);
    }
}
