package com.xhs.xhspicturebackend.manager;

import cn.hutool.core.io.FileUtil;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.COSObject;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.ciModel.persistence.PicOperations;
import com.xhs.xhspicturebackend.config.CosClientConfig;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 小辛同学
 * @CreateTime: 2025-09-30
 * @Description: 通用对象存储操作
 * @Version: 1.0
 */
@Component
public class CosManager {
    @Resource
    private CosClientConfig cosClientConfig;

    @Resource
    private COSClient cosClient;

    /**
     * 上传对象
     *
     * @param key
     * @param file
     * @return
     */
    public PutObjectResult putObject(String key, File file) {
        PutObjectRequest request = new PutObjectRequest(cosClientConfig.getBucket(), key, file);
        return cosClient.putObject(request);
    }

    /**
     * 获取对象
     *
     * @param key
     * @return
     */
    public COSObject getObject(String key) {
        return cosClient.getObject(new GetObjectRequest(cosClientConfig.getBucket(), key));
    }

    /**
     * 上传图片对象
     *
     * @param key
     * @param file
     * @return
     */
    public PutObjectResult putPictureObject(String key, File file) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(cosClientConfig.getBucket(), key, file);
        PicOperations picOperations = new PicOperations();
        picOperations.setIsPicInfo(1);
        //图片压缩
        List<PicOperations.Rule> rules = new ArrayList<>();
        String webKey= FileUtil.mainName( key)+".webp";
        PicOperations.Rule compressedRule = new PicOperations.Rule();
        compressedRule.setBucket(cosClientConfig.getBucket());
        compressedRule.setFileId(webKey);
        compressedRule.setRule("imageMogr2/format/webp");
        rules.add(compressedRule);
        //缩略图做处理，仅对>20kb的图片进行处理
        if(file.length()>20*1024){
            //缩略图处理
            PicOperations.Rule thumbnailRule = new PicOperations.Rule();
            thumbnailRule.setBucket(cosClientConfig.getBucket());
            String thumbnailKey = FileUtil.mainName( key)+"_thumbnail."+FileUtil.getSuffix(key);
            thumbnailRule.setFileId(thumbnailKey);
            //缩放规则
            thumbnailRule.setRule(String.format("imageMogr2/thumbnail/%sx%s>", 128, 128));
            rules.add(thumbnailRule);
        }


        //构造处理参数
        picOperations.setRules(rules);
        putObjectRequest.setPicOperations(picOperations);
        return cosClient.putObject(putObjectRequest);
    }

    public void deleteObject(String key) {
        cosClient.deleteObject(cosClientConfig.getBucket(), key);
    }
}
