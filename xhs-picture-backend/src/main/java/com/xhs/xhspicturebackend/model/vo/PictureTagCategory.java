package com.xhs.xhspicturebackend.model.vo;

import lombok.Data;

import java.util.List;

/**
 * @Author: 小辛同学
 * @CreateTime: 2025-10-01
 * @Description: 图片标签分类列表视图
 * @Version: 1.0
 */
@Data
public class PictureTagCategory {
    private List<String> tagList;
    private List<String> categoryList;
}
