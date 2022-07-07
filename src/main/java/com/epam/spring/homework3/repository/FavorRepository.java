package com.epam.spring.homework3.repository;

import com.epam.spring.homework3.model.Favor;

import java.util.List;

public interface FavorRepository {
    List<Favor> getAllFavor();

    Favor getFavor(int id);

    Favor createFavor(Favor favor);

    void deleteFavor(int id);
}
