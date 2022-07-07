package com.epam.spring.homework3.service.impl;

import com.epam.spring.homework3.dto.MasterDto;
import com.epam.spring.homework3.mapper.MasterMapper;
import com.epam.spring.homework3.model.Master;
import com.epam.spring.homework3.model.enums.Speciality;
import com.epam.spring.homework3.repository.MasterRepository;
import com.epam.spring.homework3.service.MasterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        Master master = mapper.masterDtoToMaster(masterDto);
        log.trace("New entity - {}", master);
        master = masterRepository.createMaster(master);
        return mapper.masterToMasterDto(master);
    }

    @Override
    public MasterDto getMaster(int masterId) {
        log.info("Start get master with id {}", masterId);
        Master master = masterRepository.getMaster(masterId);
        log.info("Finder master - {}", master);
        return mapper.masterToMasterDto(master);
    }

    @Override
    public List<MasterDto> getAllMaster(List<Speciality> filterParam, String sortType) {
        log.trace("Get all master filer param: {}, sort type - {}", filterParam, sortType);
        return masterRepository
                .getAllMaster()
                .stream()
                .sorted((o1, o2) -> {
                    if (sortType == null) {
                        return 0;
                    } else if (sortType.equals("byName")) {
                        return o1.getFirstName().compareTo(o2.getFirstName());
                    } else if (sortType.equals("byRate")) {
                        if (o1.getRate() > o2.getRate()) {
                            return 1;
                        } else {
                            return -1;
                        }
                    } else {
                        return 0;
                    }
                })
                .filter(filterParam != null ? master -> filterParam.contains(master.getSpeciality()) : master -> true)
                .map(mapper::masterToMasterDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteMaster(int id) {
        log.info("Delete master by id {}", id);
        masterRepository.deleteMaster(id);
    }
}
