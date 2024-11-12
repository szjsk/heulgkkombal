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

}