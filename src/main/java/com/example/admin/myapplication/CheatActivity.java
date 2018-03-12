package com.example.admin.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {
    private boolean mAnswerIsTrue;
    private boolean mHasTime;
    private TextView mAnswerTextView;
    private Button mShowAnswerButton;
    private static final String KEY_INDEX="index";
    private static final String KEY_INDEX2="index2";
    private static final String EXTRA_ANSWER_IS_TRUE =
            "com.example.admin.myapplication.MainActivity.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN =
            "com.example.admin.myapplication.MainActivity.answer_shown";
    private static final String EXTRA_ANSWER_CheatTime =
            "com.example.admin.myapplication.MainActivity.answer_cheattime";
    public static Intent newIntent(Context packageContext, boolean answerIsTrue,boolean has_tt){
        Intent intent=new Intent(packageContext,CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE,answerIsTrue);
        intent.putExtra(EXTRA_ANSWER_CheatTime,has_tt);
        return intent;
    }
    public  static boolean wasAnswerShown(Intent result){
        return  result.getBooleanExtra(EXTRA_ANSWER_SHOWN,false);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        if(savedInstanceState!=null){
            ////防止用户作弊->旋转CheatActivity:将mAnswerIsTrue传回
            mAnswerIsTrue=savedInstanceState.getBoolean(KEY_INDEX,true);
            mHasTime=savedInstanceState.getBoolean(KEY_INDEX2,true);
            /*
            //defaultValue或者为0
            */
        }
        mAnswerIsTrue= getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE,false);
       // mHasTime=getIntent().getIntExtra(EXTRA_ANSWER_CheatTime,1);
        mAnswerTextView=(TextView)findViewById(R.id.answer_text_view);
        mShowAnswerButton=(Button)findViewById(R.id.show_answer_button);
        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mAnswerIsTrue){
                    mAnswerTextView.setText(R.string.true_button);
                }else{
                    mAnswerTextView.setText(R.string.false_button);
                }
                setAnswerShownResult(true);
            }
        });
    }
    private void setAnswerShownResult(boolean isAnswerShown){
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN,isAnswerShown);
        setResult(RESULT_OK,data);
    }
}

