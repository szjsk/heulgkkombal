package heulgkkombal;

import heulgkkombal.codegen.CustomJavaFeignCodegen;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.openapitools.codegen.ClientOptInput;
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

/**
 * author: jaesic Hwang (n-dis@hanmail.net)
 * description:
 */
public class HeulgkkombalGenerator {

    public void generate(ConfigVO config) {

        File specDir = new File(config.getInputSpecDir());

        Stream.of(Objects.requireNonNull(specDir.listFiles()))
                .forEach(file -> {
                    generateFeignClient(config.getGeneratorName(),
                            config.getLibrary(),
                            config.getOutputFolder(),
                            config.getInvokerPackage() + "." + FilenameUtils.removeExtension(file.getName()),
                            file.getAbsolutePath());
                });

    }

    private void generateFeignClient(String generatorName, String library, String outputFolder, String invokerPackage, String inputSpec) {

        String customTemplateUriWithLibrary = extractTemplatesToTempDir(getClass().getClassLoader().getResource("templates"));

        // Check if outputFolder is an absolute path
        Path outputPath = Paths.get(outputFolder);
        if (!outputPath.isAbsolute()) {
            outputPath = Paths.get(System.getProperty("user.dir"), outputFolder);
        }

        System.out.println("""
                generatorName = %s
                library = %s
                outputFolder = %s
                invokerPackage = %s
                inputSpec = %s
                customTemplateUriWithLibrary = %s"""
                .formatted(generatorName, library, outputFolder, invokerPackage, inputSpec, customTemplateUriWithLibrary));

        // Configure the OpenAPI Generator
        CodegenConfigurator configurator = new CodegenConfigurator();
        configurator.setInputSpec(inputSpec);
        configurator.setTemplateDir(customTemplateUriWithLibrary);
        configurator.setGeneratorName(generatorName);

        CustomJavaFeignCodegen customJavaFeignCodegen = new CustomJavaFeignCodegen();
        customJavaFeignCodegen.setTemplateDir(customTemplateUriWithLibrary + "/" + library);
        customJavaFeignCodegen.setSourceFolder(StringUtils.EMPTY);
        customJavaFeignCodegen.setApiPackage(invokerPackage + ".api");
        customJavaFeignCodegen.setModelPackage(invokerPackage + ".model");
        customJavaFeignCodegen.setInvokerPackage(invokerPackage);
        customJavaFeignCodegen.setOutputDir(outputPath.toString());
        customJavaFeignCodegen.setLibrary(library);
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


    private String extractTemplatesToTempDir(URL customTemplateUri) {
        try {
            System.out.println("start 1");
            if ("jar".equals(customTemplateUri.getProtocol())) {
                System.out.println("2 customTemplateUri.getPath() = " + customTemplateUri.getPath());
                JarURLConnection jarURLConnection = (JarURLConnection) customTemplateUri.openConnection();
                JarFile jarFile = jarURLConnection.getJarFile();
                Path tempDir = Files.createTempDirectory("templates");
                System.out.println("start 3");
                Enumeration<JarEntry> entries = jarFile.entries();
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    if (entry.getName().startsWith("templates") && !entry.isDirectory()) {
                        File tempFile = new File(tempDir.toFile(), entry.getName().substring("templates/".length()));
                        tempFile.deleteOnExit();
                        FileUtils.copyInputStreamToFile(jarFile.getInputStream(entry), tempFile);
                    }
                }
                System.out.println("start 4 :: " + tempDir.toString());
                return tempDir.toString();
            } else {
                return Paths.get(customTemplateUri.getPath()).normalize().toString();
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}