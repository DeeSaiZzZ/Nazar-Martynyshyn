package com.epam.spring.homework5.service;

import com.epam.spring.homework5.dto.FavorDto;
import com.epam.spring.homework5.mapper.FavorMapperImpl;
import com.epam.spring.homework5.model.CustomPage;
import com.epam.spring.homework5.model.Favor;
import com.epam.spring.homework5.model.enums.Speciality;
import com.epam.spring.homework5.model.exeptions.EntityAlreadyExists;
import com.epam.spring.homework5.repository.FavorRepository;
import com.epam.spring.homework5.service.impl.FavorServiceImpl;
import com.epam.spring.homework5.test.TestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FavorServiceTest {

    @InjectMocks
    FavorServiceImpl favorService;

    @Mock
    FavorRepository favorRepository;

    @Spy
    FavorMapperImpl favorMapper;

    @Test
    void getFavorTest() {
        Favor testFavor = TestUtil.createTestFavor();

        when(favorRepository.findById(testFavor.getId())).thenReturn(Optional.of(testFavor));

        FavorDto favor = favorService.getFavor(testFavor.getId());

        assertThat(favor, allOf(
                hasProperty("name", equalTo(testFavor.getName())),
                hasProperty("speciality", equalTo(testFavor.getSpeciality())),
                hasProperty("price", equalTo(testFavor.getPrice()))
        ));

    }

    @Test
    void favorNotFoundTest() {
        when(favorRepository.findById(anyInt())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> favorService.getFavor(anyInt()));
    }

    @Test
    void getAllFavorWithFilterParam() {
        List<Favor> favorTestList = TestUtil.createFavorTestList();

        List<Speciality> filterParam = new ArrayList<>(List.of(Speciality.HAIRDRESSER, Speciality.BEAUTICIAN));

        List<Favor> expectedList = favorTestList.stream()
                .filter(favor -> filterParam.contains(favor.getSpeciality()))
                .limit(3)
                .collect(Collectors.toList());

        Pageable pageable = PageRequest.of(TestUtil.START_PAGE_NUM, 3);
        Page<Favor> page = new PageImpl<>(expectedList, pageable, 1);

        when(favorRepository.findAllBySpecialityIn(filterParam, pageable)).thenReturn(page);

        CustomPage result = favorService.getAllFavor(filterParam, TestUtil.START_PAGE_NUM);
        assertThat(result, allOf(
                hasProperty("resultList", equalTo(expectedList.stream()
                        .map(favor -> favorMapper.favorToFavorDto(favor))
                        .collect(Collectors.toList()))),
                hasProperty("currentPage", equalTo(TestUtil.START_PAGE_NUM)),
                hasProperty("elementOnPage", equalTo(page.getSize())),
                hasProperty("totalPages", equalTo(page.getTotalPages()))
        ));
    }

    @Test
    void getAllFavorWithoutFilterParam() {
        List<Favor> favorTestList = TestUtil.createFavorTestList();

        List<Favor> expectedList = favorTestList.stream().limit(3).collect(Collectors.toList());

        Pageable pageable = PageRequest.of(TestUtil.START_PAGE_NUM, 3);
        Page<Favor> page = new PageImpl<>(expectedList, pageable, 1);

        when(favorRepository.findAllBySpecialityIn(anyList(), eq(pageable))).thenReturn(page);

        CustomPage result = favorService.getAllFavor(null, TestUtil.START_PAGE_NUM);

        assertThat(result, allOf(
                hasProperty("resultList", equalTo(expectedList.stream()
                        .map(favorMapper::favorToFavorDto)
                        .collect(Collectors.toList()))),
                hasProperty("currentPage", equalTo(TestUtil.START_PAGE_NUM)),
                hasProperty("elementOnPage", equalTo(page.getSize())),
                hasProperty("totalPages", equalTo(page.getTotalPages()))
        ));
    }

    @Test
    void createFavorTest() {
        Favor testFavor = TestUtil.createTestFavor();

        when(favorRepository.existsByName(testFavor.getName())).thenReturn(false);
        when(favorRepository.save(testFavor)).thenReturn(testFavor);

        FavorDto favor = favorService.createFavor(favorMapper.favorToFavorDto(testFavor));

        assertThat(favor, allOf(
                hasProperty("name", equalTo(testFavor.getName())),
                hasProperty("speciality", equalTo(testFavor.getSpeciality())),
                hasProperty("price", equalTo(testFavor.getPrice()))
        ));
    }

    @Test
    void createFavorAlreadyExistTest() {
        Favor testFavor = TestUtil.createTestFavor();

        when(favorRepository.existsByName(testFavor.getName())).thenReturn(true);

        assertThrows(EntityAlreadyExists.class,
                () -> favorService.createFavor(favorMapper.favorToFavorDto(testFavor)));
    }

    @Test
    void updateFavorTest() {
        Favor persistFavor = TestUtil.createTestFavor();

        Favor updateFavorModel = TestUtil.createTestFavor();
        updateFavorModel.setName("New name");

        when(favorRepository.findById(persistFavor.getId())).thenReturn(Optional.of(persistFavor));

        favorService.updateFavor(persistFavor.getId(), favorMapper.favorToFavorDto(updateFavorModel));

        verify(favorRepository, times(1)).save(persistFavor);
        assertEquals(persistFavor, updateFavorModel);
    }

    @Test
    void updateFavorTheSameModelTest() {
        Favor persistFavor = TestUtil.createTestFavor();
        Favor updateFavorModel = TestUtil.createTestFavor();

        when(favorRepository.findById(persistFavor.getId())).thenReturn(Optional.of(persistFavor));

        favorService.updateFavor(persistFavor.getId(), favorMapper.favorToFavorDto(updateFavorModel));

        verify(favorRepository, times(1)).save(persistFavor);
        assertEquals(persistFavor, updateFavorModel);
    }

    @Test
    void updateFavorFavorNoFoundTest() {
        Favor testFavor = TestUtil.createTestFavor();

        when(favorRepository.findById(testFavor.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> favorService.updateFavor(testFavor.getId(), favorMapper.favorToFavorDto(testFavor)));
    }

    @Test
    void deleteFavorTest() {
        int testId = 44;

        doNothing().when(favorRepository).deleteById(testId);

        favorService.deleteFavor(testId);

        verify(favorRepository, times(1)).deleteById(testId);
    }
}
