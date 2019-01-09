package com.example.mosaab.newsreader.ViewHolder;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.mosaab.newsreader.Common.Common;
import com.example.mosaab.newsreader.Model.LocalSavedAPIS;
import com.example.mosaab.newsreader.Model.Providers;
import com.example.mosaab.newsreader.R;
import com.example.mosaab.newsreader.Service.loadDataScheduler;

import java.util.ArrayList;
import java.util.Iterator;

import io.paperdb.Paper;

public class News_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "News_Activity";



    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private ViewPager mainViewPager;
    private TabLayout tabLayout;
    private View_Pager_adapter Fragment_Adapter;


    private ArrayList<Providers> providers_list;
    private ArrayList<LocalSavedAPIS> Saved_APIS;
    private Iterator<LocalSavedAPIS> iterator ;

    private boolean IsWebview = false;
    private boolean provider_fragment_checked = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_activiy);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Init_UI();


    }


    private void Init_UI() {

        providers_list = new ArrayList<>();
        Saved_APIS = new ArrayList<>();
        Paper.init(this);
        //setSavedAPIS();

        appBarLayout = findViewById(R.id.appbar);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


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
        Saved_APIS.add(new LocalSavedAPIS(Common.TECHCrunchSource,Common.TechCrunch,"Tech Crunch",false));
        Saved_APIS.add(new LocalSavedAPIS(Common.MashableSource,Common.Mashable,Common.Mashable,false));
        Saved_APIS.add(new LocalSavedAPIS(Common.BusinessInsiderSource,Common.BusinessInsider,Common.BusinessInsider,false));
        Saved_APIS.add(new LocalSavedAPIS("cnn","cnn","CNN",false));

        providers_list.add(new Providers(Common.TECHCrunchSource,true));
        providers_list.add(new Providers(Common.MashableSource,true));
        providers_list.add(new Providers(Common.BusinessInsiderSource,true));

        Paper.book().write(Common.SavedProviders_list,providers_list);
        Paper.book().write(Common.SavedAPIS_Key,Saved_APIS);

    }



    private void Init_APIS_list(ArrayList<LocalSavedAPIS> saved_apis) {

        iterator = saved_apis.iterator();

        while (iterator.hasNext())
        {
            LocalSavedAPIS item = iterator.next();

            Fragment_Adapter.addFragmentPage(News_fragment.newInstance(item.getKeySavedData(),item.getApiSourceName(),item.isWebview()), item.getTapsName());

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

    @Override
    public void onBackPressed() {
        if (provider_fragment_checked)
        {
            appBarLayout.setVisibility(View.VISIBLE);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_add_news_provider) {
            // Handle the camera action
            Show_add_provider_dialog();
        } else if (id == R.id.nav_news_provider) {

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.coordinator_layout,new Providers_List());
            fragmentTransaction.addToBackStack("");
            fragmentTransaction.commit();
            appBarLayout.setVisibility(View.GONE);
            provider_fragment_checked = true;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void Show_add_provider_dialog()
    {
        final Dialog builder = new Dialog(this);

        View view = LayoutInflater.from(this).inflate(R.layout.add_news_provider, null);

        RadioButton radio_Source_btn,radio_Webview_btn;
        TextView title =  view.findViewById(R.id.add_news_provider_title);
        final EditText provider_name_edt = view.findViewById(R.id.add_provider_et);
        radio_Source_btn = view.findViewById(R.id.radio_Source_btn);
        radio_Webview_btn = view.findViewById(R.id.radio_Webview_btn);
        Button dismiss = view.findViewById(R.id.dismiss_btn);
        Button confirm = view.findViewById(R.id.confirm_btn);



        title.setText("Please make sure you are connected to Internet");

        //check provider
        radio_Source_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    provider_name_edt.setHint(" Source API name");
                    IsWebview = false;

                }
            }
        });
        
        radio_Webview_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                {
                    provider_name_edt.setHint(" Website name ");
                    IsWebview = true;

                }
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Api_name = provider_name_edt.getText().toString();
                LocalSavedAPIS localSavedAPIS = new LocalSavedAPIS(Api_name,Api_name,Api_name,IsWebview);
                save_new_provider_inLocal(localSavedAPIS);
                builder.dismiss();
            }
        });

        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });

        builder.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        builder.setContentView(view);
        builder.show();

    }

    private void save_new_provider_inLocal(LocalSavedAPIS localSavedAPIS) {

      if (Paper.book().read(Common.SavedAPIS_Key) != null )
        {
            Log.d(TAG, "save_new_provider_inLocal: "+Paper.book().read(Common.SavedAPIS_Key).toString());
            providers_list = Paper.book().read(Common.SavedProviders_list);
            Saved_APIS = Paper.book().read(Common.SavedAPIS_Key);

            providers_list.add(new Providers(localSavedAPIS.getApiSourceName(),true));
            Saved_APIS.add(localSavedAPIS);

            Paper.book().delete(Common.SavedProviders_list);
            Paper.book().delete(Common.SavedAPIS_Key);
            Paper.book().write(Common.SavedProviders_list,providers_list);
            Paper.book().write(Common.SavedAPIS_Key,Saved_APIS);


        }
    }


}
