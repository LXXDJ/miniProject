package com.ohgiraffers.section01.comparator;

import com.ohgiraffers.section01.dto.stockDTO;

import java.util.Comparator;

public class DescTitle implements Comparator<stockDTO> {
    @Override
    public int compare(stockDTO o1, stockDTO o2) {
        return o2.getTitle().compareTo(o1.getTitle());
    }
}
