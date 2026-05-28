plugins {
    kotlin("jvm") version "2.0.21"
    application
}

group = "com.yewmun"
version = "1.0.0"

application {
    mainClass.set("MainKt")
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "MainKt"
    }
    // Bundles all runtime dependencies into the JAR
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
kotlin {
    jvmToolchain(21)
}