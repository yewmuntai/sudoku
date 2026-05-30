plugins {
    kotlin("jvm") version "2.0.21"
    application
    groovy
    jacoco
}

group = "com.yewmun"
version = "1.0.0"

application {
    mainClass.set("MainKt")
}

dependencies {
    // Keep your existing dependencies (like junit-jupiter) here
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")

    // ADD THIS LINE TO FIX THE ERROR:
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // Spock 2.3 with Groovy 3 variant works well on JDK 21
    testImplementation("org.spockframework:spock-core:2.3-groovy-3.0")
    testImplementation("org.codehaus.groovy:groovy:3.0.19")
    testImplementation("net.bytebuddy:byte-buddy:1.14.12")
}

jacoco {
    toolVersion = "0.8.11"
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "MainKt"
    }
    // Bundles all runtime dependencies into the JAR
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.test {
    useJUnitPlatform()

    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)

    reports {
        xml.required.set(false)
        html.required.set(true)
        csv.required.set(false)
    }

    // --- ADD THIS BLOCK TO EXCLUDE FILES ---
    classDirectories.setFrom(
        sourceSets.main.get().output.asFileTree.matching {
            // Use standard Ant-style path patterns (* and **)
            exclude(
                "**/MainKt*",              // Excludes the main application entry file
                "com/yewmun/sudoku/**/*Data*", // Excludes an entire package
                "com/yewmun/sudoku/ExitException*", // Excludes an entire package
                "**/Config*"               // Excludes any class starting with "Config"
            )
        }
    )
}

kotlin {
    jvmToolchain(21)
}