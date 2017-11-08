package com.rogdanapp.stohastikalab1.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rogdanapp.stohastikalab1.R;
import com.rogdanapp.stohastikalab1.data.pojo.naive_bayes.AnalyzerItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnalyzeAdapter extends RecyclerView.Adapter<AnalyzeAdapter.AnalyzeViewHolder>{
    private ArrayList<AnalyzerItem> itemsList;
    private int visibleItemsCount;

    public AnalyzeAdapter(){
        itemsList = new ArrayList<>();
        visibleItemsCount = 0;
    }

    public void setItemsList(ArrayList<AnalyzerItem> itemsList) {
        visibleItemsCount = 0;
        this.itemsList = itemsList;
        expand();
    }

    @Override
    public AnalyzeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_analyze, parent, false);
        return new AnalyzeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AnalyzeViewHolder holder, int position) {
        AnalyzerItem item = itemsList.get(position);

        holder.percentTV.setText("В процентах: " + String.valueOf(item.getInPercent() + "%"));
        holder.countTV.setText("Повторений: " + String.valueOf(item.getCount()));
        holder.wordsTV.setText(item.getText());

        if (isReachBottom(position) && !isAllItemsExpanded()) {
            expand();
        }
    }

    @Override
    public int getItemCount() {
        return visibleItemsCount;
    }

    private boolean isReachBottom(int position) {
        return position + 1 >= visibleItemsCount;
    }

    private boolean isAllItemsExpanded() {
        return visibleItemsCount >= itemsList.size();
    }

    private void expand() {
        int wasVisible = visibleItemsCount;

        int appendedItemsCount;
        if (visibleItemsCount + PAGE_SIZE >= itemsList.size()) {
            appendedItemsCount = itemsList.size() - visibleItemsCount;
        } else {
            appendedItemsCount = PAGE_SIZE;
        }

        visibleItemsCount += appendedItemsCount;
        notifyItemRangeInserted(wasVisible, appendedItemsCount);
    }

    public class AnalyzeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.percent_tv)
        protected TextView percentTV;
        @BindView(R.id.count_tv)
        protected TextView countTV;
        @BindView(R.id.words_tv)
        protected TextView wordsTV;

        public AnalyzeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private static final int PAGE_SIZE = 10;
}
