package com.xbjnw.wangxun.xbjnetwork.webbase;

import android.util.Log;

import com.xbjnw.wangxun.xbjnetwork.webbase.core.HttpCancel;
import com.xbjnw.wangxun.xbjnetwork.webbase.core.WenbaDownloadListener;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.download.DownloadQueue;
import com.yolanda.nohttp.download.DownloadRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lijj on 16/9/22.
 */

public class WenbaDownLoader {

    /**
     * 下载队列.
     */
    private static DownloadQueue mDownloadQueue;
    private static List<DownloadRequest> downloadRequests = new ArrayList<>();

    private static DownloadQueue getDownloadQueue() {
        if (mDownloadQueue == null) {
            synchronized (WenbaDownLoader.class) {
                if (mDownloadQueue == null) {
                    mDownloadQueue = NoHttp.newDownloadQueue(2);
                }
            }
        }

        return mDownloadQueue;
    }

    /**
     * @param url      下载地址。
     * @param filePath 保存的文件路径
     * @param listener
     */
    public static int download(String url, String filePath, WenbaDownloadListener listener) {
        return download(url, filePath, true, listener);
    }

    /**
     * @param url      下载地址。
     * @param filePath 保存的文件路径
     * @param isRange  是否断点续传下载
     * @param listener
     */
    public static int download(String url, String filePath, boolean isRange, WenbaDownloadListener listener) {
        File file = new File(filePath);
        Log.d("kkkkkkkk", "file.getName() --> " + file.getName());
        DownloadRequest request = NoHttp.createDownloadRequest(url, RequestMethod.GET, file.getParent(), file.getName
                (), isRange, true);

//        HttpCancel cancle = new HttpCancel();
//        request.setCancelSign(cancle);

        downloadRequests.add(request);

        if (listener != null) {
            listener.setUrl(url);
        }

        getDownloadQueue().add(0, request, listener);

//        return cancle;
        int taskId = downloadRequests.size() - 1;
        return taskId;
    }

    public static List<DownloadRequest> getDownloadRequests(){
        return downloadRequests;
    }

    public static void startAll() {
        getDownloadQueue().start();
    }

    public static void cancelAll() {
        getDownloadQueue().cancelAll();
    }

    public static void cancelBySign(HttpCancel sign) {
        getDownloadQueue().cancelBySign(sign);
    }

}
