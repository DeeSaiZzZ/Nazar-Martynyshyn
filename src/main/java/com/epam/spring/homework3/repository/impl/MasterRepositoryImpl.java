package com.epam.spring.homework3.repository.impl;

import com.epam.spring.homework3.model.Master;
import com.epam.spring.homework3.model.enums.Role;
import com.epam.spring.homework3.repository.MasterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class MasterRepositoryImpl implements MasterRepository {

    private final List<Master> masterList;
    private int idCounter = 1;

    @Override
    public Master createMaster(Master master) {
        log.info("Create master");
        log.trace("Master id = {}", idCounter);
        master.setId(idCounter++);
        master.setRole(Role.MASTER);
        masterList.add(master);
        return master;
    }

    @Override
    public void deleteMaster(int id) {
        log.info("Delete master by id {}", id);
        masterList.removeIf(master -> master.getId() == id);
    }

    @Override
    public Master getMaster(int id) {
        log.info("Get master by id {}", id);
        return masterList.stream()
                .filter(master -> master.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Master> getAllMaster() {
        log.info("Get all master");
        return new ArrayList<>(masterList);
    }
}
