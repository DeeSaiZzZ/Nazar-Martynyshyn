package com.epam.spring.homework4.repository;

import com.epam.spring.homework4.model.Favor;
import com.epam.spring.homework4.model.enums.Speciality;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavorRepository extends JpaRepository<Favor, Integer> {
    Page<Favor> findAllBySpecialityIn(List<Speciality> specialities, Pageable pageable);

    boolean existsByName(String name);
}
