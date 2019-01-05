package com.example.mosaab.newsreader.ViewHolder;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.example.mosaab.newsreader.Model.TechCrunch;
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
    public static final String TAG = "News_fragment";

    private String News_API_Name;

    private View News_Fragment_View;
    private SwipeRefreshLayout refreshLayout;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private News_Adapter news_adapter;

    private ArrayList<News> local_dataList;
    private API_Service api_service;
    private TechCrunch techCrunch ;


    public News_fragment() {
        // Required empty public constructor
    }


    public static News_fragment newInstance(String param1) {
        News_fragment fragment = new News_fragment();
        Bundle args = new Bundle();
        args.putString(pram1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            News_API_Name = getArguments().getString(pram1);
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

        Paper.init(News_Fragment_View.getContext());

        local_dataList = new ArrayList<>();

        progressBar = News_Fragment_View.findViewById(R.id.progress_circular);
        refreshLayout = News_Fragment_View.findViewById(R.id.refresh_news_layout);
        recyclerView = News_Fragment_View.findViewById(R.id.news_recycler_view);
       recyclerView.setHasFixedSize(true);
       recyclerView.setLayoutManager(new LinearLayoutManager(News_Fragment_View.getContext()));

       if (Paper.book().read(Common.Mashable)!= null)
        {
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
            //Check_Internet_Connection();
            progressBar.setVisibility(View.VISIBLE);
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

        if (Common.isConnectedToInternet(getActivity()))
        {
            Log.d(TAG, "Check_Internet_Connection: " +"checked");
            InitRetorFit();
            }
        else
            {

            Toast.makeText(getActivity(), "Please Check your internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private void Load_Data(String news_API_Name) {

        switch (Common.Mashable)
        {
            case Common.Mashable :
            {
                local_dataList =  Paper.book().read(Common.Mashable);
                Init_recycler_News(Common.Mashable);
                return;
            }
            case Common.BusinessInsider :
            {
                local_dataList =  Paper.book().read(Common.BusinessInsider);
                Init_recycler_News(Common.BusinessInsider);
                return;
            }

            default:
                return;
        }

    }

    private void Init_recycler_News(String API_name)
    {
                news_adapter = new News_Adapter(local_dataList,this);
                refreshLayout.setRefreshing(false);
                recyclerView.setAdapter(news_adapter);
                news_adapter.setOnItemClickListner(News_fragment.this);

    }

    private void InitRetorFit() {
        api_service = RetrofitClient.getInstance(Common.TechCrunch_URL).create(API_Service.class);
        getNews_data();
    }



    //Getting the data from the API
    private void getNews_data()
    {
        Call<TechCrunch> call =  api_service.getTechCrunch_News("business",Common.TechCrunch_API_KEY);

        call.enqueue(new Callback<TechCrunch>() {
            @Override
            public void onResponse(Call<TechCrunch> call, Response<TechCrunch> response) {

                if (!response.isSuccessful()) {
                    Log.d(TAG, "onResponse: error"+ response.code() + response.message());
                    return;
                }


                techCrunch = response.body();

                if (Paper.book().read(Common.Mashable) == null ||
                        Paper.book().read(Common.BusinessInsider) == null)
                {

               local_dataList.addAll(techCrunch.getArticles());
               SaveInLocal(local_dataList);
                    progressBar.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<TechCrunch> call, Throwable t) {
                Log.d(TAG, "onFailure: "+ t.getMessage());
            }
        });

    }

    private void SaveInLocal(ArrayList<News> arraylist) {
        Paper.book().write(Common.TechCrunch,arraylist);
        Paper.book().write(Common.Mashable,arraylist);
        Paper.book().write(Common.BusinessInsider,arraylist);
        Load_Data(News_API_Name);
    }


    @Override
    public void onItemClick(View view, int position) {

        Toast.makeText(getActivity(), String.valueOf(position), Toast.LENGTH_SHORT).show();

    }
}
