package com.epam.spring.homework5.service;

import com.epam.spring.homework5.dto.MasterDto;
import com.epam.spring.homework5.dto.OrderDto;
import com.epam.spring.homework5.model.CustomPage;
import com.epam.spring.homework5.model.enums.MasterSortType;
import com.epam.spring.homework5.model.enums.Speciality;

import java.util.List;

public interface MasterService {
    MasterDto createMaster(MasterDto masterDto);

    MasterDto getMaster(int masterId);

    MasterDto updateMaster(int id, MasterDto masterDto);

    CustomPage getAllMaster(List<Speciality> filterParam, MasterSortType sortType, int pageNum);

    List<OrderDto> getMastersTimeTable(int id);

    void completeOrder(int masterId, int orderId);

    void deleteMaster(int id);
}
