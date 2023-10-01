package com.epam.esm.repositories;

import com.epam.esm.config.TestDatabaseConfig;
import com.epam.esm.models.GiftCertificate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig
@ContextConfiguration(classes = TestDatabaseConfig.class)
class CertificateRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplateTest;
    @Autowired
    private CertificateRepository certificateRepository;

    @BeforeEach
    void initDataBase() throws SQLException {
        ScriptUtils.executeSqlScript(jdbcTemplateTest.getDataSource().getConnection(),
                new ClassPathResource("drop_n_create_test_db.sql"));
        ScriptUtils.executeSqlScript(jdbcTemplateTest.getDataSource().getConnection(),
                new ClassPathResource("fill_out_db.sql"));
    }

    @Test
    void should_FindFiveEntities_When_ShowAll() {
        //given, when
        List<GiftCertificate> list = certificateRepository.findAll("", "");
        //then
        assertEquals(5, list.size());
    }

    @Test
    void should_FindEntity_When_FindById() {
        //given, when, then
        assertEquals(3, certificateRepository.findById(3)
                .orElse(GiftCertificate.builder().build()).getId());
    }

    @Test
    void should_FindEntityWithCertainName_When_Searched() {
        //given, when
        List<GiftCertificate> result = certificateRepository.searchByName("5", "id", "asc");
        //then
        assertTrue(result.size() > 0);
        assertTrue(result.stream().allMatch(x -> x.getName().startsWith("5")));
    }

    @Test
    void should_FindEntityByTagWithCertainName_When_Searched() {
        //given, when
        List<GiftCertificate> result = certificateRepository
                .searchByTagAndName("anything", "5", "id", "asc");
        //then
        assertTrue(result.size() > 0);
        assertTrue(result.stream().allMatch(x -> x.getName().startsWith("5")));
    }

    @Test
    void should_AddNewEntity_When_Save() {
        //given
        GiftCertificate certificate = createNewCertificate();
        //when
        int id = certificateRepository.save(certificate);
        //then
        GiftCertificate certificateFromDb = certificateRepository.findById(id).orElseThrow(null);
        assertEquals(certificate.getName(), certificateFromDb.getName());
    }

    @Test
    void should_UpdateEntityName_When_Update() {
        //given
        GiftCertificate certificate = certificateRepository.findById(1).orElseThrow(RuntimeException::new);
        //when
        certificate.setName("totallyNewName");
        certificateRepository.update(1, certificate);
        //then
        assertEquals("totallyNewName",
                certificateRepository.findById(1).orElseThrow(RuntimeException::new).getName());
    }

    @Test
    void should_FindNull_When_Deleted() {
        //given, when
        certificateRepository.delete(3);
        //then
        assertNull(certificateRepository.findById(3).orElse(null));
    }

    @Test
    void should_ReturnTrue_When_ExistsId() {
        //given, when, then
        assertTrue(certificateRepository.existsById(3));
    }

    private static GiftCertificate createNewCertificate() {
        return GiftCertificate.builder()
                .name("testSave")
                .description("testDescription")
                .duration(10)
                .createDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .build();
    }
}