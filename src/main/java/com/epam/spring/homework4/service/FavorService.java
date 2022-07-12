package com.epam.spring.homework4.service;

import com.epam.spring.homework4.dto.FavorDto;
import com.epam.spring.homework4.model.enums.Speciality;

import java.util.List;

public interface FavorService {
    List<FavorDto> getAllFavor(List<Speciality> filterParam);

    FavorDto getFavor(int id);

    FavorDto createFavor(FavorDto favorDto);

    FavorDto updateFavor(int id, FavorDto favorDto);

    void deleteFavor(int id);
}
