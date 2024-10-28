package org.heulgkkombal.plugins


import org.gradle.api.Plugin
import org.gradle.api.Project
import org.heulgkkombal.plugins.extention.GeneratorConfigExtension
import org.heulgkkombal.plugins.task.CleanSpecAndSourceTask
import org.heulgkkombal.plugins.task.GenerateSourceFromSpecTask
import org.heulgkkombal.plugins.task.LoadSpecFromServerTask

class GeneratorPlugin implements Plugin<Project> {


    @Override
    void apply(Project project) {

        project.extensions.create('heulgkkombal', GeneratorConfigExtension)

        project.tasks.register('load-spec-from-server', LoadSpecFromServerTask) {
            specApiUri = project.extensions.heulgkkombal.specApiUri
            projectName = project.extensions.heulgkkombal.projectName
            envType = project.extensions.heulgkkombal.envType
            group = 'heulgkkombal'
        }

        project.tasks.register('generate-source-from-spec', GenerateSourceFromSpecTask) {
            generatorName = project.extensions.heulgkkombal.generatorName ?: 'java'
            library = project.extensions.heulgkkombal.library ?: 'custom-feign'
            invokerPackage = project.extensions.heulgkkombal.invokerPackage
            outputFolder = project.extensions.heulgkkombal.outputFolder
            group = 'heulgkkombal'
        }

        project.tasks.register('clean-spec-and-source', CleanSpecAndSourceTask) {
            group = 'heulgkkombal'
        }

        project.tasks.register('heulgkkombal-build-all') {
            group = 'heulgkkombal'
            dependsOn 'clean-spec-and-source', 'load-spec-from-server', 'generate-source-from-spec'
        }

    }
}