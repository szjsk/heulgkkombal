package heulgkkombal;

import heulgkkombal.codegen.CustomJavaFeignCodegen;
import org.openapitools.codegen.ClientOptInput;
import org.openapitools.codegen.CodegenConstants;
import org.openapitools.codegen.DefaultGenerator;
import org.openapitools.codegen.config.CodegenConfigurator;

public class HeulgkkombalGenerator {

    public void generate() {

        // Load the configuration
        ConfigVO config = ConfigLoader.loadConfig();

        // Configure the OpenAPI Generator
        CodegenConfigurator configurator = new CodegenConfigurator();
        configurator.setInputSpec(config.getInputSpec());
        configurator.setTemplateDir(config.getTemplateDir());
        configurator.setGeneratorName(config.getGeneratorName());

        CustomJavaFeignCodegen customJavaFeignCodegen = new CustomJavaFeignCodegen();
        customJavaFeignCodegen.setTemplateDir(config.getTemplateDir() + "/" + config.getLibrary());
        customJavaFeignCodegen.setSourceFolder(config.getSourceFolder());
        customJavaFeignCodegen.setApiPackage(config.getInvokerPackage()  + "api");
        customJavaFeignCodegen.setModelPackage(config.getInvokerPackage()  + "model");
        customJavaFeignCodegen.setInvokerPackage(config.getInvokerPackage());
        customJavaFeignCodegen.setOutputDir(config.getOutputFolder());
        customJavaFeignCodegen.setLibrary(config.getLibrary());
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