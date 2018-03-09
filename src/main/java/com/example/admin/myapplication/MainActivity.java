package com.example.admin.myapplication;
/*
*  time：2018-3-10  00：00
*  dowhat：P61  3.7  3.8
*  author：Joe
* */
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
    private ImageButton mPrevButton;
    private int alltime=0;
    private TextView mQuestionTextView;
    private int RightResultTimes=0;
    private int mCurrentIndex = 0;
    private static final String TAG="MainActivity";
    private static final String KEY_INDEX="index";
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate(Bundle)called");
        setContentView(R.layout.activity_main);
        if(savedInstanceState!=null){
            mCurrentIndex=savedInstanceState.getInt(KEY_INDEX,0);
        }
        mTrueButton=(Button)findViewById(R.id.button3);
        mFalseButton=(Button)findViewById(R.id.button4);
        mNextButton =(ImageButton)findViewById(R.id.button5);
        mPrevButton=(ImageButton)findViewById(R.id.button6);
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
                updateQuestion();
            }
         });
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex=(mCurrentIndex+mQuestionBank.length-1)%mQuestionBank.length;
              //在设置向前时必须加上mQuestionBank.length以保证mCurrentIndex>0,否则会闪退。
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
        int questionstate=mQuestionBank[mCurrentIndex].getState();

        if(questionstate==0) {
            alltime++;
            if (userPressTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
                RightResultTimes++;
            } else {
                messageResId = R.string.incorrect_toast;
            }
            Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
        }

        if(alltime==mQuestionBank.length){
            try{//等待1s显示
                Thread.sleep(500);
            }catch(Exception e){

            }
            double Result=(double) RightResultTimes/(double) alltime;
            String temp=Double.toString(Result);//以下7部过程是将double类型转化为String+%号
            int start=temp.indexOf(".");
            String result=(temp.substring(0,start+2));
            double d = Double.parseDouble(result);
            d=d*100;
            temp=Double.toString(d);
            result=temp+"%";
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show();}
    }
    private void diaplayScore(){
        Toast.makeText(this,RightResultTimes,Toast.LENGTH_SHORT).show();
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
