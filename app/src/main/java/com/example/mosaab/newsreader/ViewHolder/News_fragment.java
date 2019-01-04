package com.example.mosaab.newsreader.ViewHolder;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mosaab.newsreader.Adapter.News_Adapter;
import com.example.mosaab.newsreader.Interface.ItemClickListner;
import com.example.mosaab.newsreader.R;
import com.example.mosaab.newsreader.Model.news;

import java.util.ArrayList;

public class News_fragment extends Fragment implements ItemClickListner {

    private static final String News_Service_API = "param1";

    private String mParam1;

    private View News_Fragment_View;
    private RecyclerView recyclerView;
    private News_Adapter news_adapter;
    private ArrayList<news> news_ArrayList;



    public News_fragment() {
        // Required empty public constructor
    }


    public static News_fragment newInstance(String param1) {
        News_fragment fragment = new News_fragment();
        Bundle args = new Bundle();
        args.putString(News_Service_API, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(News_Service_API);
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

        recyclerView = News_Fragment_View.findViewById(R.id.news_recycler_view);
       recyclerView.setHasFixedSize(true);
       recyclerView.setLayoutManager(new LinearLayoutManager(News_Fragment_View.getContext()));
       news_ArrayList = new ArrayList<>();

       Init_recycler_Adapter();
       recyclerView.setAdapter(news_adapter);
       news_adapter.setOnItemClickListner(News_fragment.this);



    }

    private void Init_recycler_Adapter() {
        news_ArrayList.add(new news(1,"news","lablabla"));
        news_ArrayList.add(new news(1,"news","lablabla"));
        news_ArrayList.add(new news(1,"news","lablabla"));
        news_ArrayList.add(new news(1,"news","lablabla"));
        news_ArrayList.add(new news(1,"news","lablabla"));
        news_ArrayList.add(new news(1,"news","lablabla"));
        news_ArrayList.add(new news(1,"news","lablabla"));
        news_ArrayList.add(new news(1,"news","lablabla"));
        news_ArrayList.add(new news(1,"news","lablabla"));
        news_ArrayList.add(new news(1,"news","lablabla"));

        news_adapter = new News_Adapter(news_ArrayList,this,"RSS");

    }

    @Override
    public void onItemClick(View view, int position) {

        Toast.makeText(getActivity(), String.valueOf(position), Toast.LENGTH_SHORT).show();

    }
}
