package com.example.mosaab.newsreader;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class View_Pager_adapter extends FragmentPagerAdapter {
    public View_Pager_adapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){

            case 0:
                return News_fragment.newInstance("param1") ;


            case 1:
                News_fragment news_fragment2 = new News_fragment();
                return news_fragment2 ;

            case 2:
                News_fragment news_fragment3 = new News_fragment();
                return news_fragment3 ;

            default:
                return null;
        }


    }

    @Override
    public int getCount() {
        return 3;
    }
}
