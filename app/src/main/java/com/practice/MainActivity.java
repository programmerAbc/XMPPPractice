package com.practice;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.practice.entity.Message;
import com.practice.util.DebugUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import me.everything.android.ui.overscroll.IOverScrollDecor;
import me.everything.android.ui.overscroll.IOverScrollUpdateListener;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    Button sendBtn;
    MyApplication myApplication;
    RecyclerView speechRecyclerView;
    LinearLayoutManager linearLayoutManager;
    SpeechRecyclerViewAdatper speechRecyclerViewAdatper;
    List<Message> dataSet=new LinkedList<>();
    EditText messageEt;
    InputMethodManager inputMethodManager =null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        speechRecyclerView= (RecyclerView) findViewById(R.id.speechRecyclerView);
        linearLayoutManager=new LinearLayoutManager(this);
        speechRecyclerView.setLayoutManager(linearLayoutManager);
        speechRecyclerView.setItemAnimator(new DefaultItemAnimator());
        dataSet.addAll(DebugUtil.generateMessageDummyData(100));
        speechRecyclerViewAdatper=new SpeechRecyclerViewAdatper(dataSet);
        speechRecyclerView.setAdapter(speechRecyclerViewAdatper);
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        OverScrollDecoratorHelper.setUpOverScroll(speechRecyclerView,OverScrollDecoratorHelper.ORIENTATION_VERTICAL).setOverScrollUpdateListener(new IOverScrollUpdateListener() {
            final float threshold=MainActivity.this.getResources().getDisplayMetrics().heightPixels/15;
            @Override
            public void onOverScrollUpdate(IOverScrollDecor decor, int state, float offset) {
                if(offset<-threshold){
                    Log.e(TAG, "onOverScrollUpdate: offset="+offset+",threshold="+threshold );
                    speechRecyclerView.scrollToPosition(speechRecyclerViewAdatper.getItemCount()-1);
                    messageEt.requestFocus();
                    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_IMPLICIT_ONLY);
                }
            }
        });
        myApplication= (MyApplication) getApplication();
        messageEt= (EditText) findViewById(R.id.messageEt);

        sendBtn= (Button) findViewById(R.id.sendBtn);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataSet.add(new Message(messageEt.getText().toString(),new Random().nextBoolean()));
                speechRecyclerViewAdatper.notifyItemInserted(speechRecyclerViewAdatper.getItemCount()-1);
                speechRecyclerView.smoothScrollToPosition(speechRecyclerViewAdatper.getItemCount()-1);
            }
        });
    }
}
