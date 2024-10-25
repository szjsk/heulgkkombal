package heulgkkombal;

public class ConfigVO {

    //Api가 만들어질 spec 내용이 있는 json file 위치 openapi-generator에서 input spec으로 사용됨
    private String inputSpecDir;
    //openapi-generator에서 사용할 generator 이름 현재 버전은 java만 사용
    private String generatorName;
    //openapi-generator에서 사용할 라이브러리, 커스텀한 codeGen생성 이름 현재 버전은 custom-feign만 사용
    private String library;
    //openapi-generator에서 사용할 invoker package 이름
    private String invokerPackage;
    //openapi-generator에서 생성된 코드가 저장될 위치
    private String outputFolder;

    // Getters and Setters
    public String getInputSpecDir() {
        return inputSpecDir;
    }

    public void setInputSpecDir(String inputSpecDir) {
        this.inputSpecDir = inputSpecDir;
    }

    public String getGeneratorName() {
        return generatorName;
    }

    public void setGeneratorName(String generatorName) {
        this.generatorName = generatorName;
    }

    public String getLibrary() {
        return library;
    }

    public void setLibrary(String library) {
        this.library = library;
    }

    public String getInvokerPackage() {
        return invokerPackage;
    }

    public void setInvokerPackage(String invokerPackage) {
        this.invokerPackage = invokerPackage;
    }

    public String getOutputFolder() {
        return outputFolder;
    }

    public void setOutputFolder(String outputFolder) {
        this.outputFolder = outputFolder;
    }
}