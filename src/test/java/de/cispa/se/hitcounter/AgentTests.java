package de.cispa.se.hitcounter;

import com.example.Foo;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.agent.builder.ResettableClassFileTransformer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.instrument.Instrumentation;

public class AgentTests {

    private Instrumentation instrumentation;
    private ResettableClassFileTransformer transformer;

    @Before
    public void setUp() {
        CounterState.METHOD_COUNTERS.clear();
        instrumentation = ByteBuddyAgent.install();
    }

    @After
    public void tearDown() {
        if (transformer != null) {
            transformer.reset(instrumentation, AgentBuilder.RedefinitionStrategy.REDEFINITION);
        }
    }

    @Test
    public void hitCountingInterceptor_seesLocalClasses() {
        String prefix = "com.example";

        transformer = HitCountingAgent.buildAgent(prefix)
            .with(AgentBuilder.Listener.StreamWriting.toSystemOut().withTransformationsOnly())
            .installOnByteBuddyAgent();

        new Foo().foo();

        Assert.assertFalse(CounterState.METHOD_COUNTERS.isEmpty());
        Assert.assertEquals(2, CounterState.METHOD_COUNTERS.getOrDefault("com.example.Foo::bar()", 0).intValue());
        Assert.assertEquals(1, CounterState.METHOD_COUNTERS.getOrDefault("com.example.Qux::qux()", 0).intValue());
    }
}
