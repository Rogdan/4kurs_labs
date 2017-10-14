package com.rogdanapp.stohastikalab1.ui.didenko;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ProgressBar;

import com.rogdanapp.stohastikalab1.R;
import com.rogdanapp.stohastikalab1.core.BaseActivity;
import com.rogdanapp.stohastikalab1.data.InMemoryStore;
import com.rogdanapp.stohastikalab1.di.Injector;
import com.rogdanapp.stohastikalab1.di.scope.ActivityScope;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;

public class AnalyzeActivity extends BaseActivity implements AnalyzeContract.IAnalyzeView{
    @Inject
    protected AnalyzePresenter presenter;

    ArrayList<AnalyzerItem> ham, spam;

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
        showProgress();
    }

    private void initViewPager() {
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                DataShowingFragment fragment = new DataShowingFragment();
                Bundle arguments = new Bundle();
                switch (position) {
                    case 0:
                        arguments.putSerializable(DataShowingFragment.STATISTIC_KEY, ham);
                        break;
                    default:
                    case 1:
                        arguments.putSerializable(DataShowingFragment.STATISTIC_KEY, spam);
                        break;
                }

                fragment.setArguments(arguments);
                return fragment;
            }

            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "Ham";
                    default:
                        return "Spam";
                }
            }
        });
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

    @Override
    protected void providePresenter() {
        Injector.getApplicationComponent().plus(new AnalyzeModule()).inject(this);
        presenter.bindView(this);

        Analyzer analyzer = new Analyzer(AnalyzeActivity.this);
        analyzer.analysisData();

        ham = analyzer.convertToFrequencies(analyzer.getHamWords());
        spam = analyzer.convertToFrequencies(analyzer.getSpamWords());

        initViewPager();
        hideProgress();
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
        public AnalyzePresenter provideAnalyzePresenter(@NonNull InMemoryStore inMemoryStore) {
            return new AnalyzePresenter(inMemoryStore);
        }
    }
}
