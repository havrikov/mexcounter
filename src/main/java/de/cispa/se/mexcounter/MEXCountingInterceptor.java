package de.cispa.se.mexcounter;

import net.bytebuddy.asm.Advice;

public class MEXCountingInterceptor {

    @Advice.OnMethodEnter
    public static void onEnter(@Advice.Origin("#t::#m#s") String methodName) {
        CounterState.report(methodName);
    }
}
