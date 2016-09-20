package com.example.moku.accessibilityseiviceforweixin;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityRecordCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by moku on 16/7/21.
 */
public class WindowAccessibilitySeivice extends AccessibilityService {
    private static final int PACKAGE_NAME = 0;
    final String TAG = WindowAccessibilitySeivice.class.getSimpleName();
    String installPackge[] = {"com.tencent.mm"};
    private String packageName;
    private WindowManager windowManager = null;
    private WindowManager.LayoutParams windowManagerParams = null;
    private FloatView floatView = null;
    private boolean isFirstInWX = true;
    private ClipboardManager clipboard;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case PACKAGE_NAME:
                    packageName = (String) msg.obj;
                    //Log.e(" package_name", packageName);
                    if (packageName.equals("com.tencent.mm") && isFirstInWX) {
                        createView();
                        isFirstInWX = !isFirstInWX;
                    }

                    break;
            }
            super.handleMessage(msg);
        }
    };
    private int width;
    private static WindowAccessibilitySeivice instance = new WindowAccessibilitySeivice();
    private AccessibilityEvent nowEvent;
    private AccessibilityNodeInfo willClickNoteInfo;
    private AccessibilityNodeInfo child1;
    private AccessibilityNodeInfo child_movie;
    private AccessibilityNodeInfoCompat video_source;
    private AccessibilityNodeInfo video_node;
//    private Unsafe unsafe;

    public static WindowAccessibilitySeivice getInstance() {
        return instance;
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
    }

    @SuppressLint("NewApi")
    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        DisplayMetrics metrics;
        metrics = getResources().getDisplayMetrics();
        width = metrics.widthPixels;

        String packagename = accessibilityEvent.getPackageName().toString();
        if (isFirstInWX) {
            createView();
        }
        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        String packageName = cn.getPackageName();
//        if (packageName.contains("com.tencent.mm")) {
//            floatView.setVisibility(View.VISIBLE);
//        } else {
//            floatView.setVisibility(View.INVISIBLE);
//        }
        String className = accessibilityEvent.getClassName().toString();
        if ("com.tencent.mm.plugin.sns.ui.SightUploadUI".equals(className)) {
            Log.e("className", "className==" + className);
            //发小视频页面
        }
        int eventType = accessibilityEvent.getEventType();
        AccessibilityNodeInfo noteInfo = accessibilityEvent.getSource();
        willClickNoteInfo = null;
        if (noteInfo != null) {
            Log.e("noteInfo", "eventType=" + accessibilityEvent.toString() + noteInfo.toString());
        }
        switch (eventType) {
            case AccessibilityEvent.TYPE_VIEW_CLICKED://
                if (noteInfo == null) {
                    break;
                }
                if ("发送".equals(noteInfo.getText())) {
                    SystemClock.sleep(500);
                    getVideoPath();
                }
                Log.e("noteInfo", "eventType=" + accessibilityEvent.toString() + noteInfo.toString());
                break;
            case AccessibilityEvent.TYPE_VIEW_LONG_CLICKED:
                if (noteInfo == null) {
                    break;
                }
                Log.e("noteInfo", "eventType=" + accessibilityEvent.toString() + noteInfo.toString());
                break;
            case AccessibilityEvent.TYPE_VIEW_SELECTED:
                if (noteInfo == null) {
                    break;
                }
                Log.e("noteInfo", "eventType=" + accessibilityEvent.toString() + noteInfo.toString());
                break;
            case AccessibilityEvent.TYPE_VIEW_FOCUSED:
                if (noteInfo == null) {
                    break;
                }
                if (accessibilityEvent.getItemCount() > 0) {
                    int childCount = noteInfo.getChildCount();
                    Log.e("noteInfo", "aaaaa" + childCount + "");
                    AccessibilityNodeInfo ll_child = noteInfo.getChild(0);
                    if (ll_child != null) {//最近14天小视频 点击添加按钮
                        if (ll_child.getChild(0) != null && "android.widget.FrameLayout".equals(ll_child.getChild(0).getClassName())) {
                            ll_child.getChild(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
                        }
                    }
                }

                Log.e("noteInfo", "eventType=" + accessibilityEvent.toString() + noteInfo.toString());
                break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED:
                if (noteInfo == null) {
                    break;
                }
                Log.e("noteInfo", "eventType=" + accessibilityEvent.toString() + noteInfo.toString());
                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                if (noteInfo == null) {
                    break;
                }
                this.video_node = noteInfo;
                Log.e("noteInfo", "eventType=" + accessibilityEvent.toString() + noteInfo.toString());
                break;
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                if (noteInfo == null) {
                    break;
                }
                Log.e("noteInfo", "eventType=" + accessibilityEvent.toString() + noteInfo.toString());
                break;
            case AccessibilityEvent.TYPE_VIEW_HOVER_ENTER:
                if (noteInfo == null) {
                    break;
                }
                Log.e("noteInfo", "eventType=" + accessibilityEvent.toString() + noteInfo.toString());
                break;
            case AccessibilityEvent.TYPE_VIEW_HOVER_EXIT:
                if (noteInfo == null) {
                    break;
                }
                Log.e("noteInfo", "eventType=" + accessibilityEvent.toString() + noteInfo.toString());
                break;
            case AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_START:
                if (noteInfo == null) {
                    break;
                }
                Log.e("noteInfo", "eventType=" + accessibilityEvent.toString() + noteInfo.toString());
                break;
            case AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_END:
                if (noteInfo == null) {
                    break;
                }
                Log.e("noteInfo", "eventType=" + accessibilityEvent.toString() + noteInfo.toString());
                break;
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED://主要事件在这里
                if (noteInfo == null) {
                    break;
                }
                Log.e("noteInfo", "eventType=" + accessibilityEvent.toString() + noteInfo.toString());

                willClickNoteInfo = noteInfo;
                nowEvent = accessibilityEvent;
                int childCount = noteInfo.getChildCount();
                if (childCount == 2) {
                    AccessibilityNodeInfo child_movie = noteInfo.getChild(1);
                    Log.e("noteInfo", child_movie + "");
                    if ("android.widget.LinearLayout".equals(child_movie.getClassName()) && child_movie.isEnabled() && child_movie.isClickable()) {

                    }
                }
                if ("android.widget.ListView".equals(noteInfo.getClassName()) && accessibilityEvent.getItemCount() == 2) {
                    AccessibilityNodeInfo child_collector = noteInfo.getChild(1);
                    child_collector.performAction(AccessibilityNodeInfo.ACTION_CLICK);//长按事件 点击收藏 将视频收藏 其实也在本地文件夹也保存了该视频
                }
                break;
            case AccessibilityEvent.TYPE_VIEW_SCROLLED:
                if (noteInfo == null) {
                    break;
                }
                if ("android.widget.ListView".equals(accessibilityEvent.getClassName())) {
                    if (accessibilityEvent.getItemCount() > 0) {
                        int childCount1 = noteInfo.getChildCount();
                        for (int i = 0; i < childCount1; i++) {
                            AccessibilityNodeInfo child = noteInfo.getChild(i);
                            if (child == null) {
                                break;
                            }
                            Log.e("lv_nodeinfo", "child" + i + "==" + child);
                            if ("android.widget.FrameLayout".equals(child.getClassName())) {
                                for (int j = 0; j < child.getChildCount(); j++) {
                                    AccessibilityNodeInfo child1 = child.getChild(j);
                                    Log.e("list_in_noteInfo", "child" + j + "==" + child1);
                                    if ("图片".equals(child1.getContentDescription())) {
//                                                child1.performAction(AccessibilityNodeInfo.ACTION_CLICK);//拿到图片类型的数据
                                        Rect rect = new Rect();
                                        child1.getBoundsInParent(rect);
                                        Log.e("rect", rect.toString());
                                        if (rect.left == 0 && rect.top == 0 && rect.right == -400 && rect.bottom == 323) {
                                        }
                                    }
                                    if ("android.widget.RelativeLayout".equals(child1.getClassName())) {
                                        if (child1.isEnabled() && child1.isClickable() && child1.isLongClickable()) {
//                                            child1.performAction(AccessibilityNodeInfo.ACTION_CLICK);//拿到某一条小视频
                                            this.child1 = child1;
                                            AccessibilityRecordCompat record = AccessibilityEventCompat.asRecord(accessibilityEvent);
                                            video_source = record.getSource();
                                        }
                                    }
                                }
                            }
                            Log.e("listnoteInfo", "child" + i + "==" + child);
                        }
                        this.child_movie = noteInfo.getChild(1);//点击小视频按钮
                        if (child_movie != null && accessibilityEvent.getItemCount() == 2) {
                            child_movie.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                            Log.e("child_movie", child_movie.toString());
                        }

                    }
                }
                Log.e("noteInfo", "eventType=" + accessibilityEvent.toString() + noteInfo.toString());
                break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED:
                if (noteInfo == null) {
                    break;
                }
                Log.e("noteInfo", "eventType=" + accessibilityEvent.toString() + noteInfo.toString());

                break;
            case AccessibilityEvent.TYPE_ANNOUNCEMENT:
                if (noteInfo == null) {
                    break;
                }
                Log.e("noteInfo", "eventType=" + accessibilityEvent.toString() + noteInfo.toString());

                break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_TRAVERSED_AT_MOVEMENT_GRANULARITY:
                if (noteInfo == null) {
                    break;
                }
                Log.e("noteInfo", "eventType=" + accessibilityEvent.toString() + noteInfo.toString());

                break;
            case AccessibilityEvent.TYPE_GESTURE_DETECTION_START:
                if (noteInfo == null) {
                    break;
                }
                Log.e("noteInfo", "eventType=" + accessibilityEvent.toString() + noteInfo.toString());
                break;
            case AccessibilityEvent.TYPE_GESTURE_DETECTION_END:
                if (noteInfo == null) {
                    break;
                }
                Log.e("noteInfo", "eventType=" + accessibilityEvent.toString() + noteInfo.toString());
            case AccessibilityEvent.TYPE_TOUCH_INTERACTION_START:
                if (noteInfo == null) {
                    break;
                }
                Log.e("noteInfo", "eventType=" + accessibilityEvent.toString() + noteInfo.toString());
                break;
            case AccessibilityEvent.TYPE_TOUCH_INTERACTION_END:
                if (noteInfo == null) {
                    break;
                }
                Log.e("noteInfo", "eventType=" + accessibilityEvent.toString() + noteInfo.toString());
                break;
            case AccessibilityEvent.TYPE_WINDOWS_CHANGED:
                if (noteInfo == null) {
                    break;
                }
                Log.e("noteInfo", "eventType=" + accessibilityEvent.toString() + noteInfo.toString());
                break;
            case AccessibilityEvent.TYPE_VIEW_CONTEXT_CLICKED:
                if (noteInfo == null) {
                    break;
                }
                Log.e("noteInfo", "eventType=" + accessibilityEvent.toString() + noteInfo.toString());
                break;
        }
        /*if (noteInfo != null) {
            boolean IsShipin = recycle1(noteInfo);
//            Log.e("NodeInfo", "" + noteInfo);
            if (IsShipin)
                recycle2(noteInfo);
            else
                recycle3(noteInfo);
        }*/
    }

    /*public boolean recycle1(AccessibilityNodeInfo noteInfo) {
        if (noteInfo == null) return false;
        boolean isHave = noteInfo.getText() != null && noteInfo.getText().equals("最近14天拍摄的小视频");
        if (!isHave) {
            for (int j = 0; j < noteInfo.getChildCount(); j++) {
                isHave = recycle1(noteInfo.getChild(j));
                if (isHave)
                    return isHave;
            }
        }
        return isHave;
    }

    public void recycle2(AccessibilityNodeInfo noteInfo) {
        if (noteInfo == null) return;
        Rect rect = new Rect(0, 0, 0, 0);
        noteInfo.getBoundsInScreen(rect);
        if (rect.left == 0 && rect.right <= width / 3)
            noteInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        else {
            for (int j = 0; j < noteInfo.getChildCount(); j++) {
                recycle2(noteInfo.getChild(j));
            }
        }
    }

    public void recycle3(AccessibilityNodeInfo noteInfo) {
        if (noteInfo == null) return;

        if (noteInfo.getChildCount() == 0) {
            if (noteInfo.getText() != null && noteInfo.getText().equals("小视频")) {
                noteInfo.getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
        } else {
            for (int j = 0; j < noteInfo.getChildCount(); j++) {
                recycle3(noteInfo.getChild(j));
            }
        }
    }
*/
    @Override
    public void onInterrupt() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        windowManager.removeView(floatView);
    }

    public void removeView() {
        if (windowManager != null && floatView != null) {
            windowManager.removeView(floatView);
        }
    }

    private void createView() {
        if (floatView == null) {
            floatView = new FloatView(getApplicationContext());
            isFirstInWX = false;
        }
        floatView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkKey1();
            }
        });
        floatView.setImageResource(R.drawable.changanavi);
        if (windowManager == null) {
            windowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        }

        windowManagerParams = ((FloatApplication) getApplication()).getWindowParams();

        windowManagerParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        windowManagerParams.format = PixelFormat.RGBA_8888;

        windowManagerParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

        windowManagerParams.gravity = Gravity.LEFT | Gravity.TOP;
        windowManagerParams.x = 700;
        windowManagerParams.y = 0;
        windowManagerParams.width = 120;
        windowManagerParams.height = 120;

        windowManager.addView(floatView, windowManagerParams);
    }

    private void checkKey1() {
        //拿到按钮的位置
        Log.e("position==", "x=" + floatView.positionX + ",y=" + floatView.positionY);
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        if (nodeInfo == null) {
            return;
        }
        List<AccessibilityNodeInfo> list1 = nodeInfo.findAccessibilityNodeInfosByText("更多功能按钮");
        for (AccessibilityNodeInfo n : list1) {
            if (n.isEnabled()) {
                n.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
        }
        if (child1 != null) {
            Rect rect = new Rect();
            child1.getBoundsInParent(rect);
            Log.e("rect", rect.toString() + ",,");//Rect(0, 0 - 448, 336)
            if (floatView.positionY >= rect.bottom) {
                child1.performAction(AccessibilityNodeInfo.ACTION_LONG_CLICK);
                Log.e("video1", child1.toString());
                Log.e("video2", video_node.toString());
            }
        }
    }

    /**
     * 替换录制小视频为收藏小视频
     *
     * @return
     */
    private void getVideoPath() {
        File directory = Environment.getExternalStorageDirectory();
        File f = new File(directory + "/tencent/MicroMsg");
        File[] files1 = f.listFiles();
        String path = "";
        List<String> paths = new ArrayList<>();
        List<Integer> indexs = new ArrayList<>();
        for (int i = 0; i < files1.length; i++) {
            indexs.add(files1[i].getName().length());
        }
        int maxIndex = 0;
        for (int i = 0; i < indexs.size(); i++) {
            Log.e("name", files1[i].getName());
            for (int j = i + 1; j < indexs.size(); j++) {
                if (indexs.get(j) >= indexs.get(maxIndex)) {
                    if (!files1[j].toString().contains(".")) {
                        maxIndex = j;
                    }
                }
            }
        }
        int o = indexs.indexOf(indexs.get(maxIndex));
        String filePath = files1[maxIndex].toString();
        File defaultFile = null;
        File defaultTwoFile = null;//朋友圈录制小视频 保存的本地路径
        if (filePath.contains("temp")) {
            defaultFile = new File(filePath.substring(0, filePath.indexOf("temp")) + "/favorite");
            defaultTwoFile = new File(filePath.substring(0, filePath.indexOf("temp")) + "/draft");
        } else {
            defaultTwoFile = new File(filePath + "/draft");//朋友圈录制小视频 保存的本地路径
            defaultFile = new File(filePath + "/favorite");
        }
        if (defaultFile.listFiles() != null) {
            if (defaultFile.listFiles().length == 0) {
                filePath = files1[o].toString();
                defaultFile = new File(filePath);
            }
        } else {
            return;
        }
        File[] defaultTwoFiles = defaultTwoFile.listFiles();
        List<Long> allModiTimes = new ArrayList<>();
        if (defaultTwoFiles != null) {
            for (File mf : defaultTwoFiles) {
                allModiTimes.add(mf.lastModified());
            }
        } else {
            return;
        }
        if (allModiTimes.size() > 0) {
            int max = 0;
            long max_value = allModiTimes.get(max);
            for (int i = 0; i < allModiTimes.size(); i++) {
                Long theValue = allModiTimes.get(i);
                if (theValue > max_value) {
                    max_value = theValue;
                    max = i;
                }
            }
            File ffff = defaultTwoFiles[max];//拿到录制的小视频文件
            Log.e(o + "length", maxIndex + ",indexs.size=" + indexs.toString() + "," + files1[maxIndex]);
            File[] files = defaultFile.listFiles();
            ArrayList<String> list = new ArrayList<>();
            long now = 0;
            for (int i = 0; i < files.length; i++) {
                File fff = files[i];
                Log.e("length", fff.toString() + "");
                now = fff.lastModified();
                path = fff.getAbsolutePath();
                paths.add(path);
                list.add(String.valueOf(now) + "," + i);
            }
            Collections.sort(list);
            Log.e("list", list.toString());
            if (list.size() > 0) {
                try {
                    String s = list.get(list.size() - 1);
                    s = s.substring(s.indexOf(",") + 1);
                    File mFile = files[Integer.parseInt(s)];
                    File[] files2 = mFile.listFiles();
                    File mF = files2[0];
                    String cap_path = ffff.getAbsolutePath();
                    if (ffff.exists()) {
                        ffff.delete();
                    }
                    FileInputStream fis = new FileInputStream(mF);
                    FileOutputStream fos = new FileOutputStream(cap_path);
                    byte[] buf = new byte[1024];
                    int len = 0;
                    while ((len = fis.read(buf)) != -1) {
                        fos.write(buf);
                    }
                    fos.flush();
                    fis.close();
                    fos.close();
                    Log.e("path==","cap_path="+cap_path+",mF.name"+mF.getName());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
