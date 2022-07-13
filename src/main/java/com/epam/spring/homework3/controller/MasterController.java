package com.epam.spring.homework3.controller;

import com.epam.spring.homework3.dto.MasterDto;
import com.epam.spring.homework3.model.enums.MasterSortType;
import com.epam.spring.homework3.model.enums.Speciality;
import com.epam.spring.homework3.service.MasterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/master")
@RequiredArgsConstructor
public class MasterController {

    private final MasterService masterService;

    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/{id}")
    MasterDto getMaster(@PathVariable int id) {
        log.info("Get master by id {}", id);
        return masterService.getMaster(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    List<MasterDto> getMasters(@RequestParam(required = false) MasterSortType sortType,
                               @RequestParam(required = false) List<Speciality> filterParam) {
        log.info("Get all master, sort type - {}, filter param: {}", sortType, filterParam);
        return masterService.getAllMaster(filterParam, sortType);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    MasterDto createMaster(@RequestBody MasterDto masterDto) {
        log.info("Create master, request body {}", masterDto);
        return masterService.createMaster(masterDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    MasterDto updateMater(@PathVariable int id, @RequestBody MasterDto masterDto) {
        return masterService.updateMaster(id, masterDto);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteMaster(@PathVariable int id) {
        log.info("Delete master id {}", id);
        masterService.deleteMaster(id);
        return ResponseEntity.noContent().build();
    }
}
