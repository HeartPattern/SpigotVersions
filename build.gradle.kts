plugins {
    kotlin("jvm") version "1.3.61"
    kotlin("plugin.serialization") version "1.3.61"
    id("org.jetbrains.dokka") version "0.10.1"
    `maven-publish`
}

group = "kr.heartpattern"
version = "1.0-SNAPSHOT"

repositories {
    maven("https://maven.heartpattern.kr/repository/maven-public/")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx", "kotlinx-coroutines-jdk8", "1.3.3")
    implementation("org.jetbrains.kotlinx", "kotlinx-serialization-runtime", "0.14.0")
    implementation("com.github.kittinunf.fuel", "fuel-coroutines", "2.2.1")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    withType<org.jetbrains.dokka.gradle.DokkaTask> {
        outputFormat = "html"
        outputDirectory = "$buildDir/dokka"
    }

    create<Jar>("sourceJar") {
        from(sourceSets.main.get().allSource)
        archiveClassifier.set("source")
    }

    create<Jar>("dokkaJar") {
        from("$buildDir/dokka")
        archiveClassifier.set("javadoc")
    }
}

publishing {
    publications {
        create<MavenPublication>("heartpattern") {
            artifactId = "spigotversions"

            from(components["java"])

            artifact(tasks["sourceJar"])
            artifact(tasks["dokkaJar"])
        }
    }

    repositories{
        if("nexusUser" in properties && "nexusPassword" in properties){
            maven(
                if(version.toString().endsWith("SNAPSHOT"))
                    "https://maven.heartpattern.kr/repository/maven-public-snapshots/"
                else
                    "https://maven.heartpattern.kr/repository/maven-public-releases/"
            ){
                credentials{
                    username = properties["nexusUser"] as String
                    password = properties["nexusPassword"] as String
                }
            }
        }
    }
}