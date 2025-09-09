import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AppPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {

            pluginManager.apply("com.android.application")
            pluginManager.apply("org.jetbrains.kotlin.android")

            extensions.configure<ApplicationExtension> {
                compileSdk = 34
                setupDefaultConfigurations()
                setupCompileOptions()
            }

            extensions.configure<BaseAppModuleExtension> {
                setBuildOptions()
                setKotlinCompilerOptions()
            }

        }
    }
}
