package com.epam.spring.homework3.service;

import com.epam.spring.homework3.dto.FavorDto;
import com.epam.spring.homework3.model.enums.Speciality;

import java.util.List;

public interface FavorService {
    List<FavorDto> getAllFavor(List<Speciality> filterParam);

    FavorDto createFavor(FavorDto favorDto);

    void deleteFavor(int id);
}
