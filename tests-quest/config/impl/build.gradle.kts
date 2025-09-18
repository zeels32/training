import com.azabost.quest.build.Config

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.azabost.quest.config"
    compileSdk = Config.compileSdk

    defaultConfig {
        minSdk = Config.minSdk
    }
    buildFeatures {
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = Config.javaVersion
        targetCompatibility = Config.javaVersion
    }
}

kotlin {
    compilerOptions {
        jvmTarget = Config.kotlinJvmTarget
    }
}

dependencies {
    api(projects.config.api)
    implementation(libs.javax.inject)
    implementation(libs.hilt.core)
    ksp(libs.hilt.compiler)
}
