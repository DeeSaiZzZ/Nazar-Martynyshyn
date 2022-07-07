package com.epam.spring.homework3.service.impl;

import com.epam.spring.homework3.dto.FavorDto;
import com.epam.spring.homework3.mapper.FavorMapper;
import com.epam.spring.homework3.model.Favor;
import com.epam.spring.homework3.model.enums.Speciality;
import com.epam.spring.homework3.repository.FavorRepository;
import com.epam.spring.homework3.service.FavorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FavorServiceImpl implements FavorService {

    private final FavorRepository favorRepository;
    private final FavorMapper mapper;

    @Override
    public List<FavorDto> getAllFavor(List<Speciality> filterParam) {
        log.info("Start get all favour with param: {}", filterParam);
        return favorRepository
                .getAllFavor()
                .stream()
                .filter(filterParam != null ? favor -> filterParam.contains(favor.getSpeciality()) : favor -> true)
                .map(mapper::favorToFavorDto)
                .collect(Collectors.toList());
    }

    @Override
    public FavorDto createFavor(FavorDto favorDto) {
        log.info("Start create favour");
        Favor favor = mapper.favorDtoToFavor(favorDto);
        log.trace("New entity - {}", favor);
        favor = favorRepository.createFavor(favor);
        return mapper.favorToFavorDto(favor);
    }

    @Override
    public void deleteFavor(int id) {
        log.info("Delete favour by id {}", id);
        favorRepository.deleteFavor(id);
    }
}
