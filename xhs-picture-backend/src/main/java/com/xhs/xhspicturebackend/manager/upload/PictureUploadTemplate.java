package com.xhs.xhspicturebackend.manager.upload;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.RandomUtil;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.ciModel.persistence.CIObject;
import com.qcloud.cos.model.ciModel.persistence.ImageInfo;
import com.qcloud.cos.model.ciModel.persistence.ProcessResults;
import com.xhs.xhspicturebackend.config.CosClientConfig;
import com.xhs.xhspicturebackend.exception.BusinessException;
import com.xhs.xhspicturebackend.exception.ErrorCode;
import com.xhs.xhspicturebackend.manager.CosManager;
import com.xhs.xhspicturebackend.model.dto.file.UploadPictureResult;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * @Author: 小辛同学
 * @CreateTime: 2025-10-07
 * @Description: 图片上传的模板
 * @Version: 1.0
 */
@Slf4j
public abstract class PictureUploadTemplate {
    @Resource
    private CosManager cosManager;
    @Resource
    private CosClientConfig cosClientConfig;

    /**
     * 定义上传图片流程模板
     *
     * @param inputSource
     * @return
     */
    public final UploadPictureResult uploadPicture(Object inputSource ,String uploadPathPrefix  ) {
        //校验图片
        validPicture(inputSource);
        //生成文件名，生成上传路径
        String uuid = RandomUtil.randomString(10);
        String originFileName = getOriginFileName(inputSource);
        String uploadFileName = String.format("%s_%s.%s", DateUtil.formatDate(new Date()), uuid, FileUtil.getSuffix(originFileName));
        String uploadPath = String.format("/%s/%s", uploadPathPrefix, uploadFileName);
        File file = null;
        try {
            //创建临时文件
            file = File.createTempFile(uploadPath, null);
            //处理文件
            processFile(inputSource, file);
            //上传文件到对象存储
            PutObjectResult putObjectResult = cosManager.putPictureObject(uploadPath, file);
            ImageInfo imageInfo = putObjectResult.getCiUploadResult().getOriginalInfo().getImageInfo();
            //获取压缩后的处理结果
            ProcessResults processResults = putObjectResult.getCiUploadResult().getProcessResults();
            List<CIObject> objectList = processResults.getObjectList();
            if(CollUtil.isNotEmpty(objectList)){
                CIObject compressedCiObject = objectList.get(0);
                //默认缩略图等于压缩图
                CIObject thumbnailCiObject = compressedCiObject;
                //有缩略图才生产缩略图
                if(objectList.size()>1){
                    thumbnailCiObject = objectList.get(1);
                }
                return buildResult(originFileName,compressedCiObject,thumbnailCiObject);
            }
            //封装结果
            return buildResult(originFileName,file,uploadPath, imageInfo);

        } catch (Exception e) {
            log.error("上传文件到对象存储失败", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "上传文件失败");
        } finally {
            //删除临时文件 清理资源
            this.deleteTempFile(file);
        }

    }
    /**
     * 处理文件
     * @param inputSource
     * @param file
     */
    protected abstract void processFile(Object inputSource, File file) throws Exception;
    /**
     * 获取原始文件名
     * @param inputSource
     * @return
     */
    protected abstract String getOriginFileName(Object inputSource);
    /**
     * 校验图片
     * @param inputSource
     */
    protected abstract void validPicture(Object inputSource);

    /**
     * 构建结果
     * @param originFileName
     * @param file
     * @param uploadPath
     * @param imageInfo
     * @return
     */
    public UploadPictureResult buildResult(String originFileName,File file,String uploadPath, ImageInfo imageInfo){
        UploadPictureResult uploadPictureResult = new UploadPictureResult();
        int width = imageInfo.getWidth();
        int height = imageInfo.getHeight();
        double picScale = NumberUtil.round(width * 1.0 / height, 2).doubleValue();
        uploadPictureResult.setUrl(cosClientConfig.getHost() + "/" + uploadPath);
        uploadPictureResult.setPicName(FileUtil.mainName(originFileName));
        uploadPictureResult.setPicSize(FileUtil.size(file));
        uploadPictureResult.setPicWidth(width);
        uploadPictureResult.setPicHeight(height);
        uploadPictureResult.setPicScale(picScale);
        uploadPictureResult.setPicFormat(imageInfo.getFormat());
        return uploadPictureResult;
    }

    /**
     * 构建结果
     *
     * @param originFilename
     * @param compressedCiObject
     * @return
     */
    private UploadPictureResult buildResult(String originFilename, CIObject compressedCiObject, CIObject thumbnailCiObject) {
        UploadPictureResult uploadPictureResult = new UploadPictureResult();
        int picWidth = compressedCiObject.getWidth();
        int picHeight = compressedCiObject.getHeight();
        double picScale = NumberUtil.round(picWidth * 1.0 / picHeight, 2).doubleValue();
        uploadPictureResult.setPicName(FileUtil.mainName(originFilename));
        uploadPictureResult.setPicWidth(picWidth);
        uploadPictureResult.setPicHeight(picHeight);
        uploadPictureResult.setPicScale(picScale);
        uploadPictureResult.setPicFormat(compressedCiObject.getFormat());
        uploadPictureResult.setPicSize(compressedCiObject.getSize().longValue());
        // 设置图片为压缩后的地址
        uploadPictureResult.setUrl(cosClientConfig.getHost() + "/" + compressedCiObject.getKey());

        //设置缩略图的地址
        uploadPictureResult.setThumbnailUrl(cosClientConfig.getHost() + "/" + thumbnailCiObject.getKey());
        return uploadPictureResult;
    }


    /**
     * 删除临时文件
     *
     * @param file
     */
    private void deleteTempFile(File file) {
        if (file == null) {
            return;
        }
        boolean delete = file.delete();
        if (!delete) {
            log.error("file delete error,filePath={}", file.getAbsoluteFile());
        }
    }
}
