package com.epam.esm.dbinitialization.generator;

import com.epam.esm.dbinitialization.generator.EntityType;
import com.epam.esm.dbinitialization.generator.RandomEntitiesGenerator;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;

@Component
public class GeneratorFactory {

    private final EnumMap<EntityType, RandomEntitiesGenerator<?>> map;

    public GeneratorFactory(List<RandomEntitiesGenerator<?>> generators) {
        this.map = new EnumMap<>(EntityType.class);
        generators.forEach(x -> this.map.put(x.getType(), x));
    }

    public RandomEntitiesGenerator<?> getGenerator(EntityType entityType){
        return map.get(entityType);
    }
}
