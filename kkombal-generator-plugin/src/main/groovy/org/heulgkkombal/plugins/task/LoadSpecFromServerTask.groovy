package org.heulgkkombal.plugins.task

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

abstract class LoadSpecFromServerTask extends DefaultTask {

    @Input
    String specApiUri
    @Input
    String projectName
    @Input
    String envType

    LoadSpecFromServerTask() {
    }

    @TaskAction
    def loadSpecFromServer() {
        String createdSpecFileDir = "${project.buildDir}/spec/"

        def specDir = new File(createdSpecFileDir)
        if (specDir.exists()) {
            specDir.eachFile { file ->
                file.delete()
            }
        }

        def url = new URL(specApiUri)
        def connection = (HttpURLConnection) url.openConnection()
        connection.setRequestMethod('GET')
        connection.connect()

        if (connection.responseCode == 200) {
            def response = connection.inputStream.text
            def jsonSlurper = new groovy.json.JsonSlurper()
            def specMap = jsonSlurper.parseText(response) as Map<String, String>

            specMap.each { fileName, fileContent ->
                if (fileContent == null || fileContent.toString().trim().isEmpty()) {
                    project.logger.lifecycle("Skipping file creation for ${fileName} as the content is null or empty.")
                    return
                }
                def file = new File(createdSpecFileDir + fileName + ".json")
                file.parentFile.mkdirs()
                file.text = fileContent
                project.logger.lifecycle("File created: ${file.absolutePath}")
            }

            project.logger.lifecycle("Specifications loaded and files created successfully.")
        } else {
            throw new RuntimeException("Failed to load specifications from server. HTTP response code: ${connection.responseCode}")
        }
    }
}