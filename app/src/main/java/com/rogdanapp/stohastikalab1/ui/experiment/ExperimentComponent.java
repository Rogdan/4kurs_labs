package com.rogdanapp.stohastikalab1.ui.experiment;

import com.rogdanapp.stohastikalab1.core.IBaseView;

/**
 * Created by Рома on 02.10.2017.
 */

public interface ExperimentComponent {
    interface IExperimentView extends IBaseView {
        void updateProgress(int alreadyDone);

        void onExperimentStopped(int[] yLeftOut, int[] yRightOut, int [] xTopOut, int []xBottomOut);
    }

    interface IExperimentPresenter {
        void pauseExperiment();
        void continueExperiment(int repeatCount);
        void restartExperiment(int repeatCount);
    }
}
