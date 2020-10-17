package de.cispa.se.hitcounter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CounterState {
    public static final Map<String, Integer> METHOD_COUNTERS = new ConcurrentHashMap<>(1000);

    private CounterState() {
        // no instances
    }
}
