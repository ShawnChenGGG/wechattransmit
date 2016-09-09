package com.mxth.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * Created by Administrator on 2016/8/29.
 */
public class MyView extends ImageView {

    private Paint p;
    private float x;
    private float y;
    private float downY;
    private float downX;

    public MyView(Context context) {
        super(context);
        initView();
    }


    private void initView() {
        p = new Paint();
        p.setColor(Color.RED);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Rect r=new Rect(100,100,100,100);
        canvas.drawRect(r,p);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Rect frame = new Rect();
        getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        System.out.println("statusBarHeight:" + statusBarHeight);
        // 获取相对屏幕的坐标，即以屏幕左上角为原点
        x = event.getRawX();
        // statusBarHeight是系统状态栏的高度
        y = event.getRawY() - statusBarHeight;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX = getX();
                downY = getY();
                break;
            case MotionEvent.ACTION_MOVE:
                updateViewPosition();
                break;
            case MotionEvent.ACTION_UP:
                downX=downY=0;
                updateViewPosition();
                break;
        }
        return true;
    }
    private WindowManager windowManager = (WindowManager) getContext()
            .getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
    // 此windowManagerParams变量为获取的全局变量，用以保存悬浮窗口的属性
    public WindowManager.LayoutParams windowManagerParams = new WindowManager.LayoutParams();
    private void updateViewPosition() {
        // 更新浮动窗口位置参数
        windowManagerParams.x = (int) (x - downX);
        windowManagerParams.y = (int) (y - downY);
        windowManager.updateViewLayout(this, windowManagerParams); // 刷新显示
    }
}
