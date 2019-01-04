package com.example.mosaab.newsreader.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.mosaab.newsreader.Common.Common;
import com.example.mosaab.newsreader.ViewHolder.News_fragment;

import java.util.ArrayList;
import java.util.List;

public class View_Pager_adapter extends FragmentPagerAdapter {

        private List<Fragment> FragmentCategory =new ArrayList<>();

        public View_Pager_adapter(FragmentManager fm)
        {
            super(fm);
        }

        public void addFragmentPage(Fragment fragment,String fragmentName ) {

            FragmentCategory.add(fragment);

        }

        @Override
        public Fragment getItem(int position) {
            return FragmentCategory.get(position);
        }

        @Override
        public int getCount() {
            return FragmentCategory.size();
        }




    }

