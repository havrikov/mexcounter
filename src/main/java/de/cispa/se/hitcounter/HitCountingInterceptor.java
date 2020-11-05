package de.cispa.se.hitcounter;

import net.bytebuddy.asm.Advice;

public class HitCountingInterceptor {

    @Advice.OnMethodEnter
    public static void onEnter(@Advice.Origin("#t::#m#s") String methodName) {
        CounterState.report(methodName);
    }
}
