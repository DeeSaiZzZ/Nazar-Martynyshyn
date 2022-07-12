package com.epam.spring.homework4.repository;

import com.epam.spring.homework4.model.Favor;

import java.util.List;

public interface FavorRepository {
    List<Favor> getAllFavor();

    Favor getFavor(int id);

    Favor createFavor(Favor favor);

    Favor updateFavor(int id, Favor favor);

    void deleteFavor(int id);
}
