package com.rogdanapp.stohastikalab1.ui.experiment;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.nipunbirla.boxloader.BoxLoaderView;
import com.rogdanapp.stohastikalab1.R;
import com.rogdanapp.stohastikalab1.core.BaseActivity;
import com.rogdanapp.stohastikalab1.data.InMemoryStore;
import com.rogdanapp.stohastikalab1.di.Injector;
import com.rogdanapp.stohastikalab1.di.scope.ActivityScope;
import com.rogdanapp.stohastikalab1.tools.Informator;

import java.util.ArrayList;

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
    @BindView(R.id.chart_layout)
    protected View chartLayoutView;
    @BindView(R.id.x_chart)
    protected BarChart xChart;
    @BindView(R.id.y_chart)
    protected BarChart yChart;

    @Override
    protected int getLayoutId() {
        return R.layout.acitivy_experiment;
    }

    @Override
    protected void initView() {
        currentState = STATE_NOTHING;
        playButtonTV.setText(R.string.start_test);
        titleTV.setText(R.string.experimental);
        repeatCountET.setInputType(InputType.TYPE_CLASS_NUMBER);

        initChats();
    }

    private void initChats() {
        Description description = new Description();
        description.setText("");
        xChart.setDescription(description);
        yChart.setDescription(description);

        YAxis axis = yChart.getAxisRight();
        axis.setValueFormatter((value, axis1) -> " ");
        axis.setDrawAxisLine(false);

    }

    @OnClick(R.id.refresh_tv)
    protected void refresh(){
        presenter.pauseExperiment();

        repeatCountET.setEnabled(true);
        playButtonTV.setText(R.string.start_test);
        currentState = STATE_NOTHING;
        chartLayoutView.setVisibility(View.GONE);
    }

    @OnClick(R.id.status_bar_left_iv)
    protected void goBack() {
        onBackPressed();
    }

    @OnClick(R.id.play_button_tv)
    protected void onPlayPauseClicked() {
        String repeatCountString = repeatCountET.getText().toString().trim();
        if (repeatCountString.isEmpty() || repeatCountString.equals("0")) {
            Informator.toast(this, R.string.repeat_count_must_be_positive);
            return;
        }

        repeatCountET.setEnabled(false);
        int repeatCount = Integer.valueOf(repeatCountString);

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
    public void onExperimentStopped(int[] yLeftOut, int[] yRightOut, int [] xTopOut, int []xBottomOut) {

        chartLayoutView.setVisibility(View.VISIBLE);

        if (currentState != STATE_PAUSE) {
            playButtonTV.setText(R.string.start_again);
            currentState = STATE_NOTHING;
            repeatCountET.setEnabled(true);
        }

        initChartData(yLeftOut, yRightOut, xTopOut, xBottomOut);
    }

    private void initChartData(int[] yLeftOut, int[] yRightOut, int [] xTopOut, int []xBottomOut) {
        xChart.clear();
        yChart.clear();

        float groupSpace = 0.08f;
        float barSpace = 0.03f; // x4 DataSet
        float start = 0f;

        ArrayList<BarEntry> yLeft = new ArrayList<>();
        ArrayList<BarEntry> yRight = new ArrayList<>();
        ArrayList<BarEntry> xTop = new ArrayList<>();
        ArrayList<BarEntry> xBottom = new ArrayList<>();

        for (int i = 0; i < yLeftOut.length; i++) {
            yLeft.add(new BarEntry(i, yLeftOut[i]));
            yRight.add(new BarEntry(i, yRightOut[i]));
        }

        BarDataSet ySet1 = new BarDataSet(yLeft, "Left");
        ySet1.setColor(Color.BLUE);
        BarDataSet tSer2 = new BarDataSet(yRight, "Right");
        tSer2.setColor(Color.RED);

        BarData yData = new BarData(ySet1, tSer2);
        yChart.setData(yData);
        yChart.groupBars(0, groupSpace, barSpace);
        yChart.invalidate();

        for (int i = 0; i < xTopOut.length; i++) {
            xTop.add(new BarEntry(i, xTopOut[i]));
            xBottom.add(new BarEntry(i, xBottomOut[i]));
        }

        BarDataSet xSet1 = new BarDataSet(xTop, "Top");
        xSet1.setColor(Color.BLUE);
        BarDataSet xSet2 = new BarDataSet(xBottom, "Bottom");
        xSet2.setColor(Color.RED);

        BarData xData = new BarData(xSet1, xSet2);
        xChart.setData(xData);
        xChart.groupBars(0, groupSpace, barSpace);
        xChart.invalidate();
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
