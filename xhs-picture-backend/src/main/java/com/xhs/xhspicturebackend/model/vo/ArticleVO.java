package com.xhs.xhspicturebackend.model.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: 小辛同学
 * @CreateTime: 2025-11-26
 * @Description:
 * @Version: 1.0
 */
@Data
public class ArticleVO {
    private Long id;
    private Long categoryId;
    private String title;
    private String coverImage;
    private String summary;
    private String content;
    private String author;
    private String source;
    private String tags;
    private Integer viewCount;
    private Integer likeCount;
    private Integer commentCount;
    private Integer shareCount;
    private Integer collectCount;
    private Integer isTop;
    private Integer isHot;
    private Integer isRecommend;
    private String status;
    private LocalDateTime publishTime;
    private LocalDateTime createTime;

    private String categoryName;
    private Boolean liked;
    private Boolean collected;
}
