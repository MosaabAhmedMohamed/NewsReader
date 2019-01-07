package com.example.mosaab.newsreader.ViewHolder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.example.mosaab.newsreader.Common.Common;
import com.example.mosaab.newsreader.Model.LocalSavedAPIS;
import com.example.mosaab.newsreader.R;
import com.example.mosaab.newsreader.Service.loadDataScheduler;
import java.util.ArrayList;
import java.util.Iterator;
import io.paperdb.Paper;


public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";



    private ViewPager mainViewPager;
    private TabLayout tabLayout;
    private View_Pager_adapter Fragment_Adapter;


    ArrayList<LocalSavedAPIS> Saved_APIS;
    Iterator<LocalSavedAPIS> iterator ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Init_UI();
    }


    private void Init_UI() {

        Saved_APIS = new ArrayList<>();
        Paper.init(this);
        setSavedAPIS();

        tabLayout = findViewById(R.id.TabLayout);
        mainViewPager = findViewById(R.id.mainViewPager);
        tabLayout.setupWithViewPager(mainViewPager);
        Fragment_Adapter = new View_Pager_adapter(getSupportFragmentManager());
        

        if (Paper.book().read(Common.SavedAPIS_Key) != null)
        {
            Saved_APIS = Paper.book().read(Common.SavedAPIS_Key);
            Init_APIS_list(Saved_APIS);

        }

        update_data_every_10Mintes();


        mainViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                //Change_Taps(position);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void setSavedAPIS()
    {
        Saved_APIS.add(new LocalSavedAPIS(Common.TECHCrunchSource,Common.TechCrunch,"Tech Crunch"));
        Saved_APIS.add(new LocalSavedAPIS(Common.MashableSource,Common.Mashable,Common.Mashable));
        Saved_APIS.add(new LocalSavedAPIS(Common.BusinessInsiderSource,Common.BusinessInsider,Common.BusinessInsider));
        Saved_APIS.add(new LocalSavedAPIS("cnn","cnn","CNN"));


        Paper.book().write(Common.SavedAPIS_Key,Saved_APIS);

    }

    

    private void Init_APIS_list(ArrayList<LocalSavedAPIS> saved_apis) {

        iterator = saved_apis.iterator();

        while (iterator.hasNext())
        {
            LocalSavedAPIS item = iterator.next();
            Fragment_Adapter.addFragmentPage(News_fragment.newInstance(item.getKeySavedData(),item.getApiSourceName()), item.getTapsName());

        }

        mainViewPager.setAdapter(Fragment_Adapter);
    }


    private void update_data_every_10Mintes() {


        AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);

           Log.d(TAG, "update_data_every_10Mintes: ");
           Intent broadcastIntent =new Intent(this,loadDataScheduler.class);
           PendingIntent pendingIntent = PendingIntent.getBroadcast(this,1000,broadcastIntent,0);


           alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                   SystemClock.elapsedRealtime() + 10 * 60 * 1000,
                   10 * 60 *1000, pendingIntent);



    }







}