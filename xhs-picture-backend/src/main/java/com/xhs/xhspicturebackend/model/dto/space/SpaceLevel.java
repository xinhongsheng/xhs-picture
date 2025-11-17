package com.xhs.xhspicturebackend.model.dto.space;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author: 小辛同学
 * @CreateTime: 2025-10-12
 * @Description: 展示空间级别
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
public class SpaceLevel {

    private int value;

    private String text;

    private long maxCount;

    private long maxSize;
}

