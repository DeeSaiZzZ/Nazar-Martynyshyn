package com.epam.spring.homework3.mapper;

import com.epam.spring.homework3.dto.MasterDto;
import com.epam.spring.homework3.model.Master;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MasterMapper {
    MasterDto masterToMasterDto(Master master);

    Master masterDtoToMaster(MasterDto masterDto);
}
