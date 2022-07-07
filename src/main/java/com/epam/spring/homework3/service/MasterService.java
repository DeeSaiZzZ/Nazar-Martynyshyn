package com.epam.spring.homework3.service;

import com.epam.spring.homework3.dto.MasterDto;
import com.epam.spring.homework3.model.enums.Speciality;

import java.util.List;

public interface MasterService {
    MasterDto createMaster(MasterDto masterDto);

    MasterDto getMaster(int masterId);

    List<MasterDto> getAllMaster(List<Speciality> filterParam, String sortType);

    void deleteMaster(int id);
}
