package com.askerlve.ums.controller.oauth2.dto.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Askerlve
 * @Description: Oauth2角色列表入参
 * @date 2018/5/3下午5:58
 */
@ApiModel("Oauth2获取角色列表入参实体")
public class Oauth2ListRoleParams {

    @ApiModelProperty(value = "需要鉴权的项目key", required = true)
    private String applicationKey;

    public String getApplicationKey() {
        return applicationKey;
    }

    public void setApplicationKey(String applicationKey) {
        this.applicationKey = applicationKey;
    }

}
