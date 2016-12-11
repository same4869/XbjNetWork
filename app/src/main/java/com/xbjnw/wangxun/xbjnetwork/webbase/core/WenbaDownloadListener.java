package com.xbjnw.wangxun.xbjnetwork.webbase.core;

import com.xbjnw.wangxun.xbjnetwork.APPUtil;
import com.xbjnw.wangxun.xbjnetwork.BBLog;
import com.xbjnw.wangxun.xbjnetwork.R;
import com.yolanda.nohttp.Headers;
import com.yolanda.nohttp.download.DownloadListener;
import com.yolanda.nohttp.error.NetworkError;
import com.yolanda.nohttp.error.ServerError;
import com.yolanda.nohttp.error.StorageReadWriteError;
import com.yolanda.nohttp.error.StorageSpaceNotEnoughError;
import com.yolanda.nohttp.error.TimeoutError;
import com.yolanda.nohttp.error.URLError;
import com.yolanda.nohttp.error.UnKnownHostError;

/**
 * Created by Lijj on 16/9/22.
 */

public abstract class WenbaDownloadListener implements DownloadListener {
    private String mUrl;

    public void setUrl(String url){
        this.mUrl = url;
    }

    public abstract void onDownloadError(String msg);

    public abstract void onStart();

    public abstract void onProgress(int progress, long fileCount);

    public abstract void onFinish(String filePath);

    public abstract void onCancel();

    @Override
    public void onDownloadError(int i, Exception exception) {
        String msg = null;
        int logCode = 0;

        if (exception instanceof ServerError) {
            msg = APPUtil.getString(R.string.error_request_error_server);
            logCode = 3;
        } else if (exception instanceof NetworkError) {
            msg = APPUtil.getString(R.string.error_wenba);
            logCode = 1;
        } else if (exception instanceof StorageReadWriteError) {
            msg = APPUtil.getString(R.string.error_request_error_storage);
            logCode = 10;
        } else if (exception instanceof StorageSpaceNotEnoughError) {
            msg = APPUtil.getString(R.string.error_request_error_space);
            logCode = 11;
        } else if (exception instanceof TimeoutError) {
            msg = APPUtil.getString(R.string.error_request_timeout);
            logCode = 9;
        } else if (exception instanceof UnKnownHostError) {
            msg = APPUtil.getString(R.string.error_request_unknown_host_error);
            logCode = 5;
        } else if (exception instanceof URLError) {
            msg = APPUtil.getString(R.string.error_request_url_error);
            logCode = 6;
        } else {
            msg = APPUtil.getString(R.string.error_network);
            logCode = 0;
        }

        BBLog.e(BaseHttpRequest.TAG,"错误：" + exception.getMessage());


        onDownloadError(msg);
    }

    @Override
    public void onStart(int i, boolean isResume, long beforeLength, Headers headers, long allCount) {
        onStart();
    }

    @Override
    public void onProgress(int i, int progress, long fileCount) {
        onProgress(progress,fileCount);
    }

    @Override
    public void onFinish(int i, String filePath) {
        onFinish(filePath);
    }

    @Override
    public void onCancel(int i) {
        onCancel();
    }
}
