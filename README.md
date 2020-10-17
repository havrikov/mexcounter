# HitCounter
A Java agent that counts method executions.

Run it with `-javaagent:path-to-agent.jar=package.prefix,output.csv` where
- `package.prefix` is the prefix of the package of the classes for which you want the method executions to be counted,
- `output.csv` is the path to a file where the findings will be written into once the jvm exits.

Requires at least Java 11.