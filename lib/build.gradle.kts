plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.22"

    `java-library`

    id("maven-publish")
}

group = "dev.enderman"
version = "0.2.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    implementation("commons-io:commons-io:2.17.0")
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
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            groupId = group.toString()
            artifactId = rootProject.name
            version = version.toString()
        }
    }
}
