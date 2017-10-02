package com.rogdanapp.stohastikalab1.ui.experiment;

import android.support.annotation.NonNull;
import android.view.View;
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

    @Override
    protected int getLayoutId() {
        return R.layout.acitivy_experiment;
    }

    @Override
    protected void initView() {
        titleTV.setText(R.string.experimental);
    }

    @OnClick(R.id.status_bar_left_iv)
    protected void goBack() {
        onBackPressed();
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
        progressBar.setVisibility(View.VISIBLE);
        transparentOverlay.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        transparentOverlay.setVisibility(View.GONE);
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
}
