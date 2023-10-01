package com.epam.esm.dbinitialization.generator.impl;

import com.epam.esm.dbinitialization.generator.EntityType;
import com.epam.esm.dbinitialization.generator.RandomEntitiesGenerator;
import com.epam.esm.user.model.UserModel;
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
public class RandomUsersGenerator implements RandomEntitiesGenerator<UserModel> {

    private final Faker faker;

    @Override
    public List<UserModel> generateUniqEntities(int size) {
        List<UserModel> result = new ArrayList<>();
        List<String> userEmails = generateUniqEmails(size);
        for (int i = 0; i < size; i++) {
            UserModel user = new UserModel();
            user.setName(faker.name().firstName());
            user.setLastName(faker.name().lastName());
            user.setEmail(userEmails.get(i));
            result.add(user);
        }
        return result;
    }

    private List<String> generateUniqEmails(int listSize) {
        Set<String> result = new HashSet<>();
        while (listSize != result.size()) {
            result.add(faker.internet().safeEmailAddress());
        }
        return new ArrayList<>(result);
    }

    @Override
    public EntityType getType() {
        return EntityType.USER;
    }
}
