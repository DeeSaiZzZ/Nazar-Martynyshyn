package com.epam.spring.homework5.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CustomPage {

    private List<Object> resultList;
    private int currentPage;
    private int totalPages;
    private int elementOnPage;
}
