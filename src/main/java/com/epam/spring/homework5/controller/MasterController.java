package com.epam.spring.homework5.controller;

import com.epam.spring.homework5.dto.MasterDto;
import com.epam.spring.homework5.dto.OrderDto;
import com.epam.spring.homework5.dto.groups.OnCreate;
import com.epam.spring.homework5.dto.groups.OnUpdate;
import com.epam.spring.homework5.model.CustomPage;
import com.epam.spring.homework5.model.enums.MasterSortType;
import com.epam.spring.homework5.model.enums.Speciality;
import com.epam.spring.homework5.service.MasterService;
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

    @ResponseStatus(HttpStatus.OK)
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
    CustomPage getMasters(@RequestParam(required = false) MasterSortType sortType,
                          @RequestParam(required = false) List<Speciality> filterParam,
                          @RequestParam(defaultValue = "0") int pageNum) {
        log.info("Get all master, sort type - {}, filter param: {}", sortType, filterParam);
        return masterService.getAllMaster(filterParam, sortType, pageNum);
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
    @Operation(method = "PUT",
            summary = "Update master entity",
            description = "Fill all field, all information will update",
            responses = @ApiResponse(responseCode = "200"))
    MasterDto updateMater(@PathVariable int id, @RequestBody @Validated(OnUpdate.class) MasterDto masterDto) {
        return masterService.updateMaster(id, masterDto);
    }

    @PatchMapping("/{masterId}/time-table/{orderId}")
    @Operation(method = "PATCH",
            summary = "Marks the order completed",
            responses = @ApiResponse(responseCode = "204"))
    ResponseEntity<Void> completeOrder(@PathVariable int masterId, @PathVariable int orderId) {
        masterService.completeOrder(masterId, orderId);
        return ResponseEntity.noContent().build();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}/time-table")
    @Operation(method = "GET",
            summary = "Get all master order",
            responses = @ApiResponse(responseCode = "200"))
    List<OrderDto> getTimeTable(@PathVariable int id) {
        log.info("Master with {id} want to search his time table");
        return masterService.getMastersTimeTable(id);
    }

    @DeleteMapping("/{id}")
    @Operation(method = "DELETE",
            summary = "Delete master by id",
            description = "Add master id to path variable and execute request",
            responses = @ApiResponse(responseCode = "204"))
    ResponseEntity<Void> deleteMaster(@PathVariable int id) {
        log.info("Delete master id {}", id);
        masterService.deleteMaster(id);
        return ResponseEntity.noContent().build();
    }
}
