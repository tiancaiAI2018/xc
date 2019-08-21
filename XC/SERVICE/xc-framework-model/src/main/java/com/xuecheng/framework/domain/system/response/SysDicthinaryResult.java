package com.xuecheng.framework.domain.system.response;

import com.xuecheng.framework.domain.system.SysDictionary;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SysDicthinaryResult extends ResponseResult {
    private SysDictionary sysDictionary;
    public SysDicthinaryResult(ResultCode code,SysDictionary sysDictionary){
        super(code);
        this.sysDictionary=sysDictionary;
    }
}
