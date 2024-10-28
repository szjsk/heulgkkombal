package org.heulgkkombal.plugins

import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import spock.lang.Specification;
import spock.lang.TempDir;

/**
 플러그인을 참조하는 build.gradle 파일을 동적으로 생성합니다.
 해당 빌드에 대해 특정 작업을 실행합니다.
 작업이 성공적으로 실행되었는지 확인
 */
@Deprecated
class FileDiffPluginIntegrationTest extends Specification{
    @TempDir // 테스트를 위한 디렉토리를 생성하기 위해 사용됩니다.
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
/*
    def "can  diff 2 files of same length"() {
        given:
        //gradle에서 플러그인 configruation을 정의하는 문구를 삽입합니다.
        buildFile << """
            fileDiff {
                file1 = 'testFile1'
                file2 = 'testFile2'
            }
        """

        when:
        //GradleRunner를 이용해 task를 실행시킵니다. task이름은 fileDiff입니다.
        def result = GradleRunner.create()
                .withProjectDir(testProjectDir)
                .withArguments('fileDiff')
                .withPluginClasspath()
                .build()

        then:
        //의도한 출력이 나오는지 확인하고 성공여부를 검증합니다.
        result.output.contains("String data testFile1  , testFile2")
        result.task(":fileDiff").outcome == TaskOutcome.SUCCESS
    }*/

}