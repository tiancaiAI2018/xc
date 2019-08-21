package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.system.response.SysDicthinaryResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("字典管理")
public interface SysDicthinaryControllerApi {
    @ApiOperation("查看对应类型的字典")
    SysDicthinaryResult getByType( String type);
}
