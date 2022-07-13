package com.epam.spring.homework3.controller;

import com.epam.spring.homework3.dto.FavorDto;
import com.epam.spring.homework3.model.enums.Speciality;
import com.epam.spring.homework3.service.FavorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/favor")
@RequiredArgsConstructor
public class FavorController {

    private final FavorService favorService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    FavorDto getFavor(@PathVariable int id) {
        return favorService.getFavor(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    List<FavorDto> getFavor(@RequestParam(required = false) List<Speciality> filterParam) {
        log.info("Get all favour");
        return favorService.getAllFavor(filterParam);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    FavorDto createFavor(@RequestBody FavorDto favorDto) {
        log.info("Create favor");
        log.trace("Request body {}", favorDto);
        return favorService.createFavor(favorDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    FavorDto updateFavor(@PathVariable int id, @RequestBody FavorDto favorDto) {
        return favorService.updateFavor(id, favorDto);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteFavor(@PathVariable int id) {
        log.info("Delete favour with id {}", id);
        favorService.deleteFavor(id);
        return ResponseEntity.noContent().build();
    }
}
