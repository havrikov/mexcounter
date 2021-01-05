import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.4.21"
    id("com.github.johnrengelman.shadow") version "6.1.0"
}

group = "de.cispa.se"
version = "1.0.0"


repositories {
    jcenter()
}

dependencies {
    val byteBuddyVersion = "1.10.19"
    implementation("net.bytebuddy", "byte-buddy", byteBuddyVersion)
    implementation("com.opencsv", "opencsv", "5.2")

    testImplementation("junit", "junit", "4.13.1")
    testImplementation("net.bytebuddy", "byte-buddy-agent", byteBuddyVersion)
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

    withType<JavaCompile> {
        options.release.set(8)
    }

    val shadow = named<ShadowJar>("shadowJar") {
        archiveClassifier.set("")
        manifest.attributes["Premain-Class"] = "de.cispa.se.mexcounter.MEXCountingAgent"
    }

    named("assemble") {
        dependsOn(shadow)
    }

    withType<Jar> {
        enabled = name == "shadowJar"
    }
}
