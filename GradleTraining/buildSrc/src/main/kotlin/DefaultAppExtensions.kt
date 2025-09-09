
import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.JavaVersion


fun ApplicationExtension.setupDefaultConfigurations() {

    namespace = "com.training.gradle"
    compileSdk = 36

    defaultConfig {
        minSdk = 28
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

}

fun ApplicationExtension.setupCompileOptions() {

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

}

fun BaseAppModuleExtension.setBuildOptions() {

    buildFeatures {
        compose = true
        viewBinding = true
    }

}

fun BaseAppModuleExtension.setKotlinCompilerOptions() {

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }

}

