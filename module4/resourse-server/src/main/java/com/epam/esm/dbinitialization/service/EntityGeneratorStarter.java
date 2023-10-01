package com.epam.esm.dbinitialization.service;

import com.epam.esm.certificate.model.GiftCertificateModel;
import com.epam.esm.dbinitialization.dto.InitializationDto;
import com.epam.esm.dbinitialization.mapper.DtoToEntityNumberMapper;
import com.epam.esm.dbinitialization.generator.EntityType;
import com.epam.esm.dbinitialization.generator.GeneratorFactory;
import com.epam.esm.tag.model.TagModel;
import com.epam.esm.user.model.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.epam.esm.dbinitialization.generator.EntityType.*;

/**
 * This class generates only initial data in empty tables.
 * If tables are not empty, you should clean data, to avoid uniqueness constraints violation
 * with duplicate values.
 */

@Profile("dev")
@Component
@PropertySource("/hibernate.properties")
@RequiredArgsConstructor
public class EntityGeneratorStarter {

    private final GeneratorFactory generatorFactory;
    private final EntitiesBatchSaveService saveService;
    private final Map<String, String> responceMap = new LinkedHashMap<>();

    @SuppressWarnings("unchecked")
    public Map<String, String> generate(InitializationDto dto) {
        if (!saveService.checkDatabaseIsEmpty()) {
            responceMap.put("message", "initial data already generated");
            return responceMap;
        }
        Map<EntityType, Integer> quantityMap = DtoToEntityNumberMapper.map(dto);
        Map<EntityType, List<?>> entitiesListMap = quantityMap.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        value -> getListOfModels(value.getValue(), value.getKey())));
        entitiesListMap.forEach(saveService::saveToDatabase);
        //saving entities` relations
        List<GiftCertificateModel> certificates = (List<GiftCertificateModel>) entitiesListMap.get(CERTIFICATE);
        List<TagModel> tags = (List<TagModel>) entitiesListMap.get(TAG);
        saveService.setCertificateTagsRelations(certificates, tags);
        responceMap.put("created certificate-tag relations", "done");
        List<UserModel> users = (List<UserModel>) entitiesListMap.get(USER);
        saveService.setUserOrderRelations(dto.getOrdersPerUser(), users, certificates);
        responceMap.put("created user-order relations",
                String.valueOf(dto.getOrdersPerUser() * entitiesListMap.get(USER).size()));
        return responceMap;
    }

    private List<?> getListOfModels(int size, EntityType entityType) {
        var result = generatorFactory
                .getGenerator(entityType)
                .generateUniqEntities(size);
        String message = ("created " + entityType.name() + "s").toLowerCase();
        responceMap.put(message, String.valueOf(result.size()));
        return result;
    }
}




