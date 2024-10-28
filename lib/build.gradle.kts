plugins {
    alias(libs.plugins.jvm)

    `java-library`

    id("maven-publish")
}

group = "dev.enderman"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")

    testImplementation(libs.junit.jupiter.engine)

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    api(libs.commons.math3)

    implementation("commons-io:commons-io:2.17.0")

    implementation(libs.guava)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

tasks.named<Jar>("jar") {
    archiveFileName.set("${rootProject.name}-${project.version}.jar")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

publishing {
    publications.create<MavenPublication>("esoteric-utility") {
        from(components["java"])
        artifactId = rootProject.name
    }
}
