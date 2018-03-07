package com.example.admin.myapplication;
/*
*  time：2018-3-7  22：49
*  dowhat：P42   2.7  2.8
*  author：Joe
* */
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mPrevButton;
    private TextView mQuestionTextView;
    private Question[] mQuestionBank=new Question[]{
            new Question(R.string.question_1,true),
            new Question(R.string.question_2,false),
            new Question(R.string.question_3,false),
            new Question(R.string.question_4,true)
    };
    private int mCurrentIndex = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTrueButton=(Button)findViewById(R.id.button3);
        mFalseButton=(Button)findViewById(R.id.button4);
        mNextButton =(Button)findViewById(R.id.button5);
        mPrevButton=(Button)findViewById(R.id.button6);
        mQuestionTextView=(TextView) findViewById(R.id.question_text_view);
        int question=mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  Toast.makeText(MainActivity.this,
                        R.string.correct_toast,
                        Toast.LENGTH_SHORT).show();*/
              checkAnswer(true);
            }
        });

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
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               checkAnswer(false);
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
        if (userPressTrue==answerIsTrue){
            messageResId=R.string.correct_toast;
        }else{messageResId=R.string.incorrect_toast;}
        Toast.makeText(this,messageResId,Toast.LENGTH_SHORT).show();
}
}
