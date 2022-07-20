package com.epam.spring.homework4.service;

import com.epam.spring.homework4.dto.MasterDto;
import com.epam.spring.homework4.model.CustomPage;
import com.epam.spring.homework4.model.enums.MasterSortType;
import com.epam.spring.homework4.model.enums.Speciality;

import java.util.List;

public interface MasterService {
    MasterDto createMaster(MasterDto masterDto);

    MasterDto getMaster(int masterId);

    MasterDto updateMaster(int id, MasterDto masterDto);

    CustomPage getAllMaster(List<Speciality> filterParam, MasterSortType sortType, int pageNum);

    void deleteMaster(int id);
}
