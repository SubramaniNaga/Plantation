package com.fresh.mind.plantation.activity;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.VideoView;

import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.customized.CustomTextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;


/**
 * Created by AND I5 on 15-04-2016.
 */
public class PlayingVideo extends Activity implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener {
    private String filename;
    private DisplayMetrics dm;
    private View rootView;
    private VideoView vv;
    MediaController mc;
    private CustomTextView mDurationTxt, mDurationElapsed;
    ImageView mForwardTxt, mBackwordTxt, ic_media_play;
    private SurfaceView mSurfaceView;
    private MediaPlayer mMediaPlayer;
    private SurfaceHolder mSurfaceHolder;
    private double timeElapsed = 0, finalTime = 0;
    private int forwardTime = 5000, backwardTime = 5000;
    private Handler durationHandler = new Handler();
    private SeekBar seekbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.video_view);
        vv = (VideoView) findViewById(R.id.VideoView);
        mSurfaceView = (SurfaceView) findViewById(R.id.surefaace);
        seekbar = (SeekBar) findViewById(R.id.mSeekerBar);
        mDurationTxt = (CustomTextView) findViewById(R.id.mDuration);
        mDurationElapsed = (CustomTextView) findViewById(R.id.mDurationElapsed);
        mBackwordTxt = (ImageView) findViewById(R.id.mBackwprd);
        mForwardTxt = (ImageView) findViewById(R.id.mForward);
        ic_media_play = (ImageView) findViewById(R.id.ic_media_play);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(PlayingVideo.this);
        mSurfaceView.setVisibility(View.VISIBLE);
        vv.setVisibility(View.GONE);
        seekbar.setMax((int) finalTime);
        seekbar.setClickable(false);
        System.gc();
        Intent extras = getIntent();
        filename = extras.getStringExtra("videofilename");
        String dil = "sdcard/Treepedia/Videos/2017-12-04.mp4";
        File file = new File(filename);

        Log.d("fileName", "" + filename + " " + file.length());
        Uri uri = Uri.fromFile(file);
        try {
            if (filename != null) {
                mc = new MediaController(this);
                dm = new DisplayMetrics();
                this.getWindowManager().getDefaultDisplay().getMetrics(dm);
                int height = dm.heightPixels;
                int width = dm.widthPixels;
                vv.setMinimumWidth(width);
                vv.setMinimumHeight(height);
                vv.setMediaController(mc);
                vv.setVideoPath(filename);
                vv.start();

            } else {
                Log.d("FileName", "Null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mForwardTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((timeElapsed - backwardTime) <= finalTime) {
                    timeElapsed = timeElapsed + backwardTime;
                    Log.d("asasasa41234", "" + timeElapsed);
                    mMediaPlayer.seekTo((int) timeElapsed);
                }
            }
        });
        mBackwordTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((timeElapsed + forwardTime) <= finalTime) {
                    timeElapsed = timeElapsed - forwardTime;
                    mMediaPlayer.seekTo((int) timeElapsed);
                }
            }
        });
        mSurfaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.pause();
                    ic_media_play.setBackgroundResource(android.R.drawable.ic_media_play);

                } else {
                    mMediaPlayer.start();
                    ic_media_play.setBackgroundResource(android.R.drawable.ic_media_pause);
                }
            }
        });
        ic_media_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.pause();
                    ic_media_play.setBackgroundResource(android.R.drawable.ic_media_play);
                } else {
                    mMediaPlayer.start();
                    ic_media_play.setBackgroundResource(android.R.drawable.ic_media_pause);
                }
            }
        });


    }


    public void play() {

        mMediaPlayer.start();
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(getApplicationContext(), Uri.fromFile(new File(filename)));
        String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        long timeInMillisec = Long.parseLong(time);
        ic_media_play.setBackgroundResource(android.R.drawable.ic_media_pause);
        long finalTime = mMediaPlayer.getDuration();
        timeElapsed = mMediaPlayer.getCurrentPosition();
        seekbar.setMax((int) finalTime);
        seekbar.setProgress((int) finalTime);
        /*String timeFinal = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(timeInMillisec), TimeUnit.MILLISECONDS.toMinutes(timeInMillisec) +
                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeInMillisec)), TimeUnit.MILLISECONDS.toSeconds(timeInMillisec) + TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeInMillisec)));
*/
        int seconds = (int) (timeInMillisec / 1000) % 60;
        int minutes = (int) ((timeInMillisec / (1000 * 60)) % 60);
        int hours = (int) ((timeInMillisec / (1000 * 60 * 60)) % 24);
        String timeFinal = "" + hours + ":" + minutes + ":" + seconds;
        Log.d("hdjasfhoi43", "" + hours + ":" + minutes + ":" + seconds);
        mDurationTxt.setText("" + timeFinal);
        Log.d("timeFinal", "" + timeFinal);
        durationHandler.postDelayed(updateSeekBarTime, 100);

    }

    private Runnable updateSeekBarTime = new Runnable() {
        public void run() {
            if (mMediaPlayer != null) {
                finalTime = mMediaPlayer.getDuration();
                timeElapsed = mMediaPlayer.getCurrentPosition();
                seekbar.setProgress((int) timeElapsed);
                seekbar.setVisibility(View.GONE);

                long timeRemaining = (long) (finalTime - timeElapsed);
                long timeElapsed1 = (long) timeElapsed;

                Log.d("dsareawr", "" + finalTime + "  " + timeElapsed + "  " + timeRemaining);
                String elaspedTimeFinal = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(timeElapsed1),
                        TimeUnit.MILLISECONDS.toMinutes(timeElapsed1) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeElapsed1)),
                        TimeUnit.MILLISECONDS.toSeconds(timeElapsed1) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeElapsed1)));
                mDurationElapsed.setText("" + timeElapsed1);
                Log.d("timeElapsed1", "" + timeElapsed1);

                int seconds = (int) (timeElapsed1 / 1000) % 60;
                int minutes = (int) ((timeElapsed1 / (1000 * 60)) % 60);
                int hours = (int) ((timeElapsed1 / (1000 * 60 * 60)) % 24);
                Log.d("hdjasfhoi43", "" + hours + ":" + minutes + ":" + seconds);
                durationHandler.postDelayed(this, 100);
                Log.d("timeFinalelaspedTimeFinal", "" + elaspedTimeFinal);
                if (elaspedTimeFinal.equals("00:00:00")) {
                    ic_media_play.setBackgroundResource(android.R.drawable.ic_media_play);
                }
            }
        }

        private String getDate(long timeElapsed, String dateFormat) {
            SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

            // Create a calendar object that will convert the date and time value in milliseconds to date.
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(timeElapsed);
            return formatter.format(calendar.getTime());
        }
    };


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    @Override
    public void onPrepared(MediaPlayer mp) {
        mMediaPlayer.start();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setDisplay(mSurfaceHolder);
        try {
            File file = new File(filename);
            File videoFiles = new File(String.valueOf(Environment.getExternalStorageDirectory()));
            Log.d("sdafadsf", "" + videoFiles);

            Uri uri1 = Uri.parse(Environment.getExternalStorageDirectory() + "/Treepedia/Videos/" + file.getName());
            Log.d("uri1", "" + uri1);
            Uri uri = Uri.fromFile(file);
            mMediaPlayer.setOnPreparedListener(PlayingVideo.this);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setDataSource("" + uri);
            mMediaPlayer.prepareAsync();
            play();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseMediaPlayer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }
}
