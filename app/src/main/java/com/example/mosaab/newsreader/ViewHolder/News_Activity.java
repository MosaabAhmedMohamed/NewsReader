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
import android.text.TextUtils;
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
import android.widget.Toast;

import com.example.mosaab.newsreader.Common.Common;
import com.example.mosaab.newsreader.Interface.Update_Ptoviders_Callback;
import com.example.mosaab.newsreader.Model.LocalSavedAPIS;
import com.example.mosaab.newsreader.Model.Providers;
import com.example.mosaab.newsreader.R;
import com.example.mosaab.newsreader.Service.loadDataScheduler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import io.paperdb.Paper;

public class News_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,Update_Ptoviders_Callback {

    private static final String TAG = "News_Activity";



    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private ViewPager mainViewPager;
    private TabLayout tabLayout;
    private View_Pager_adapter Fragment_Adapter;


    private ArrayList<Providers> providers_list;
    private ArrayList<LocalSavedAPIS> Saved_APIS;
    private Iterator<LocalSavedAPIS> iterator ;

    private boolean provider_fragment_checked = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_activiy);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("News Reader");
        setSupportActionBar(toolbar);

        Init_UI();


    }


    private void Init_UI() {

        providers_list = new ArrayList<>();
        Saved_APIS = new ArrayList<>();
        Paper.init(this);
        //setSavedAPIS();
        if (Paper.book().read(Common.SavedAPIS_Key) == null)
        {
            setSavedAPIS();
            Log.d(TAG, "Init_UI: null");
        }

        appBarLayout = findViewById(R.id.appbar);
        tabLayout = findViewById(R.id.TabLayout);
        mainViewPager = findViewById(R.id.mainViewPager);
        tabLayout.setupWithViewPager(mainViewPager);
        Fragment_Adapter = new View_Pager_adapter(getSupportFragmentManager());


        if (Paper.book().read(Common.SavedAPIS_Key) != null)
        {
            Log.d(TAG, "Init_UI: "+ "saved APIS");
            Saved_APIS = Paper.book().read(Common.SavedAPIS_Key);
            Init_APIS_list(Saved_APIS);
        }

      /*  if(Paper.book().read(Common.SavedProviders_list) != null)
        {
            providers_list = Paper.book().read(Common.SavedProviders_list);
        }*/

        update_data_every_10Mintes();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



    }

    private void setSavedAPIS()
    {
        Saved_APIS.add(new LocalSavedAPIS(Common.TECHCrunchSource,Common.TechCrunch,"Tech Crunch"));
        Saved_APIS.add(new LocalSavedAPIS(Common.MashableSource,Common.Mashable,Common.Mashable));
        Saved_APIS.add(new LocalSavedAPIS(Common.BusinessInsiderSource,Common.BusinessInsider,Common.BusinessInsider));

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

            Fragment_Adapter.addFragmentPage(News_fragment.newInstance(
                    item.getKeySavedData(),
                    item.getApiSourceName()),
                    item.getTapsName());

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

            //Saved_APIS = Paper.book().read(Common.SavedAPIS_Key);
            //Fragment_Adapter = new View_Pager_adapter(getSupportFragmentManager());
            //Init_APIS_list(Saved_APIS);

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
        }
        else if (id == R.id.nav_news_provider) {


            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.coordinator_layout,new Providers_List(this));
            fragmentTransaction.addToBackStack("providers_list");
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

        TextView title =  view.findViewById(R.id.add_news_provider_title);
        final EditText provider_name_edt = view.findViewById(R.id.add_provider_et);

        Button dismiss = view.findViewById(R.id.dismiss_btn);
        Button confirm = view.findViewById(R.id.confirm_btn);



        title.setText("Add new Provider");
        provider_name_edt.setVisibility(View.VISIBLE);
        provider_name_edt.setHint(" Source API name");

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Api_name = provider_name_edt.getText().toString();
                LocalSavedAPIS localSavedAPIS = new LocalSavedAPIS(Api_name,Api_name,Api_name);
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
            Iterator<Providers> providersIterator;
            ArrayList uniqeProviders = new ArrayList();
            providersIterator = providers_list.iterator();

            Log.d(TAG, "save_new_provider_inLocal: "+Paper.book().read(Common.SavedAPIS_Key).toString());
            providers_list = Paper.book().read(Common.SavedProviders_list);
            Saved_APIS = Paper.book().read(Common.SavedAPIS_Key);


            while (providersIterator.hasNext())
            {
               Providers item = providersIterator.next();
               uniqeProviders.add(item.getProvider_name());
            }

            if (!uniqeProviders.contains(localSavedAPIS.getApiSourceName()))
            {
                Fragment_Adapter.addFragmentPage(News_fragment.newInstance(
                        localSavedAPIS.getKeySavedData(),
                        localSavedAPIS.getApiSourceName()),
                        localSavedAPIS.getTapsName());

                providers_list.add(new Providers(localSavedAPIS.getApiSourceName(),true));
                Saved_APIS.add(localSavedAPIS);

                Paper.book().delete(Common.SavedProviders_list);
                Paper.book().delete(Common.SavedAPIS_Key);
                Paper.book().write(Common.SavedProviders_list,providers_list);
                Paper.book().write(Common.SavedAPIS_Key,Saved_APIS);
                providers_list = Paper.book().read(Common.SavedProviders_list);
                Fragment_Adapter.notifyDataSetChanged();

            }
           else
            {
                Toast.makeText(this, "This news provider is exist !!", Toast.LENGTH_SHORT).show();
            }





        }


    }


    @Override
    public void onItemChecked(LocalSavedAPIS localSavedAPIS, boolean state) {
        if (state == true)
        {


            iterator = Saved_APIS.iterator();
            ArrayList<String> uniqeAPIS_list = new ArrayList<>();

            while (iterator.hasNext())
            {
                LocalSavedAPIS item = iterator.next();

                uniqeAPIS_list.add(item.getApiSourceName());
            }

            if (!uniqeAPIS_list.contains(localSavedAPIS.getApiSourceName()))
            {
                Saved_APIS.add(localSavedAPIS);

                Fragment_Adapter.addFragmentPage(News_fragment.newInstance(
                        localSavedAPIS.getKeySavedData(),
                        localSavedAPIS.getApiSourceName()),
                        localSavedAPIS.getTapsName());

                Fragment_Adapter.notifyDataSetChanged();
                Log.d(TAG, "onItemChecked: "+"saf");

            }




        }
        else if (state == false)
        {


            int position = 0;
            iterator = Saved_APIS.iterator();
            while (iterator.hasNext())
            {
                LocalSavedAPIS item = iterator.next();

                if (item.getApiSourceName().equals(localSavedAPIS.getApiSourceName()))
                {
                    Log.d(TAG, "onItemChecked: "+Saved_APIS.size());
                    iterator.remove();
                    Fragment_Adapter.RemoveFragment(position);
                    Fragment_Adapter.notifyDataSetChanged();
                }
                position ++;
            }



        }

    }
}
