package com.rogdanapp.stohastikalab1.ui.experiment;

import com.rogdanapp.stohastikalab1.core.IBaseView;
import com.rogdanapp.stohastikalab1.data.pojo.Field;
import com.rogdanapp.stohastikalab1.data.pojo.Unit;

/**
 * Created by Рома on 02.10.2017.
 */

public interface ExperimentComponent {
    interface IExperimentView extends IBaseView {
        void updateProgress(int alreadyDone);

        void onExperimentStopped(Field field, Unit unit);
    }

    interface IExperimentPresenter {
        void pauseExperiment();
        void continueExperiment(int repeatCount);
        void restartExperiment(int repeatCount);
    }
}
