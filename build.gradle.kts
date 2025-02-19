import org.jetbrains.intellij.platform.gradle.tasks.RunIdeTask

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "2.0.21"
    id("org.jetbrains.intellij.platform")
}

group = "com.frybits"
version = "1.1-SNAPSHOT"

dependencies {
    intellijPlatform {
        androidStudio("2024.1.1.3")
        bundledPlugin("org.jetbrains.android")
        bundledPlugin("com.android.tools.design")
    }
}

intellijPlatform {
    publishing {
        token = providers.gradleProperty("intellijPlatformPublishingToken")
        channels = listOf("alpha")
    }
    signing {
        certificateChain.set(providers.gradleProperty("certChain"))
        privateKey.set(providers.gradleProperty("privateKey"))
    }
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }

    patchPluginXml {
        sinceBuild.set("241")
        untilBuild.set("243.*")
    }
}
