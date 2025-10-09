dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        gradlePluginPortal()
    }
}

rootProject.name = "GradleTraining"
include(":app")
include(":feature-one")
include(":feature-two")
