package com.epam.spring.homework5.controller;

import com.epam.spring.homework5.dto.MasterDto;
import com.epam.spring.homework5.dto.OrderDto;
import com.epam.spring.homework5.mapper.MasterMapperImpl;
import com.epam.spring.homework5.mapper.OrderMapperImpl;
import com.epam.spring.homework5.model.CustomPage;
import com.epam.spring.homework5.model.enums.MasterSortType;
import com.epam.spring.homework5.model.enums.Speciality;
import com.epam.spring.homework5.service.MasterService;
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

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MasterController.class)
class MasterControllerTest {

    @MockBean
    private MasterService masterService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Spy
    private MasterMapperImpl masterMapper;

    @Spy
    private OrderMapperImpl orderMapper;

    @Test
    void getMasterTest() throws Exception {
        MasterDto testMaster = masterMapper.masterToMasterDto(TestUtil.createTestMasterWithoutTimetable());

        when(masterService.getMaster(testMaster.getId())).thenReturn(testMaster);

        mockMvc.perform(get("/master/" + testMaster.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value(testMaster.getEmail()));
    }

    @Test
    void getMastersWithoutFilterParamAndSortTest() throws Exception {
        int page = 0;
        int elementOnPage = 3;

        List<MasterDto> masterTestList = TestUtil.createMasterTestList().stream()
                .map(masterMapper::masterToMasterDto)
                .collect(Collectors.toList());

        List<Object> expectedList = masterTestList.stream()
                .limit(elementOnPage)
                .collect(Collectors.toList());

        CustomPage customPage = new CustomPage(expectedList, page,
                masterTestList.size() / elementOnPage, elementOnPage);

        when(masterService.getAllMaster(null, null, page)).thenReturn(customPage);

        mockMvc.perform(get("/master")
                        .param("pageNum", String.valueOf(page)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.resultList.size()").value(expectedList.size()))
                .andExpect(jsonPath("$.resultList[*].email").value(expectedList.stream()
                        .map(o -> ((MasterDto) o).getEmail())
                        .collect(Collectors.toList())))
                .andExpect(jsonPath("$.currentPage").value(page));
    }

    @Test
    void getMastersWithFilterParamWithoutSortTest() throws Exception {
        int page = 0;
        int elementOnPage = 3;

        List<MasterDto> masterTestList = TestUtil.createMasterTestList().stream()
                .map(masterMapper::masterToMasterDto)
                .collect(Collectors.toList());

        List<Speciality> filterParam = List.of(Speciality.HAIRDRESSER, Speciality.BEAUTICIAN);

        List<Object> expectedList = masterTestList.stream()
                .filter(masterDto -> filterParam.contains(masterDto.getSpeciality()))
                .limit(elementOnPage)
                .collect(Collectors.toList());

        CustomPage customPage = new CustomPage(expectedList, page,
                masterTestList.size() / elementOnPage, elementOnPage);

        when(masterService.getAllMaster(filterParam, null, page)).thenReturn(customPage);

        mockMvc.perform(get("/master")
                        .param("pageNum", String.valueOf(page))
                        .param("filterParam", filterParam.get(0).name())
                        .param("filterParam", filterParam.get(1).name()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.resultList.size()").value(expectedList.size()))
                .andExpect(jsonPath("$.resultList[*].email").value(expectedList.stream()
                        .map(o -> ((MasterDto) o).getEmail())
                        .collect(Collectors.toList())))
                .andExpect(jsonPath("$.currentPage").value(page));
    }

    @Test
    void getMastersWithoutFilterParamWithSortTest() throws Exception {
        int page = 0;
        int elementOnPage = 3;

        List<MasterDto> masterTestList = TestUtil.createMasterTestList().stream()
                .map(masterMapper::masterToMasterDto)
                .collect(Collectors.toList());

        List<Object> expectedList = masterTestList.stream()
                .sorted(Comparator.comparing(MasterDto::getName))
                .limit(elementOnPage)
                .collect(Collectors.toList());

        CustomPage customPage = new CustomPage(expectedList, page,
                masterTestList.size() / elementOnPage, elementOnPage);

        when(masterService.getAllMaster(null, MasterSortType.NAME, page)).thenReturn(customPage);

        mockMvc.perform(get("/master")
                        .param("pageNum", String.valueOf(page))
                        .param("sortType", MasterSortType.NAME.name()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.resultList.size()").value(expectedList.size()))
                .andExpect(jsonPath("$.resultList[*].email").value(expectedList.stream()
                        .map(o -> ((MasterDto) o).getEmail())
                        .collect(Collectors.toList())))
                .andExpect(jsonPath("$.currentPage").value(page));
    }

    @Test
    void getMastersWithFilterParamAndSortTest() throws Exception {
        int page = 0;
        int elementOnPage = 3;

        List<MasterDto> masterTestList = TestUtil.createMasterTestList().stream()
                .map(masterMapper::masterToMasterDto)
                .collect(Collectors.toList());

        List<Speciality> filterParam = List.of(Speciality.HAIRDRESSER, Speciality.BEAUTICIAN);

        List<Object> expectedList = masterTestList.stream()
                .filter(masterDto -> filterParam.contains(masterDto.getSpeciality()))
                .sorted(Comparator.comparing(MasterDto::getName))
                .limit(elementOnPage)
                .collect(Collectors.toList());

        CustomPage customPage = new CustomPage(expectedList, page,
                masterTestList.size() / elementOnPage, elementOnPage);

        when(masterService.getAllMaster(filterParam, MasterSortType.NAME, page)).thenReturn(customPage);

        mockMvc.perform(get("/master")
                        .param("pageNum", String.valueOf(page))
                        .param("filterParam", filterParam.get(0).name())
                        .param("filterParam", filterParam.get(1).name())
                        .param("sortType", MasterSortType.NAME.name()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.resultList.size()").value(expectedList.size()))
                .andExpect(jsonPath("$.resultList[*].email").value(expectedList.stream()
                        .map(o -> ((MasterDto) o).getEmail())
                        .collect(Collectors.toList())))
                .andExpect(jsonPath("$.currentPage").value(page));
    }

    @Test
    void createMasterTest() throws Exception {
        MasterDto testMaster = masterMapper.masterToMasterDto(TestUtil.createTestMasterWithoutTimetable());

        testMaster.setPassword("testPassword12345");
        testMaster.setRate(0.0);

        when(masterService.createMaster(testMaster)).thenReturn(testMaster);

        mockMvc.perform(post("/master")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testMaster)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(testMaster));
    }

    @Test
    void createMasterBadInputModelTest() throws Exception {
        MasterDto testMaster = new MasterDto();

        testMaster.setRate(3.2);

        mockMvc.perform(post("/master")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testMaster)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(7));
    }

    @Test
    void updateMasterTest() throws Exception {
        MasterDto testMaster = masterMapper.masterToMasterDto(TestUtil.createTestMasterWithoutTimetable());

        when(masterService.updateMaster(testMaster.getId(), testMaster)).thenReturn(testMaster);

        mockMvc.perform(put("/master/" + testMaster.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testMaster)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value(testMaster.getEmail()));
    }

    @Test
    void updateMasterWithNotNullPasswordTest() throws Exception {
        MasterDto testMaster = masterMapper.masterToMasterDto(TestUtil.createTestMasterWithoutTimetable());
        testMaster.setPassword("testPass");

        String expectedMessage = "Password should be empty when entity update";

        when(masterService.updateMaster(testMaster.getId(), testMaster)).thenReturn(testMaster);

        mockMvc.perform(put("/master/" + testMaster.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testMaster)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].message").value(expectedMessage));
    }

    @Test
    void completeOrderTest() throws Exception {
        int testOrderId = 33;
        int testMasterId = 55;

        doNothing().when(masterService).completeOrder(testMasterId, testOrderId);

        mockMvc.perform(patch("/master/" + testMasterId
                        + "/time-table/" + testOrderId))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(masterService, times(1)).completeOrder(testMasterId, testOrderId);
    }

    @Test
    void getTimetableTest() throws Exception {
        List<OrderDto> orderDtoList = TestUtil.createTestOrderList().stream()
                .map(orderMapper::orderToOrderDto)
                .collect(Collectors.toList());

        when(masterService.getMastersTimeTable(anyInt())).thenReturn(orderDtoList);

        mockMvc.perform(get("/master/" + 33 + "/time-table"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(orderDtoList.size()));
    }

    @Test
    void deleteMasterTest() throws Exception {
        doNothing().when(masterService).deleteMaster(anyInt());

        mockMvc.perform(delete("/master/" + anyInt()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}