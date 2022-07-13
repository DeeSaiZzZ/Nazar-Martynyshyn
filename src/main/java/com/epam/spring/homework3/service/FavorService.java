package com.epam.spring.homework3.service;

import com.epam.spring.homework3.dto.FavorDto;
import com.epam.spring.homework3.model.enums.Speciality;

import java.util.List;

public interface FavorService {
    List<FavorDto> getAllFavor(List<Speciality> filterParam);

    FavorDto getFavor(int id);

    FavorDto createFavor(FavorDto favorDto);

    FavorDto updateFavor(int id, FavorDto favorDto);

    void deleteFavor(int id);
}
