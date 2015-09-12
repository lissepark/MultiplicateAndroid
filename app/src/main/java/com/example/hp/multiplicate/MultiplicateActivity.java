package com.example.hp.multiplicate;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;


public class MultiplicateActivity extends ActionBarActivity implements View.OnClickListener {
    private static final String MULTIPLICATE_ACTIVITY = "multiplicate_activity";
    SoundPool mSoundPool;
    int selectClickSound = -1;
    AssetManager mAssetManager;
    AssetFileDescriptor mDescriptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplicate);

        Button startGame = (Button)findViewById(R.id.buttonStartPlay);
        startGame.setOnClickListener(this);

        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);

        mSoundPool = new SoundPool(2, AudioManager.STREAM_MUSIC,0);
        mAssetManager = getAssets();
        try {
            mDescriptor = mAssetManager.openFd("select.wav");
            selectClickSound = mSoundPool.load(mDescriptor,0);
        }catch (IOException e){
            //catch exception
        }
        mSoundPool.play(selectClickSound,1,1,1,0,1);
    }


    @Override
    public void onClick(View v) {
        mSoundPool.play(selectClickSound,1,1,1,0,1);
        Intent i;
        i = new Intent(this,GameActivity.class);
        startActivity(i);
    }
}
