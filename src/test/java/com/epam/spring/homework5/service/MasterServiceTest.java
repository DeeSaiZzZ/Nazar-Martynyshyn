package com.epam.spring.homework5.service;

import com.epam.spring.homework5.dto.MasterDto;
import com.epam.spring.homework5.dto.OrderDto;
import com.epam.spring.homework5.mapper.MasterMapperImpl;
import com.epam.spring.homework5.mapper.OrderMapperImpl;
import com.epam.spring.homework5.model.CustomPage;
import com.epam.spring.homework5.model.Master;
import com.epam.spring.homework5.model.enums.MasterSortType;
import com.epam.spring.homework5.model.enums.Speciality;
import com.epam.spring.homework5.model.enums.Status;
import com.epam.spring.homework5.model.exeptions.EntityAlreadyExists;
import com.epam.spring.homework5.model.exeptions.EntityNotFoundException;
import com.epam.spring.homework5.model.exeptions.IllegalStateException;
import com.epam.spring.homework5.repository.MasterRepository;
import com.epam.spring.homework5.service.impl.MasterServiceImpl;
import com.epam.spring.homework5.test.TestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MasterServiceTest {

    @InjectMocks
    MasterServiceImpl masterService;

    @Mock
    MasterRepository masterRepository;

    @Spy
    MasterMapperImpl masterMapper;

    @Spy
    OrderMapperImpl orderMapper;

    @Test
    void createMasterTest() {
        Master testMaster = TestUtil.createTestMasterWithoutTimetable();

        when(masterRepository.existsByEmail(testMaster.getEmail())).thenReturn(false);
        when(masterRepository.save(testMaster)).thenReturn(testMaster);

        MasterDto master = masterService.createMaster(masterMapper.masterToMasterDto(testMaster));

        assertThat(master, allOf(
                hasProperty("name", equalTo(testMaster.getName())),
                hasProperty("surname", equalTo(testMaster.getSurname())),
                hasProperty("email", equalTo(testMaster.getEmail())),
                hasProperty("rate", equalTo(testMaster.getRate())),
                hasProperty("speciality", equalTo(testMaster.getSpeciality()))
        ));
    }

    @Test
    void createMasterAlreadyExistTest() {
        Master testMaster = TestUtil.createTestMasterWithoutTimetable();

        when(masterRepository.existsByEmail(testMaster.getEmail())).thenReturn(true);

        assertThrows(EntityAlreadyExists.class,
                () -> masterService.createMaster(masterMapper.masterToMasterDto(testMaster)));
    }

    @Test
    void deleteMasterTest() {
        int testId = 44;

        doNothing().when(masterRepository).deleteById(testId);

        masterService.deleteMaster(testId);

        verify(masterRepository, times(1)).deleteById(testId);
    }

    @Test
    void getMasterTest() {
        Master testMaster = TestUtil.createTestMasterWithoutTimetable();

        when(masterRepository.findById(testMaster.getId())).thenReturn(Optional.of(testMaster));

        MasterDto master = masterService.getMaster(testMaster.getId());

        assertThat(master, allOf(
                hasProperty("name", equalTo(testMaster.getName())),
                hasProperty("surname", equalTo(testMaster.getSurname())),
                hasProperty("email", equalTo(testMaster.getEmail())),
                hasProperty("rate", equalTo(testMaster.getRate())),
                hasProperty("speciality", equalTo(testMaster.getSpeciality()))
        ));
    }

    @Test
    void getMasterNotFoundTest() {
        Master testMaster = TestUtil.createTestMasterWithoutTimetable();

        when(masterRepository.findById(testMaster.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> masterService.getMaster(testMaster.getId()));
    }

    @Test
    void updateMasterTest() {
        Master persistMaster = TestUtil.createTestMasterWithoutTimetable();

        MasterDto newMasterModel = masterMapper.masterToMasterDto(TestUtil.createTestMasterWithoutTimetable());
        newMasterModel.setSpeciality(Speciality.MANICURIST);
        newMasterModel.setName("NewMasterName");

        when(masterRepository.findById(persistMaster.getId())).thenReturn(Optional.of(persistMaster));
        when(masterRepository.save(persistMaster)).thenReturn(persistMaster);

        MasterDto resultMasterDto = masterService.updateMaster(persistMaster.getId(), newMasterModel);

        assertThat(persistMaster, allOf(
                hasProperty("name", equalTo(resultMasterDto.getName())),
                hasProperty("surname", equalTo(resultMasterDto.getSurname())),
                hasProperty("email", equalTo(resultMasterDto.getEmail())),
                hasProperty("rate", equalTo(resultMasterDto.getRate())),
                hasProperty("speciality", equalTo(resultMasterDto.getSpeciality()))
        ));
    }

    @Test
    void updateMasterTheSameModelTest() {
        Master persistMaster = TestUtil.createTestMasterWithoutTimetable();
        MasterDto newMasterModel = masterMapper.masterToMasterDto(TestUtil.createTestMasterWithoutTimetable());

        when(masterRepository.findById(persistMaster.getId())).thenReturn(Optional.of(persistMaster));
        when(masterRepository.save(persistMaster)).thenReturn(persistMaster);

        MasterDto resultMasterDto = masterService.updateMaster(persistMaster.getId(), newMasterModel);

        assertThat(persistMaster, allOf(
                hasProperty("name", equalTo(resultMasterDto.getName())),
                hasProperty("surname", equalTo(resultMasterDto.getSurname())),
                hasProperty("email", equalTo(resultMasterDto.getEmail())),
                hasProperty("rate", equalTo(resultMasterDto.getRate())),
                hasProperty("speciality", equalTo(resultMasterDto.getSpeciality()))
        ));
    }

    @Test
    void updateMasterMasterNotFoundTest() {
        Master persistMaster = TestUtil.createTestMasterWithoutTimetable();
        MasterDto newMasterModel = masterMapper.masterToMasterDto(TestUtil.createTestMasterWithoutTimetable());

        when(masterRepository.findById(persistMaster.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> masterService.updateMaster(persistMaster.getId(), newMasterModel));
    }

    @Test
    void getMastersTimetableTest() throws ParseException {
        Master master = TestUtil.createTestMasterWithTimetable();

        when(masterRepository.findById(master.getId())).thenReturn(Optional.of(master));

        List<OrderDto> mastersTimeTable = masterService.getMastersTimeTable(master.getId());

        assertThat(master.getTimeTable().stream()
                .map(orderMapper::orderToOrderDto)
                .collect(Collectors.toList()), is(mastersTimeTable));
    }

    @Test
    void getMastersTimetableMasterNotFoundTest() throws ParseException {
        Master master = TestUtil.createTestMasterWithTimetable();

        when(masterRepository.findById(master.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> masterService.getMastersTimeTable(master.getId()));
    }

    @Test
    void completeOrderTest() throws ParseException {
        Master master = TestUtil.createTestMasterWithTimetable();

        when(masterRepository.findById(master.getId())).thenReturn(Optional.of(master));
        when(masterRepository.save(master)).thenReturn(master);

        masterService.completeOrder(master.getId(), 2);

        assertTrue(master.getTimeTable().stream()
                .filter(order -> order.getId() == 2)
                .allMatch(order -> order.getOrderStatus().equals(Status.COMPLETE) && order.getCompleteDate() != null));
    }

    @Test
    void completeOrderOrderIsNotReadyToComplete() throws ParseException {
        Master master = TestUtil.createTestMasterWithTimetable();

        when(masterRepository.findById(master.getId())).thenReturn(Optional.of(master));

        assertThrows(IllegalStateException.class,
                () -> masterService.completeOrder(master.getId(), 1));
    }

    @Test
    void completeOrderOrderNotFound() throws ParseException {
        Master master = TestUtil.createTestMasterWithTimetable();

        when(masterRepository.findById(master.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> masterService.completeOrder(master.getId(), 999));
    }

    @Test
    void getAllMasterWithFilterParamWithoutSort() {
        List<Master> masterTestList = TestUtil.createMasterTestList();

        List<Speciality> filterParam = new ArrayList<>(List.of(Speciality.HAIRDRESSER, Speciality.MANICURIST));

        List<Master> expectedList = masterTestList.stream()
                .filter(master -> filterParam.contains(master.getSpeciality()))
                .limit(3)
                .collect(Collectors.toList());

        Pageable pageable = PageRequest.of(TestUtil.START_PAGE_NUM, 3);
        Page<Master> page = new PageImpl<>(expectedList, pageable, 1);

        when(masterRepository.findBySpecialityIn(filterParam, pageable)).thenReturn(page);

        CustomPage result = masterService.getAllMaster(filterParam, null, TestUtil.START_PAGE_NUM);

        assertThat(result, allOf(
                hasProperty("resultList", equalTo(expectedList.stream()
                        .map(masterMapper::masterToMasterDto)
                        .peek(masterDto -> masterDto.setPassword(null))
                        .collect(Collectors.toList()))),
                hasProperty("currentPage", equalTo(TestUtil.START_PAGE_NUM)),
                hasProperty("elementOnPage", equalTo(page.getSize())),
                hasProperty("totalPages", equalTo(page.getTotalPages()))
        ));
    }

    @Test
    void getAllMasterWithFilterParamAndSortByName() {
        List<Master> masterTestList = TestUtil.createMasterTestList();

        List<Speciality> filterParam = new ArrayList<>(List.of(Speciality.BEAUTICIAN, Speciality.MANICURIST));

        List<Master> expectedList = masterTestList.stream()
                .filter(master -> filterParam.contains(master.getSpeciality()))
                .sorted(Comparator.comparing(Master::getName))
                .limit(3)
                .collect(Collectors.toList());

        Pageable pageable = PageRequest.of(TestUtil.START_PAGE_NUM, 3, Sort.by(MasterSortType.NAME.name().toLowerCase()));
        Page<Master> page = new PageImpl<>(expectedList, pageable, 1);

        when(masterRepository.findBySpecialityIn(filterParam, pageable)).thenReturn(page);

        CustomPage result = masterService.getAllMaster(filterParam, MasterSortType.NAME, TestUtil.START_PAGE_NUM);

        assertThat(result, allOf(
                hasProperty("resultList", equalTo(expectedList.stream()
                        .map(masterMapper::masterToMasterDto)
                        .peek(masterDto -> masterDto.setPassword(null))
                        .collect(Collectors.toList()))),
                hasProperty("currentPage", equalTo(TestUtil.START_PAGE_NUM)),
                hasProperty("elementOnPage", equalTo(page.getSize())),
                hasProperty("totalPages", equalTo(page.getTotalPages()))
        ));
    }

    @Test
    void getAllMasterWithFilterParamAndSortByRate() {
        List<Master> masterTestList = TestUtil.createMasterTestList();

        List<Speciality> filterParam = new ArrayList<>(List.of(Speciality.BEAUTICIAN, Speciality.MANICURIST));

        List<Master> expectedList = masterTestList.stream()
                .filter(master -> filterParam.contains(master.getSpeciality()))
                .sorted(Comparator.comparing(Master::getRate))
                .limit(3)
                .collect(Collectors.toList());

        Pageable pageable = PageRequest.of(TestUtil.START_PAGE_NUM, 3, Sort.by(MasterSortType.RATE.name().toLowerCase()));
        Page<Master> page = new PageImpl<>(expectedList, pageable, 1);

        when(masterRepository.findBySpecialityIn(filterParam, pageable)).thenReturn(page);

        CustomPage result = masterService.getAllMaster(filterParam, MasterSortType.RATE, TestUtil.START_PAGE_NUM);

        assertThat(result, allOf(
                hasProperty("resultList", equalTo(expectedList.stream()
                        .map(masterMapper::masterToMasterDto)
                        .peek(masterDto -> masterDto.setPassword(null))
                        .collect(Collectors.toList()))),
                hasProperty("currentPage", equalTo(TestUtil.START_PAGE_NUM)),
                hasProperty("elementOnPage", equalTo(page.getSize())),
                hasProperty("totalPages", equalTo(page.getTotalPages()))
        ));
    }

    @Test
    void getAllMasterWithoutFilterParamWithSortByRate() {
        List<Master> masterTestList = TestUtil.createMasterTestList();

        List<Master> expectedList = masterTestList.stream()
                .sorted(Comparator.comparing(Master::getRate))
                .limit(3)
                .collect(Collectors.toList());

        Pageable pageable = PageRequest.of(TestUtil.START_PAGE_NUM, 3, Sort.by(MasterSortType.RATE.name().toLowerCase()));
        Page<Master> page = new PageImpl<>(expectedList, pageable, 1);

        when(masterRepository.findBySpecialityIn(anyList(), any())).thenReturn(page);

        CustomPage result = masterService.getAllMaster(null, MasterSortType.RATE, TestUtil.START_PAGE_NUM);

        assertThat(result, allOf(
                hasProperty("resultList", equalTo(expectedList.stream()
                        .map(masterMapper::masterToMasterDto)
                        .peek(masterDto -> masterDto.setPassword(null))
                        .collect(Collectors.toList()))),
                hasProperty("currentPage", equalTo(TestUtil.START_PAGE_NUM)),
                hasProperty("elementOnPage", equalTo(page.getSize())),
                hasProperty("totalPages", equalTo(page.getTotalPages()))
        ));
    }

    @Test
    void getAllMasterWithoutFilterParamWithSortByName() {
        List<Master> masterTestList = TestUtil.createMasterTestList();

        List<Master> expectedList = masterTestList.stream()
                .sorted(Comparator.comparing(Master::getName))
                .limit(3)
                .collect(Collectors.toList());

        Pageable pageable = PageRequest.of(TestUtil.START_PAGE_NUM, 3, Sort.by(MasterSortType.NAME.name().toLowerCase()));
        Page<Master> page = new PageImpl<>(expectedList, pageable, 1);

        when(masterRepository.findBySpecialityIn(anyList(), any())).thenReturn(page);

        CustomPage result = masterService.getAllMaster(null, MasterSortType.RATE, TestUtil.START_PAGE_NUM);

        assertThat(result, allOf(
                hasProperty("resultList", equalTo(expectedList.stream()
                        .map(masterMapper::masterToMasterDto)
                        .peek(masterDto -> masterDto.setPassword(null))
                        .collect(Collectors.toList()))),
                hasProperty("currentPage", equalTo(TestUtil.START_PAGE_NUM)),
                hasProperty("elementOnPage", equalTo(page.getSize())),
                hasProperty("totalPages", equalTo(page.getTotalPages()))
        ));
    }
}
