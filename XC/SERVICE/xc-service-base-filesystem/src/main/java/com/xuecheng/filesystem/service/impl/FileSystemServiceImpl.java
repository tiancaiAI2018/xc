package com.xuecheng.filesystem.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.xuecheng.filesystem.dao.FileSystemRepository;
import com.xuecheng.filesystem.service.FileSystemService;
import com.xuecheng.framework.domain.filesystem.FileSystem;
import com.xuecheng.framework.domain.filesystem.response.FileSystemCode;
import com.xuecheng.framework.exception.ExceptionCast;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class FileSystemServiceImpl implements FileSystemService {
    @Autowired
    private FastFileStorageClient fastFileStorageClient; //用于上传问价到fastDFS上
    @Autowired
    private FileSystemRepository fileSystemRepository;//用于保存元数据信息到MongoDB上
    /**
     * 功能描述: <br>
     * 〈将文件上传到fastdfs上，并将元数据保存到MongoDB中〉
     * MongoDB中保存FileSystem实体类（包含文件的地址，名称，大小，id，类型，和其他信息）
     * @Param:
     * @param file  文件
     * @param filetage 文件标签
     * @param businesskey 业务key
     * @param metadata 元数据信息
     * @Return: java.lang.String
     * @Author: wangbohong
     * @Date: 2019/8/17 15:30
     */
    @Override
    public FileSystem uploadFile(MultipartFile file, String filetage, String businesskey, String metadata) {
        //文件标签不能为空
        if(StringUtils.isEmpty(filetage))ExceptionCast.cast(FileSystemCode.FS_UPLOADFILE_FILETAGE);
        //文件路径为空则上传失败
        String path = uploadFile(file);
        if(StringUtils.isEmpty(path))ExceptionCast.cast(FileSystemCode.FS_UPLOADFILE_SERVERFAIL);
        FileSystem fileSystem = new FileSystem();
        //路径是唯一的，路径作为id
        fileSystem.setFileId(path);
        fileSystem.setFilePath(path);
        fileSystem.setFileName(file.getOriginalFilename());
        fileSystem.setFileSize(file.getSize());
        fileSystem.setFileType(file.getContentType());
        fileSystem.setFiletag(filetage);
        fileSystem.setBusinesskey(businesskey);
        if(StringUtils.isNotEmpty(metadata)) {
            try {
                Map map = JSON.parseObject(metadata, Map.class);
                fileSystem.setMetadata(map);
            } catch (Exception e) {
                ExceptionCast.cast(FileSystemCode.FS_UPLOADFILE_METAERROR);
            }
        }
        //保存fileSystem
        FileSystem save = fileSystemRepository.save(fileSystem);
        //返回路径
        return save;
    }
    /**
     * 功能描述: <br>
     * 〈上传文件到fastDFS〉
     * @Param:
     * @param file
     * @Return: java.lang.String
     * @Author: wangbohong
     * @Date: 2019/8/17 15:32
     */
    private String uploadFile(MultipartFile file){
        if(file==null) ExceptionCast.cast(FileSystemCode.FS_UPLOADFILE_FILEISNULL);
        //文件名
        String name = file.getOriginalFilename();
        //文件扩展名
        String extName = name.substring(name.lastIndexOf(".")+1);
        try {
            StorePath storePath = fastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(), extName, null);
            return storePath.getFullPath();
        } catch (IOException e) {
            e.printStackTrace();
            ExceptionCast.cast(FileSystemCode.FS_UPLOADFILE_SERVERFAIL);
        }
        return null;
    }
}
