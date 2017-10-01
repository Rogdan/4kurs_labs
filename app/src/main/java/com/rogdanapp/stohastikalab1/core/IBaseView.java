package com.rogdanapp.stohastikalab1.core;

public interface IBaseView {
    void showErrorDialog(String message);

    void showProgress();

    void hideProgress();

    boolean isActive();
}
