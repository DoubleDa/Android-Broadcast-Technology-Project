package com.dyx.abtp.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * project name：Android-Broadcast-Technology-Project
 * class describe：
 * create person：dayongxin
 * create time：16/7/28 下午4:59
 * alter person：dayongxin
 * alter time：16/7/28 下午4:59
 * alter remark：
 */
public abstract class BaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void intentTo(Class<?> cla) {
        Intent intent = new Intent(this, cla);
        startActivity(intent);
    }
}
