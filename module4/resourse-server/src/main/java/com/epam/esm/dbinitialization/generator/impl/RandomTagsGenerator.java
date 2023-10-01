package com.epam.esm.dbinitialization.generator.impl;

import com.epam.esm.dbinitialization.generator.EntityType;
import com.epam.esm.dbinitialization.generator.RandomEntitiesGenerator;
import com.epam.esm.tag.model.TagModel;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Profile("dev")
@Component
@RequiredArgsConstructor
@Slf4j
public class RandomTagsGenerator implements RandomEntitiesGenerator<TagModel> {

    private final Faker faker;

    @Override
    public List<TagModel> generateUniqEntities(int size) {
        Set<TagModel> result = new HashSet<>();
        while (size != result.size()) {
            TagModel tag = new TagModel();
            tag.setName(faker.commerce().productName());
            result.add(tag);
        }
        return new ArrayList<>(result);
    }

    @Override
    public EntityType getType() {
        return EntityType.TAG;
    }
}
