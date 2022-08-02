package com.epam.spring.homework5.utils;

import com.epam.spring.homework5.model.enums.Speciality;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class FilterParamManager {

    private FilterParamManager() {
        //The constructor is needed so that it is not possible to create an object of the class
    }

    public static List<Speciality> manageFilterParam(List<Speciality> filterParam) {
        if (filterParam == null || filterParam.isEmpty()) {
            filterParam = Arrays.stream(Speciality.values()).collect(Collectors.toList());
        }
        return filterParam;
    }
}
