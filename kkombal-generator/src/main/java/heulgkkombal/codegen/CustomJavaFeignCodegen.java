package heulgkkombal.codegen;


import org.openapitools.codegen.CodegenConfig;
import org.openapitools.codegen.SupportingFile;
import org.openapitools.codegen.languages.JavaClientCodegen;

import java.io.File;

public class CustomJavaFeignCodegen extends JavaClientCodegen implements CodegenConfig {

    public CustomJavaFeignCodegen() {
        super();
        this.supportedLibraries.put("custom-feign", "custom user feign library");
    }

    @Override
    public void processOpts() {
        super.processOpts();
        this.supportingFiles.clear();

        String invokerFolder = (this.sourceFolder + "/" + this.invokerPackage).replace(".", "/");
        String var10000 = this.sourceFolder;
        String modelsFolder = (var10000 + File.separator + this.modelPackage().replace('.', File.separatorChar)).replace('/', File.separatorChar);

        this.supportingFiles.add(new SupportingFile("ApiResponse.mustache", modelsFolder, "ApiResponse.java"));
        this.supportingFiles.add(new SupportingFile("ApiConfig.mustache", invokerFolder, "FeignApiConfig.java"));

    }

}
