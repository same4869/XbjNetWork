package com.xbjnw.wangxun.xbjnetwork.web;

import android.content.Context;
import android.content.Intent;
import android.provider.SyncStateContract;
import android.support.v4.content.LocalBroadcastManager;

import com.xbjnw.wangxun.xbjnetwork.BBLog;
import com.xbjnw.wangxun.xbjnetwork.MyApplication;
import com.xbjnw.wangxun.xbjnetwork.json.JSONToBeanHandler;
import com.xbjnw.wangxun.xbjnetwork.webbase.core.BaseHttpRequest;
import com.xbjnw.wangxun.xbjnetwork.webbase.core.WenbaResponse;
import com.xbjnw.wangxun.xbjnetwork.webbase.core.WenbaUploadResponse;
import com.yolanda.nohttp.Headers;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.StringRequest;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.Map;

/**
 * @Author:Lijj
 * @Date:2014-4-16下午3:43:23
 * @Todo:TODO
 */

public class WenbaRequest extends BaseHttpRequest<Object> {
    public static final String TAG = "wenba_req";


    private void addCommonParams() {
        Context context = MyApplication.getInstance();
//        add("platform", "0");
        addHeader("apikey", "c6f2e486536540cc21f58d80200a035a");
        //公共参数设置
//        add("version", String.valueOf(AppInfoUtils.getCurrentVersionCode(context)));
//        add("channel", AppInfoUtils.getChannelByMeta(context));
//        add("deviceId", SoUtil.getDeviceId());
//        add("diven", SoUtil.getDivenId());
//        add("model", APPUtil.getPhoneType());
//        add("os", android.os.Build.VERSION.RELEASE);
    }

    public WenbaRequest(String url, Map<String, String> params, WenbaResponse responseListener) {
        this(url, RequestMethod.POST, params, responseListener);
    }

    public WenbaRequest(String url, RequestMethod requestMethod, Map<String, String> params, WenbaResponse
            wenbaResponse) {
        super(url, requestMethod, params, wenbaResponse);
        addCommonParams();
    }

    public WenbaRequest(String url, Map<String, String> params, Map<String, String> fileParams, WenbaUploadResponse
            wenbaUploadResponse) {
        super(url, params, fileParams, wenbaUploadResponse);
        addCommonParams();
    }

    @Override
    public boolean certificateLocalKeystore() {
        return false;
    }

//    @Override
//    public boolean certificateLocalKeystore() {
//        return url().startsWith(SoUtil.getBaseUrl());
//    }
//
//    private String decrypt(Class cls, String body) throws Exception {
//        if (!cls.isAnnotationPresent(WenbaDecrypt.class)) {
//            return body;
//        }
//
//        WenbaDecrypt decrypt = (WenbaDecrypt) cls.getAnnotation(WenbaDecrypt.class);
//
//        String[] array = decrypt.value();
//        if (array == null) {
//            return body;
//        }
//
//        boolean[] modeArray = decrypt.mode();
//
//        JSONObject jsonObject = new JSONObject(body);
//
//        for (int i = 0; i < array.length; i++) {
//            boolean isLiteral = false;
//
//            String itemName = array[i];
//
//            String itemBody = jsonObject.optString(itemName);
//
//            if (StringUtil.isBlank(itemBody)) {
//                continue;
//            }
//
//            try {
//                byte[] outBytes = SoUtil.decryptServerBytes(DateUtil.getCurWenbaTime() / 1000, itemBody.getBytes());
//
//                itemBody = new String(outBytes);
//            } catch (Exception e) {
//                e.toString();
//            }
//
//            if (modeArray != null) {
//                if (i < modeArray.length) {
//                    isLiteral = modeArray[i];
//                }
//            }
//
//            if (isLiteral) {
//                jsonObject.put(itemName, itemBody);
//            } else {
//                JSONTokener tokener = new JSONTokener(itemBody);
//                jsonObject.put(itemName, tokener.nextValue());
//            }
//        }
//        return jsonObject.toString();
//    }

    /**
     * Parse request results for generic objects.
     *
     * @param responseHeaders response headers of server.
     * @param responseBody    response data of server.
     * @return your response result.
     * @throws Throwable parse error.
     */
    @Override
    public Object parseResponse(Headers responseHeaders, byte[] responseBody) throws Throwable {
        //同步服务器时间到本地
        long time = responseHeaders == null ? 0 : responseHeaders.getDate();
        if (time > 0) {
            //DateUtil.setWenbaTime(time);
        }

        String result = StringRequest.parseResponseString(responseHeaders, responseBody);

        Class clazz = getResponseListener().getResponseClass();
//
//        String parsed = decrypt(clazz, result);
//
//        BBLog.d(TAG, url() + " ==> " + parsed);
//
//        if (parsed == null || clazz == StringÔ.class) {
//            return parsed;
//        }

        Object bean = JSONToBeanHandler.fromJsonString(result, clazz);

//        if (bean != null && bean instanceof BBObject) {
//            BBObject wenbaBean = (BBObject) bean;
//            if (wenbaBean.getStatusCode() == 2 || wenbaBean.getStatusCode() == 20) {// 用户cookie实效，登出应用
//                Context context = WenbaApplication.getInstance();
//                if (context != null) {
//                    BBLog.e(TAG, "sendBroadcast by loginout");
//
//                    /**
//                     * 登出
//                     */
//                    Intent intent = new Intent(SyncStateContract.Constants.BROADCAST_USER_NOT_LOGIN);
//                    intent.putExtra(PageParam.USER_LONOUT_INITIATIVE, false);// 用户主动退出标识
//                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
//                }
//            }
//        }
        return bean;
    }

}
