package com.example.mosaab.newsreader.ViewHolder;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mosaab.newsreader.Common.Common;
import com.example.mosaab.newsreader.Interface.ItemClickListner;
import com.example.mosaab.newsreader.Model.NewsArticles;
import com.example.mosaab.newsreader.Model.news_api;
import com.example.mosaab.newsreader.R;
import com.example.mosaab.newsreader.Remote.API_Service;
import com.example.mosaab.newsreader.Remote.RetrofitClient;

import java.util.ArrayList;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class News_fragment extends Fragment implements ItemClickListner {

    private static final String pram1 = "pram1";
    private static final String pram2 = "pram2";

    public static final String TAG = "News_fragment";

    private String News_API_Name;
    private String Source_API_Name;
    private boolean IslocalData = false;

    private View News_Fragment_View;
    private SwipeRefreshLayout refreshLayout;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private News_Adapter news_adapter;

    private ArrayList<NewsArticles> local_dataList;
    private API_Service api_service;


    public News_fragment() {
        // Required empty public constructor
    }


    public static News_fragment newInstance(String param1,String param2) {
        News_fragment fragment = new News_fragment();
        Bundle args = new Bundle();
        args.putString(pram1, param1);
        args.putString(pram2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            News_API_Name = getArguments().getString(pram1);
            Source_API_Name = getArguments().getString(pram2);
            Log.d(TAG, "onCreate: " + News_API_Name);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        News_Fragment_View =  inflater.inflate(R.layout.fragment_news_fragment, container, false);

        Init_UI();

        return News_Fragment_View;
    }

    private void Init_UI()
    {
        local_dataList = new ArrayList<>();
        Paper.init(News_Fragment_View.getContext());

        if (Paper.book().read(News_API_Name) != null)
        {
            IslocalData = true;
        }


        progressBar = News_Fragment_View.findViewById(R.id.progress_circular);
        refreshLayout = News_Fragment_View.findViewById(R.id.refresh_news_layout);
        recyclerView = News_Fragment_View.findViewById(R.id.news_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(News_Fragment_View.getContext()));

       if (IslocalData != false)
        {
            local_dataList = Paper.book().read(News_API_Name);

            refreshLayout.post(new Runnable() {
                @Override
                public void run()
                {
                    Load_Data(News_API_Name);
                }
            });
        }
        else
        {
            Check_Internet_Connection();
        }

        refreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh()
            {
               Check_Internet_Connection();
            }
        });



    }

    private void Check_Internet_Connection() {

        if (Common.isConnectedToInternet())
        {
            Log.d(TAG, "Check_Internet_Connection: ");
            progressBar.setVisibility(View.VISIBLE);
            InitRetorFit();
            getNews_data();
        }
        else
            {
                refreshLayout.setRefreshing(false);
               Show_alert_dialog();
        }
    }

    private void InitRetorFit() {
        api_service = RetrofitClient.getInstance(Common.APIS_URL).create(API_Service.class);
        Log.d(TAG, "InitRetorFit: ");
    }



    //Getting the data from the API
    private void getNews_data()
    {
        Log.d(TAG, "getNews_data: ");
        Call<news_api> call =  api_service.get_News_Json(Source_API_Name,Common.API_KEY);

        call.enqueue(new Callback<news_api>() {
            @Override
            public void onResponse(Call<news_api> call, Response<news_api> response) {

                if (!response.isSuccessful()) {
                    Log.d(TAG, "onResponse: error"+ response.code() + response.message());
                    return;
                }


                    news_api news_api = response.body();


                    local_dataList.addAll(news_api.getArticles());
                    SaveInLocal(local_dataList);
                    progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<news_api> call, Throwable t) {
                Log.d(TAG, "onFailure: "+ t.getMessage());
            }
        });

    }

    private void SaveInLocal(ArrayList<NewsArticles> arraylist)
    {
        if (IslocalData != false)
        {
            Log.d(TAG, "SaveInLocal: updaet");
            Paper.book().delete(News_API_Name);
            Log.d(TAG, "SaveInLocal: "+News_API_Name);
            Paper.book().write(News_API_Name,arraylist);
            Load_Data(News_API_Name);
        }
        else if(IslocalData == false)
        {
            Log.d(TAG, "SaveInLocal: ERROR");
            if (arraylist.size() != 0)
            {
                Paper.book().write(News_API_Name,arraylist);
                Load_Data(News_API_Name);
            }

        }
        else
        {
            Log.d(TAG, "SaveInLocal: new");
            Paper.book().write(News_API_Name,arraylist);
            Load_Data(News_API_Name);
        }

    }


    private void Load_Data(String news_API_Name)
    {
        Log.d(TAG, "Load_Data: ");
        local_dataList =  Paper.book().read(news_API_Name);
        Init_recycler_News();
        news_adapter.notifyDataSetChanged();



    }

    private void Init_recycler_News()
    {
        Log.d(TAG, "Init_recycler_News: ");

                news_adapter = new News_Adapter(local_dataList,this);
                refreshLayout.setRefreshing(false);
                recyclerView.setAdapter(news_adapter);
                news_adapter.setOnItemClickListner(News_fragment.this);

    }

    public void Show_alert_dialog()
    {
        final Dialog builder = new Dialog(News_Fragment_View.getContext());

        View view = LayoutInflater.from(News_Fragment_View.getContext()).inflate(R.layout.no_internet_dialog, null);


        TextView title =  view.findViewById(R.id.title);
        Button alert_btn = view.findViewById(R.id.alert_btn);

        title.setText("Please make sure you are connected to Internet");

        alert_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });

        builder.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        builder.setContentView(view);
        builder.show();

    }

    @Override
    public void onItemClick(View view, int position) {

        Toast.makeText(getActivity(), String.valueOf(position), Toast.LENGTH_SHORT).show();

    }
}
