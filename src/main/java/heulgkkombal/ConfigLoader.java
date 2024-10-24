package heulgkkombal;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {

    public static ConfigVO loadConfig() {
        ConfigVO parameterVO = new ConfigVO();
        Properties properties = new Properties();
        try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                return parameterVO;
            }
            properties.load(input);

            parameterVO.setInputSpec(properties.getProperty("inputSpec"));
            parameterVO.setTemplateDir(properties.getProperty("templateDir"));
            parameterVO.setGeneratorName(properties.getProperty("generatorName"));
            parameterVO.setLibrary(properties.getProperty("library"));
            parameterVO.setSourceFolder(properties.getProperty("sourceFolder"));
            parameterVO.setInvokerPackage(properties.getProperty("invokerPackage"));
            parameterVO.setOutputFolder(properties.getProperty("outputFolder"));

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return parameterVO;
    }
}