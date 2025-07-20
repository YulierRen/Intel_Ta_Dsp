package com.lty.www.intel_ta_dsp.controller;

import com.lty.www.intel_ta_dsp.service.AliOssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/oss")
public class OssController {

    @Autowired
    private AliOssService ossService;

    @PostMapping("/upload")
    public String uploadAvatar(@RequestParam("file") MultipartFile file) {
        try {
            return ossService.upload(file);
        } catch (Exception e) {
            e.printStackTrace();
            return "上传失败";
        }
    }
}
