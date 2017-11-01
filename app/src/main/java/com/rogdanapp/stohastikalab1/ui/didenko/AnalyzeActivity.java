package com.rogdanapp.stohastikalab1.ui.didenko;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ProgressBar;

import com.rogdanapp.stohastikalab1.R;
import com.rogdanapp.stohastikalab1.adapters.ViewPagerAdapter;
import com.rogdanapp.stohastikalab1.core.BaseActivity;
import com.rogdanapp.stohastikalab1.data.pojo.Analyzer;
import com.rogdanapp.stohastikalab1.data.pojo.AnalyzerItem;
import com.rogdanapp.stohastikalab1.di.Injector;
import com.rogdanapp.stohastikalab1.di.scope.ActivityScope;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;

public class AnalyzeActivity extends BaseActivity implements AnalyzeContract.IAnalyzeView{
    private ViewPagerAdapter adapter;
    private Handler handler;
    private Runnable runnable;

    @Inject
    protected AnalyzePresenter presenter;

    @BindView(R.id.statystic_view_pager)
    protected ViewPager viewPager;
    @BindView(R.id.title_tab_layout)
    protected TabLayout tabLayout;
    @BindView(R.id.screen_content)
    protected View screenContent;
    @BindView(R.id.progress_bar)
    protected ProgressBar progressBar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_analize;
    }

    @Override
    protected void initView() {
        initHandler();
        initViewPager();
    }

    private void initViewPager() {
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initHandler() {
        handler = new Handler();
        runnable = () -> presenter.startAnalyze();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    @Override
    protected void providePresenter() {
        Injector.getApplicationComponent().plus(new AnalyzeModule()).inject(this);
        presenter.bindView(this);
        showProgress();
        handler.postDelayed(runnable, DELAY_TIME_MILLIS);
    }

    @Override
    protected void unbindPresenter() {
        presenter.unbindView(this);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        screenContent.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        screenContent.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDataAnalyzed(ArrayList<AnalyzerItem> analyzedHam, ArrayList<AnalyzerItem> analyzedSpam) {
        adapter.setData(analyzedHam, analyzedSpam);
    }

    @Subcomponent(modules = AnalyzeModule.class)
    @ActivityScope
    public interface AnalyzeComponent {
        void inject(@NonNull AnalyzeActivity activity);
    }

    @Module
    public static class AnalyzeModule {
        @Provides
        @ActivityScope
        @NonNull
        public AnalyzePresenter provideAnalyzePresenter(@NonNull Analyzer analyzer) {
            return new AnalyzePresenter(analyzer);
        }
    }

    private int DELAY_TIME_MILLIS = 500;
}
