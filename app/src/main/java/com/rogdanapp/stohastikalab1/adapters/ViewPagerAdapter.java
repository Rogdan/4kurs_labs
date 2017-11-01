package com.rogdanapp.stohastikalab1.adapters;

import android.content.Context;
import android.support.annotation.FloatRange;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.rogdanapp.stohastikalab1.R;
import com.rogdanapp.stohastikalab1.data.pojo.AnalyzerItem;
import com.rogdanapp.stohastikalab1.ui.didenko.data_showing.DataShowingFragment;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<AnalyzerItem> ham, spam;
    private Context context;

    public ViewPagerAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);

        this.context = context;
        this.ham = new ArrayList<>();
        this.spam = new ArrayList<>();
    }

    public void setData(ArrayList<AnalyzerItem> ham, ArrayList<AnalyzerItem> spam) {
        this.ham = ham;
        this.spam = spam;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        DataShowingFragment fragment = new DataShowingFragment();
        switch (position) {
            case HAM_POSITION:
                fragment.setShowingItems(ham);
                break;
            default:
            case SPAM_POSITION:
                fragment.setShowingItems(spam);
                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getResources().getString(R.string.ham);
            default:
                return context.getResources().getString(R.string.spam);
        }
    }

    private static final int HAM_POSITION = 0;
    private static final int SPAM_POSITION = 1;
}
