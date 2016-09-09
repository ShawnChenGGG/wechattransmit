package com.mxth.myapplication;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.VideoView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private VideoView videoView;
    private VideoView videoView1;
    private VideoView videoView2;
    private VideoView videoView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {/*
        videoView = (VideoView) findViewById(R.id.videoView);
        videoView1 = (VideoView) findViewById(R.id.videoView1);
        videoView2 = (VideoView) findViewById(R.id.videoView2);
        videoView3 = (VideoView) findViewById(R.id.videoView3);
        File f = new File("/mnt/sdcard/tencent/MicroMsg");// /ce6a38ab33fcd1854eb771f656f62174/video
        File[] files1 = f.listFiles();
        String path = "";
        String path1 = "";
        List<String> paths = new ArrayList<>();
        List<Integer> indexs = new ArrayList<>();
        for (int i = 0; i < files1.length; i++) {
            indexs.add(files1[i].getName().length());
        }
        int maxIndex = 0;
        for (int i = 0; i < indexs.size(); i++) {
            for (int j = i + 1; j < indexs.size(); j++) {
                if (indexs.get(j) > indexs.get(i)) {
                    if (!files1[j].toString().contains(".")) {
                        maxIndex = j;
                    }
                }
            }
        }
        int o = indexs.indexOf(indexs.get(maxIndex));
        String filePath = files1[maxIndex].toString();
        File defaultFile = null;
        if (filePath.contains("temp")) {
            defaultFile = new File(filePath.substring(0, filePath.indexOf("temp")) + "/video");
        } else {
            defaultFile = new File(filePath + "/video");
        }
        if (defaultFile.listFiles().length == 0) {
            filePath = files1[o].toString();
        }
        Log.e(o + "length", maxIndex + ",indexs.size=" + indexs.toString() + "," + files1[maxIndex]);
        File ff = null;
        if (filePath.contains("temp")) {
            ff = new File(filePath.substring(0, filePath.indexOf("temp")) + "/video");
        } else {
            ff = new File(filePath + "/video");
        }
        Log.e("length", ff.toString() + "");

        File[] files = ff.listFiles();
        for (int i = 0; i < files.length; i++) {
            File fff = files[i];
            if (fff.getName().endsWith(".mp4")) {
                path = fff.getAbsolutePath();
                paths.add(path);
            }
        }

        videoView.setVideoPath(paths.get(0));
        videoView1.setVideoPath(paths.get(1));
        videoView2.setVideoPath(paths.get(2));
        videoView3.setVideoPath(paths.get(2));

        videoView.start();
        SystemClock.sleep(5);
        videoView1.start();
        SystemClock.sleep(6);
        videoView2.start();
        SystemClock.sleep(7);
        videoView3.start();*/
        MyView v=new MyView(this);
        v.setImageResource(R.mipmap.ic_launcher);
         WindowManager windowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        v.windowManagerParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        v.windowManagerParams.format = PixelFormat.RGBA_8888;

        v.windowManagerParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

        v.windowManagerParams.gravity = Gravity.LEFT | Gravity.TOP;

        v.windowManagerParams.x = 700;
        v.windowManagerParams.y = 0;

        v.windowManagerParams.width = 120;
        v.windowManagerParams.height = 120;
        windowManager.addView(v, v.windowManagerParams);
    }
}
