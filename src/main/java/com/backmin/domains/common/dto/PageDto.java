package com.backmin.domains.common.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class PageDto<T> {

    private long totalCount;

    private int pageNumber;

    private int pageSize;

    private List<T> list;

    private boolean hasNext;

    @Builder
    public PageDto(long totalCount, int pageNumber, int pageSize, List<T> list, boolean hasNext) {
        this.totalCount = totalCount;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.list = list;
        this.hasNext = hasNext;
    }

}
