package de.cispa.se.hitcounter;

import net.bytebuddy.asm.Advice;

public class HitCountingInterceptor {

    @Advice.OnMethodEnter
    public static void onEnter(@Advice.Origin String methodName) {
        System.out.println("Counting " + methodName);
        CounterState.METHOD_COUNTERS.merge(methodName, 1, Integer::sum);
    }
}
