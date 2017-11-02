package com.rogdanapp.stohastikalab1.ui.didenko.analyze.input;

import com.rogdanapp.stohastikalab1.R;
import com.rogdanapp.stohastikalab1.core.BaseActivity;
import com.rogdanapp.stohastikalab1.tools.Informator;

import butterknife.OnClick;

public class AnalyzeInputActivity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_analyze_input;

    }

    @Override
    protected void initView() {
    }

    @OnClick(R.id.back_button_tv)
    protected void goBack() {
        onBackPressed();
    }

    @OnClick(R.id.analyze_button_tv)
    protected void startAnalyze() {
        Informator.toast(this, "coming soon");
    }

    @Override
    protected void providePresenter() {

    }

    @Override
    protected void unbindPresenter() {

    }
}
