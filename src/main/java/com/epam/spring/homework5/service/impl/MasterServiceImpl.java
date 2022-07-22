package com.epam.spring.homework5.service.impl;

import com.epam.spring.homework5.dto.MasterDto;
import com.epam.spring.homework5.dto.OrderDto;
import com.epam.spring.homework5.mapper.MasterMapper;
import com.epam.spring.homework5.mapper.OrderMapper;
import com.epam.spring.homework5.model.CustomPage;
import com.epam.spring.homework5.model.Master;
import com.epam.spring.homework5.model.Order;
import com.epam.spring.homework5.model.enums.MasterSortType;
import com.epam.spring.homework5.model.enums.Role;
import com.epam.spring.homework5.model.enums.Speciality;
import com.epam.spring.homework5.model.enums.Status;
import com.epam.spring.homework5.model.exeptions.EntityAlreadyExists;
import com.epam.spring.homework5.model.exeptions.EntityNotFoundException;
import com.epam.spring.homework5.model.exeptions.IllegalStateException;
import com.epam.spring.homework5.repository.MasterRepository;
import com.epam.spring.homework5.service.MasterService;
import com.epam.spring.homework5.utils.FilterParamManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class MasterServiceImpl implements MasterService {

    private static final String MASTER_NOT_FOUND_MESSAGE = "Master with id-%d not found";

    private final MasterRepository masterRepository;

    private final OrderMapper orderMapper;
    private final MasterMapper masterMapper;

    @Override
    @Transactional
    public MasterDto createMaster(MasterDto masterDto) {
        log.info("Start create master");
        if (masterRepository.existsByEmail(masterDto.getEmail())) {
            throw new EntityAlreadyExists(String.format("Master with email %s already exists", masterDto.getEmail()));
        }
        Master master = masterMapper.masterDtoToMaster(masterDto);

        log.trace("New entity - {}", master);
        master.setRole(Role.MASTER);
        master = masterRepository.save(master);

        return masterMapper.masterToMasterDto(master);
    }

    @Override
    public MasterDto getMaster(int id) {
        log.info("Start get master with id {}", id);
        Master master = masterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(MASTER_NOT_FOUND_MESSAGE, id)));

        log.info("Finder master - {}", master);
        MasterDto masterDto = masterMapper.masterToMasterDto(master);
        masterDto.setPassword(null);

        return masterDto;
    }

    @Override
    public MasterDto updateMaster(int id, MasterDto masterDto) {
        Master master = masterMapper.masterDtoToMaster(masterDto);
        Master persistMaster = masterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(MASTER_NOT_FOUND_MESSAGE, id)));

        persistMaster.update(master);
        master = masterRepository.save(persistMaster);

        return masterMapper.masterToMasterDto(master);
    }

    @Override
    public CustomPage getAllMaster(List<Speciality> filterParam, MasterSortType sortType, int pageNum) {
        log.trace("Get all master filer param: {}, sort type - {}", filterParam, sortType);

        Pageable pageable = sortType == null ? PageRequest.of(pageNum, 3) : PageRequest.of(pageNum, 3, Sort.by(sortType.name().toLowerCase()));
        filterParam = FilterParamManager.manageFilterParam(filterParam);

        Page<Master> result = masterRepository.findBySpecialityIn(filterParam, pageable);
        return new CustomPage(result.stream()
                .map(masterMapper::masterToMasterDto)
                .peek(masterDto -> masterDto.setPassword(null))
                .collect(Collectors.toList()), pageNum, result.getTotalPages(), result.getSize());
    }

    @Override
    @Transactional
    public List<OrderDto> getMastersTimeTable(int id) {
        log.info("Starting to find a schedule for master with id {}", id);
        Master persistMaster = masterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(MASTER_NOT_FOUND_MESSAGE, id)));

        return persistMaster.getTimeTable().stream()
                .map(orderMapper::orderToOrderDto)
                .peek(orderDto -> {
                    orderDto.getOrderUser().setPassword(null);
                    orderDto.getOrderMaster().setPassword(null);
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void completeOrder(int masterId, int orderId) {
        log.info("Start pay process");
        Master master = masterRepository.findById(masterId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(MASTER_NOT_FOUND_MESSAGE, masterId)));

        Order order = master.getTimeTable().stream()
                .filter(o -> o.getId() == orderId)
                .peek(o -> {
                    if (!o.getOrderStatus().equals(Status.PAID)) {
                        throw new IllegalStateException("Order with id-% isn't ready to complete");
                    }
                })
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(String.format("Order with id-%d didn't exist in time table", orderId)));

        order.setOrderStatus(Status.COMPLETE);
        masterRepository.save(master);
    }

    @Override
    public void deleteMaster(int id) {
        log.info("Delete master by id {}", id);
        masterRepository.deleteById(id);
    }
}
