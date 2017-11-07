package com.rogdanapp.stohastikalab1.ui.didenko.page_rank_lab_4;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rogdanapp.stohastikalab1.R;
import com.rogdanapp.stohastikalab1.core.BaseActivity;
import com.rogdanapp.stohastikalab1.di.scope.ActivityScope;
import com.rogdanapp.stohastikalab1.tools.Informator;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;

public class PageRankActivity extends BaseActivity implements PageRankContract.IPageRankView {
    @Inject
    protected PageRankPresenter presenter;

    @BindView(R.id.link_et)
    protected EditText linkET;
    @BindView(R.id.calculate_page_rank_tv)
    protected TextView calculatePageRankTV;
    @BindView(R.id.title_tv)
    protected TextView titleTV;
    @BindView(R.id.progress_bar)
    protected ProgressBar progressBar;
    @BindView(R.id.page_rank_result_tv)
    protected TextView pageRankResultTV;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_page_rank;
    }

    @Override
    protected void initView() {
        titleTV.setText(R.string.page_rank);
    }

    @OnClick(R.id.calculate_page_rank_tv)
    protected void calculatePageRank() {
        Informator.toast(this, "В разработке");
    }

    @OnClick(R.id.status_bar_left_iv)
    protected void goBack() {
        onBackPressed();
    }

    @Override
    protected void providePresenter() {
        presenter.bindView(this);
    }

    @Override
    protected void unbindPresenter() {
        presenter.unbindView(this);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        pageRankResultTV.setVisibility(View.GONE);
        calculatePageRankTV.setEnabled(false);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        pageRankResultTV.setVisibility(View.VISIBLE);
        calculatePageRankTV.setEnabled(true);
    }

    @Override
    public void onPageRankCalculated(double pageRank) {
        String resultFormat = getString(R.string.result_format);
        String result = String.format(resultFormat, String.valueOf(pageRank));
        pageRankResultTV.setText(result);
    }

    @Override
    public void onCalculatingError(String message) {
        pageRankResultTV.setText(message);
    }

    @Subcomponent(modules = PageRankModule.class)
    @ActivityScope
    public interface PageRankComponent {
        void inject(@NonNull PageRankActivity activity);
    }

    @Module
    public static class PageRankModule {
        @Provides
        @ActivityScope
        @NonNull
        public PageRankPresenter providePageRankPresenter() {
            return new PageRankPresenter();
        }
    }
}
