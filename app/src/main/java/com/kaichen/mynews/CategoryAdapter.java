package com.kaichen.mynews;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Kai on 1/21/2018.
 */

public class CategoryAdapter extends FragmentPagerAdapter {

    public CategoryAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return new AllNewsFragment();
        }else if (position == 1){
            return new PoliticsFragment();
        }else if(position == 2){
            return new TechNewsFragment();
        }{
            return new FashionFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0){
            return "All";
        }else if(position == 1){
            return "Politics";
        }else if(position == 2){
            return "Tech";
        }{
            return "Fashion";
        }
    }
}
