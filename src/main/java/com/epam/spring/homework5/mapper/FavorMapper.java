package com.epam.spring.homework5.mapper;

import com.epam.spring.homework5.dto.FavorDto;
import com.epam.spring.homework5.model.Favor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FavorMapper {
    Favor favorDtoToFavor(FavorDto favorDto);

    FavorDto favorToFavorDto(Favor favor);
}
