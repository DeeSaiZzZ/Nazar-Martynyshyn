package com.epam.spring.homework4.mapper;

import com.epam.spring.homework4.dto.MasterDto;
import com.epam.spring.homework4.model.Master;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MasterMapper {

    MasterDto masterToMasterDto(Master master);

    Master masterDtoToMaster(MasterDto masterDto);
}
