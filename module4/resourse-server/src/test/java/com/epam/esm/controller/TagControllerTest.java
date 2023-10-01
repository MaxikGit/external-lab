package com.epam.esm.controller;

import com.epam.esm.common.controller.advice.ErrorCode;
import com.epam.esm.tag.controller.TagController;
import com.epam.esm.tag.dto.TagDto;
import com.epam.esm.tag.representation.TagLinkAssembler;
import com.epam.esm.tag.service.TagService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.MediaTypes;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TagController.class)
@Import(TagLinkAssembler.class)
@WithMockUser(username="customUsername@example.io", authorities = "USER")
@TestPropertySource(properties = {"spring.data.web.pageable.page-parameter=offset"})
class TagControllerTest {

    @MockBean
    private TagService tagService;

    @Autowired
    private MockMvc mvc;

    @Test
    void should_ReturnJsonWithTagsAndLinks_When_GetTags() throws Exception {
        //given
        Pageable pageable = PageRequest.of(0, 20, Sort.Direction.ASC, "id");
        TagDto tagDto1 = new TagDto(1, "First tag");
        TagDto tagDto2 = new TagDto(2, "Second tag");
        when(tagService.find(anyString(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(tagDto1, tagDto2), pageable, 2));
        //when & then
        mvc.perform(MockMvcRequestBuilders.get("/tags")
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$._embedded.tagDtoList.size()").value(2))
                .andExpect(jsonPath("$._embedded.tagDtoList[0].id").value(1))
                .andExpect(jsonPath("$._embedded.tagDtoList[0]._links.self.size()").value(2))
                .andExpect(jsonPath("$._embedded.tagDtoList[1].id").value(2))
                .andExpect(jsonPath("$._embedded.tagDtoList[1]._links.self.size()").value(2))
                .andExpect(jsonPath("$._links.self.href")
                        .value("http://localhost/tags?offset=0&size=20&sort=id,asc"))
                .andExpect(jsonPath("$.page").isNotEmpty());
    }

    @Test
    void should_ReturnJsonWithTagAndLinks_When_GetById() throws Exception {
        //given
        TagDto tagDto1 = new TagDto(35, "First_tag");
        when(tagService.findById(35)).thenReturn(tagDto1);
        //when & then
        mvc.perform(MockMvcRequestBuilders.get("/tags/" + 35)
                        .with(csrf())
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.id").value(35))
                .andExpect(jsonPath("$.name").value("First_tag"))
                .andExpect(jsonPath("$._links.self.size()").value(2))
                .andExpect(jsonPath("$._links.self[0].href").value("http://localhost/tags/" + 35))
                .andExpect(jsonPath("$._links.self[1].href").value("http://localhost/tags/name/" + "First_tag"));
    }

    @Test
    void should_ReturnNoContent_When_DeleteById() throws Exception {
        //given & when & then
        mvc.perform(MockMvcRequestBuilders.delete("/tags/5")
                .with(csrf()))
                .andExpect(status().isNoContent());
        verify(tagService, only()).delete(5);
    }

    @Test
    void should_ConvertDtoToJson_When_TagsPost() throws Exception {
        //given & when & then
        TagDto tagDto1 = new TagDto(35, "First_tag");
        when(tagService.save(tagDto1)).thenReturn(tagDto1);
        mvc.perform(MockMvcRequestBuilders.post("/tags/")
                        .with(csrf())
                        .contentType(MediaTypes.HAL_JSON)
                        .content(new ObjectMapper().writeValueAsString(tagDto1)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.id").value(35))
                .andExpect(jsonPath("$.name").value("First_tag"))
                .andExpect(jsonPath("$._links.self.size()").value(2))
                .andExpect(jsonPath("$._links.self[0].href").value("http://localhost/tags/" + 35));
    }

    @Test
    void should_ReturnError_When_NameTooShort() throws Exception {
        //given
        TagDto tagDto1 = new TagDto(1, "N");
        when(tagService.save(tagDto1)).thenReturn(tagDto1);
        //when & then
        mvc.perform(MockMvcRequestBuilders.post("/tags/")
                        .with(csrf())
                        .contentType(MediaTypes.HAL_JSON)
                        .content(new ObjectMapper().writeValueAsString(tagDto1)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage.name")
                        .value("Name should be between 2 and 30 characters"))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.VALIDATION_ERROR.getValue()));
    }

    @Test
    void should_ReturnError_When_NameEmpty() throws Exception {
        //given
        TagDto tagDto1 = new TagDto(null, "");
        when(tagService.save(tagDto1)).thenReturn(tagDto1);
        //when & then
        mvc.perform(MockMvcRequestBuilders.post("/tags/")
                        .with(csrf())
                        .contentType(MediaTypes.HAL_JSON)
                        .content(new ObjectMapper().writeValueAsString(tagDto1)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Name should not be empty")))
                .andExpect(content().string(containsString("Name should be between 2 and 30 characters")))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.VALIDATION_ERROR.getValue()));
    }
}