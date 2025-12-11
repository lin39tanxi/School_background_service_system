package com.we_are_team.school_background_service_system.controller.user;


import com.we_are_team.school_background_service_system.result.Result;
import com.we_are_team.school_background_service_system.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/common")
@Slf4j
public class CommonController {
//    注入AliOss
    @Autowired
    private AliOssUtil aliOssUtil;

//    文件上传
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file){
        log.info("上传的文件: {}",file);
        String originalFilename = file.getOriginalFilename();
        String objectName =  originalFilename.substring(originalFilename.lastIndexOf("."));
        String obj = UUID.randomUUID().toString() + objectName;
        String url = null;
        try {
            url = aliOssUtil.upload(file.getBytes(), obj);
            return Result.success("上传图片成功",url);
        } catch (IOException e) {
            return Result.error("上传失败");
        }

    }
    @PostMapping("/uploads")
    public Result<List<String>> uploads(@RequestParam("name") String name,@RequestParam(value = "files",required = false) MultipartFile[] files) {
        log.info("姓名: {}", name);
//        MultipartFile[] files = textDTO.getFiles();
        List<String> urls = new ArrayList<>();
        if(files  ==  null){
            log.info("没有上传图片");

            return Result.success("1");
        }
        for (MultipartFile file : files) {
            String originalFilename = file.getOriginalFilename();
            String objectName = originalFilename.substring(originalFilename.lastIndexOf("."));
            String obj = UUID.randomUUID().toString() + objectName;
            String url = null;
            try {
                url = aliOssUtil.upload(file.getBytes(), obj);
                urls.add(url);
            } catch (IOException e) {
                return Result.error("上传失败");
            }

        }
        return Result.success("上传图片成功", urls);
    }
}
