package com.rogdanapp.stohastikalab1.ui.didenko.naive_bayes_lab_2_3.data_showing;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.rogdanapp.stohastikalab1.R;
import com.rogdanapp.stohastikalab1.adapters.AnalyzeAdapter;
import com.rogdanapp.stohastikalab1.core.BaseFragment;
import com.rogdanapp.stohastikalab1.data.pojo.AnalyzerItem;

import java.util.ArrayList;

import butterknife.BindView;

public class DataShowingFragment extends BaseFragment {
    private AnalyzeAdapter adapter;
    private ArrayList<AnalyzerItem> data;

    @BindView(R.id.statistic_recycler_view)
    protected RecyclerView recyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_data_showing;
    }

    @Override
    protected void initView() {
        initAdapter();
    }

    private void initAdapter() {
        adapter = new AnalyzeAdapter();
        if (data != null) {
            adapter.setItemsList(data);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    public void setShowingItems(ArrayList<AnalyzerItem> statistic) {
        data = statistic;
        if (adapter != null) {
            adapter.setItemsList(data);
        }
    }

    @Override
    protected void providePresenter() {

    }

    @Override
    protected void unbindPresenter() {

    }
}
