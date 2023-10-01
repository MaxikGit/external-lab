package com.epam.esm.service;

import com.epam.esm.certificate.dto.GiftCertificateDto;
import com.epam.esm.certificate.mapper.CertificateDtoMapper;
import com.epam.esm.certificate.model.GiftCertificateModel;
import com.epam.esm.certificate.repository.CertificateRepository;
import com.epam.esm.certificate.service.CertificateService;
import com.epam.esm.certificate.service.CertificateServiceImpl;
import com.epam.esm.common.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.PagedModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
        PagedModel<GiftCertificateModel> input = createNewCertificateList();
        PagedModel<GiftCertificateDto> expected = CertificateDtoMapper.INSTANCE.toDto(input);
        doReturn(input).when(repository).findAll(anyString(), anyString(), anyInt(), anyInt());
        //when
        PagedModel<GiftCertificateDto> actual = objectUnderTest.findAll("", "", 0, 20);
        //then
        assertEquals(expected, actual);
    }

    @Test
    void should_returnDto_When_findById() {
        //given
        GiftCertificateModel entity = createNewCertificate();
        GiftCertificateDto expected = CertificateDtoMapper.INSTANCE.toDto(entity);
        doReturn(Optional.of(entity)).when(repository).findById(anyInt());
        //when
        GiftCertificateDto actual = objectUnderTest.findById(1);
        //then
        assertEquals(expected, actual);
    }

    @Test
    void should_throwException_When_DidntFind() {
        //given
        doReturn(Optional.empty()).when(repository).findById(0);
        //when & then
        assertThrows(ResourceNotFoundException.class, () -> objectUnderTest.findById(0));
    }

    @Test
    void should_ReturnDtoList_When_find() {
        //given
        PagedModel<GiftCertificateModel> input = createNewCertificateList();
        PagedModel<GiftCertificateDto> expected = CertificateDtoMapper.INSTANCE.toDto(input);
        doReturn(input).when(repository)
                .findByTagAndName("key", List.of("tags"), "", "", 0, 20);
        //when
        PagedModel<GiftCertificateDto> actual = objectUnderTest
                .find("key", List.of("tags"), "", "", 0, 20);
        //then
        assertEquals(expected, actual);
    }

    @Test
    void should_RunShowAll_When_findEmptyTagEmptyName() {
        //given
        doReturn(createNewCertificateList()).when(repository)
                .findAll("", "", 0, 20);
        //when
        objectUnderTest.find("", new ArrayList<>(), "", "", 0, 20);
        //then
        verify(repository, only()).findAll("", "", 0, 20);

    }

    @Test
    void should_SearchByName_When_findEmptyTag() {
        //given
        PagedModel<GiftCertificateModel> input = createNewCertificateList();
        PagedModel<GiftCertificateDto> expected = CertificateDtoMapper.INSTANCE.toDto(input);
        doReturn(input).when(repository).findByName("key", "", "", 0, 20);
        //when
        PagedModel<GiftCertificateDto> actual = objectUnderTest
                .find("key", new ArrayList<>(), "", "", 0, 20);
        // then
        assertEquals(expected, actual);
    }

    @Test
    void should_ChangeId_When_Save() {
        //given
        GiftCertificateDto input = createNewDto();
        input.setId(225);
        //when
        GiftCertificateDto output = objectUnderTest.save(input);
        //then
        verify(repository, only()).save(any(GiftCertificateModel.class));
        assertNull(output.getId());
    }

    @Test
    void should_RunUpdateIfExist_When_Update() {
        //given
        GiftCertificateDto input = createNewDto();
        doReturn(true).when(repository).existsById(1);
        //when
        objectUnderTest.update(1, input);
        //then
        verify(repository, times(1)).update(any(GiftCertificateModel.class));
    }

    @Test
    void should_throwException_When_UpdateZeroId() {
        //given
        GiftCertificateDto input = createNewDto();
        doReturn(false).when(repository).existsById(0);
        //when & then
        assertThrows(ResourceNotFoundException.class, () -> objectUnderTest.update(0, input));
    }

    @Test
    void should_SetNewIdAndUpdateOneField_When_PartialUpdate() {
        //given
        GiftCertificateDto input = GiftCertificateDto.builder().duration(1500).build();
        Integer expectedInputId = input.getId();
        GiftCertificateModel entity = createNewCertificate();
        doReturn(Optional.of(entity)).when(repository).findById(anyInt());
        //when
        GiftCertificateDto output = objectUnderTest.partialUpdate(1, input);
        //then
        assertNotSame(expectedInputId, output.getId());
        assertEquals(input.getDuration(), output.getDuration());
    }

    @Test
    void should_throwException_When_PartialUpdateZeroId() {
        //given
        GiftCertificateDto input = createNewDto();
        doReturn(Optional.empty()).when(repository).findById(0);
        //when & then
        assertThrows(ResourceNotFoundException.class, () -> objectUnderTest.partialUpdate(0, input));
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
        //when & then
        assertThrows(ResourceNotFoundException.class, () -> objectUnderTest.delete(0));
    }

    private static GiftCertificateModel createNewCertificate() {
        return GiftCertificateModel.builder()
                .id(1)
                .name("testSave")
                .description("testDescription")
                .price(BigDecimal.valueOf(1500))
                .duration(10)
                .createDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .build();
    }

    private static PagedModel<GiftCertificateModel> createNewCertificateList() {
        PagedModel.PageMetadata metadata = new PagedModel.PageMetadata(1, 1, 1, 1);
        return PagedModel.of(Stream.generate(CertificateServiceTest::createNewCertificate)
                        .limit(10).toList(),
                metadata);
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
}