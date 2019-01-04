package com.example.mosaab.newsreader.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mosaab.newsreader.Interface.ItemClickListner;
import com.example.mosaab.newsreader.R;
import com.example.mosaab.newsreader.Model.news;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class News_Adapter extends RecyclerView.Adapter<News_Adapter.ViewHolder> {

    private ArrayList<news> news_ArrayList;
    private ItemClickListner itemClickListner;
    private String news_service_api;

    public void setOnItemClickListner(ItemClickListner listner)
    {
        itemClickListner=listner;
    }

    public News_Adapter(ArrayList<news> news_ArrayList, ItemClickListner itemClickListner, String news_service_Api) {
        this.news_ArrayList = news_ArrayList;
        this.itemClickListner = itemClickListner;
        news_service_api = news_service_Api;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View single_news_item= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_news_item,viewGroup,false);
        return new ViewHolder(single_news_item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.news_title.setText(news_ArrayList.get(i).getTitle());
    }

    @Override
    public int getItemCount() {
        return news_ArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        protected CardView news_card;
        protected CircleImageView news_iamge;
        protected TextView news_title;

        public ViewHolder(View single_news_item) {
            super(single_news_item);

            news_card = single_news_item.findViewById(R.id.news_card);
            news_iamge = single_news_item.findViewById(R.id.news_image);
            news_title = single_news_item.findViewById(R.id.news_title);


            news_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (itemClickListner != null)
                    {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION)
                        {
                            itemClickListner.onItemClick(v,getAdapterPosition());
                        }

                    }

                }
            });
        }


    }
}
