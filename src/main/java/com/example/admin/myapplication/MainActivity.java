package com.example.admin.myapplication;
/*
*  time：2018-3-10  00：00
*  dowhat：P61  3.7  3.8
*  author：Joe
* */
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {
    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private Button mCheatButton;
    private TextView mQuestionTextView;
    private int mCurrentIndex = 0;
    private static final String TAG="MainActivity";
    private static final String KEY_INDEX="index";
    private static final int REQUEST_CODE_CHEAT = 0;
    private boolean mIsCheater;

    private Question[] mQuestionBank=new Question[]{
            new Question(R.string.question_1,true,0),
            new Question(R.string.question_2,false,0),
            new Question(R.string.question_3,false,0),
            new Question(R.string.question_4,true,0)
    };
    @Override
    public void  onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG,"onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX,mCurrentIndex);
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        if(resultCode!= Activity.RESULT_OK){
            return;
        }
        if(requestCode==REQUEST_CODE_CHEAT){
            if(data==null){
                return;
            }
            mIsCheater=CheatActivity.wasAnswerShown(data);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate(Bundle)called");
        setContentView(R.layout.activity_main);
        if(savedInstanceState!=null){
            //用户旋转过后activity会重新加载导致显示的是第一个题
            mCurrentIndex=savedInstanceState.getInt(KEY_INDEX,0);
            //defaultValue代表如果不是...值就更改

            //防止用户作弊->旋转MainActivity:将mIsCheater传回
            mIsCheater=savedInstanceState.getBoolean(KEY_INDEX,true);
        }
        mTrueButton=(Button)findViewById(R.id.button3);
        mFalseButton=(Button)findViewById(R.id.button4);
        mNextButton =(ImageButton)findViewById(R.id.button5);
        mCheatButton=(Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean answerIsTrue=mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent intent= CheatActivity.newIntent(MainActivity.this,answerIsTrue);
                startActivityForResult(intent,REQUEST_CODE_CHEAT);
            }
        });
        mQuestionTextView=(TextView) findViewById(R.id.question_text_view);
        int question=mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);

        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  Toast.makeText(MainActivity.this,
                        R.string.correct_toast,
                        Toast.LENGTH_SHORT).show();*/
                mCurrentIndex=(mCurrentIndex+1)%mQuestionBank.length;
                updateQuestion();
            }
        });
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  Toast.makeText(MainActivity.this,
                        R.string.correct_toast,
                        Toast.LENGTH_SHORT).show();*/
              checkAnswer(true);
              mQuestionBank[mCurrentIndex].setState(1);

            }
        });
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               checkAnswer(false);
               mQuestionBank[mCurrentIndex].setState(1);
            }
        });
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex=(mCurrentIndex+1)%mQuestionBank.length;
                mIsCheater=true;//防止用户作弊->连续点击NEXT回到偷看的题目上:将mIsCheater值置为true
                updateQuestion();
            }
         });

        updateQuestion();
    }
    private void updateQuestion(){
    int question=mQuestionBank[mCurrentIndex].getTextResId();
    mQuestionTextView.setText(question);
    }
    private void checkAnswer(boolean userPressTrue){
        boolean answerIsTrue=mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId =0;

        if(mIsCheater){messageResId=R.string.judgment_toast;}else{


            if (userPressTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;

            } else {
                messageResId = R.string.incorrect_toast;
            }}
            Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();



    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"onStart()called");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume()called");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onPause()called");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"onStop()called");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy()called");
    }

}
