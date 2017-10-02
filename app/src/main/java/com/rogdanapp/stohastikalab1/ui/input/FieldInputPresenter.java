package com.rogdanapp.stohastikalab1.ui.input;

import com.rogdanapp.stohastikalab1.core.Presenter;
import com.rogdanapp.stohastikalab1.data.InMemoryStore;
import com.rogdanapp.stohastikalab1.data.pojo.Field;
import com.rogdanapp.stohastikalab1.data.pojo.Unit;

import javax.inject.Inject;

public class FieldInputPresenter extends Presenter<FieldInputContract.IFieldInputView> implements FieldInputContract.IFieldInputPresenter {
    private InMemoryStore repository;

    @Inject
    public FieldInputPresenter(InMemoryStore repository) {
        this.repository = repository;
    }

    @Override
    public void autocomplete() {
        Field field = Field.autoComplete();
        Unit unit = Unit.autoComplete(field);

        view().onAutocompleteResult(field, unit);
    }

    @Override
    public void saveData(Field field, Unit unit) {
        repository.setUnit(unit);
        repository.setField(field);

        view().onDataSaved();
    }
}
