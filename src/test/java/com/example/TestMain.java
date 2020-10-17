package com.example;


import com.library.Baz;
import de.cispa.se.hitcounter.HitCountingInterceptor;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.dynamic.loading.ByteArrayClassLoader;

import java.lang.instrument.Instrumentation;

import static net.bytebuddy.matcher.ElementMatchers.isMethod;
import static net.bytebuddy.matcher.ElementMatchers.nameStartsWith;

public class TestMain {

    public static void main(String[] args) throws Exception {
        Instrumentation instrumentation = ByteBuddyAgent.install();

        var classLoader = new ByteArrayClassLoader.ChildFirst(TestMain.class.getClassLoader(),
                ClassFileLocator.ForClassLoader.readToNames(Foo.class, Baz.class, Qux.class),
                ByteArrayClassLoader.PersistenceHandler.MANIFEST);

        agentmain(null, instrumentation);

        Class<?> type = classLoader.loadClass(Foo.class.getName());
        type.getDeclaredMethod("foo").invoke(type.getDeclaredConstructor().newInstance());
    }

    public static void agentmain(String arguments,
                               Instrumentation instrumentation) {
        var prefix = "com.example";

        new AgentBuilder.Default()
                .disableClassFormatChanges()
                .type(nameStartsWith(prefix))
                .transform((builder, type, classLoader, module) -> builder
                        .visit(Advice.to(HitCountingInterceptor.class).on(isMethod()))
                )
                .installOn(instrumentation);
    }

}
