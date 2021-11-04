package com.backmin.domains.common.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageResult<T> {

    private long totalCount;

    private int pageNumber;

    private int pageSize;

    private List<T> list;

    private boolean hasNext;

}
