package heulgkkombal.heulgkkombal

import org.gradle.api.Plugin
import org.gradle.api.Project

internal class HelloApiPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.tasks.register("hello-api-heulgkkombal") {
            doLast {
                println("Hello, API!")
            }
        }
        project.tasks.register("printTest") {
            doLast {
                println("test")
            }
        }
    }
}