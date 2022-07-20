package com.epam.spring.homework4.service.impl;

import com.epam.spring.homework4.dto.MasterDto;
import com.epam.spring.homework4.mapper.MasterMapper;
import com.epam.spring.homework4.model.CustomPage;
import com.epam.spring.homework4.model.Master;
import com.epam.spring.homework4.model.enums.MasterSortType;
import com.epam.spring.homework4.model.enums.Role;
import com.epam.spring.homework4.model.enums.Speciality;
import com.epam.spring.homework4.model.exeptions.EntityAlreadyExists;
import com.epam.spring.homework4.model.exeptions.EntityNotFoundException;
import com.epam.spring.homework4.repository.MasterRepository;
import com.epam.spring.homework4.service.MasterService;
import com.epam.spring.homework4.utils.FilterParamManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class MasterServiceImpl implements MasterService {

    private final MasterRepository masterRepository;
    private final MasterMapper mapper;

    @Override
    public MasterDto createMaster(MasterDto masterDto) {
        log.info("Start create master");
        if (masterRepository.existsByEmail(masterDto.getEmail())) {
            throw new EntityAlreadyExists(String.format("Master with email %s already exists", masterDto.getEmail()));
        }
        Master master = mapper.masterDtoToMaster(masterDto);

        log.trace("New entity - {}", master);
        master.setRole(Role.MASTER);
        master = masterRepository.save(master);

        return mapper.masterToMasterDto(master);
    }

    @Override
    public MasterDto getMaster(int id) {
        log.info("Start get master with id {}", id);
        Master master = masterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Master with id-%d not found", id)));

        log.info("Finder master - {}", master);
        MasterDto masterDto = mapper.masterToMasterDto(master);
        masterDto.setPassword(null);

        return masterDto;
    }

    @Override
    public MasterDto updateMaster(int id, MasterDto masterDto) {
        Master master = mapper.masterDtoToMaster(masterDto);
        Master persistMaster = masterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Master with id-%d not found", id)));

        persistMaster.update(master);
        master = masterRepository.save(persistMaster);

        return mapper.masterToMasterDto(master);
    }

    @Override
    public CustomPage getAllMaster(List<Speciality> filterParam, MasterSortType sortType, int pageNum) {
        log.trace("Get all master filer param: {}, sort type - {}", filterParam, sortType);

        Pageable pageable = sortType == null ? PageRequest.of(pageNum, 3) : PageRequest.of(pageNum, 3, Sort.by(sortType.name().toLowerCase()));
        filterParam = FilterParamManager.manageFilterParam(filterParam);

        Page<Master> result = masterRepository.findBySpecialityIn(filterParam, pageable);
        return new CustomPage(result.stream()
                .map(mapper::masterToMasterDto)
                .peek(masterDto -> masterDto.setPassword(null))
                .collect(Collectors.toList()), pageNum, result.getTotalPages(), result.getSize());
    }

    @Override
    public void deleteMaster(int id) {
        log.info("Delete master by id {}", id);
        masterRepository.deleteById(id);
    }
}
