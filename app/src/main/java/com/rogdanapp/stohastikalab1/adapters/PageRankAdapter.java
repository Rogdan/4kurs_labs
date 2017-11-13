package com.rogdanapp.stohastikalab1.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rogdanapp.stohastikalab1.R;
import com.rogdanapp.stohastikalab1.data.pojo.page_rank.Page;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PageRankAdapter extends RecyclerView.Adapter<PageRankAdapter.PageRankViewHolder>{
    private ArrayList<Page> pages;
    private Context context;

    public PageRankAdapter(Context context) {
        this.context = context;
        pages = new ArrayList<>();
    }

    public void setPages(ArrayList<Page> pages) {
        this.pages = pages;
        notifyDataSetChanged();
    }

    public void clear() {
        pages.clear();
        notifyDataSetChanged();
    }

    @Override
    public PageRankViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_page_rank, parent, false);
        return new PageRankViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PageRankViewHolder holder, int position) {
        Page page = pages.get(position);

        String pageFormat = context.getResources().getString(R.string.page_format);
        holder.linkTV.setText(String.format(pageFormat, page.getPageLink()));

        String pageRankFormat = context.getResources().getString(R.string.page_rank_format);
        holder.pageRankTV.setText(String.format(pageRankFormat, page.getRank()));

        String innerLinksFormat = context.getResources().getString(R.string.inner_links_count_format);
        holder.innerLinksTV.setText(String.format(innerLinksFormat, page.getInnerLinksCount()));
    }

    @Override
    public int getItemCount() {
        return pages.size();
    }

    public class PageRankViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.link_tv)
        protected TextView linkTV;
        @BindView(R.id.inner_links_tv)
        protected TextView innerLinksTV;
        @BindView(R.id.page_rank_tv)
        protected TextView pageRankTV;

        public PageRankViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
