package com.xhs.xhspicturebackend.common;

import lombok.Data;

/**
 * @Author: 小辛同学
 * @CreateTime: 2025-08-29
 * @Description:请求包装类
 * @Version: 1.0
 */
@Data
public class PageRequest {

    /**
     * 当前页号
     */
    private int current = 1;

    /**
     * 页面大小
     */
    private int pageSize = 10;

    /**
     * 排序字段
     */
    private String sortField;

    /**
     * 排序顺序（默认降序）
     */
    private String sortOrder = "descend";
}
