package com.xhs.xhspicturebackend.config;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: 小辛同学
 * @CreateTime: 2025-09-30
 * @Description:cos存储相关配置
 * @Version: 1.0
 */
@Configuration
@Data
@ConfigurationProperties(prefix = "cos.client")
public class CosClientConfig {
    private String host;
    private String secretId;
    private String secretKey;
    private String bucket;
    private String region;


    @Bean
    public COSClient cosClient() {
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        COSClient cosClient = new COSClient(cred, clientConfig);
        return cosClient;
    }
}
