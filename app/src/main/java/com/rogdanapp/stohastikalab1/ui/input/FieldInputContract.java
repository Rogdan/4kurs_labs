package com.rogdanapp.stohastikalab1.ui.input;

import com.rogdanapp.stohastikalab1.core.IBaseView;
import com.rogdanapp.stohastikalab1.data.pojo.Field;
import com.rogdanapp.stohastikalab1.data.pojo.Unit;

public interface FieldInputContract {
    interface IFieldInputView extends IBaseView {
        void onAutocompleteResult(Field field, Unit unit);
        void onDataSaved();
    }

    interface IFieldInputPresenter {
        void autocomplete();

        void saveData(Field field, Unit unit);
    }
}
