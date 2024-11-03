plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.22"

    `java-library`

    id("maven-publish")
}

group = "foundation.esoteric"
version = "1.1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    implementation("commons-io:commons-io:2.17.0")

    implementation("net.lingala.zip4j", "zip4j", "2.11.5")
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

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            groupId = group.toString()
            artifactId = rootProject.name
            version = version.toString()

            artifact(sourcesJar.get())
        }
    }
}
