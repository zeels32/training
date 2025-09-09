import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class FlavorPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            pluginManager.apply("library.plugin")


            extensions.configure<LibraryExtension> {
                buildTypes {
                    getByName("debug") {
                        // Add any staging-specific configurations here
                    }
                    getByName("release") {
                        // Add any production-specific configurations here
                    }
                }

                productFlavors {
                    flavorDimensions += "environment"
                    create("staging") {
                        dimension = "environment"
                        // Add any staging-specific configurations here
                    }
                    create("production") {
                        dimension = "environment"
                        // Add any production-specific configurations here
                    }
                }

            }

        }
    }
}
