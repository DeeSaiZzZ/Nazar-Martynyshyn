package com.epam.spring.homework3.service;

import com.epam.spring.homework3.dto.MasterDto;
import com.epam.spring.homework3.model.enums.MasterSortType;
import com.epam.spring.homework3.model.enums.Speciality;

import java.util.List;

public interface MasterService {
    MasterDto createMaster(MasterDto masterDto);

    MasterDto getMaster(int masterId);

    MasterDto updateMaster(int id, MasterDto masterDto);

    List<MasterDto> getAllMaster(List<Speciality> filterParam, MasterSortType sortType);

    void deleteMaster(int id);
}
