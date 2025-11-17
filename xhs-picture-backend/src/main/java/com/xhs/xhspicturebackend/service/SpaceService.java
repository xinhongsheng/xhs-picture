package com.xhs.xhspicturebackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhs.xhspicturebackend.model.dto.space.SpaceAddRequest;
import com.xhs.xhspicturebackend.model.dto.space.SpaceQueryRequest;
import com.xhs.xhspicturebackend.model.entity.Picture;
import com.xhs.xhspicturebackend.model.entity.Space;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xhs.xhspicturebackend.model.entity.User;
import com.xhs.xhspicturebackend.model.vo.SpaceVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author 小辛
* @description 针对表【space(空间)】的数据库操作Service
* @createDate 2025-10-10 23:16:08
*/
public interface SpaceService extends IService<Space> {
    long addSpace(SpaceAddRequest spaceAddRequest, User loginUser);


    void validSpace(Space space, boolean add);


    void fillSpaceBySpaceLevel(Space space);

    SpaceVO getSpaceVO(Space space, HttpServletRequest request);

    Page<SpaceVO> getSpaceVOPage(Page<Space> spacePage, HttpServletRequest request);

    QueryWrapper<Space> getQueryWrapper(SpaceQueryRequest spaceQueryRequest);
}
