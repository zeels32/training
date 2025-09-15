plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    google()
}

dependencies {
    /*
     * The declaration below is a workaround for accessing the version catalog generated accessors
     * in convention plugins. Borrowed from: https://github.com/gradle/gradle/issues/15383#issuecomment-779893192
     * If it ever stops working, delete this dependency and replace all
     * `the<org.gradle.accessors.dm.LibrariesForLibs>()` with
     * `extensions.getByType<VersionCatalogsExtension>().named("libs")` and hardcoded string-y names
     * of the libraries. For example:
     * libs.mockk -> extensions.getByType<VersionCatalogsExtension>().named("libs").findLibrary("mockk").get()
     */
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.android.gradle.plugin)
    implementation(libs.javapoet) // https://github.com/google/dagger/issues/3282
}

gradlePlugin {
    plugins {
        register("JUnit 5 Plugin") {
            id = "junit5"
            implementationClass = "com.azabost.quest.build.JUnit5Plugin"
        }
    }
}