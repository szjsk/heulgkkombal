package heulgkkombal

import org.gradle.api.Plugin
import org.gradle.api.Project

internal class HelloApiPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.tasks.register("hello-api-heulgkkombal") {
            it.doLast {
                println("Hello, API!")
            }
        }
    }
}