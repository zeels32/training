import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion


fun LibraryExtension.setupDefaultConfigurations() {

    compileSdk = 36

    defaultConfig {
        minSdk = 28
        targetSdk = 36

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

}

fun LibraryExtension.setupCompileOptions() {

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

}

fun LibraryExtension.setBuildOptions() {

    buildFeatures {
        compose = true
        viewBinding = true
    }

}

fun LibraryExtension.setKotlinCompilerOptions() {

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }

}
