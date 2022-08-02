package com.epam.spring.homework5.service;

import com.epam.spring.homework5.dto.FavorDto;
import com.epam.spring.homework5.model.CustomPage;
import com.epam.spring.homework5.model.enums.Speciality;

import java.util.List;

public interface FavorService {
    CustomPage getAllFavor(List<Speciality> filterParam, int pageNum);

    FavorDto getFavor(int id);

    FavorDto createFavor(FavorDto favorDto);

    FavorDto updateFavor(int id, FavorDto favorDto);

    void deleteFavor(int id);
}
