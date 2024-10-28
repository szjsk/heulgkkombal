package org.heulgkkombal.plugins.task

import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

abstract class CleanSpecAndSourceTask extends DefaultTask {

    @OutputFile
    abstract RegularFileProperty getResultFile()

    CleanSpecAndSourceTask() {
    }

    @TaskAction
    def loadSpecFromServer() {
    }
}