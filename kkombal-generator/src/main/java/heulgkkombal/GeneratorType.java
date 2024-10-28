package heulgkkombal;

public enum GeneratorType {
    SPRING_REST_TEMPLATE("spring-rest-template"),
    SPRING_FEIGN("custom-feign");

    private final String type;

    GeneratorType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}