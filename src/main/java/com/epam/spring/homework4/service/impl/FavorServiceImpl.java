package com.epam.spring.homework4.service.impl;

import com.epam.spring.homework4.dto.FavorDto;
import com.epam.spring.homework4.mapper.FavorMapper;
import com.epam.spring.homework4.model.CustomPage;
import com.epam.spring.homework4.model.Favor;
import com.epam.spring.homework4.model.enums.Speciality;
import com.epam.spring.homework4.model.exeptions.EntityAlreadyExists;
import com.epam.spring.homework4.repository.FavorRepository;
import com.epam.spring.homework4.service.FavorService;
import com.epam.spring.homework4.utils.FilterParamManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FavorServiceImpl implements FavorService {

    private final FavorRepository favorRepository;
    private final FavorMapper mapper;

    @Override
    public CustomPage getAllFavor(List<Speciality> filterParam, int pageNum) {
        log.info("Start get all favour with param: {}", filterParam);

        Pageable pageable = PageRequest.of(pageNum, 3);
        filterParam = FilterParamManager.manageFilterParam(filterParam);
        Page<Favor> result = favorRepository.findAllBySpecialityIn(filterParam, pageable);

        return new CustomPage(result.stream().
                map(mapper::favorToFavorDto)
                .collect(Collectors.toList()), pageNum, result.getTotalPages(), result.getSize());
    }

    @Override
    public FavorDto getFavor(int id) {
        Favor favor = favorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Favor with id-%d not found", id)));
        return mapper.favorToFavorDto(favor);
    }

    @Override
    public FavorDto createFavor(FavorDto favorDto) {
        log.info("Start create favour");
        if (favorRepository.existsByName(favorDto.getName())) {
            throw new EntityAlreadyExists(String.format("Favor with name %s already exists", favorDto.getName()));
        }

        Favor favor = mapper.favorDtoToFavor(favorDto);
        log.trace("New entity - {}", favor);
        favor = favorRepository.save(favor);

        return mapper.favorToFavorDto(favor);
    }

    @Override
    public FavorDto updateFavor(int id, FavorDto favorDto) {
        Favor favorForUpdate = mapper.favorDtoToFavor(favorDto);
        Favor persistFavor = favorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Favor with id-%d not found", id)));

        persistFavor.update(favorForUpdate);
        favorRepository.save(persistFavor);

        return mapper.favorToFavorDto(persistFavor);
    }

    @Override
    public void deleteFavor(int id) {
        log.info("Delete favour by id {}", id);
        favorRepository.deleteById(id);
    }
}
