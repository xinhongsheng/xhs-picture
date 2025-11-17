package com.xhs.xhspicturebackend.model.dto.picture;

import lombok.Data;

/**
 * @Author: 小辛同学
 * @CreateTime: 2025-10-07
 * @Description: 批量上传请求
 * @Version: 1.0
 */
@Data
public class PictureUploadByBatchRequest {
    private String searchText;
    private Integer count=10;
    private String namePrefix;
}
