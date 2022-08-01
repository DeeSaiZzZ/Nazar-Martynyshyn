package com.epam.spring.homework5.controller;

import com.epam.spring.homework5.dto.FavorDto;
import com.epam.spring.homework5.mapper.FavorMapperImpl;
import com.epam.spring.homework5.model.CustomPage;
import com.epam.spring.homework5.model.enums.Speciality;
import com.epam.spring.homework5.service.FavorService;
import com.epam.spring.homework5.test.TestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(FavorController.class)
class FavorControllerTest {

    @MockBean
    private FavorService favorService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Spy
    private FavorMapperImpl favorMapper;

    @Test
    void getFavorTest() throws Exception {
        FavorDto favorDto = new FavorDto();
        favorDto.setName("FavorTestName");

        when(favorService.getFavor(anyInt())).thenReturn(favorDto);

        mockMvc.perform(get("/favor/" + anyInt()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("FavorTestName"));
    }

    @Test
    void getFavorsWithFilterParamTest() throws Exception {
        int page = 0;
        int elementOnPage = 3;

        List<Speciality> filterParam = List.of(Speciality.HAIRDRESSER, Speciality.MANICURIST);

        List<Object> favorDtoList = TestUtil.createFavorTestList()
                .stream()
                .filter(favor -> filterParam.contains(favor.getSpeciality()))
                .limit(elementOnPage)
                .map(favorMapper::favorToFavorDto)
                .collect(Collectors.toList());

        CustomPage customPage = new CustomPage(favorDtoList, page,
                favorDtoList.size() / elementOnPage, elementOnPage);

        when(favorService.getAllFavor(filterParam, page)).thenReturn(customPage);

        mockMvc.perform(get("/favor")
                        .param("filterParam", Speciality.HAIRDRESSER.name())
                        .param("filterParam", Speciality.MANICURIST.name())
                        .param("pageNum", String.valueOf(page)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.resultList[*].name").value(favorDtoList.stream()
                        .map(o -> ((FavorDto) o).getName())
                        .collect(Collectors.toList())));
    }

    @Test
    void getFavorsWithoutFilterParamTest() throws Exception {
        int page = 0;
        int elementOnPage = 3;

        List<Object> favorDtoList = TestUtil.createFavorTestList()
                .stream()
                .limit(elementOnPage)
                .map(favorMapper::favorToFavorDto)
                .collect(Collectors.toList());

        CustomPage customPage = new CustomPage(favorDtoList, page, favorDtoList.size() / elementOnPage, elementOnPage);

        when(favorService.getAllFavor(null, page)).thenReturn(customPage);

        mockMvc.perform(get("/favor")
                        .param("pageNum", String.valueOf(page)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.resultList[*].name").value(favorDtoList.stream()
                        .map(o -> ((FavorDto) o).getName())
                        .collect(Collectors.toList())));
    }

    @Test
    void createFavorTest() throws Exception {
        FavorDto testFavor = favorMapper.favorToFavorDto(TestUtil.createTestFavor());

        when(favorService.createFavor(testFavor)).thenReturn(testFavor);

        mockMvc.perform(post("/favor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(testFavor)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(testFavor.getName()));
    }

    @Test
    void createFavorWithEmptyNameTest() throws Exception {
        FavorDto testFavor = favorMapper.favorToFavorDto(TestUtil.createTestFavor());
        String expectedErrorMessage = "Name shouldn't be empty";

        testFavor.setName(null);

        mockMvc.perform(post("/favor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(testFavor)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].message").value(expectedErrorMessage));
    }

    @Test
    void createFavorWithNegativePriceTest() throws Exception {
        FavorDto testFavor = favorMapper.favorToFavorDto(TestUtil.createTestFavor());
        String expectedErrorMessage = "Price cannot be negative";

        testFavor.setPrice(-500);

        mockMvc.perform(post("/favor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(testFavor)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].message").value(expectedErrorMessage));
    }

    @Test
    void createFavorWithoutSpecialityTest() throws Exception {
        FavorDto testFavor = favorMapper.favorToFavorDto(TestUtil.createTestFavor());
        String expectedErrorMessage = "must not be null";

        testFavor.setSpeciality(null);

        mockMvc.perform(post("/favor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(testFavor)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].message").value(expectedErrorMessage));
    }

    @Test
    void updateFavorTest() throws Exception {
        FavorDto testFavor = favorMapper.favorToFavorDto(TestUtil.createTestFavor());

        when(favorService.updateFavor(testFavor.getId(), testFavor)).thenReturn(testFavor);

        mockMvc.perform(put("/favor/" + testFavor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(testFavor)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(testFavor.getName()));
    }

    @Test
    void updateFavorNewModelWithEmptyNameTest() throws Exception {
        FavorDto testFavor = favorMapper.favorToFavorDto(TestUtil.createTestFavor());
        String expectedErrorMessage = "Name shouldn't be empty";

        testFavor.setName("");

        mockMvc.perform(put("/favor/" + testFavor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(testFavor)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].message").value(expectedErrorMessage));
    }

    @Test
    void updateFavorWithoutSpecialityTest() throws Exception {
        FavorDto testFavor = favorMapper.favorToFavorDto(TestUtil.createTestFavor());
        String expectedErrorMessage = "must not be null";

        testFavor.setSpeciality(null);

        mockMvc.perform(put("/favor/" + testFavor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(testFavor)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].message").value(expectedErrorMessage));
    }

    @Test
    void updateFavorWithNegativePriceTest() throws Exception {
        FavorDto testFavor = favorMapper.favorToFavorDto(TestUtil.createTestFavor());
        String expectedErrorMessage = "Price cannot be negative";

        testFavor.setPrice(-500);

        mockMvc.perform(put("/favor/" + testFavor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(testFavor)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].message").value(expectedErrorMessage));
    }

    @Test
    void deleteFavorTest() throws Exception {
        doNothing().when(favorService).deleteFavor(anyInt());

        mockMvc.perform(delete("/favor/" + anyInt()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
