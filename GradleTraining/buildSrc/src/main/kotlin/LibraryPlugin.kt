import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class LibraryPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            // ✅ Apply required plugins
            pluginManager.apply("com.android.library")
            pluginManager.apply("org.jetbrains.kotlin.android")

            // ✅ Configure Android Library
            extensions.configure<LibraryExtension> {
                compileSdk = 34

                setupDefaultConfigurations()
                setupCompileOptions()
                setBuildOptions()
                setKotlinCompilerOptions()

            }
        }
    }
}
