package com.enigma.tokonyadia.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class PageResponseWrapper<T> {
    private List<T> data;
    private Long totalElements;
    private Integer totalPages;
    private Integer page;
    private Integer size;

    public PageResponseWrapper(Page<T> pageFromGeneric) {
        this.data = pageFromGeneric.getContent();
        this.totalElements = pageFromGeneric.getTotalElements();
        this.totalPages = pageFromGeneric.getTotalPages();
        this.page = pageFromGeneric.getNumber();
        this.size = pageFromGeneric.getSize();
    }
}
