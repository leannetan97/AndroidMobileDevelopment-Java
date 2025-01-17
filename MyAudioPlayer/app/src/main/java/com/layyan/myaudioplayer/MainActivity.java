package com.layyan.myaudioplayer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    public static final int MEDIA_RES_ID = R.raw.lion_heart;

    private TextView mTextDebug;
    private SeekBar mSeekbarAudio;
    private ScrollView mScrollContainer;
    private PlayerAdapter mPlayerAdapter;
    private boolean mUserIsSeeking = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeUI();
        initializeSeekbar();
        initializePlaybackController();
        Log.d(TAG,"onCreate: finished");
    }

    @Override
    protected void onStart(){
        super.onStart();
        mPlayerAdapter.loadMedia(MEDIA_RES_ID);
        Log.d(TAG,"onStart: create MediaPlayer");
    }

    @Override
    protected void onStop(){
        super.onStop();
        if(isChangingConfigurations() && mPlayerAdapter.isPlaying()){
            Log.d(TAG,"onStart: create MediaPlayer");
        } else {
            mPlayerAdapter.release();
            Log.d(TAG, "onStop: release MediaPlayer");
        }
    }

    private void initializeUI(){
        mTextDebug = findViewById(R.id.text_debug);
        Button mPlayButton = findViewById(R.id.button_play);
        Button mPauseButton = findViewById(R.id.button_pause);
        Button mResetButton = findViewById(R.id.button_reset);
        mSeekbarAudio = findViewById(R.id.seekbar_audio);
        mScrollContainer = findViewById(R.id.scroll_container);

        mPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlayerAdapter.pause();
            }
        });

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlayerAdapter.play();
            }
        });

        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlayerAdapter.reset();
            }
        });
    }

    private void initializePlaybackController(){
        MediaPlayerHolder mMediaPlayerHolder = new MediaPlayerHolder(this);
        Log.d(TAG, "initializePlaybackController: created MediaPlayerHolder");
        mMediaPlayerHolder.setPlaybackInfoListener(new PlaybackListener());
        mPlayerAdapter = mMediaPlayerHolder;
        Log.d(TAG, "initializePlaybackController: MediaPlayerHolder progress callback set");

    }

    private void initializeSeekbar(){
        mSeekbarAudio.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int userSelectedPosition = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int position, boolean fromUser) {
                if(fromUser){
                    userSelectedPosition = position;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mUserIsSeeking = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mUserIsSeeking = false;
                mPlayerAdapter.seekTo(userSelectedPosition);
            }
        });
    }

    public  class PlaybackListener extends PlaybackInfoListener{

        @Override
        public void onDurationChanged(int duration) {
            mSeekbarAudio.setMax(duration);
            Log.d(TAG, String.format("setPlaybackDuration: setMax(%d)",duration));
        }


        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onPositionChanged(int position) {
            if(!mUserIsSeeking){
                mSeekbarAudio.setProgress(position,true);
                Log.d(TAG,String.format("setPlaybackPosition: setProgress(%d)", position));
            }
        }

        @Override
        public void onStateChanged(int state) {
            String stateToString = PlaybackInfoListener.convertStateToString(state);
            onLogUpdated(String.format("onStateChanged(%s)",stateToString));
        }

        @Override
        public void onPlaybackCompleted() {

        }

        @Override
        public void onLogUpdated(String formattedMessage) {
            if (mTextDebug != null){
                mTextDebug.append(formattedMessage);
                mTextDebug.append("\n");
                //Moves the scrollContainer focus to the end
                mScrollContainer.post(new Runnable() {
                    @Override
                    public void run() {
                        mScrollContainer.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });
            }
        }
    }
}
