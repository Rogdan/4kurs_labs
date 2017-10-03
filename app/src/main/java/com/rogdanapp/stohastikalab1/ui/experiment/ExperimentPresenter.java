package com.rogdanapp.stohastikalab1.ui.experiment;

import com.rogdanapp.stohastikalab1.core.Presenter;
import com.rogdanapp.stohastikalab1.data.Constants;
import com.rogdanapp.stohastikalab1.data.InMemoryStore;
import com.rogdanapp.stohastikalab1.data.pojo.Field;
import com.rogdanapp.stohastikalab1.data.pojo.Unit;
import com.rogdanapp.stohastikalab1.tools.Informator;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.rogdanapp.stohastikalab1.data.Constants.*;

public class ExperimentPresenter extends Presenter<ExperimentComponent.IExperimentView> implements ExperimentComponent.IExperimentPresenter{
    private InMemoryStore repository;
    private int experimentsCounter;
    private boolean isPause;
    private Unit unit;
    private Field field;

    private int[] yLeftOut;
    private int[] yRightOut;
    private int[] xTopOut;
    private int[] xBottomtOut;

    @Inject
    public ExperimentPresenter(InMemoryStore inMemoryStore) {
        this.repository = inMemoryStore;
        this.unit = repository.getUnit();
        this.field = repository.getField();
    }

    @Override
    public void pauseExperiment() {
        isPause = true;
    }

    @Override
    public void continueExperiment(int repeatsCount) {
        int startFrom = experimentsCounter;

        int repeatsCountToNotify = calculateRepeatsCountForNotifyView(repeatsCount);
        isPause = false;

        view().showProgress();

        Subscription subscription = Observable.create(emitter -> {
            for (int i = startFrom; i < repeatsCount; i++) {
                if (isPause) {
                    break;
                }

                letsGetExperiment();
                experimentsCounter++;

                if (i % repeatsCountToNotify == 0) {
                    emitter.onNext(experimentsCounter);
                }
            }

            emitter.onCompleted();
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> {
                    Informator.error(getClass(), throwable.toString());
                })
                .doOnNext(result -> {
                    int currentPosition = (Integer) result;

                    if (view().isActive()) {
                        view().updateProgress(currentPosition);
                    }
                }).doOnCompleted(() -> {
                    if (view().isActive()) {
                        view().hideProgress();
                        view().onExperimentStopped(yLeftOut, yRightOut, xTopOut, xBottomtOut);
                    }
                }).subscribe();

        subscriptionsToUnbind.add(subscription);
    }

    private int calculateRepeatsCountForNotifyView(int repeatsCount) {
        if (repeatsCount < PROGRESS_BAR_ITERATIONS_COUNT) {
            return MINIMAL_ITEMS_NOTIFYING;
        } else {
            float result = (float)repeatsCount / (float) PROGRESS_BAR_ITERATIONS_COUNT;
            int ceilInt = (int) Math.ceil(result);

            return ceilInt < MINIMAL_ITEMS_NOTIFYING ? MINIMAL_ITEMS_NOTIFYING : ceilInt;
        }
    }

    private void letsGetExperiment(){
        unit.moveToStart();
        int x, y, direction;

        while (true) {
            direction = unit.randomStep();
            x = unit.getActualX();
            y = unit.getActualY();

            if (direction == Constants.SLEEP_DIRECTION){
                field.incOnPointSteps(x, y, direction);
                return;
            } else if (field.isOnField(x, y)) {
                field.incOnPointSteps(x, y, direction);
            } else {
                switch (direction) {
                    case DOWN_DIRECTION:
                        xBottomtOut[x]++;
                        break;
                    case UP_DIRECTION:
                        xTopOut[x]++;
                        break;
                    case LEFT_DIRECTION:
                        yLeftOut[y]++;
                        break;
                    case RIGHT_DIRECTION:
                        yRightOut[y]++;
                        break;
                }
                return;
            }
        }
    }

    @Override
    public void restartExperiment(int repeatCount) {
        experimentsCounter = 0;
        isPause = false;
        unit.clear();
        field.clear();

        yLeftOut = new int[field.getHeight()];
        yRightOut = new int[field.getHeight()];
        xTopOut = new int[field.getWidth()];
        xBottomtOut = new int[field.getWidth()];

        continueExperiment(repeatCount);
    }

    private static final int PROGRESS_BAR_ITERATIONS_COUNT = 10000;
    private static final int MINIMAL_ITEMS_NOTIFYING = 423;
}
