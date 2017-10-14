package com.rogdanapp.stohastikalab1.ui.didenko;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.rogdanapp.stohastikalab1.R;
import com.rogdanapp.stohastikalab1.adapters.AnalyzeAdapter;
import com.rogdanapp.stohastikalab1.core.BaseFragment;

import java.util.ArrayList;

import butterknife.BindView;

public class DataShowingFragment extends BaseFragment {
    @BindView(R.id.statistic_recycler_view)
    protected RecyclerView recyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_data_showing;
    }

    @Override
    protected void initView() {
        AnalyzeAdapter adapter = new AnalyzeAdapter();
        ArrayList<AnalyzerItem> items = (ArrayList<AnalyzerItem>) getArguments().getSerializable(STATISTIC_KEY);

        adapter.setItemsList(items);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void providePresenter() {

    }

    @Override
    protected void unbindPresenter() {

    }

    public static final String STATISTIC_KEY = "statistic";
}
