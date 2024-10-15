package msmgw.heulgkkombal;

import msmgw.heulgkkombal.codegen.CustomJavaFeignCodegen;
import org.openapitools.codegen.ClientOptInput;
import org.openapitools.codegen.CodegenConstants;
import org.openapitools.codegen.DefaultGenerator;
import org.openapitools.codegen.config.CodegenConfigurator;

import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Configure the OpenAPI Generator
        CodegenConfigurator configurator = new CodegenConfigurator();
        configurator.setInputSpec("src/main/resources/example/openapi.yml");
        configurator.setTemplateDir("src/main/resources/templates");
        configurator.setGeneratorName("java");

        CustomJavaFeignCodegen customJavaFeignCodegen = new CustomJavaFeignCodegen();
        customJavaFeignCodegen.setTemplateDir("src/main/resources/templates/custom-feign");
        customJavaFeignCodegen.setSourceFolder("");
        customJavaFeignCodegen.setApiPackage("kr.co.monolith.test.api");
        customJavaFeignCodegen.setModelPackage("kr.co.monolith.test.model");
        customJavaFeignCodegen.setInvokerPackage("kr.co.monolith.test");
        customJavaFeignCodegen.setOutputDir("src/main/java");
        customJavaFeignCodegen.setLibrary("custom-feign");
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

        List<File> generate = generator.opts(clientOptInput).generate();


        //   System.out.println(collect);

    }
}