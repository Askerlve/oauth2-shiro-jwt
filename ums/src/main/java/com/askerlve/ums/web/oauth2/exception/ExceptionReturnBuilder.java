package com.askerlve.ums.web.oauth2.exception;

import com.alibaba.fastjson.JSON;
import com.askerlve.ums.web.result.ResultInfo;
import com.askerlve.ums.web.result.ResultUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Askerlve
 * @Description:
 * @date 2018/5/4下午2:31
 */
public class ExceptionReturnBuilder {

    public static void buildExceptionReturn(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Exception e) throws IOException {
        ResultInfo resultInfo = ResultUtil.createFail(e);
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(resultInfo));
    }

}
