package com.rogdanapp.stohastikalab1.core;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;


public abstract class BaseFragment extends Fragment {
    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void providePresenter();

    protected abstract void unbindPresenter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initView();
        providePresenter();
    }

    @Override
    public void onDestroyView() {
        unbindPresenter();
        super.onDestroyView();
    }

    public boolean isActive() {
        return getActivity() != null && isAdded() && !isDetached()
                && getView() != null && !isRemoving();
    }

    public void handleKeyboardAppearance(View view, boolean show) {
        if(getActivity() != null) {
            ((BaseActivity) getActivity()).handleKeyboardAppearance(view, show);
        }
    }
}
