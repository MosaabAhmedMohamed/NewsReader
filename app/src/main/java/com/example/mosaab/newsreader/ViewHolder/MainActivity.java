package com.example.mosaab.newsreader.ViewHolder;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.example.mosaab.newsreader.Adapter.View_Pager_adapter;
import com.example.mosaab.newsreader.Common.Common;
import com.example.mosaab.newsreader.Model.news;
import com.example.mosaab.newsreader.R;
import java.util.ArrayList;
import io.paperdb.Paper;


public class MainActivity extends AppCompatActivity {


    private ArrayList<news> TechCrunch_local_dataList;
    private TextView TechCrunch_TV, Mashable_TV, BusinessInsider_TV;
    private ViewPager mainViewPager;

    private View_Pager_adapter view_pager_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Init_UI();
    }


    private void Init_UI() {
        Paper.init(this);
        TechCrunch_local_dataList = new ArrayList<>();

        TechCrunch_TV = findViewById(R.id.TECHCrunch_TV);
        Mashable_TV = findViewById(R.id.Mashable_TV);
        BusinessInsider_TV = findViewById(R.id.Insider_TV);

        if (    Paper.book().read(Common.TechCrunch )== null ||
                Paper.book().read(Common.Mashable) == null ||
                Paper.book().read(Common.BusinessInsider) == null)
        {
            Dummy_Data();
        }


        if (!HasLocalData())
        {
            Dummy_Data();
        }


        mainViewPager = findViewById(R.id.mainViewPager);
        mainViewPager.setOffscreenPageLimit(2);


        view_pager_adapter = new View_Pager_adapter(getSupportFragmentManager());
        view_pager_adapter.addFragmentPage(News_fragment.newInstance(Common.TechCrunch),"");
        view_pager_adapter.addFragmentPage(News_fragment.newInstance(Common.Mashable),"");
        view_pager_adapter.addFragmentPage(News_fragment.newInstance(Common.BusinessInsider),"");
        mainViewPager.setAdapter(view_pager_adapter);

        TechCrunch_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainViewPager.setCurrentItem(0);
            }
        });

        Mashable_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainViewPager.setCurrentItem(1);
            }
        });

        BusinessInsider_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainViewPager.setCurrentItem(2);
            }
        });

        mainViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                Change_Taps(position);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private boolean HasLocalData() {

        TechCrunch_local_dataList =  Paper.book().read(Common.TechCrunch);


        if(TechCrunch_local_dataList != null)
        {

            return true;
        }

        return false;
    }

    private void Dummy_Data()
    {
        TechCrunch_local_dataList.add(new news(1,"newsTECH","lablabla"));
        TechCrunch_local_dataList.add(new news(1,"news","lablabla"));
        TechCrunch_local_dataList.add(new news(1,"news","lablabla"));
        TechCrunch_local_dataList.add(new news(1,"news","lablabla"));
        TechCrunch_local_dataList.add(new news(1,"news","lablabla"));
        TechCrunch_local_dataList.add(new news(1,"news","lablabla"));
        TechCrunch_local_dataList.add(new news(1,"news","lablabla"));
        TechCrunch_local_dataList.add(new news(1,"news","lablabla"));

        SaveInLocal(TechCrunch_local_dataList);

    }

    private void SaveInLocal(ArrayList<news> news_arraylist) {
        Paper.book().write(Common.TechCrunch,news_arraylist);
        Paper.book().write(Common.Mashable,news_arraylist);
        Paper.book().write(Common.BusinessInsider,news_arraylist);
    }



    private void Change_Taps(int position) {

        if (position == 0){

            TechCrunch_TV.setTextColor(getResources().getColor(R.color.textTabBright));
            TechCrunch_TV.setTextSize(22);

            Mashable_TV.setTextColor(getResources().getColor(R.color.textTabLight));
            Mashable_TV.setTextSize(16);

            BusinessInsider_TV.setTextColor(getResources().getColor(R.color.textTabLight));
            BusinessInsider_TV.setTextSize(16);
        }
        else if(position == 1)
        {

            TechCrunch_TV.setTextColor(getResources().getColor(R.color.textTabLight));
            TechCrunch_TV.setTextSize(16);

            Mashable_TV.setTextColor(getResources().getColor(R.color.textTabBright));
            Mashable_TV.setTextSize(22);

            BusinessInsider_TV.setTextColor(getResources().getColor(R.color.textTabLight));
            BusinessInsider_TV.setTextSize(16);
        }
        else if(position == 2)
        {

            TechCrunch_TV.setTextColor(getResources().getColor(R.color.textTabLight));
            TechCrunch_TV.setTextSize(16);

            Mashable_TV.setTextColor(getResources().getColor(R.color.textTabLight));
            Mashable_TV.setTextSize(16);

            BusinessInsider_TV.setTextColor(getResources().getColor(R.color.textTabBright));
            BusinessInsider_TV.setTextSize(22);
        }
    }


}