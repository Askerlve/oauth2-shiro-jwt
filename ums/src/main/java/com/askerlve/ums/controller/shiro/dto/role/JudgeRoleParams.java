package com.askerlve.ums.controller.shiro.dto.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Askerlve
 * @Description: 判断当前用户是否拥有某个角色入参实体
 * @date 2018/5/3下午6:09
 */
@ApiModel("判断当前用户是否拥有某个角色入参实体")
public class JudgeRoleParams {

    @ApiModelProperty(value = "角色key",readOnly = true)
    protected String roleKey;

    public String getRoleKey() {
        return roleKey;
    }

    public void setRoleKey(String roleKey) {
        this.roleKey = roleKey;
    }
}
