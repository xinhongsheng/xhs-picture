package com.xhs.xhspicturebackend.manager.upload;

import cn.hutool.core.io.FileUtil;
import com.xhs.xhspicturebackend.exception.ErrorCode;
import com.xhs.xhspicturebackend.exception.ThrowUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: 小辛同学
 * @CreateTime: 2025-10-07
 * @Description:本地图片上传文件
 * @Version: 1.0
 */
@Service
public class FilePictureUpload extends PictureUploadTemplate{
    @Override
    protected void processFile(Object inputSource, File file)  throws Exception{
        MultipartFile multipartFile = (MultipartFile) inputSource;
        multipartFile.transferTo( file);
    }

    @Override
    protected String getOriginFileName(Object inputSource) {
        MultipartFile multipartFile = (MultipartFile) inputSource;
        return multipartFile.getOriginalFilename();
    }

    @Override
    protected void validPicture(Object inputSource) {
        MultipartFile multipartFile = (MultipartFile) inputSource;
        ThrowUtils.throwIf(multipartFile == null, ErrorCode.PARAMS_ERROR, "上传文件为空");
        //校验文件大小
        long fileSize = multipartFile.getSize();
        final Long ONE_M = 1024 * 1024L;
        ThrowUtils.throwIf(fileSize > 15 * ONE_M, ErrorCode.PARAMS_ERROR, "上传文件大小不能超过15M");
        //校验文件后缀
        String fileSuffix = FileUtil.getSuffix(multipartFile.getOriginalFilename());

        final List<String> fileSuffixList = Arrays.asList("jpg", "jpeg", "png", "webp");
        ThrowUtils.throwIf(!fileSuffixList.contains(fileSuffix), ErrorCode.PARAMS_ERROR, "上传文件格式错误");

    }
}
