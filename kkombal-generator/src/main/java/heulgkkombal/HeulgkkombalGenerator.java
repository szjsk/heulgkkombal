package heulgkkombal;

import heulgkkombal.codegen.CustomJavaFeignCodegen;
import org.apache.commons.lang3.StringUtils;
import org.openapitools.codegen.ClientOptInput;
import org.openapitools.codegen.CodegenConstants;
import org.openapitools.codegen.DefaultGenerator;
import org.openapitools.codegen.config.CodegenConfigurator;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.jar.JarFile;
import java.util.stream.Stream;

/**
 * author: jaesic Hwang (n-dis@hanmail.net)
 * description:
 */
public class HeulgkkombalGenerator {

    public void generate(ConfigVO config) {

        URL customTemplateUri = getClass().getClassLoader().getResource("templates");

        if (customTemplateUri == null) {
            throw new RuntimeException("Template directory not found");
        }

        File specDir = new File(config.getInputSpecDir());

        Stream.of(Objects.requireNonNull(specDir.listFiles()))
                .forEach(file -> {
                    generateFeignClient(config.getGeneratorName(), config.getLibrary(), config.getOutputFolder(), config.getInvokerPackage(),
                            customTemplateUri, config.getInvokerPackage() + "." + file.getName(), file.getAbsolutePath());
                });

    }

    private void generateFeignClient(String generatorName, String library, String outputFolder, String invokerPackage,
                                     URL customTemplateUri, String customPackage, String inputSpec) {

        //String customTemplateUriWithLibrary = getTemplatePathFromJar(customTemplateUri, library);
        System.out.println("customPackage : " + customPackage);
        // Configure the OpenAPI Generator
        CodegenConfigurator configurator = new CodegenConfigurator();
        //configurator.setInputSpec(inputSpec);
        configurator.setInputSpec("C:\\Users\\note-sz\\IdeaProjects\\heulgkkombal\\kkombal-generator\\src\\main\\resources\\example\\apidocs.json");

        configurator.setTemplateDir("C:\\Users\\note-sz\\IdeaProjects\\heulgkkombal\\kkombal-generator\\src\\main\\resources\\templates");
        //configurator.setGeneratorName(generatorName);
        configurator.setGeneratorName("java");

        CustomJavaFeignCodegen customJavaFeignCodegen = new CustomJavaFeignCodegen();
        customJavaFeignCodegen.setTemplateDir("C:\\Users\\note-sz\\IdeaProjects\\heulgkkombal\\kkombal-generator\\src\\main\\resources\\templates\\custom-feign");
        customJavaFeignCodegen.setSourceFolder(StringUtils.EMPTY);
        customJavaFeignCodegen.setApiPackage(customPackage + ".api");
        customJavaFeignCodegen.setModelPackage(customPackage + ".model");
        customJavaFeignCodegen.setInvokerPackage(invokerPackage);
        customJavaFeignCodegen.setOutputDir("C:\\Users\\note-sz\\IdeaProjects\\heulgkkombal");
        //customJavaFeignCodegen.setLibrary("custom-feign");
        //customJavaFeignCodegen.setOutputDir(outputFolder);
        //customJavaFeignCodegen.setLibrary(library);
        // Create ClientOptInput and set the custom codegen
        ClientOptInput clientOptInput = configurator.toClientOptInput();
        clientOptInput.config(customJavaFeignCodegen);

        // Run the OpenAPI Generator
        DefaultGenerator generator = new DefaultGenerator(false);

        generator.setGeneratorPropertyDefault(CodegenConstants.APIS, "true");
        generator.setGeneratorPropertyDefault(CodegenConstants.MODELS, "true");
        generator.setGeneratorPropertyDefault(CodegenConstants.SUPPORTING_FILES, "true");
        generator.setGeneratorPropertyDefault(CodegenConstants.WEBHOOKS, "false");
        generator.setGeneratorPropertyDefault(CodegenConstants.MODEL_TESTS, "false");
        generator.setGeneratorPropertyDefault(CodegenConstants.MODEL_DOCS, "false");
        generator.setGeneratorPropertyDefault(CodegenConstants.API_TESTS, "false");
        generator.setGeneratorPropertyDefault(CodegenConstants.API_DOCS, "false");
        generator.setGeneratorPropertyDefault(CodegenConstants.GENERATE_RECURSIVE_DEPENDENT_MODELS, "true");
        generator.setGenerateMetadata(false);

        generator.opts(clientOptInput).generate();

    }


    private String getTemplatePathFromJar(URL customTemplateUri, String library) {
        try {
            if ("jar".equals(customTemplateUri.getProtocol())) {
                JarURLConnection jarURLConnection = (JarURLConnection) customTemplateUri.openConnection();
                JarFile jarFile = jarURLConnection.getJarFile();
                return "jar:file:" + jarFile.getName() + "!/templates/" + library;
            } else {
                return Paths.get(customTemplateUri.getPath(), library).normalize().toString();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error getting template path from jar", e);
        }
    }
}