package com.epam.esm.services;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.exceptions.CertificateNotFoundException;
import com.epam.esm.mappers.CertificateDtoMapper;
import com.epam.esm.models.GiftCertificate;
import com.epam.esm.repositories.CertificateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CertificateServiceTest {

    private final CertificateRepository repository = mock(CertificateRepository.class);
    private CertificateService objectUnderTest;

    @BeforeEach
    void setUp() {
        objectUnderTest = new CertificateServiceImpl(repository);
    }

    @Test
    void should_ReturnDtoList_When_ShowAll() {
        //given
        List<GiftCertificate> input = createNewCertificateList();
        List<GiftCertificateDto> expected = CertificateDtoMapper.INSTANCE.toDto(input);
        doReturn(input).when(repository).findAll(anyString(), anyString());
        //then, when
        assertEquals(expected, objectUnderTest.findAll("", ""));
    }

    @Test
    void should_returnDto_When_findById() {
        //given
        GiftCertificate entity = createNewCertificate();
        GiftCertificateDto expected = CertificateDtoMapper.INSTANCE.toDto(entity);
        doReturn(Optional.of(entity)).when(repository).findById(anyInt());
        //when, then
        assertEquals(expected, objectUnderTest.findById(1));
    }

    @Test
    void should_throwException_When_DidntFind() {
        //given
        doReturn(Optional.empty()).when(repository).findById(0);
        //when, then
        assertThrows(CertificateNotFoundException.class, () -> objectUnderTest.findById(0));
    }

    @Test
    void should_ReturnDtoList_When_find() {
        //given
        List<GiftCertificate> input = createNewCertificateList();
        List<GiftCertificateDto> expected = CertificateDtoMapper.INSTANCE.toDto(input);
        doReturn(input).when(repository).searchByTagAndName("tag", "key", "", "");
        //when, then
        assertEquals(expected, objectUnderTest.find("tag", "key", "", ""));
    }

    @Test
    void should_RunShowAll_When_findEmptyTagEmptyName() {
        //given, when
        objectUnderTest.find("", "", "", "");
        //then
        verify(repository, times(1)).findAll("", "");
    }

    @Test
    void should_RunSearchByName_When_findEmptyTag() {
        //given
        List<GiftCertificate> input = createNewCertificateList();
        List<GiftCertificateDto> expected = CertificateDtoMapper.INSTANCE.toDto(input);
        doReturn(input).when(repository).searchByName("key", "", "");
        //when, then
        assertEquals(expected, objectUnderTest.find("", "key", "", ""));
    }

    @Test
    void should_ChangeTimeAndId_When_Save() {
        //given
        GiftCertificateDto input = createNewDto();
        input.setId(0);
        LocalDateTime inputCreateTime = convertTime(input.getCreateDate());
        LocalDateTime inputUpdateTime = convertTime(input.getLastUpdateDate());
        doReturn(5).when(repository).save(any(GiftCertificate.class));
        //when
        GiftCertificateDto output = objectUnderTest.save(input);
        LocalDateTime outputCreateTime = convertTime(output.getCreateDate());
        LocalDateTime outputUpdateTime = convertTime(output.getLastUpdateDate());
        //then
        assertTrue(inputCreateTime.isBefore(outputCreateTime));
        assertTrue(inputUpdateTime.isBefore(outputUpdateTime));
        assertEquals(5, output.getId());
    }

    @Test
    void should_UpdateIfExist_When_Update() {
        //given
        GiftCertificateDto input = createNewDto();
        doReturn(true).when(repository).existsById(1);
        //when
        objectUnderTest.update(1, input);
        //then
        verify(repository, times(1)).update(anyInt(), any(GiftCertificate.class));
    }

    @Test
    void should_throwException_When_UpdateZeroId() {
        //given
        GiftCertificateDto input = createNewDto();
        doReturn(false).when(repository).existsById(0);
        //when, then
        assertThrows(CertificateNotFoundException.class, () -> objectUnderTest.update(0, input));
    }

    @Test
    void should_ChangeUpdateTime_When_PartialUpdate() {
        //given
        GiftCertificateDto input = createNewDto();
        GiftCertificate entity = CertificateDtoMapper.INSTANCE.fromDto(input);
        LocalDateTime inputCreateTime = entity.getCreateDate();
        LocalDateTime inputUpdateTime = entity.getLastUpdateDate();
        doReturn(Optional.of(entity)).when(repository).findById(input.getId());
        //when
        GiftCertificateDto output = objectUnderTest.partialUpdate(input.getId(), input);
        LocalDateTime outputCreateTime = convertTime(output.getCreateDate());
        LocalDateTime outputUpdateTime = convertTime(output.getLastUpdateDate());
        //then
        assertEquals(inputCreateTime, outputCreateTime);
        assertTrue(inputUpdateTime.isBefore(outputUpdateTime));
    }

    @Test
    void should_throwException_When_PartialUpdateZeroId() {
        //given
        GiftCertificateDto input = createNewDto();
        doReturn(Optional.empty()).when(repository).findById(0);
        //when, then
        assertThrows(CertificateNotFoundException.class, () -> objectUnderTest.partialUpdate(0, input));
    }

    @Test
    void should_delete_When_IdExist() {
        //given
        doReturn(true).when(repository).existsById(1);
        //when
        objectUnderTest.delete(1);
        //then
        verify(repository, times(1)).delete(1);
    }

    @Test
    void should_ThrowException_When_DeleteZeroId() {
        //given
        doReturn(false).when(repository).existsById(0);
        //when, then
        assertThrows(CertificateNotFoundException.class, () -> objectUnderTest.delete(0));
    }

    private static GiftCertificate createNewCertificate() {
        return GiftCertificate.builder()
                .id(1)
                .name("testSave")
                .description("testDescription")
                .price(BigDecimal.valueOf(1500))
                .duration(10)
                .createDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .build();
    }

    private static List<GiftCertificate> createNewCertificateList() {
        return Stream.generate(CertificateServiceTest::createNewCertificate)
                .limit(10).toList();
    }

    private static GiftCertificateDto createNewDto() {
        return GiftCertificateDto.builder()
                .id(1)
                .name("testSave")
                .description("testDescription")
                .price(BigDecimal.valueOf(1500))
                .duration(10)
                .createDate(LocalDateTime.now().toString())
                .lastUpdateDate(LocalDateTime.now().toString())
                .build();
    }

    private static LocalDateTime convertTime(String str) {
        return LocalDateTime.parse(str, DateTimeFormatter.ISO_DATE_TIME);
    }
}