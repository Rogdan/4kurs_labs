package com.rogdanapp.stohastikalab1.ui.didenko.naive_bayes_lab_2_3.input;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rogdanapp.stohastikalab1.R;
import com.rogdanapp.stohastikalab1.core.BaseActivity;
import com.rogdanapp.stohastikalab1.data.InMemoryStore;
import com.rogdanapp.stohastikalab1.di.Injector;
import com.rogdanapp.stohastikalab1.di.scope.ActivityScope;
import com.rogdanapp.stohastikalab1.tools.Informator;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;

public class AnalyzeInputActivity extends BaseActivity implements AnalyzeInputContract.IAnalyzeInputView {
    @BindView(R.id.analyze_et)
    protected EditText analyzeET;
    @BindView(R.id.analyze_result_tv)
    protected TextView analyzeResultTV;
    @BindView(R.id.analyze_button_tv)
    protected TextView analyzeButtonTV;
    @BindView(R.id.progress_bar)
    protected ProgressBar progressBar;

    @Inject
    protected AnalyzeInputPresenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_analyze_input;
    }

    @Override
    protected void initView() {
        clearAnalyzeResult();
    }

    @OnClick(R.id.back_button_tv)
    protected void goBack() {
        onBackPressed();
    }

    @OnClick(R.id.analyze_button_tv)
    protected void startAnalyze() {
        String textToAnalyze = analyzeET.getText().toString();
        if (isTextValid(textToAnalyze)) {
            clearAnalyzeResult();
            presenter.startAnalyze(textToAnalyze);
        } else {
            Informator.toast(this, R.string.text_coudnt_be_empty);
        }
    }

    private boolean isTextValid(String text) {
        return !text.trim().isEmpty();
    }

    @Override
    protected void providePresenter() {
        Injector.getApplicationComponent().plus(new AnalyzeInputModule()).inject(this);
        presenter.bindView(this);
    }

    private void clearAnalyzeResult(){
        analyzeResultTV.setText("");
    }

    @Override
    protected void unbindPresenter() {
        presenter.unbindView(this);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        analyzeButtonTV.setEnabled(false);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        analyzeButtonTV.setEnabled(true);
    }

    @Override
    public void onDataAnalyzed(double spamProbabilityPercent, double hamProbabilityPercent) {
        String analyzeResultFormat = getResources().getString(R.string.analyze_result_format);
        analyzeResultTV.setText(String.format(analyzeResultFormat, spamProbabilityPercent, hamProbabilityPercent));
    }

    @Subcomponent(modules = AnalyzeInputModule.class)
    @ActivityScope
    public interface AnalyzeInputComponent {
        void inject(@NonNull AnalyzeInputActivity activity);
    }

    @Module
    public static class AnalyzeInputModule {
        @Provides
        @ActivityScope
        @NonNull
        public AnalyzeInputPresenter provideAnalyzeInputPresenter(@NonNull InMemoryStore inMemoryStore) {
            return new AnalyzeInputPresenter(inMemoryStore);
        }
    }
}
