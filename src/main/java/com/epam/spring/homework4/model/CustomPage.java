package com.epam.spring.homework4.model;

import lombok.Data;

import java.util.List;

@Data
public class CustomPage {

    private List<Object> resultList;
    private int currentPage;
    private int totalPages;
    private int elementOnPage;

    public CustomPage(List<Object> resultList, int currentPage, int totalPages, int elementOnPage) {
        this.resultList = resultList;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.elementOnPage = elementOnPage;
    }
}
