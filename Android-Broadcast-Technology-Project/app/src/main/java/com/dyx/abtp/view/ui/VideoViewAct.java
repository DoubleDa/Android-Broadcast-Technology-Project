package com.dyx.abtp.view.ui;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.MediaController;
import android.widget.VideoView;

import com.dyx.abtp.R;
import com.dyx.abtp.view.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * project name：Android-Broadcast-Technology-Project
 * class describe：
 * create person：dayongxin
 * create time：16/7/28 下午5:13
 * alter person：dayongxin
 * alter time：16/7/28 下午5:13
 * alter remark：
 */
public class VideoViewAct extends BaseActivity {
    @Bind(R.id.vv)
    VideoView vv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_video_view_layout);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath() + MenuAct.videoFileName);
        //设置MediaController
        vv.setMediaController(new MediaController(this));
        //设置Uri
        vv.setVideoURI(uri);
        //开始播放
        vv.start();
        //请求聚焦
        vv.requestFocus();
    }
}
