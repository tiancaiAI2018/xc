package com.xuecheng.filesystem.controller;

import com.xuecheng.api.filesystem.FileSystemControllerApi;
import com.xuecheng.filesystem.service.FileSystemService;
import com.xuecheng.framework.domain.filesystem.FileSystem;
import com.xuecheng.framework.domain.filesystem.response.UploadFileResult;
import com.xuecheng.framework.model.response.CommonCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
@RestController
@RequestMapping("filesystem")
public class FileSystemController implements FileSystemControllerApi {
    @Autowired
    private FileSystemService fileSystemService;
    /**
     * 功能描述: <br>
     * 〈上传文件〉
     * @Param:  * @param file
     * @param filetage
     * @param businesskey
     * @param metadata
     * @Return: com.xuecheng.framework.domain.filesystem.response.UploadFileResult
     * @Author: wangbohong
     * @Date: 2019/8/17 16:14
     */
    @PostMapping
    @Override
    public UploadFileResult upload(@RequestParam(value = "file",required = true) MultipartFile file,
                                   @RequestParam(value = "filetag",required = true) String filetag,
                                   @RequestParam(value = "businesskey",required = false) String businesskey,
                                   @RequestParam(value = "metadata",required = false) String metadata) {
        FileSystem fileSystem = fileSystemService.uploadFile(file, filetag, businesskey, metadata);
        return new UploadFileResult(CommonCode.SUCCESS,fileSystem);
    }
}
