package com.xhs.xhspicturebackend.model.dto.file;

import lombok.Data;

/**
 * @Author: 小辛同学
 * @CreateTime: 2025-09-30
 * @Description: 图片文件解析
 * @Version: 1.0
 */
@Data
public class UploadPictureResult {
    private String url;
    private String picName;
    private Long picSize;
    private int picWidth;
    private int picHeight;
    private Double picScale;
    private String picFormat;
    private String thumbnailUrl;

}
