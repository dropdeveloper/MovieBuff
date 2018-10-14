package com.example.mylibrary;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

public class Loading extends LinearLayout implements Animation.AnimationListener {

    private LinearLayout loadView;
    private View loadBar;
    Animation moveRight;

    public Loading(Context context) {
        super(context);
        init();
    }

    public Loading(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Loading(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.activity_loading, this);
        moveRight = AnimationUtils.loadAnimation(getContext(), R.anim.left_right);
        loadBar = findViewById(R.id.loading_bar);
        loadView = findViewById(R.id.load_view);
        loadBar.startAnimation(moveRight);
        moveRight.setAnimationListener(this);
    }

    public void setLoading(boolean vl){

        if (vl){

            loadView.setVisibility(VISIBLE);

        }else {

            loadView.setVisibility(GONE);
        }


    }


    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

        loadBar.startAnimation(moveRight);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
