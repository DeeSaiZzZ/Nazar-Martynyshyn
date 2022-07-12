package com.epam.spring.homework3.controller;

import com.epam.spring.homework3.dto.MasterDto;
import com.epam.spring.homework3.dto.groups.OnCreate;
import com.epam.spring.homework3.dto.groups.OnUpdate;
import com.epam.spring.homework3.model.enums.MasterSortType;
import com.epam.spring.homework3.model.enums.Speciality;
import com.epam.spring.homework3.service.MasterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/master")
@RequiredArgsConstructor
@Tag(name = "Master Controller", description = "Master entity manage")
public class MasterController {

    private final MasterService masterService;

    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/{id}")
    @Operation(method = "GET",
            summary = "Get master by id")
    MasterDto getMaster(@PathVariable int id) {
        log.info("Get master by id {}", id);
        return masterService.getMaster(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    @Operation(method = "GET",
            summary = "Get all master",
            responses = @ApiResponse(responseCode = "200"))
    List<MasterDto> getMasters(@RequestParam(required = false) MasterSortType sortType,
                               @RequestParam(required = false) List<Speciality> filterParam) {
        log.info("Get all master, sort type - {}, filter param: {}", sortType, filterParam);
        return masterService.getAllMaster(filterParam, sortType);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @Operation(method = "POST",
            summary = "Create new master",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "All attributes are require"),
            responses = @ApiResponse(responseCode = "201"))
    MasterDto createMaster(@RequestBody @Validated(OnCreate.class) MasterDto masterDto) {
        log.info("Create master, request body {}", masterDto);
        return masterService.createMaster(masterDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    MasterDto updateMater(@PathVariable int id, @RequestBody @Validated(OnUpdate.class) MasterDto masterDto) {
        return masterService.updateMaster(id, masterDto);
    }

    @DeleteMapping("/{id}")
    @Operation(method = "DELETE",
            summary = "Delete master by id",
            description = "Add master id to path variable and execute request",
            responses = @ApiResponse(responseCode = "200"))
    ResponseEntity<Void> deleteMaster(@PathVariable int id) {
        log.info("Delete master id {}", id);
        masterService.deleteMaster(id);
        return ResponseEntity.noContent().build();
    }
}
