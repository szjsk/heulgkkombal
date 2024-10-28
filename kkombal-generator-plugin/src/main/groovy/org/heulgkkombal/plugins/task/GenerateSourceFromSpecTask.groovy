package org.heulgkkombal.plugins.task

import heulgkkombal.ConfigVO
import heulgkkombal.CustomGeneratorFactory
import heulgkkombal.generator.CustomSpringFeignGenerator
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

abstract class GenerateSourceFromSpecTask extends DefaultTask {

    @Input
    String generatorName
    @Input
    String library
    @Input
    String invokerPackage
    @Input
    String outputFolder


    @TaskAction
    def loadSpecFromServer() {
        String createdSpecFileDir = "${project.buildDir}/spec/"

        ConfigVO configVO = new ConfigVO()
        configVO.setInputSpecDir(createdSpecFileDir)
        configVO.setGeneratorName(generatorName)
        configVO.setLibrary(library)
        configVO.setInvokerPackage(invokerPackage)
        configVO.setOutputFolder(outputFolder)

        CustomGeneratorFactory.of().getGenerator(library).generate(configVO);
    }
}