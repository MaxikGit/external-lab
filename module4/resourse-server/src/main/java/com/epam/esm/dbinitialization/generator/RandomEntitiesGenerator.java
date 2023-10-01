package com.epam.esm.dbinitialization.generator;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public interface RandomEntitiesGenerator<T> {

    List<T> generateUniqEntities(int size);

    EntityType getType();

    /**
     * generates list of uniq nums from min (including) to max (including)
     * @param size
     * @param min
     * @param max
     * @return
     */
    default List<Integer> getListOfUniqNums(int size, int min, int max) {
        if ((size > max - min) || (max - min < 0))
            return List.of(min);
        return ThreadLocalRandom.current()
                .ints(min, max + 1).distinct()
                .limit(size).boxed()
                .sorted()
                .toList();
    }
}
