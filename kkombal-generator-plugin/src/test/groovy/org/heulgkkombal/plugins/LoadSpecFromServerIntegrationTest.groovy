package org.heulgkkombal.plugins

import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import spock.lang.Specification
import spock.lang.TempDir


class LoadSpecFromServerIntegrationTest extends Specification{
/*
    @TempDir
    File testProjectDir
    File buildFile

    def setup() {
        buildFile = new File(testProjectDir, 'build.gradle')
        buildFile << """
            plugins {
                id 'org.heulgkkombal.generator'
            }
        """
    }

    def "when call server spec api should be then create file"() {
        given:
        buildFile << """
            heulgkkombal {
                specApiUri = 'http://localhost:8079/spec/api-json/testProject'
                projectName = 'testProject'
                envType = 'dev'
            }
        """

        when:
        def result = GradleRunner.create()
                .withProjectDir(testProjectDir)
                .withArguments('load-spec-from-server')
                .withPluginClasspath()
                .build()

        then:
        result.output.contains("successfully")
        result.task(":load-spec-from-server").outcome == TaskOutcome.SUCCESS
    }
*/

}