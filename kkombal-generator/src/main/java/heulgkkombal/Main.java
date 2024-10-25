package heulgkkombal;

public class Main {
    public static void main(String[] args) {
        HeulgkkombalGenerator generator = new HeulgkkombalGenerator();
        ConfigVO config = new ConfigVO();
        config.setInputSpecDir("C:\\Users\\note-sz\\IdeaProjects\\cju-activity-video-control\\build\\spec");
        config.setGeneratorName("java");
        config.setLibrary("custom-feign");
        config.setOutputFolder("C:\\Users\\note-sz\\IdeaProjects\\heulgkkombal\\kkombal-generator\\src\\main\\java");
        config.setInvokerPackage("heulgkkombal.test");
        generator.generate(config);

        System.out.println("Hello, World!");
    }
}
