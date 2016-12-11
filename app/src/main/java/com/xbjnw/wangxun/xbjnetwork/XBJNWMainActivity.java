package com.xbjnw.wangxun.xbjnetwork;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.xbjnw.wangxun.xbjnetwork.web.WenbaImageLoader;
import com.xbjnw.wangxun.xbjnetwork.web.WenbaRequest;
import com.xbjnw.wangxun.xbjnetwork.webbase.WenbaWebLoader;
import com.xbjnw.wangxun.xbjnetwork.webbase.core.WenbaResponse;
import com.yolanda.nohttp.RequestMethod;

import java.util.HashMap;

public class XBJNWMainActivity extends AppCompatActivity {
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xbjnwmain);

        requestGuidePic();

        imageView = (ImageView) findViewById(R.id.test_im);
        WenbaImageLoader.getInstance(getApplicationContext()).displayImage("http://ww3.sinaimg.cn/mw690/5f623dcegw1fakxd784zjj20zk0qo45h.jpg", imageView);
    }

    private void requestGuidePic() {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("num", "5");
        params.put("rand", "1");
        params.put("word", "盗墓笔记");
        params.put("page", "1");
        params.put("src", "人民日报");
        final WenbaRequest request = new WenbaRequest("http://apis.baidu.com/txapi/weixin/wxhot", RequestMethod.GET, params, new
                WenbaResponse<ResponseBean>() {

                    @Override
                    public void onResponse(ResponseBean response) {
                        if (response != null) {
                            Log.d("kkkkkkkk", "response --> " + response.toString());
                            if (response.getNewslist() != null) {
                                for (int i = 0; i < response.getNewslist().size(); i++) {
                                    Log.d("kkkkkkkk", " response.getNewslist().get(i).getTitle() --> " + response.getNewslist().get(i).getTitle());
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
