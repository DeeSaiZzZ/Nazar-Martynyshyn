package com.epam.spring.homework5.controller;

import com.epam.spring.homework5.dto.FavorDto;
import com.epam.spring.homework5.dto.groups.OnCreate;
import com.epam.spring.homework5.dto.groups.OnUpdate;
import com.epam.spring.homework5.model.CustomPage;
import com.epam.spring.homework5.model.enums.Speciality;
import com.epam.spring.homework5.service.FavorService;
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
@RequestMapping("/favor")
@RequiredArgsConstructor
@Tag(name = "Favor controller", description = "Management favor entity")
public class FavorController {

    private final FavorService favorService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    @Operation(method = "GET",
            summary = "Get favor by id",
            responses = @ApiResponse(responseCode = "200"))
    FavorDto getFavor(@PathVariable int id) {
        return favorService.getFavor(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    @Operation(method = "GET",
            summary = "Get all favor",
            description = "If need, select filter params",
            responses = @ApiResponse(responseCode = "200"))
    CustomPage getFavor(@RequestParam(required = false) List<Speciality> filterParam,
                        @RequestParam(defaultValue = "0") int pageNum) {
        log.info("Get all favour");
        return favorService.getAllFavor(filterParam, pageNum);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @Operation(method = "POST",
            summary = "Create new favor",
            description = "Fill all field in RequestBody entity",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Favor entity for create"),
            responses = @ApiResponse(responseCode = "201"))
    FavorDto createFavor(@RequestBody @Validated(OnCreate.class) FavorDto favorDto) {
        log.info("Create favor");
        log.trace("Request body {}", favorDto);
        return favorService.createFavor(favorDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    @Operation(method = "PUT",
            summary = "Update favor entity",
            description = "Enter all field in request body")
    FavorDto updateFavor(@PathVariable int id, @RequestBody @Validated(OnUpdate.class) FavorDto favorDto) {
        return favorService.updateFavor(id, favorDto);
    }

    @DeleteMapping("/{id}")
    @Operation(method = "DELETE",
            summary = "Delete favor by id",
            description = "Add to path variable id and execute request",
            responses = @ApiResponse(responseCode = "204"))
    ResponseEntity<Void> deleteFavor(@PathVariable int id) {
        log.info("Delete favour with id {}", id);
        favorService.deleteFavor(id);
        return ResponseEntity.noContent().build();
    }
}
