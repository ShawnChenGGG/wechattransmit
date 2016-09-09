package com.example.moku.accessibilityseiviceforweixin;

import android.app.Application;
import android.view.WindowManager;

/**
 * Created by Administrator on 2016/7/26.
 */
public class FloatApplication extends Application {
	private WindowManager.LayoutParams windowParams = new WindowManager.LayoutParams();

	public WindowManager.LayoutParams getWindowParams() {
		return windowParams;
	}
}
