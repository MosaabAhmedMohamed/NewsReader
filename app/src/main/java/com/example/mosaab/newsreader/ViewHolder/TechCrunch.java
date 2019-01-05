package com.example.mosaab.newsreader.ViewHolder;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mosaab.newsreader.Adapter.News_Adapter;
import com.example.mosaab.newsreader.Common.Common;
import com.example.mosaab.newsreader.Interface.ItemClickListner;
import com.example.mosaab.newsreader.Interface.News;
import com.example.mosaab.newsreader.Model.TechCrunchArticles;
import com.example.mosaab.newsreader.R;
import com.example.mosaab.newsreader.Remote.API_Service;
import com.example.mosaab.newsreader.Remote.RetrofitClient;

import java.util.ArrayList;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TechCrunch extends Fragment implements ItemClickListner
{

    private static final String pram1 = "pram1";
    public static final String TAG = "TechCrunch_fragment";

    private View News_Fragment_View;
    private SwipeRefreshLayout refreshLayout;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private News_Adapter news_adapter;

    private ArrayList<News> local_dataList;
    private API_Service api_service;
    private com.example.mosaab.newsreader.Model.TechCrunch techCrunch;


    public TechCrunch() {
        // Required empty public constructor
    }


    public static News_fragment newInstance() {
        News_fragment fragment = new News_fragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        News_Fragment_View = inflater.inflate(R.layout.fragment_news_fragment, container, false);

        Init_UI();                    refreshLayout.setRefreshing(false);


        return News_Fragment_View;
    }

    private void Init_UI() {

        Paper.init(News_Fragment_View.getContext());

        local_dataList = new ArrayList<>();

        progressBar = News_Fragment_View.findViewById(R.id.progress_circular);
        refreshLayout = News_Fragment_View.findViewById(R.id.refresh_news_layout);
        recyclerView = News_Fragment_View.findViewById(R.id.news_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(News_Fragment_View.getContext()));

        InitRetorFit();

        if (Paper.book().read(Common.TechCrunch) != null) {
            refreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    Load_Data();
                }
            });
        }
        else
            {
            Check_Internet_Connection();
            progressBar.setVisibility(View.VISIBLE);
        }

        refreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Check_Internet_Connection();
                progressBar.setVisibility(View.VISIBLE);
            }
        });


    }

    private void Check_Internet_Connection() {

        if (Common.isConnectedToInternet(getActivity()))
        {
            getNews_data();
        } else
            {
            Toast.makeText(getActivity(), "Please Check your internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private void Load_Data()
    {
                local_dataList = Paper.book().read(Common.TechCrunch);
                Init_recycler_News();
    }

    private void Init_recycler_News() {
        news_adapter = new News_Adapter(local_dataList, this);
        refreshLayout.setRefreshing(false);
        recyclerView.setAdapter(news_adapter);
        news_adapter.setOnItemClickListner(TechCrunch.this);

    }

    private void InitRetorFit()
    {
        api_service = RetrofitClient.getInstance(Common.TechCrunch_URL).create(API_Service.class);
    }

    //Getting the data from the API
    private void getNews_data() {
        Call<com.example.mosaab.newsreader.Model.TechCrunch> call = api_service.getTechCrunch_News("business", Common.TechCrunch_API_KEY);

        call.enqueue(new Callback<com.example.mosaab.newsreader.Model.TechCrunch>() {
            @Override
            public void onResponse(Call<com.example.mosaab.newsreader.Model.TechCrunch> call, Response<com.example.mosaab.newsreader.Model.TechCrunch> response) {

                if (!response.isSuccessful()) {
                    Log.d(TAG, "onResponse: error" + response.code() + response.message());
                    return;
                }


                techCrunch = response.body();
                Log.d(TAG, "onResponse: "+response.body());

                if (Paper.book().read(Common.TechCrunch) != null || Paper.book().read(Common.TechCrunch) == null)
                {
                    local_dataList.addAll(techCrunch.getArticles());
                    SaveInLocal(local_dataList);
                }

            }
            @Override
            public void onFailure(Call<com.example.mosaab.newsreader.Model.TechCrunch> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });

    }

    private void SaveInLocal(ArrayList<News> arraylist) {
        Paper.book().write(Common.TechCrunch, arraylist);
        Load_Data();
        refreshLayout.setRefreshing(false);
        progressBar.setVisibility(View.GONE);
    }


    @Override
    public void onItemClick(View view, int position) {

        Toast.makeText(getActivity(), String.valueOf(position), Toast.LENGTH_SHORT).show();

    }

}