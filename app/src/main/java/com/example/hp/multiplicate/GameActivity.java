package com.example.hp.multiplicate;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Random;

/**
 * Created by hp on 20.08.15.
 */
public class GameActivity extends ActionBarActivity implements View.OnClickListener {
    int currentScore = 0;
    int currentLevel = 1;
    int totalRight = 0;
    int totalWrong = 0;
    int correctAnswer;
    TextView textObjectPartA;
    TextView textObjectPartB;
    TextView textScore;
    TextView textLevel;
    TextView textTotal;
    TextView textTotalRight;
    TextView textTotalWrong;
    TextView youBest;
    Button buttonObjectChoice1;
    Button buttonObjectChoice2;
    Button buttonObjectChoice3;
    SoundPool mSoundPool;
    int rightAnswerClickSound = -1;
    int wrongAnswerClickSound = -1;
    AssetManager mAssetManager;
    AssetFileDescriptor mDescriptor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        textObjectPartA = (TextView)findViewById(R.id.textPartA);
        textObjectPartB = (TextView)findViewById(R.id.textPartB);
        textScore = (TextView)findViewById(R.id.textScore);
        textLevel = (TextView)findViewById(R.id.textLevel);
        buttonObjectChoice1 = (Button)findViewById(R.id.buttonChoice1);
        buttonObjectChoice1.setOnClickListener(this);
        buttonObjectChoice2 = (Button)findViewById(R.id.buttonChoice2);
        buttonObjectChoice2.setOnClickListener(this);
        buttonObjectChoice3 = (Button)findViewById(R.id.buttonChoice3);
        buttonObjectChoice3.setOnClickListener(this);
        textTotal = (TextView)findViewById(R.id.textTotal);
        textTotalRight = (TextView)findViewById(R.id.textTotalRight);
        textTotalWrong = (TextView)findViewById(R.id.textTotalWrong);
        youBest = (TextView)findViewById(R.id.youBest);
        mAssetManager = getAssets();
        mSoundPool = new SoundPool(5, AudioManager.STREAM_MUSIC,0);
        try {
            mDescriptor = mAssetManager.openFd("rightanswer.wav");
            rightAnswerClickSound = mSoundPool.load(mDescriptor,0);
            mDescriptor = mAssetManager.openFd("wronganswer.wav");
            wrongAnswerClickSound = mSoundPool.load(mDescriptor,0);
        }catch(IOException e){
            //Log.i("Exception sounds files: ",""+e);
        }
        setQuestion();

    }

    @Override
    public void onClick(View v) {
        int answerGiven = 0;
        switch (v.getId()){
            case R.id.buttonChoice1:
                answerGiven = Integer.parseInt(""+buttonObjectChoice1.getText());
                break;
            case R.id.buttonChoice2:
                answerGiven = Integer.parseInt(""+buttonObjectChoice2.getText());
                break;
            case R.id.buttonChoice3:
                answerGiven = Integer.parseInt(""+buttonObjectChoice3.getText());
                break;
        }
        updateScoreAndLevel(answerGiven);
        setQuestion();
    }

    void setQuestion(){
        int numberRange = currentLevel*3;
        Random randInt = new Random();
        int partA = randInt.nextInt(numberRange);
        partA++;
        int partB = randInt.nextInt(numberRange);
        partB++;
        correctAnswer = partA*partB;
        int wrongAnswer1 = correctAnswer-(randInt.nextInt(partA)+1);
        int wrongAnswer2 = correctAnswer+(randInt.nextInt(partB)+1);
        textObjectPartA.setText(""+partA);
        textObjectPartB.setText(""+partB);

        int buttonLayout = randInt.nextInt(3);
        switch (buttonLayout){
            case 0:
                buttonObjectChoice1.setText(""+correctAnswer);
                buttonObjectChoice2.setText("" + wrongAnswer1);
                buttonObjectChoice3.setText(""+wrongAnswer2);
                break;
            case 1:
                buttonObjectChoice3.setText(""+correctAnswer);
                buttonObjectChoice1.setText(""+wrongAnswer1);
                buttonObjectChoice2.setText(""+wrongAnswer2);
                break;
            case 2:
                buttonObjectChoice2.setText(""+correctAnswer);
                buttonObjectChoice3.setText(""+wrongAnswer1);
                buttonObjectChoice1.setText(""+wrongAnswer2);
                break;
        }
    }

    void updateScoreAndLevel(int answerGiven){
        if (isCorrect(answerGiven)){
            totalRight++;
            currentScore = currentScore + 1;
            if(currentScore%10==0){
                if (currentLevel<3){
                    currentLevel++;
                }else{
                    currentLevel=3;
                }
            }

        }else{
            totalWrong=totalWrong+1;
            currentScore = 0;
            if (currentLevel>1) {
                currentLevel--;
            }else{
                currentLevel=1;
            }
        }
        if (currentScore>20){
            youBest.setText("Masha, you are the BEST!!! Come on!");
        }else{
            youBest.setText("");
        }
        textLevel.setText("Level: "+currentLevel);
        textScore.setText("Score: "+currentScore);
        textTotalRight.setText("Total Right: "+totalRight);
        textTotalWrong.setText("Total Wrong: "+totalWrong);
    }

    boolean isCorrect(int answerGiven){
        boolean result;
        if (answerGiven==correctAnswer){
            mSoundPool.play(rightAnswerClickSound,1,1,1,0,1);
            Toast.makeText(getApplicationContext(),"Well done!",Toast.LENGTH_SHORT).show();
            result = true;
        }else{
            mSoundPool.play(wrongAnswerClickSound,1,1,1,0,1);
            Toast.makeText(getApplicationContext(),"Sorry",Toast.LENGTH_SHORT).show();
            result = false;
        }
        return result;
    }
}
