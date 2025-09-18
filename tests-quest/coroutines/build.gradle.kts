import com.azabost.quest.build.Config

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.testFixtures)
}

kotlin {
    compilerOptions {
        jvmTarget = Config.kotlinJvmTarget
    }
}

java {
    sourceCompatibility = Config.javaVersion
    targetCompatibility = Config.javaVersion
}

dependencies {
    testFixturesApi(libs.junit4)
    testFixturesApi(libs.kotlinx.coroutines.core)
    testFixturesApi(libs.kotlinx.coroutines.test)
}