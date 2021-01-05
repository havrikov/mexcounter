# MEXCounter
A very simple Java agent that counts **m**ethod **ex**ecutions and reports them in a CSV file after the targeted process has finished.

It works by instrumenting the bytecode of all non-abstract methods of classes matching a given package prefix.
The method execution counts are collected at runtime in a `ConcurrentHashMap`,
whose contents are dumped into a CSV file when the JVM finishes.

## Usage

Run it with
```bash
java -javaagent:path-to-mexcounter.jar=package.prefix,output.csv -jar path-to-target.jar
```
Where
- `package.prefix` is the prefix of the package of the classes for which you want the method executions to be counted,
- `output.csv` is the path to a file the findings will be written into, once the jvm exits.

The targeted program may be compiled for any Java version, but you must run it on at least a Java 8 JVM
because we rely on the `Map::merge` method.

## Building

Build the agent jar with
```bash
./gradlew build
```

This will create a `mexcounter-1.0.0.jar` inside the `build/libs/` directory.
