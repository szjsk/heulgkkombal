package heulgkkombal;

import heulgkkombal.generator.CustomGenerator;
import heulgkkombal.generator.CustomSpringFeignGenerator;
import heulgkkombal.generator.CustomSpringRestTemplateGenerator;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

public class CustomGeneratorFactory {

    private static CustomGeneratorFactory instance;
    private final CustomSpringRestTemplateGenerator customSpringRestTemplateGenerator = new CustomSpringRestTemplateGenerator();
    private final CustomSpringFeignGenerator customSpringFeignGenerator = new CustomSpringFeignGenerator();
    private final Map<GeneratorType, CustomGenerator> factoryMap = Map.of(
            GeneratorType.SPRING_REST_TEMPLATE, customSpringRestTemplateGenerator,
            GeneratorType.SPRING_FEIGN, customSpringFeignGenerator
    );

    private CustomGeneratorFactory() {
    }

    public static CustomGeneratorFactory of() {
        if (instance == null) {
            synchronized (CustomGeneratorFactory.class) {
                if (instance == null) {
                    instance = new CustomGeneratorFactory();
                }
            }
        }
        return instance;
    }

    public CustomGenerator getGenerator(GeneratorType generatorType) {
        return factoryMap.get(generatorType);
    }

    public CustomGenerator getGenerator(String library) {
        GeneratorType generatorType = Arrays.stream(GeneratorType.values())
                .filter(o -> Objects.equals(o.getType(), library))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid generator type"));

        return factoryMap.get(generatorType);
    }
}
