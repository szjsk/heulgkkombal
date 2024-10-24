package heulgkkombal;

import heulgkkombal.codegen.CustomJavaFeignCodegen;
import org.openapitools.codegen.ClientOptInput;
import org.openapitools.codegen.CodegenConstants;
import org.openapitools.codegen.DefaultGenerator;
import org.openapitools.codegen.config.CodegenConfigurator;

public class HeulgkkombalGenerator {

    private String inputSpec = "src/main/resources/example/apidocs.json";
    private final String templateDir = "src/main/resources/templates";
    private final String generatorName = "java";
    private String templateName = "custom-feign";
    private String sourceFolder = "";
    private String invokerPackage = "kr.co.monolith.test";
    private String outputFolder = "src/main/java";


    public void custom() {
        // Configure the OpenAPI Generator
        CodegenConfigurator configurator = new CodegenConfigurator();
        configurator.setInputSpec(inputSpec);
        configurator.setTemplateDir(templateDir);
        configurator.setGeneratorName(generatorName);

        CustomJavaFeignCodegen customJavaFeignCodegen = new CustomJavaFeignCodegen();
        customJavaFeignCodegen.setTemplateDir(templateDir + "/" + templateName);
        customJavaFeignCodegen.setSourceFolder(sourceFolder);
        customJavaFeignCodegen.setApiPackage("kr.co.monolith.test.api");
        customJavaFeignCodegen.setModelPackage("kr.co.monolith.test.model");
        customJavaFeignCodegen.setInvokerPackage("kr.co.monolith.test");
        customJavaFeignCodegen.setOutputDir(outputFolder);
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

        generator.opts(clientOptInput).generate();

    }
}