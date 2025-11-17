package com.xhs.xhspicturebackend.model.dto.picture;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: 小辛同学
 * @CreateTime: 2025-09-30
 * @Description:图片上传请求
 * @Version: 1.0
 */
@Data
public class PictureUploadRequest implements Serializable {
    private Long id;
    private String fileUrl;
    private String picName;
    private Long spaceId;
    private static final long serialVersionUID = 1L;
}
