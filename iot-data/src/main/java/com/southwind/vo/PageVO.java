package com.southwind.vo;

import lombok.Data;

import java.util.List;

@Data
public class PageVO {
    private Long totalCount;
    private Long pageSize;
    private Long totalPage;
    private Long currPage;
    private List list;
}
