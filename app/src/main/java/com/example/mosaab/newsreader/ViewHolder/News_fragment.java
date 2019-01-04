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
import android.widget.Toast;
import com.example.mosaab.newsreader.Adapter.News_Adapter;
import com.example.mosaab.newsreader.Common.Common;
import com.example.mosaab.newsreader.Interface.ItemClickListner;
import com.example.mosaab.newsreader.R;
import com.example.mosaab.newsreader.Model.news;

import java.util.ArrayList;

import io.paperdb.Paper;

public class News_fragment extends Fragment implements ItemClickListner {

    private static final String pram1 = "pram1";
    public static final String TAG = "News_fragment";

    private String News_API_Name;

    private View News_Fragment_View;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private News_Adapter news_adapter;
    private ArrayList<news> local_dataList;



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

        refreshLayout = News_Fragment_View.findViewById(R.id.refresh_news_layout);
        recyclerView = News_Fragment_View.findViewById(R.id.news_recycler_view);
       recyclerView.setHasFixedSize(true);
       recyclerView.setLayoutManager(new LinearLayoutManager(News_Fragment_View.getContext()));

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

        refreshLayout.post(new Runnable() {
            @Override
            public void run()
            {
                Check_Internet_Connection();
            }
        });

    }

    private void Check_Internet_Connection() {

        if (Common.isConnectedToInternet(getActivity()))
        {

                switch (News_API_Name)
                {
                    case Common.TechCrunch :
                    {
                        local_dataList =  Paper.book().read(Common.TechCrunch);
                        Init_recycler_News(Common.TechCrunch);
                        return;
                    }
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
        else
            {

            Toast.makeText(getActivity(), "Please Check your internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private void Init_recycler_News(String API_name)
    {
                local_dataList.add(0,new news(10,API_name,"la"));
                news_adapter = new News_Adapter(local_dataList,this,"RSS");
                refreshLayout.setRefreshing(false);
                recyclerView.setAdapter(news_adapter);
                news_adapter.setOnItemClickListner(News_fragment.this);

    }


    @Override
    public void onItemClick(View view, int position) {

        Toast.makeText(getActivity(), String.valueOf(position), Toast.LENGTH_SHORT).show();

    }
}
