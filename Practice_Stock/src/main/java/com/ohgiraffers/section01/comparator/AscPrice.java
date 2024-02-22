package com.ohgiraffers.section01.comparator;

import com.ohgiraffers.section01.dto.stockDTO;

import java.util.Comparator;

public class AscPrice implements Comparator<stockDTO> {
    @Override
    public int compare(stockDTO o1, stockDTO o2) {
        return (o1.getPrice() > o2.getPrice())? 1 : -1;
    }
}
