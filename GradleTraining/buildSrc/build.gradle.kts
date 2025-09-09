plugins {
    `kotlin-dsl`
}

gradlePlugin {
    plugins {
        register("libraryPlugin") {
            id = "library.plugin"
            implementationClass = "LibraryPlugin"
        }
        register("appPlugin") {
            id = "app.plugin"
            implementationClass = "AppPlugin"
        }
        register("flavorPlugin") {
            id = "flavor.plugin"
            implementationClass = "FlavorPlugin"
        }
    }
}


dependencies {

    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.android.gradle.plugin)

}