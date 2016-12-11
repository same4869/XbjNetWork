package com.xbjnw.wangxun.xbjnetwork;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by wangxun on 16/12/11.
 */

public class CacheStoreUtil {
    public static final String WENBA_DIR = "/wenba";
    public static final String WENBA_IMAGES = WENBA_DIR + "/images";

    public static File getImagesDir(Context ctx) {
        return getRootDir(ctx, WENBA_IMAGES,false);
    }

    /**
     * 获得应用的可用根目录
     *
     * @param ctx
     * @param childPath
     * @param external
     * @return
     */
    protected static File getRootDir(Context ctx, String childPath, boolean external) {
        File file = null;
        if (ctx != null) {

            file = makeFile(ctx.getExternalCacheDir(),childPath);
            if(file != null){
                return file;
            }

            if(!external) {//external==true，仅仅需要外部存储
                file = makeFile(ctx.getCacheDir(), childPath);
                if (file != null) {
                    return file;
                }

                file = makeFile(ctx.getFilesDir(), childPath);
                if (file != null) {
                    return file;
                }
            }
        }
        if (file == null && Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            file = makeFile(Environment.getExternalStorageDirectory(),childPath);
        }
        return file;
    }

    private static File makeFile(File parentFile, String childPath) {
        if (isAvailableDirectory(parentFile)) {
            File childFile = new File(parentFile, childPath);
            if (!childFile.exists()) {
                if (!childFile.mkdirs()) {
                    return null;
                }
            }
            return childFile;
        }

        return null;
    }

    /**
     * 目录是否可用
     *
     * @param file
     * @return
     */
    public static boolean isAvailableDirectory(File file) {
        if (file == null) {
            return false;
        }

        if (!file.exists()) {
            return false;
        }

        if (!file.isDirectory()) {
            return false;
        }

        if (file.listFiles() == null) {
            return false;
        }

        if(!file.canRead()){
            return false;
        }

        if (!file.canWrite()) {
            return false;
        }

        return true;
    }

}
