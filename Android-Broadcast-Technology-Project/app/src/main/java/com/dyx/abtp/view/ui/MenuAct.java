package com.dyx.abtp.view.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import com.dyx.abtp.R;
import com.dyx.abtp.view.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * project name：Android-Broadcast-Technology-Project
 * class describe：http://android.jobbole.com/83849/
 * create person：dayongxin
 * create time：16/7/28 下午4:59
 * alter person：dayongxin
 * alter time：16/7/28 下午4:59
 * alter remark：
 */
public class MenuAct extends BaseActivity {
    private static final String TAG = "MenuAct";

    public static final String videoFileName = "test.mp4";
    @Bind(R.id.but1)
    Button but1;
    @Bind(R.id.but2)
    Button but2;
    @Bind(R.id.but3)
    Button but3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.but1, R.id.but2, R.id.but3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.but1:
                playVideoByDefault();
                break;
            case R.id.but2:
                intentTo(VideoViewAct.class);
                break;
            case R.id.but3:
                intentTo(MediaPlayerAct.class);
                break;
        }
    }

    private void playVideoByDefault() {
        Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath() + videoFileName);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "video/mp4");
        startActivity(intent);
    }
}
