package com.xuecheng.api.filesystem;

import com.xuecheng.framework.domain.filesystem.response.UploadFileResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

@Api("文件系统服务")
public interface FileSystemControllerApi {
    @ApiOperation("上传文件")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name="file",value = "文件",required = true),
//            @ApiImplicitParam(name="filetage",value = "文件标签",required = true),
//            @ApiImplicitParam(name="businesskey",value = "业务key",required = false),
//            @ApiImplicitParam(name="metadata",value = "元数据",required = false)
//    })
    UploadFileResult upload(MultipartFile file,String filetage,String businesskey,String metadata);
}
