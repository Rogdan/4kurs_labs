package com.rogdanapp.stohastikalab1.ui;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rogdanapp.stohastikalab1.R;
import com.rogdanapp.stohastikalab1.core.BaseActivity;
import com.rogdanapp.stohastikalab1.ui.didenko.analyze.AnalyzeActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    @BindView(R.id.title_tv)
    protected TextView titleTV;
    @BindView(R.id.status_bar_left_iv)
    protected ImageView leftIV;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        titleTV.setText(R.string.choose_lesson);
        leftIV.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.didenko_labs_tv)
    protected void gotoDidenkoLab(){
        Intent intent = new Intent(this, AnalyzeActivity.class);
        startActivity(intent);
    }

    @Override
    protected void providePresenter() {

    }

    @Override
    protected void unbindPresenter() {

    }
}
