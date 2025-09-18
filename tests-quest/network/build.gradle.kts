import com.azabost.quest.build.Config

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ksp)
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
    implementation(libs.retrofit)
    implementation(libs.hilt.core)
    implementation(libs.javax.inject)
    ksp(libs.hilt.compiler)
}