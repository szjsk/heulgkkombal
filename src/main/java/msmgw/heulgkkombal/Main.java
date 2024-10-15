package msmgw.heulgkkombal;

import msmgw.heulgkkombal.codegen.CustomJavaCodegen;
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

        CustomJavaCodegen customJavaCodegen = new CustomJavaCodegen();
        customJavaCodegen.setTemplateDir("src/main/resources/templates");
        customJavaCodegen.setSourceFolder("");
        customJavaCodegen.setApiPackage("kr.co.monolith.test.api");
        customJavaCodegen.setModelPackage("kr.co.monolith.test.model");
        customJavaCodegen.setInvokerPackage("kr.co.monolith.test");
        customJavaCodegen.setOutputDir("src/main/java");
        customJavaCodegen.setLibrary("feign");
        // Create ClientOptInput and set the custom codegen
        ClientOptInput clientOptInput = configurator.toClientOptInput();
        clientOptInput.config(customJavaCodegen);

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