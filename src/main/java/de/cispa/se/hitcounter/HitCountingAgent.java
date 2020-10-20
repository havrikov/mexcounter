package de.cispa.se.hitcounter;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;

import java.lang.instrument.Instrumentation;

import static net.bytebuddy.matcher.ElementMatchers.isAbstract;
import static net.bytebuddy.matcher.ElementMatchers.isMethod;
import static net.bytebuddy.matcher.ElementMatchers.nameStartsWith;
import static net.bytebuddy.matcher.ElementMatchers.not;

public class HitCountingAgent {
    public static void premain(String arguments,
                               Instrumentation instrumentation) {
        ArgParser argParser = new ArgParser(arguments);

        HitCountWriter hitCountWriter = new HitCountWriter(argParser.outputFile);
        Runtime.getRuntime().addShutdownHook(new Thread(hitCountWriter::writeResultsToFile));

        buildAgent(argParser.packagePrefix).installOn(instrumentation);
    }

    static AgentBuilder buildAgent(String prefix) {
        return new AgentBuilder.Default()
            .disableClassFormatChanges()
            .type(nameStartsWith(prefix))
            .transform((builder, type, classLoader, module) -> builder
                .visit(Advice.to(HitCountingInterceptor.class).on(isMethod().and(not(isAbstract()))))
            );
    }

    /**
     * Parses the given command line and exposes the parts as its fields.
     */
    private static class ArgParser {
        private final String packagePrefix;
        private final String outputFile;

        private ArgParser(String arguments) {
            String errorMessage = "You must invoke the agent with arguments in the form \"-javaagent:agent.jar=package-prefix,output-file-path\"";
            if (arguments == null) throw new IllegalArgumentException(errorMessage);
            int commaIndex = arguments.indexOf(',');
            if (-1 == commaIndex) throw new IllegalArgumentException(errorMessage);
            packagePrefix = arguments.substring(0, commaIndex);
            outputFile = arguments.substring(commaIndex + 1);
        }
    }

}
