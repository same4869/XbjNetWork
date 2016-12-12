package com.xbjnw.wangxun.xbjnetwork;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.xbjnw.wangxun.xbjnetwork.web.WenbaImageLoader;
import com.xbjnw.wangxun.xbjnetwork.web.WenbaRequest;
import com.xbjnw.wangxun.xbjnetwork.webbase.WenbaDownLoader;
import com.xbjnw.wangxun.xbjnetwork.webbase.WenbaWebLoader;
import com.xbjnw.wangxun.xbjnetwork.webbase.core.WenbaDownloadListener;
import com.xbjnw.wangxun.xbjnetwork.webbase.core.WenbaResponse;
import com.yolanda.nohttp.RequestMethod;

import java.util.HashMap;

public class XBJNWMainActivity extends AppCompatActivity {
    private ImageView imageView;
    private Button startBtn, stopBtn, contiuneBtn;
    private ProgressBar progressBar;
    private int taskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xbjnwmain);

        requestGuidePic();

        imageView = (ImageView) findViewById(R.id.test_im);
        WenbaImageLoader.getInstance(getApplicationContext()).displayImage("http://ww3.sinaimg" +
                ".cn/mw690/5f623dcegw1fakxd784zjj20zk0qo45h.jpg", imageView);
        startBtn = (Button) findViewById(R.id.start_download);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                download("http://www.bmob.cn/sdk/Bmob_AndroidSDK_V3.5.2_1027.zip");
            }
        });

        stopBtn = (Button) findViewById(R.id.pause_download);
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WenbaDownLoader.getDownloadRequests().get(taskId).cancel();
            }
        });
    }

    /**
     * 下载
     *
     * @param appUrl
     */
    private void download(String appUrl) {
        // 下载文件
        String downloadPath = Environment.getExternalStorageDirectory().getPath() + "/123.a";
        Log.d("kkkkkkkk", "downloadPath --> " + downloadPath);
        taskId = WenbaDownLoader.download(appUrl, downloadPath, new WenbaDownloadListener() {
            @Override
            public void onDownloadError(String msg) {
                Log.d("kkkkkkkk", "onDownloadError msg --> " + msg);
            }

            @Override
            public void onStart() {
                Log.d("kkkkkkkk", "onStart");
            }

            @Override
            public void onProgress(int progress, long fileCount) {
                Log.d("kkkkkkkk", "progress  --> " + progress);
                progressBar.setProgress(progress);
            }

            @Override
            public void onFinish(String filePath) {
                Log.d("kkkkkkkk", "onFinish");
            }

            @Override
            public void onCancel() {
                Log.d("kkkkkkkk", "onCancel");
            }
        });
    }

    private void requestGuidePic() {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("num", "5");
        params.put("rand", "1");
        params.put("word", "盗墓笔记");
        params.put("page", "1");
        params.put("src", "人民日报");
        final WenbaRequest request = new WenbaRequest("http://apis.baidu.com/txapi/weixin/wxhot", RequestMethod.GET,
                params, new
                WenbaResponse<ResponseBean>() {

                    @Override
                    public void onResponse(ResponseBean response) {
                        if (response != null) {
                            Log.d("kkkkkkkk", "response --> " + response.toString());
                            if (response.getNewslist() != null) {
                                for (int i = 0; i < response.getNewslist().size(); i++) {
                                    Log.d("kkkkkkkk", " response.getNewslist().get(i).getTitle() --> " + response
                                            .getNewslist().get(i).getTitle());
                                }
                            }
                        }
                    }

                    @Override
                    public void onExcepetion(String msg) {

                    }

                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onFinish() {

                    }

                });
        WenbaWebLoader.startHttpLoader(request);
    }
}
