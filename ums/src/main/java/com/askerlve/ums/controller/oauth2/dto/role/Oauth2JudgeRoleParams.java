package com.askerlve.ums.controller.oauth2.dto.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Askerlve
 * @Description: Oauth2判断当前用户是否拥有某个角色入参实体
 * @date 2018/5/3下午6:14
 */
@ApiModel("Oauth2判断当前用户是否拥有某个角色入参实体")
public class Oauth2JudgeRoleParams {

    @ApiModelProperty(value = "需要鉴权的项目key", required = true)
    private String applicationKey;

    @ApiModelProperty(value = "角色key", required = true)
    private String roleKey;

    public String getRoleKey() {
        return roleKey;
    }

    public void setRoleKey(String roleKey) {
        this.roleKey = roleKey;
    }

    public String getApplicationKey() {
        return applicationKey;
    }

    public void setApplicationKey(String applicationKey) {
        this.applicationKey = applicationKey;
    }

}
