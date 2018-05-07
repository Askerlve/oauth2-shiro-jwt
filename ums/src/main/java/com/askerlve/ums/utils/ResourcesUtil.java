package com.askerlve.ums.utils;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.*;

/**
 * 资源文件读取工具类
 */
public class ResourcesUtil implements Serializable {

    private static final long serialVersionUID = -7657898714983901418L;

    /**
     * 系统语言环境，默认为中文zh
     */
    public static final String LANGUAGE = "zh";

    /**
     * 系统国家环境，默认为中国CN
     */
    public static final String COUNTRY = "CN";

    private static Locale getLocale() {
        Locale locale = new Locale(LANGUAGE, COUNTRY);
        return locale;
    }

    /**
     * 根据语言、国家、资源文件名和key名字获取资源文件值
     *
     * @param baseName 资源文件名
     * @param section  key名字
     * @return 值
     */
    private static String getProperties(String baseName, String section) {
        String retValue = "";
        try {
            Locale locale = getLocale();
            ResourceBundle rb = ResourceBundle.getBundle(baseName, locale);
            retValue = (String) rb.getObject(section);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retValue;
    }

    /**
     * 通过key从资源文件读取内容
     *
     * @param fileName 资源文件名
     * @param key      索引
     * @return 索引对应的内容
     */
    public static String getValue(String fileName, String key) {
        String value = getProperties(fileName, key);
        return value;
    }

    public static List<String> gekeyList(String baseName) {
        Locale locale = getLocale();
        ResourceBundle rb = ResourceBundle.getBundle(baseName, locale);

        List<String> reslist = new ArrayList<String>();

        Set<String> keyset = rb.keySet();
        for (Iterator<String> it = keyset.iterator(); it.hasNext(); ) {
            String lkey = it.next();
            reslist.add(lkey);
        }

        return reslist;

    }

    /**
     * 通过key从资源文件读取内容，并格式化
     *
     * @param fileName 资源文件名
     * @param key      索引
     * @param objs     格式化参数
     * @return 格式化后的内容
     */
    public static String getValue(String fileName, String key, Object[] objs) {
        String pattern = getValue(fileName, key);
        String value = MessageFormat.format(pattern, objs);
        return value;
    }

    public static void main(String[] args) {
        System.out.println(getValue("i18n/messages", "101", new Object[]{100, 200}));
    }
}
