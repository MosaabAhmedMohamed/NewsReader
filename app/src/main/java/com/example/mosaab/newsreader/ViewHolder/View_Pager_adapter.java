package com.example.mosaab.newsreader.ViewHolder;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mosaab.newsreader.Common.Common;
import com.example.mosaab.newsreader.ViewHolder.News_fragment;

import java.util.ArrayList;
import java.util.List;

public class View_Pager_adapter extends FragmentPagerAdapter {

    private static final String TAG ="View_Pager_adapter" ;
    private List<Fragment> FragmentCategory ;
        private List<String> TabsName;


        public View_Pager_adapter(FragmentManager fm)
        {
            super(fm);
            FragmentCategory =new ArrayList<>();
            TabsName=new ArrayList<>();
        }

        public void addFragmentPage(Fragment fragment,String fragmentName, boolean UpdateFragment ) {

            FragmentCategory.add(fragment);
            TabsName.add(fragmentName);
        }

        @Override
        public Fragment getItem(int position) {
            return FragmentCategory.get(position);
        }

       @Override
        public CharSequence getPageTitle(int position) {
        return TabsName.get(position);
    }

        @Override
        public int getCount() {
            return FragmentCategory.size();
        }



}

