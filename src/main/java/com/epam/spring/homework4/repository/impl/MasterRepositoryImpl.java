package com.epam.spring.homework4.repository.impl;

import com.epam.spring.homework4.model.Master;
import com.epam.spring.homework4.model.enums.Role;
import com.epam.spring.homework4.model.exeptions.EntityNotFoundException;
import com.epam.spring.homework4.repository.MasterRepository;
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
    public Master updateMaster(int id, Master master) {
        Master updatableMaster = masterList.stream()
                .filter(o -> o.getId() == id)
                .findFirst()
                .orElseThrow(EntityNotFoundException::new);
        updatableMaster.update(master);
        return updatableMaster;
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
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<Master> getAllMaster() {
        log.info("Get all master");
        return new ArrayList<>(masterList);
    }
}
