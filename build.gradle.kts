import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    application
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "org.lemondedelanuit"
version = "0.2.0"

val jdaVersion = "5.0.0-beta.2"

repositories {
    mavenCentral()
    maven("https://m2.dv8tion.net/releases")
}

dependencies {
    implementation("net.dv8tion:JDA:$jdaVersion")
    implementation("org.yaml:snakeyaml:1.33")
    testImplementation(kotlin("test"))
}

application {
    mainClass.set("org.lemondedelanuit.botdelanuit.MainKt")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }
    test {
        useJUnitPlatform()
    }
    withType<ShadowJar> {

    }
}
