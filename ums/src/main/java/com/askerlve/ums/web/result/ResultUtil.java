package com.askerlve.ums.web.result;

import com.askerlve.ums.exception.AuthException;
import com.askerlve.ums.utils.ResourcesUtil;
import com.askerlve.ums.utils.content.Config;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

import java.util.Objects;

/**
 * 系统结果工具类
 */
public class ResultUtil {

    /**
     * 创建错误结果
     *
     * @return
     */
    public static ResultInfo createFail(int messageCode) {
        return createFail(Config.MESSAGE, messageCode, null);
    }

    /**
     * 创建错误结果
     *
     * @return
     */
    public static ResultInfo createFail(int messageCode, Object[] objs) {
        return createFail(Config.MESSAGE, messageCode, objs);
    }

    /**
     * 创建错误结果
     *
     * @return
     */
    public static ResultInfo createFail(Exception e) {
        ResultInfo resultInfo;

        if (Objects.nonNull(e)) {
            if (e instanceof AuthException) {
                AuthException ae = (AuthException) e;
                resultInfo = createFail(ae.getCode());
            }else if(e instanceof BadCredentialsException){
                resultInfo = createFail(114);
            } else if(e instanceof OAuth2Exception){
                OAuth2Exception oAuth2Exception = (OAuth2Exception) e;
                resultInfo = createFail(500);
                resultInfo.setMessage(oAuth2Exception.getOAuth2ErrorCode());
                resultInfo.setResult(oAuth2Exception.getSummary());
            }
            else{
                resultInfo = createFail(500);
                resultInfo.setMessage(e.getMessage());
            }
        } else {
            resultInfo = createFail(500);
        }

        return resultInfo;
    }

    /**
     * 创建警告结果
     *
     * @return
     */
    public static ResultInfo createWaring(int messageCode, Object[] objs) {
        return createWaring(Config.MESSAGE, messageCode, objs);
    }

    /**
     * 创建警告结果
     *
     * @return
     */
    public static ResultInfo createWaring(int messageCode) {
        return createWaring(Config.MESSAGE, messageCode, null);
    }

    /**
     * 创建成功结果
     *
     * @return
     */
    public static ResultInfo createSuccess(int messageCode, Object result) {
        return createSuccess(Config.MESSAGE, messageCode, null, result);
    }

    /**
     * 创建成功结果
     *
     * @return
     */
    public static ResultInfo createSuccess(int messageCode, Object[] objs, Object result) {
        return createSuccess(Config.MESSAGE, messageCode, objs, result);
    }

    /**
     * 抛出异常
     *
     * @param resultInfo
     * @throws ExceptionResultInfo
     */
    public static void throwExcepion(ResultInfo resultInfo) throws ExceptionResultInfo {
        throw new ExceptionResultInfo(resultInfo);
    }

    /**
     * 创建错误结果
     *
     * @return
     */
    private static ResultInfo createFail(String fileName, int messageCode, Object[] objs) {
        String message = null;
        if (objs == null) {
            message = ResourcesUtil.getValue(fileName, messageCode + "");
        } else {
            message = ResourcesUtil.getValue(fileName, messageCode + "", objs);
        }
        return new ResultInfo(ResultInfo.TYPE_RESULT_FAIL, messageCode, message);
    }

    /**
     * 创建成功结果
     *
     * @return
     */
    public static ResultInfo createSuccess(String fileName, int messageCode, Object[] objs, Object result) {
        String message = null;
        if (objs == null) {
            message = ResourcesUtil.getValue(fileName, messageCode + "");
        } else {
            message = ResourcesUtil.getValue(fileName, messageCode + "", objs);
        }
        return new ResultInfo(ResultInfo.TYPE_RESULT_SUCCESS, messageCode, message, result);
    }

    /**
     * 创建警告结果
     *
     * @return
     */
    private static ResultInfo createWaring(String fileName, int messageCode, Object[] objs) {
        String message = null;
        if (objs == null) {
            message = ResourcesUtil.getValue(fileName, messageCode + "");
        } else {
            message = ResourcesUtil.getValue(fileName, messageCode + "", objs);
        }
        return new ResultInfo(ResultInfo.TYPE_RESULT_WARN, messageCode, message);
    }

}
