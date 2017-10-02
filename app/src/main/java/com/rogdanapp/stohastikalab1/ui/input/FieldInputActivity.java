package com.rogdanapp.stohastikalab1.ui.input;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.rogdanapp.stohastikalab1.R;
import com.rogdanapp.stohastikalab1.core.BaseActivity;
import com.rogdanapp.stohastikalab1.data.InMemoryStore;
import com.rogdanapp.stohastikalab1.data.pojo.Field;
import com.rogdanapp.stohastikalab1.data.pojo.StepChance;
import com.rogdanapp.stohastikalab1.data.pojo.Unit;
import com.rogdanapp.stohastikalab1.di.Injector;
import com.rogdanapp.stohastikalab1.di.scope.ActivityScope;
import com.rogdanapp.stohastikalab1.ui.experiment.ExperimentActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;

public class FieldInputActivity extends BaseActivity implements FieldInputContract.IFieldInputView {
    @Inject
    protected FieldInputPresenter presenter;

    @BindView(R.id.root_scroll_view)
    protected ScrollView scrollView;
    @BindView(R.id.sleep_chance_et)
    protected EditText sleepChanceET;
    @BindView(R.id.up_chance_et)
    protected EditText upChanceET;
    @BindView(R.id.down_chance_et)
    protected EditText downChanceET;
    @BindView(R.id.left_chance_et)
    protected EditText leftChanceET;
    @BindView(R.id.right_chance_et)
    protected EditText rightChanceET;
    @BindView(R.id.width_et)
    protected EditText widthET;
    @BindView(R.id.height_et)
    protected EditText heightET;
    @BindView(R.id.start_x_et)
    protected EditText startXEditText;
    @BindView(R.id.start_y_et)
    protected EditText startYEditText;
    @BindView(R.id.title_tv)
    protected TextView titleTV;
    @BindView(R.id.status_bar_left_iv)
    protected ImageView leftIV;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        leftIV.setVisibility(View.INVISIBLE);
        titleTV.setText(R.string.test_data);
        showSoftKeyboard(sleepChanceET);
    }

    @Override
    protected void providePresenter() {
        Injector.getApplicationComponent().plus(new FieldInputModule()).inject(this);
        presenter.bindView(this);
    }

    private boolean isFill(EditText editText) {
        String text = editText.getText().toString();
        return !text.trim().isEmpty();
    }

    @OnClick(R.id.autocomplete_tv)
    protected void autocomplete(){
        presenter.autocomplete();
    }

    @OnClick(R.id.start_button_tv)
    protected void nextScreen() {
        //// TODO: 02.10.2017 check data is valid

        int width = Integer.valueOf(widthET.getText().toString());
        int height = Integer.valueOf(heightET.getText().toString());
        Field field = new Field(width, height);

        float upChance = Float.valueOf(upChanceET.getText().toString());
        float downChance = Float.valueOf(downChanceET.getText().toString());
        float rightChance = Float.valueOf(rightChanceET.getText().toString());
        float leftChance = Float.valueOf(leftChanceET.getText().toString());
        float sleepChance = Float.valueOf(sleepChanceET.getText().toString());

        StepChance stepChance = new StepChance(sleepChance, upChance, downChance, leftChance, rightChance);
        int startX = Integer.valueOf(startXEditText.getText().toString());
        int startY = Integer.valueOf(startYEditText.getText().toString());
        Unit unit = new Unit(stepChance, startX, startY);

        presenter.saveData(field, unit);
    }

    @Override
    protected void unbindPresenter() {
        presenter.unbindView(this);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void onAutocompleteResult(Field field, Unit unit) {
        StepChance stepChance = unit.getStepChance();

        upChanceET.setText(String.valueOf(stepChance.getUpChance()));
        downChanceET.setText(String.valueOf(stepChance.getDownChance()));
        leftChanceET.setText(String.valueOf(stepChance.getLeftChance()));
        rightChanceET.setText(String.valueOf(stepChance.getRightChance()));
        sleepChanceET.setText(String.valueOf(stepChance.getSleepChance()));

        startXEditText.setText(String.valueOf(unit.getStartX()));
        startYEditText.setText(String.valueOf(unit.getStartY()));

        widthET.setText(String.valueOf(field.getWidth()));
        heightET.setText(String.valueOf(field.getHeight()));
    }

    @Override
    public void onDataSaved() {
        Intent intent = new Intent(this, ExperimentActivity.class);
        startActivity(intent);
    }

    @Subcomponent(modules = FieldInputModule.class)
    @ActivityScope
    public interface FieldInputComponent {
        void inject(@NonNull FieldInputActivity activity);
    }

    @Module
    public static class FieldInputModule {
        @Provides
        @ActivityScope
        @NonNull
        public FieldInputPresenter provideMainPresenter(@NonNull InMemoryStore inMemoryStore) {
            return new FieldInputPresenter(inMemoryStore);
        }
    }
}
