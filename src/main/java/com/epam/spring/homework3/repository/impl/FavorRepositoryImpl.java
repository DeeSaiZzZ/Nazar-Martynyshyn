package com.epam.spring.homework3.repository.impl;

import com.epam.spring.homework3.model.Favor;
import com.epam.spring.homework3.model.enums.Speciality;
import com.epam.spring.homework3.repository.FavorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Component
@RequiredArgsConstructor
public class FavorRepositoryImpl implements FavorRepository {

    private final List<Favor> favors;
    private int idCounter = 1;

    @Override
    public List<Favor> getAllFavor() {
        log.info("Get all favor");
        return new ArrayList<>(favors);
    }

    @Override
    public Favor getFavor(int id) {
        log.info("Get favor by id {}", id);
        return favors.stream()
                .filter(favor -> favor.getId() == id).findFirst()
                .orElse(null);
    }

    @Override
    public Favor createFavor(Favor favor) {
        log.info("Create favour");
        log.trace("Favor id = {}", idCounter);
        favor.setId(idCounter++);
        favors.add(favor);
        return favor;
    }

    @Override
    public Favor updateFavor(int id, Favor favor) {
        log.info("Update favor by id {}", id);
        Favor updatableFavor = favors.stream()
                .filter(o -> o.getId() == id)
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
        log.trace("Favor before update - {}", updatableFavor);
        updatableFavor.update(favor);
        log.trace("Order after update - {}", updatableFavor);
        return updatableFavor;
    }

    @Override
    public void deleteFavor(int id) {
        log.info("Delete favor by id {}", id);
        favors.removeIf(service -> service.getId() == id);
    }

    @PostConstruct
    void initFavorList() {
        log.info("Init favor list method");
        favors.add(Favor.builder()
                .id(idCounter++)
                .name("Манікюр класичний")
                .speciality(Speciality.MANICURIST)
                .price(500)
                .build());

        favors.add(Favor.builder()
                .id(idCounter++)
                .name("Манікюр з нарощенням")
                .speciality(Speciality.MANICURIST)
                .price(675)
                .build());

        favors.add(Favor.builder()
                .id(idCounter++)
                .name("Стрижка")
                .speciality(Speciality.HAIRDRESSER)
                .price(250)
                .build());

        favors.add(Favor.builder()
                .id(idCounter++)
                .name("Макіяж")
                .speciality(Speciality.BEAUTICIAN)
                .price(400)
                .build());

        favors.add(Favor.builder()
                .id(idCounter++)
                .name("Фарбування волосся")
                .speciality(Speciality.HAIRDRESSER)
                .price(300)
                .build());

    }
}
