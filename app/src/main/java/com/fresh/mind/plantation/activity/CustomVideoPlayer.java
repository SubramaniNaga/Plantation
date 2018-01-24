package com.fresh.mind.plantation.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.Configuration;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;


import com.fresh.mind.plantation.R;

import java.io.File;
import java.io.IOException;


/**
 * Created by AND I5 on 20-04-2017.
 */

public class CustomVideoPlayer extends Activity implements TextureView.SurfaceTextureListener {
    String filename;
    private MediaPlayer mMediaPlayer;
    VideoView vv;
    DisplayMetrics dm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.custom_player);
        vv = (VideoView) findViewById(R.id.video_player_view);
        Intent extras = getIntent();
        filename = extras.getStringExtra("videofilename");
        try {
            System.gc();
            Log.d("filename", "" + filename);
            if (filename != null) {
                vv.setVideoPath(filename);
                dm = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(dm);
                vv.setMediaController(new MediaController(this));
                vv.requestFocus();
                vv.start();
            } else {
                Log.d("FileName", "Null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        vv.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        vv.resume();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d("dsffsadf", "23423 " + newConfig.getLayoutDirection());
    }


    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        Surface s = new Surface(surface);
        try {
            mMediaPlayer = new MediaPlayer();
            Log.d("ewew", "" + filename);
            mMediaPlayer.setDataSource(filename);
            mMediaPlayer.setSurface(s);

            mMediaPlayer.start();
            //mMediaPlayer.prepare();

        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }
}
