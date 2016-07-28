package com.dyx.abtp.view.ui;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.LinearLayout;

import com.dyx.abtp.R;
import com.dyx.abtp.view.BaseActivity;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * project name：Android-Broadcast-Technology-Project
 * class describe：
 * create person：dayongxin
 * create time：16/7/28 下午5:19
 * alter person：dayongxin
 * alter time：16/7/28 下午5:19
 * alter remark：
 */
public class MediaPlayerAct extends BaseActivity implements MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayer.OnInfoListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnSeekCompleteListener, MediaPlayer.OnVideoSizeChangedListener, SurfaceHolder.Callback {
    @Bind(R.id.sv)
    SurfaceView sv;

    private Display mDisplay;
    private SurfaceHolder mSurfaceHolder;
    private MediaPlayer mMediaPlayer;
    private int mWidth, mHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_media_player_layout);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        //获取SurfaceView的SurfaceHolder
        mSurfaceHolder = sv.getHolder();
        //为SurfaceHolder添加回调
        mSurfaceHolder.addCallback(this);
        /**
         * SURFACE_TYPE_NORMAL:0
         * SURFACE_TYPE_HARDWARE:1
         * SURFACE_TYPE_GPU:2
         * SURFACE_TYPE_PUSH_BUFFERS:3
         */
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        //创建MediaPlayer对象
        mMediaPlayer = new MediaPlayer();
        /**
         * 设置监听事件
         */
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setOnErrorListener(this);
        mMediaPlayer.setOnInfoListener(this);
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnSeekCompleteListener(this);
        mMediaPlayer.setOnVideoSizeChangedListener(this);

        //获取待播放文件路径
        String filePath = Environment.getDownloadCacheDirectory().getPath() + MenuAct.videoFileName;

        try {
            //设置播放资源
            mMediaPlayer.setDataSource(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //获取Display对象
        mDisplay = getWindowManager().getDefaultDisplay();
    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        /**
         * surface创建
         */
        //为MediaPlayer设置SurfaceHolder
        mMediaPlayer.setDisplay(surfaceHolder);
        //MediaPlayer设置异步准备
        mMediaPlayer.prepareAsync();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        //surface改变
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        //surface销毁
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        //播放完成
        finish();
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        /**
         * 播放出错
         */
        switch (i) {
            case MediaPlayer.MEDIA_ERROR_IO:
                //1004  IO错误
                break;
            case MediaPlayer.MEDIA_ERROR_MALFORMED:
                //1007  播放器畸形
                break;
            case MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK:
                //200   播放没有为进度回调进行验证
                break;
            case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                //100   服务器挂了
                break;
            case MediaPlayer.MEDIA_ERROR_TIMED_OUT:
                //110   请求超市
                break;
            case MediaPlayer.MEDIA_ERROR_UNKNOWN:
                //1     未知错误
                break;
            case MediaPlayer.MEDIA_ERROR_UNSUPPORTED:
                //1010  不支持错误
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public boolean onInfo(MediaPlayer mediaPlayer, int i, int i1) {
        /**
         * 播放信息
         */
        switch (i) {
            case MediaPlayer.MEDIA_INFO_BAD_INTERLEAVING:
                //800   不好的交错
                break;
            case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                //701   buffer开始
                break;
            case MediaPlayer.MEDIA_INFO_METADATA_UPDATE:
                //802   网络数据更新
                break;
            case MediaPlayer.MEDIA_INFO_NOT_SEEKABLE:
                //801   不能够被查找
                break;
            case MediaPlayer.MEDIA_INFO_SUBTITLE_TIMED_OUT:
                //902   声明超市
                break;
            case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                //702   buffer结束
                break;
            case MediaPlayer.MEDIA_INFO_UNKNOWN:
                //1     未知
                break;
            case MediaPlayer.MEDIA_INFO_UNSUPPORTED_SUBTITLE:
                //901   不支持的声明
                break;
            case MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
                //3     视频播放开始
                break;
            case MediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING:
                //700   视频跟踪落后
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        /**
         * MediaPlayer准备
         */
        //获取MediaPlayer的宽
        mWidth = mediaPlayer.getVideoWidth();
        //获取MediaPlayer的高
        mHeight = mediaPlayer.getVideoHeight();

        /**
         * 是播放视频进行缩放
         */
        if (mWidth > mDisplay.getWidth() || mHeight > mDisplay.getHeight()) {
            float wRatio = (float) mWidth / (float) mDisplay.getWidth();
            float hRatio = (float) mHeight / (float) mDisplay.getHeight();

            float mRatio = Math.max(wRatio, hRatio);

            mWidth = (int) Math.ceil((float) mWidth / mRatio);
            mHeight = (int) Math.ceil((float) mHeight / mRatio);

            //重新设置SurfaceView布局
            sv.setLayoutParams(new LinearLayout.LayoutParams(mWidth, mHeight));

            //开始播放
            mediaPlayer.start();
        }
    }

    @Override
    public void onSeekComplete(MediaPlayer mediaPlayer) {
        //MediaPlayer采集完成
    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i1) {
        //MediaPlayer视频尺寸发生变化
    }
}
