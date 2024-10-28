package heulgkkombal.generator;

import heulgkkombal.ConfigVO;
import heulgkkombal.codegen.CustomJavaFeignCodegen;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.openapitools.codegen.CodegenConstants;
import org.openapitools.codegen.DefaultGenerator;
import org.openapitools.codegen.config.CodegenConfigurator;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Stream;

import static heulgkkombal.GeneratorType.SPRING_FEIGN;

/**
 * Spring에서 사용되는 Feign Client를 생성하는 클래스
 * 템플릿은 resources/templates/custom-feign에 위치한다.
 * <p>
 * 스프링을 기반으로 공통 auth, error handling, retry, library implatations 등은 각 프로젝트에서 직접 설정한다.
 *
 * <p>
 * 중요 생성파일 템플릿 리스트 :
 * apiConfig.mustache - Feign Client인스턴스 생성을 위한 Spring Config 파일
 * api.mustache - Feign Client 인터페이스 파일
 * ApiResponse.mustache - API 응답 Wrapper 파일
 * pojo.mustache - API 요청/응답 객체(DTO) 파일 - 기본 템플릿도 사용.
 */
public class CustomSpringFeignGenerator implements CustomGenerator {

    private static final String CUSTOM_TEMPLATE_NAME = "templates";

    public void generate(ConfigVO config) {

        File specDir = new File(config.getInputSpecDir());
        String copiedTemplateUrl = extractTemplatesToTempDir(getClass().getClassLoader().getResource(CUSTOM_TEMPLATE_NAME));
        String outputFolder = changeFolderIfAbsolute(config.getOutputFolder());

        //프로젝트 단위로 파일 생성
        Stream.of(Objects.requireNonNull(specDir.listFiles()))
                .forEach(file -> {
                    log(config.getGeneratorName(), SPRING_FEIGN.getType(), outputFolder, config.getInvokerPackage(), file.getAbsolutePath(), copiedTemplateUrl);

                    // 프로젝트별로 패키지를 생성하여 가독성을 높이기 위해 invokerPackage에 프로젝트명을 추가.
                    String invokerPackageWithProject = config.getInvokerPackage() + "." + FilenameUtils.removeExtension(file.getName());

                    CodegenConfigurator configurator = createCodegenConfigurator(config.getGeneratorName(), file.getAbsolutePath(), copiedTemplateUrl);

                    CustomJavaFeignCodegen codegen = generateFeignClient(copiedTemplateUrl, outputFolder, invokerPackageWithProject);

                    // Run the OpenAPI Generator
                    DefaultGenerator generator = setPropertyForPureFiles(new DefaultGenerator(false));
                    generator.opts(configurator.toClientOptInput().config(codegen)).generate();

                });

    }

    /**
     * 로깅용
     */
    private void log(String generatorName, String library, String outputFolder, String invokerPackage, String inputSpec, String copiedTemplateUrl) {

        System.out.println("""
                generatorName = %s
                library = %s
                outputFolder = %s
                invokerPackage = %s
                inputSpec = %s
                customTemplateUriWithLibrary = %s"""
                .formatted(generatorName, library, outputFolder, invokerPackage, inputSpec, copiedTemplateUrl));
    }

    /**
     * outputFolder가 절대 경로가 아니라면 현재 디렉토리를 기준으로 경로 변경.
     * 상대 경로일때는 경로 생성이 부정확할때가 있음.
     *
     * @param outputFolder 사용자가 지정한 outputFolder. 가급적 /build/generated-sources 내로 생성하는 것을 권장.
     * @return 절대경로로 변경된 outputFolder
     */
    private String changeFolderIfAbsolute(String outputFolder) {
        // Check if outputFolder is an absolute path
        Path outputPath = Paths.get(outputFolder);
        if (!outputPath.isAbsolute()) {
            return Paths.get(System.getProperty("user.dir"), outputFolder).toString();
        }
        return outputPath.toString();
    }

    /**
     * Custom Feign Client. (CustomJavaFeignCodegen)
     * 서포트 파일 중 apiConfig.mustache, ApiResponse.mustache 파일만 생성하도록 변경한 커스텀 클래스.
     *
     * @param copiedTemplateUrl Open Api에서 Jar내의 template file 조회가 안되므로 템플릿을 임시로 복사한 임시 폴더 경로
     * @param outputFolder      생성될 파일이 위치할 경로
     * @param invokerPackage    패키지 경로(내부 import 경로등)
     * @return CustomJavaFeignCodegen
     */
    private CustomJavaFeignCodegen generateFeignClient(String copiedTemplateUrl, String outputFolder, String invokerPackage) {

        CustomJavaFeignCodegen customJavaFeignCodegen = new CustomJavaFeignCodegen();
        customJavaFeignCodegen.setTemplateDir(copiedTemplateUrl + "/" + SPRING_FEIGN.getType());
        customJavaFeignCodegen.setSourceFolder(StringUtils.EMPTY);
        customJavaFeignCodegen.setApiPackage(invokerPackage + ".api");
        customJavaFeignCodegen.setModelPackage(invokerPackage + ".model");
        customJavaFeignCodegen.setInvokerPackage(invokerPackage);
        customJavaFeignCodegen.setOutputDir(outputFolder);
        customJavaFeignCodegen.setLibrary(SPRING_FEIGN.getType());

        return customJavaFeignCodegen;
    }

    private @NotNull CodegenConfigurator createCodegenConfigurator(String generatorName, String inputSpec, String customTemplateUriWithLibrary) {
        // Configure the OpenAPI Generator
        CodegenConfigurator configurator = new CodegenConfigurator();
        configurator.setInputSpec(inputSpec);
        configurator.setTemplateDir(customTemplateUriWithLibrary);
        configurator.setGeneratorName(generatorName);
        return configurator;
    }

    /**
     * openapi generator에서 생성되는 파일을 최소화하기 위해 설정.
     * 사용되지 않는 TEST, DOCS, WEBHOOKS, METADATA 생성을 제외하고 API, MODEL, SUPPORTING_FILES(중 일부)만 생성성.
     *
     * @param generator DefaultGenerator
     * @return DefaultGenerator with set properties.
     */
    private DefaultGenerator setPropertyForPureFiles(DefaultGenerator generator) {

        generator.setGeneratorPropertyDefault(CodegenConstants.APIS, "true");
        generator.setGeneratorPropertyDefault(CodegenConstants.MODELS, "true");
        generator.setGeneratorPropertyDefault(CodegenConstants.SUPPORTING_FILES, "true");
        generator.setGeneratorPropertyDefault(CodegenConstants.GENERATE_RECURSIVE_DEPENDENT_MODELS, "true");
        //ignore
        generator.setGeneratorPropertyDefault(CodegenConstants.WEBHOOKS, "false");
        generator.setGeneratorPropertyDefault(CodegenConstants.MODEL_TESTS, "false");
        generator.setGeneratorPropertyDefault(CodegenConstants.MODEL_DOCS, "false");
        generator.setGeneratorPropertyDefault(CodegenConstants.API_TESTS, "false");
        generator.setGeneratorPropertyDefault(CodegenConstants.API_DOCS, "false");
        generator.setGenerateMetadata(false);

        return generator;
    }

    /**
     * openapi generator에서 Heulgkkombal jar내의 templates 파일을 읽지 못하므로 외부 템프폴더 생성 후 복사하여 해당 경로로 지정.
     * todo 완료 시 복사된 파일 삭제 .
     *
     * @param customTemplateUri
     * @return
     */
    private String extractTemplatesToTempDir(URL customTemplateUri) {
        try {

            if ("jar".equals(customTemplateUri.getProtocol())) {
                JarURLConnection jarURLConnection = (JarURLConnection) customTemplateUri.openConnection();
                JarFile jarFile = jarURLConnection.getJarFile();
                Path tempDir = Files.createTempDirectory(CUSTOM_TEMPLATE_NAME);
                Enumeration<JarEntry> entries = jarFile.entries();
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    if (entry.getName().startsWith(CUSTOM_TEMPLATE_NAME) && !entry.isDirectory()) {
                        File tempFile = new File(tempDir.toFile(), entry.getName().substring((CUSTOM_TEMPLATE_NAME + "/").length()));
                        tempFile.deleteOnExit();
                        FileUtils.copyInputStreamToFile(jarFile.getInputStream(entry), tempFile);
                    }
                }
                return tempDir.toString();
            } else {
                return Paths.get(customTemplateUri.getPath()).normalize().toString();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}