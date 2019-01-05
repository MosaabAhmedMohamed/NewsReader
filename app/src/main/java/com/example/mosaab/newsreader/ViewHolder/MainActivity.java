package com.example.mosaab.newsreader.ViewHolder;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.example.mosaab.newsreader.Adapter.View_Pager_adapter;
import com.example.mosaab.newsreader.Common.Common;
import com.example.mosaab.newsreader.R;



public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";


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

        TechCrunch_TV = findViewById(R.id.TECHCrunch_TV);
        Mashable_TV = findViewById(R.id.Mashable_TV);
        BusinessInsider_TV = findViewById(R.id.Insider_TV);

        mainViewPager = findViewById(R.id.mainViewPager);
        mainViewPager.setOffscreenPageLimit(2);
        view_pager_adapter = new View_Pager_adapter(getSupportFragmentManager());
        InitViewPager();


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

    private void InitViewPager()
    {
        view_pager_adapter.addFragmentPage(new TechCrunch(),"");
        view_pager_adapter.addFragmentPage(News_fragment.newInstance(Common.Mashable),"");
        view_pager_adapter.addFragmentPage(News_fragment.newInstance(Common.BusinessInsider),"");
        mainViewPager.setAdapter(view_pager_adapter);
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