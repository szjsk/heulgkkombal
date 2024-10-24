plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    `maven-publish`
    id("org.jetbrains.kotlin.jvm") version "1.9.20"
}

gradlePlugin {
    plugins {
        create("hello-api-heulgkkombal") {
            id = "hello-api-heulgkkombal"
            implementationClass = "heulgkkombal.HelloApiPlugin"
        }
    }
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation(gradleApi())
    implementation(localGroovy())
}