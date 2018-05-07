package com.askerlve.ums.controller.oauth2.dto.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Askerlve
 * @Description: Oauth2判断当前用户是否拥有超级管理员角色入参
 * @date 2018/5/3下午6:05
 */
@ApiModel("Oauth2判断当前用户是否拥有超级管理员角色入参实体")
public class Oauth2JudgeSuperParams {

    @ApiModelProperty(value = "需要鉴权的项目key", required = true)
    private String applicationKey;

    public String getApplicationKey() {
        return applicationKey;
    }

    public void setApplicationKey(String applicationKey) {
        this.applicationKey = applicationKey;
    }
}
