package com.example.AventureiroSpring.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter@Setter
public class PagedResponse<T> {
    private final int page;
    private final int size;
    private final int total;
    private final int totalPages;
    private List<T> aventureiros;

    public PagedResponse(int page, int size, int total, List<T> aventureiros) {
        this.page = page;
        this.size = size;
        this.total = total;
        this.totalPages = size <= 0 ? 0 : (int) Math.ceil((double)total / size);
        this.aventureiros = aventureiros;
    }
}
