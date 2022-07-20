package com.epam.spring.homework4.repository;

import com.epam.spring.homework4.model.Master;
import com.epam.spring.homework4.model.enums.Speciality;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MasterRepository extends JpaRepository<Master, Integer> {
    Page<Master> findBySpecialityIn(List<Speciality> specialities, Pageable pageable);

    boolean existsByEmail(String email);
}
