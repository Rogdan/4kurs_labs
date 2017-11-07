package com.rogdanapp.stohastikalab1.ui.didenko.page_rank;

import android.widget.EditText;
import android.widget.TextView;

import com.rogdanapp.stohastikalab1.R;
import com.rogdanapp.stohastikalab1.core.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class PageRankActivity extends BaseActivity {
    @BindView(R.id.link_et)
    protected EditText linkET;
    @BindView(R.id.calculate_page_rank_tv)
    protected TextView calculatePageRankTV;
    @BindView(R.id.title_tv)
    protected TextView titleTV;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_page_rank;
    }

    @Override
    protected void initView() {
        titleTV.setText(R.string.page_rank);
    }

    @OnClick(R.id.status_bar_left_iv)
    protected void goBack() {
        onBackPressed();
    }

    @Override
    protected void providePresenter() {

    }

    @Override
    protected void unbindPresenter() {

    }
}
