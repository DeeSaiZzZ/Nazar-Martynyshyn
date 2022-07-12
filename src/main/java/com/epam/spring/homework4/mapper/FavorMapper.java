package com.epam.spring.homework4.mapper;

import com.epam.spring.homework4.dto.FavorDto;
import com.epam.spring.homework4.model.Favor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FavorMapper {
    Favor favorDtoToFavor(FavorDto favorDto);

    FavorDto favorToFavorDto(Favor favor);
}
