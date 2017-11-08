package com.rogdanapp.stohastikalab1.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.rogdanapp.stohastikalab1.data.pojo.naive_bayes.AnalyzerItem;
import com.rogdanapp.stohastikalab1.data.pojo.naive_bayes.BaesClass;
import com.rogdanapp.stohastikalab1.ui.didenko.naive_bayes_lab_2_3.data_showing.DataShowingFragment;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<BaesClass> baesClasses;
    private Context context;

    public ViewPagerAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);

        this.context = context;
        this.baesClasses = new ArrayList<>();
    }

    public void setData(ArrayList<BaesClass> baesClasses) {
        this.baesClasses = baesClasses;
        notifyDataSetChanged();
    }


    public void addItem(BaesClass baesClass) {
        baesClasses.add(baesClass);
    }

    @Override
    public Fragment getItem(int position) {
        DataShowingFragment fragment = new DataShowingFragment();
        ArrayList<AnalyzerItem> items = baesClasses.get(position).getGroupedFrequency();

        switch (position) {
            case HAM_POSITION:
                fragment.setShowingItems(items);
                break;
            default:
            case SPAM_POSITION:
                fragment.setShowingItems(items);
                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return baesClasses.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return baesClasses.get(position).getTypeString();
    }

    private static final int HAM_POSITION = 0;
    private static final int SPAM_POSITION = 1;
}
