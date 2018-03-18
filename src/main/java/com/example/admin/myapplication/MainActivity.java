package com.example.admin.myapplication;
/*
*  time：2018-3-18  23：21
*  dowhat：  6.5
*  author：Joe
*  question：1.key-value之间的关系，如代码中：KEY_INDEX="index";之间的关系是什么，如何确定，如何使用
*            2.翻转主屏幕时cheat的次数会重置且不会接着计次
*           *3.连续点击NEXT时可以跳过cheat检查
*            4.cheat之后再连续回答该问题就会连续减少cheat的次数
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
    public int sur_t=3;
    public int has_t=0;

    private static final String TAG="MainActivity";
    private static final String KEY_INDEX="index";
    private static final String KEY_INDEX2="index2";
    private static final String KEY_INDEX3="index3";
    private static final String KEY_INDEX4="index4";
    private static final int REQUEST_CODE_CHEAT = 0;
    private boolean mIsCheater;
    private boolean mhas_t;

    private Question[] mQuestionBank=new Question[]{
            new Question(R.string.question_1,true,0),
            new Question(R.string.question_2,false,0),
            new Question(R.string.question_3,false,0),
            new Question(R.string.question_4,true,0)
    };

    public MainActivity() {
    }

    @Override
    public void  onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG,"onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX,mCurrentIndex);
        //savedInstanceState.putInt(KEY_INDEX4,has_t);
        //savedInstanceState.putBoolean(KEY_INDEX2,mhas_t);//*****
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

            mIsCheater=savedInstanceState.getBoolean(KEY_INDEX2,true);
            mhas_t=savedInstanceState.getBoolean(KEY_INDEX3,true);
        }
        mTrueButton=(Button)findViewById(R.id.button3);
        mFalseButton=(Button)findViewById(R.id.button4);
        mNextButton =(ImageButton)findViewById(R.id.button5);
        mCheatButton=(Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean answerIsTrue=mQuestionBank[mCurrentIndex].isAnswerTrue();
                boolean has_time=mQuestionBank[mCurrentIndex].getCheated();
                Intent intent= CheatActivity.newIntent(MainActivity.this,answerIsTrue,has_time);
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
                mIsCheater=false;//防止用户作弊->点击NEXT回到偷看的题目上:将mIsCheater值置为true
                                    //以上修改仍存在问题：用户一旦点击NEXT按钮就违规了，不能实现用户随意选答
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
        boolean has_time=mQuestionBank[mCurrentIndex].getCheated();
        int messageResId =0;

        if(mIsCheater){
            if(!mhas_t){
                if(!has_time){has_t++;}mQuestionBank[mCurrentIndex].setCheated(true);
                //解决第四个问题，判断用户是否cheat过这个问题，如果没有，减少用户cheat的次数，
                // 并将该题设置为被cheat过，再检查的时候会判断这个参数
            }
            int a;
            a=sur_t-has_t;
            switch (a) {
                case 0:
                    messageResId = R.string.a0;//sur_t-has_t/judgment_toast
                    mCheatButton.setEnabled(false);//当可查看次数减为0时，禁用cheat按钮
                    break;
                case 1:
                    messageResId = R.string.a1;
                    break;
                case 2:
                    messageResId = R.string.a2;
                    break;
                case 3:
                    messageResId = R.string.a3;
                    break;
                default:
                    messageResId = R.string.ad;
            }
        }else{
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
