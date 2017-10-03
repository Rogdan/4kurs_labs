package com.rogdanapp.stohastikalab1.ui.experiment;

import android.animation.LayoutTransition;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.nipunbirla.boxloader.BoxLoaderView;
import com.rogdanapp.stohastikalab1.R;
import com.rogdanapp.stohastikalab1.core.BaseActivity;
import com.rogdanapp.stohastikalab1.data.InMemoryStore;
import com.rogdanapp.stohastikalab1.di.Injector;
import com.rogdanapp.stohastikalab1.di.scope.ActivityScope;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;

public class ExperimentActivity extends BaseActivity implements ExperimentComponent.IExperimentView {
    private int currentState;

    @Inject
    protected ExperimentPresenter presenter;

    @BindView(R.id.repeat_count_et)
    protected EditText repeatCountET;
    @BindView(R.id.title_tv)
    protected TextView titleTV;
    @BindView(R.id.progress_bar)
    protected BoxLoaderView progressBar;
    @BindView(R.id.transparent_overlay)
    protected View transparentOverlay;
    @BindView(R.id.progress_state_tv)
    protected TextView progressStateTV;
    @BindView(R.id.play_button_tv)
    protected TextView playButtonTV;

    @Override
    protected int getLayoutId() {
        return R.layout.acitivy_experiment;
    }

    @Override
    protected void initView() {
        currentState = STATE_NOTHING;
        playButtonTV.setText(R.string.start_test);
        titleTV.setText(R.string.experimental);
    }

    @OnClick(R.id.status_bar_left_iv)
    protected void goBack() {
        onBackPressed();
    }

    @OnClick(R.id.play_button_tv)
    protected void onPlayPauseClicked() {
        repeatCountET.setEnabled(false);
        //// TODO: 03.10.2017 check et is empty
        int repeatCount = Integer.valueOf(repeatCountET.getText().toString());

        switch (currentState) {
            case STATE_NOTHING:
                currentState = STATE_RUN;
                presenter.restartExperiment(repeatCount);
                playButtonTV.setText(R.string.pause);
                break;
            case STATE_RUN:
                currentState = STATE_PAUSE;
                presenter.pauseExperiment();
                playButtonTV.setText(R.string.continue_string);
                break;
            case STATE_PAUSE:
                currentState = STATE_RUN;
                presenter.continueExperiment(repeatCount);
                playButtonTV.setText(R.string.pause);
                break;
        }
    }

    @Override
    protected void providePresenter() {
        Injector.getApplicationComponent().plus(new ExperimentModule()).inject(this);
        presenter.bindView(this);
    }

    @Override
    protected void unbindPresenter() {
        presenter.unbindView(this);
    }

    @Override
    public void showProgress() {
        setProgressVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        setProgressVisibility(View.GONE);
    }

    private void setProgressVisibility(int visibility) {
        progressBar.setVisibility(visibility);
        transparentOverlay.setVisibility(visibility);
        progressStateTV.setVisibility(visibility);
    }

    @OnClick(R.id.transparent_overlay)
    public void onTransparentClick() {
        //do nothing
    }

    @Override
    public void updateProgress(int alreadyDone) {
        int allRepeats = Integer.valueOf(repeatCountET.getText().toString());
        float percent = (float)(alreadyDone * 100) / (float)allRepeats;

        String progressFormat = getString(R.string.progress_output_format);
        progressStateTV.setText(String.format(progressFormat, percent, alreadyDone, allRepeats));
    }

    @Override
    public void onExperimentStopped() {
        if (currentState != STATE_PAUSE) {
            playButtonTV.setText(R.string.start_again);
            currentState = STATE_NOTHING;
            repeatCountET.setEnabled(true);
        }

        //// TODO: 03.10.2017 getData and build chart
    }

    protected void initAnimation(View view){
        ViewGroup fragmentVG = (ViewGroup) view;
        if (fragmentVG.getLayoutTransition() != null) {
            fragmentVG.getLayoutTransition().setStartDelay(LayoutTransition.CHANGE_DISAPPEARING, 50);
            fragmentVG.getLayoutTransition().setStartDelay(LayoutTransition.APPEARING, 50);
        }
    }

    @Subcomponent(modules = ExperimentModule.class)
    @ActivityScope
    public interface ExperimentComponent {
        void inject(@NonNull ExperimentActivity activity);
    }

    @Module
    public static class ExperimentModule {
        @Provides
        @ActivityScope
        @NonNull
        public ExperimentPresenter provideMainPresenter(@NonNull InMemoryStore inMemoryStore) {
            return new ExperimentPresenter(inMemoryStore);
        }
    }

    public static final int STATE_PAUSE = 1;
    public static final int STATE_RUN = 2;
    public static final int STATE_NOTHING = 3;
}
