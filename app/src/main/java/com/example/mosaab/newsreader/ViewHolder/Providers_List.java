package com.example.mosaab.newsreader.ViewHolder;


import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mosaab.newsreader.Common.Common;
import com.example.mosaab.newsreader.Interface.ItemClickListner;
import com.example.mosaab.newsreader.Model.LocalSavedAPIS;
import com.example.mosaab.newsreader.Model.Providers;
import com.example.mosaab.newsreader.R;

import java.util.ArrayList;
import java.util.Iterator;

import io.paperdb.Paper;


public class Providers_List extends Fragment implements ItemClickListner {

    public static final String TAG ="Providers_List";


    private RecyclerView recyclerView;
    private Providers_Adapter providers_adapter;
    private ArrayList<Providers> provider_list;
    private ArrayList<LocalSavedAPIS> savedAPISList;
    private Iterator<LocalSavedAPIS> iterator;
    private View Provider_list_view;


    public Providers_List()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       Provider_list_view =  inflater.inflate(R.layout.fragment_providers__list, container, false);

       Init_UI();

       return Provider_list_view;
    }


    private void Init_UI()
    {

        Paper.init(Provider_list_view.getContext());

        recyclerView = Provider_list_view.findViewById(R.id.provider_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        provider_list = new ArrayList<>();

        if (Paper.book().read(Common.SavedProviders_list) != null)
        {
            provider_list = Paper.book().read(Common.SavedProviders_list);
            savedAPISList = Paper.book().read(Common.SavedAPIS_Key);
        }
        Log.d(TAG, "Init_UI: "+provider_list.size());
        providers_adapter = new Providers_Adapter(provider_list,this);
        recyclerView.setAdapter(providers_adapter);



    }

    @Override
    public void onItemClick(View view, int position, boolean state)
    {
        iterator = savedAPISList.iterator();
        if (state == true)
        {
            if (provider_list.get(position).isActive_provider() == false)
            {
                String provider = provider_list.get(position).getProvider_name();
                savedAPISList.add(new LocalSavedAPIS(provider,provider,provider,false));
                provider_list.get(position).setActive_provider(true);

                Paper.book().delete(Common.SavedProviders_list);
                Paper.book().delete(Common.SavedAPIS_Key);
                Paper.book().write(Common.SavedAPIS_Key,savedAPISList);
                Paper.book().write(Common.SavedProviders_list,provider_list);

            }
        }
        else if (state == false)
        {
            if (provider_list.get(position).isActive_provider() == true)
            {
                provider_list.get(position).getProvider_name();

                while (iterator.hasNext())
                {
                    LocalSavedAPIS item = iterator.next();

                    if (item.getApiSourceName().equals(provider_list.get(position).getProvider_name()))
                    {

                        iterator.remove();
                        provider_list.get(position).setActive_provider(false);

                        Paper.book().delete(Common.SavedProviders_list);
                        Paper.book().delete(Common.SavedAPIS_Key);
                        Paper.book().write(Common.SavedAPIS_Key,savedAPISList);
                        Log.d(TAG, "onItemClick: " + Paper.book().read(Common.SavedAPIS_Key).toString());
                        Paper.book().write(Common.SavedProviders_list,provider_list);

                    }

                }
            }
        }

    }
}
