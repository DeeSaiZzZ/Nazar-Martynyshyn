package com.epam.spring.homework3.repository;

import com.epam.spring.homework3.model.Master;

import java.util.List;

public interface MasterRepository {
    Master createMaster(Master master);

    void deleteMaster(int id);

    Master getMaster(int masterId);

    List<Master> getAllMaster();
}
