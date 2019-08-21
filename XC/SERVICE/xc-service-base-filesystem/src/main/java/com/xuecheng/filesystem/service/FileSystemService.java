package com.xuecheng.filesystem.service;

import com.xuecheng.framework.domain.filesystem.FileSystem;
import org.springframework.web.multipart.MultipartFile;

public interface FileSystemService {
    /**
     * 功能描述: <br>
     * 〈将文件上传到fastdfs上，并将元数据保存到MongoDB中〉
     * MongoDB中保存FileSystem实体类（包含文件的地址，名称，大小，id，类型，和其他信息）
     * @Param:  * @param file
     * @param filetage 文件标签
     * @param businesskey 业务key
     * @param metadata 元数据信息
     * @Return: java.lang.String
     * @Author: wangbohong
     * @Date: 2019/8/17 15:22
     */
    FileSystem uploadFile(MultipartFile file, String filetage, String businesskey, String metadata);
}
