# HitCounter
A Java agent that counts method executions.

## Usage

Run it with
```bash
java -javaagent:path-to-agent.jar=package.prefix,output.csv -jar path-to-target.jar
```
Where
- `package.prefix` is the prefix of the package of the classes for which you want the method executions to be counted,
- `output.csv` is the path to a file where the findings will be written into once the jvm exits.

The targeted program may be compiled with any Java version, but you must run it on at least a Java 8 VM.

## Building

Build the agent jar with
```bash
./gradlew build
```
