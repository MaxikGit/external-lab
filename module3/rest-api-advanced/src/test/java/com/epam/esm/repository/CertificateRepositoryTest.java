package com.epam.esm.repository;

import com.epam.esm.certificate.model.GiftCertificateModel;
import com.epam.esm.certificate.repository.CertificateRepository;
import com.epam.esm.config.TestDatabaseConfig;
import com.epam.esm.tag.model.TagModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {CertificateRepository.class})
@Import(TestDatabaseConfig.class)
@Transactional
class CertificateRepositoryTest {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private CertificateRepository certificateRepository;

    @BeforeEach
    void initDataBase() throws SQLException {
        ScriptUtils.executeSqlScript(dataSource.getConnection(),
                new ClassPathResource("test_data.sql"));
    }

    @Test
    void should_FindFiveEntities_When_ShowAll() {
        //given & when
        List<GiftCertificateModel> list = certificateRepository.findAll("", "", 0, 20).getContent().stream().toList();
        //then
        assertEquals(5, list.size());
    }

    @Test
    void should_FindThreeEntities_When_PageSize3() {
        //given & when
        List<GiftCertificateModel> list = certificateRepository.findAll("", "", 0, 3).getContent().stream().toList();
        //then
        assertEquals(3, list.size());
    }

    @Test
    void should_FindEntity_When_FindById() {
        //given & when & then
        assertEquals(3, certificateRepository.findById(3)
                .orElse(GiftCertificateModel.builder().build()).getId());
    }

    @Test
    void should_FindEntityWithCertainName_When_Searched() {
        //given & when
        List<GiftCertificateModel> result = certificateRepository
                .findByName("5", "id", "asc", 0, 20)
                .getContent().stream().toList();
        //then
        assertTrue(result.size() > 0);
        assertTrue(result.stream().allMatch(x -> x.getName().startsWith("5")));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "100"})
    void should_FindEntityByTagsWithCertainName_When_Searched(String keyName) {
        //given & when
        List<GiftCertificateModel> result = certificateRepository
                .findByTagAndName(keyName, List.of("w", "b"), "id", "asc", 0, 20)
                .getContent().stream().toList();
        //then
        assertTrue(result.stream().allMatch(x -> x.getName().startsWith(keyName)));
        assertTrue(2 <= result.stream()
                .flatMap(x -> x.getTags().stream())
                .map(TagModel::getName)
                .filter(x -> x.startsWith("w") || x.startsWith("b"))
                .count());
    }

    @Test
    void should_AddNewEntity_When_Save() {
        //given
        GiftCertificateModel certificate = createNewCertificate();
        //when
        certificateRepository.save(certificate);
        //then
        GiftCertificateModel certificateFromDb = certificateRepository.findById(certificate.getId()).orElseThrow(null);
        assertEquals(certificate.getName(), certificateFromDb.getName());
    }

    @Test
    void should_UpdateEntityName_When_Update() {
        //given
        GiftCertificateModel certificate = certificateRepository.findById(1).orElseThrow(RuntimeException::new);
        //when
        certificate.setName("totallyNewName");
        certificateRepository.update(certificate);
        //then
        assertEquals("totallyNewName",
                certificateRepository.findById(1).orElseThrow(RuntimeException::new).getName());
    }

    @Test
    void should_FindNull_When_Deleted() {
        //given & when
        certificateRepository.delete(3);
        //then
        assertNull(certificateRepository.findById(3).orElse(null));
    }

    @Test
    void should_ReturnTrue_When_ExistsId() {
        //given & when & then
        assertTrue(certificateRepository.existsById(3));
    }

    private static GiftCertificateModel createNewCertificate() {
        return GiftCertificateModel.builder()
                .name("testSave")
                .description("testDescription")
                .duration(10)
                .price(BigDecimal.valueOf(100))
                .createDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .build();
    }
}