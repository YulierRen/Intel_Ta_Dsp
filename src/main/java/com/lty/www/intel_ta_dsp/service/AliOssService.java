package com.lty.www.intel_ta_dsp.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.lty.www.intel_ta_dsp.config.AliOssProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class AliOssService {

    @Autowired
    private AliOssProperties properties;

    public String upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String fileName = "avatar/" + UUID.randomUUID() + "-" + originalFilename;

        OSS ossClient = new OSSClientBuilder().build(
                properties.getEndpoint(),
                properties.getAccessKeyId(),
                properties.getAccessKeySecret()
        );

        ossClient.putObject(properties.getBucketName(), fileName, file.getInputStream());
        ossClient.shutdown();

        return properties.getUrlPrefix() + fileName;
    }
}
