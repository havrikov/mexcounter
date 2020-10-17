import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    java
    id("com.github.johnrengelman.shadow") version "6.1.0"
    kotlin("jvm") version "1.4.10"
}

group = "de.cispa.se"
version = "1.0.0"


repositories {
    jcenter()
}

dependencies {
    implementation("net.bytebuddy", "byte-buddy", "1.10.17")
    testImplementation("net.bytebuddy", "byte-buddy-agent", "1.10.17")
}

tasks.withType<JavaCompile> {
    options.release.set(11)
}


val shadow = tasks.named<ShadowJar>("shadowJar") {
    archiveClassifier.set("")
    manifest.attributes["Premain-Class"] = "de.cispa.se.hitcounter.HitCountingAgent"
}

tasks.named("assemble") {
    dependsOn(shadow)
}

tasks.withType<Jar> {
    enabled = name == "shadowJar"
}
