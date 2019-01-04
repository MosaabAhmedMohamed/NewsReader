package com.example.mosaab.newsreader.ViewHolder;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mosaab.newsreader.Adapter.View_Pager_adapter;
import com.example.mosaab.newsreader.R;

public class MainActivity extends AppCompatActivity {



    private TextView profile_TV,user_TV,notification_TV;
    private ViewPager mainViewPager;

    private View_Pager_adapter view_pager_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Init_UI();
    }


    private void Init_UI() {

        profile_TV = findViewById(R.id.profile_TV);
        user_TV = findViewById(R.id.users_TV);
        notification_TV = findViewById(R.id.notification_TV);

        mainViewPager = findViewById(R.id.mainViewPager);
        mainViewPager.setOffscreenPageLimit(2);

        view_pager_adapter = new View_Pager_adapter(getSupportFragmentManager());
        mainViewPager.setAdapter(view_pager_adapter);

        profile_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainViewPager.setCurrentItem(0);
            }
        });

        user_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainViewPager.setCurrentItem(1);
            }
        });

        notification_TV.setOnClickListener(new View.OnClickListener() {
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

    private void Change_Taps(int position) {

        if (position == 0){

            profile_TV.setTextColor(getResources().getColor(R.color.textTabBright));
            profile_TV.setTextSize(22);

            user_TV.setTextColor(getResources().getColor(R.color.textTabLight));
            user_TV.setTextSize(16);

            notification_TV.setTextColor(getResources().getColor(R.color.textTabLight));
            notification_TV.setTextSize(16);
        }
        else if(position == 1)
        {

            profile_TV.setTextColor(getResources().getColor(R.color.textTabLight));
            profile_TV.setTextSize(16);

            user_TV.setTextColor(getResources().getColor(R.color.textTabBright));
            user_TV.setTextSize(22);

            notification_TV.setTextColor(getResources().getColor(R.color.textTabLight));
            notification_TV.setTextSize(16);
        }
        else if(position == 2)
        {

            profile_TV.setTextColor(getResources().getColor(R.color.textTabLight));
            profile_TV.setTextSize(16);

            user_TV.setTextColor(getResources().getColor(R.color.textTabLight));
            user_TV.setTextSize(16);

            notification_TV.setTextColor(getResources().getColor(R.color.textTabBright));
            notification_TV.setTextSize(22);
        }
    }


}