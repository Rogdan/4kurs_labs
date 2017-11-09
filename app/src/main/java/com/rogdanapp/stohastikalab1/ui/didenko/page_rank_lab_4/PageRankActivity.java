package com.rogdanapp.stohastikalab1.ui.didenko.page_rank_lab_4;

import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rogdanapp.stohastikalab1.R;
import com.rogdanapp.stohastikalab1.core.BaseActivity;
import com.rogdanapp.stohastikalab1.di.Injector;
import com.rogdanapp.stohastikalab1.di.scope.ActivityScope;

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
    @BindView(R.id.iteration_et)
    protected EditText iterationET;
    @BindView(R.id.clear_link_tv)
    protected TextView clearTV;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_page_rank;
    }

    @Override
    protected void initView() {
        titleTV.setText(R.string.page_rank);

        iterationET.setOnEditorActionListener((v, actionId, event) -> {
            if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                calculatePageRank();
            }

            return false;
        });
    }

    @OnClick(R.id.clear_link_tv)
    protected void clearLink() {
        linkET.setText("");
    }

    @OnClick(R.id.calculate_page_rank_tv)
    protected void calculatePageRank() {
        if (calculatePageRankTV.isEnabled()) {

            String uriString = linkET.getText().toString().trim();
            if (uriString.isEmpty()) {
                showError(R.string.enter_link, linkET);
                return;
            }

            String iterationsCountString = iterationET.getText().toString().trim();
            if (iterationsCountString.isEmpty()) {
                showError(R.string.enter_iterations_count, iterationET);
                return;
            }

            presenter.calculatePageRank(uriString, Integer.valueOf(iterationsCountString));
            linkET.setError(null);
        }
    }

    private void showError(int errorResId, EditText editText) {
        String errorText = getString(errorResId);
        editText.setError(errorText);
    }

    @OnClick(R.id.status_bar_left_iv)
    protected void goBack() {
        onBackPressed();
    }

    @Override
    protected void providePresenter() {
        Injector.getApplicationComponent().plus(new PageRankModule()).inject(this);
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
        setComponentsEnabled(false);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        pageRankResultTV.setVisibility(View.VISIBLE);
        setComponentsEnabled(true);
    }

    private void setComponentsEnabled(boolean isEnabled) {
        calculatePageRankTV.setEnabled(isEnabled);
        iterationET.setEnabled(isEnabled);
        linkET.setEnabled(isEnabled);
        clearTV.setEnabled(isEnabled);
    }

    @Override
    public void onPageRankCalculated(double pageRank, int pagesFound) {
        String resultFormat = getString(R.string.result_format);
        String result = String.format(resultFormat, String.valueOf(pageRank), pagesFound);
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
