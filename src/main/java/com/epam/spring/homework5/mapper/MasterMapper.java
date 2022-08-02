package com.epam.spring.homework5.mapper;

import com.epam.spring.homework5.dto.MasterDto;
import com.epam.spring.homework5.model.Master;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MasterMapper {

    MasterDto masterToMasterDto(Master master);

    Master masterDtoToMaster(MasterDto masterDto);
}
