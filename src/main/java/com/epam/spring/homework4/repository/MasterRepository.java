package com.epam.spring.homework4.repository;

import com.epam.spring.homework4.model.Master;

import java.util.List;

public interface MasterRepository {
    Master createMaster(Master master);

    Master updateMaster(int id, Master master);

    void deleteMaster(int id);

    Master getMaster(int masterId);

    List<Master> getAllMaster();
}
