package com.mai.cat_chain.net.interceptor;

import com.google.gson.Gson;
import com.mai.cat_chain.net.request.MParams;
import com.mai.cat_chain.net.util.AESUtil;
import com.mai.cat_chain.utils.MLog;

/**
 * 请求工具
 *
 * @author maijuntian
 */
@SuppressWarnings("all")
public class HTTPClientUtil {

    public static String getRequestUrl(String url, MParams params, String sessionId) throws Exception {

        url += "?sessionId=" + sessionId;
        String requestSign = encryptSign(params.getParams(), sessionId);//加密数据
        String requestUrl = splicingUrl(url, requestSign);//拼接请求

        MLog.log(requestUrl);
        return requestUrl;
    }

    public static String getRequestUrl(String url, MParams params, String sessionId, String key) throws Exception {

        MLog.log("key-->" + key);
        url += "?sessionId=" + sessionId;
        String requestSign = encryptSign(params.getParams(), key);//加密数据
        String requestUrl = splicingUrl(url, requestSign);//拼接请求

        return requestUrl;
    }

    /**
     * 加密数据
     *
     * @param data 加密数据
     * @param util 加密工具
     * @param key  加密秘钥
     * @return
     * @throws Exception
     */
    public static String encryptSign(Object data, String key) throws Exception {
        String sign = null;
        String jsonStr = new Gson().toJson(data);
        sign = AESUtil.encode(jsonStr, key);
        return sign;

    }

    /**
     * 拼接请求
     *
     * @param url  请求路径
     * @param sign 请求参数-签名
     * @return
     */
    public static String splicingUrl(String url, String sign) throws Exception {
        String requestUrl = null;
        if (url.indexOf("?") > 0) {
            if (url.indexOf("=") > 0) {
                requestUrl = url + "&sign=" + sign;
            } else {
                requestUrl = url + "sign=" + sign;
            }
        } else {
            requestUrl = url + "?sign=" + sign;
        }
        return requestUrl;
    }
}
