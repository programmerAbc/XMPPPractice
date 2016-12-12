package com.practice;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import com.practice.entity.Message;

import java.util.List;

/**
 * Created by gaofeng on 2016-12-08.
 */

public class SpeechRecyclerViewAdatper extends RecyclerView.Adapter<SpeechRecyclerViewAdatper.SpeechRecyclerViewHolder> {

    public static final int SEND_TYPE = 0;
    public static final int RECEIVE_TYPE = 1;
    List<Message> dataSet;

    public SpeechRecyclerViewAdatper(List<Message> dataSet) {
        this.dataSet = dataSet;
    }

    @Override
    public SpeechRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int resId = 0;
        switch (viewType) {
            case SEND_TYPE: {
                resId = R.layout.send_speech_item_layout;
                break;
            }
            case RECEIVE_TYPE: {
                resId = R.layout.receive_speech_item_layout;
                break;
            }
            default:
                break;
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(resId, parent, false);
        SpeechRecyclerViewHolder viewHolder = new SpeechRecyclerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SpeechRecyclerViewHolder holder, int position) {
          holder.setText(dataSet.get(position).getMessage());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    @Override
    public int getItemViewType(int position) {
        return dataSet.get(position).isReceive() ? RECEIVE_TYPE : SEND_TYPE;
    }

    @Override
    public void onViewAttachedToWindow(SpeechRecyclerViewHolder holder) {
        ObjectAnimator scaleXAnimator=ObjectAnimator.ofFloat(holder.getView(),"scaleX",0.618f,1.0f);
        ObjectAnimator scaleYAnimator=ObjectAnimator.ofFloat(holder.getView(),"scaleY",0.618f,1.0f);
        ObjectAnimator translationYAnimator=ObjectAnimator.ofFloat(holder.getView(),"translationY",300,0);
        AnimatorSet set=new AnimatorSet();
        set.play(scaleXAnimator).with(scaleYAnimator).with(translationYAnimator);
        set.setInterpolator(new OvershootInterpolator());
        set.start();
    }

    @Override
    public void onViewDetachedFromWindow(SpeechRecyclerViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
    }

    public class SpeechRecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView speechTv;

        public SpeechRecyclerViewHolder(View itemView) {
            super(itemView);
            speechTv = (TextView) itemView.findViewById(R.id.speechTv);
        }

        public void setText(String text) {
            speechTv.setText(text);
        }
        public View getView(){
            return itemView;
        }
    }
}
