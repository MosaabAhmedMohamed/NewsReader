package com.example.mosaab.newsreader.ViewHolder;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.mosaab.newsreader.Interface.ItemClickListner;
import com.example.mosaab.newsreader.Model.NewsArticles;
import com.example.mosaab.newsreader.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;


public class News_Adapter extends RecyclerView.Adapter<News_Adapter.ViewHolder> {

    private static final String TAG ="News_Adapter" ;
    private ArrayList<NewsArticles> news_ArrayList;
    private ItemClickListner itemClickListner;

    public void setOnItemClickListner(ItemClickListner listner)
    {
        itemClickListner=listner;
    }

    public News_Adapter(ArrayList<NewsArticles> news_ArrayList, ItemClickListner itemClickListner) {

        this.news_ArrayList = news_ArrayList;
        this.itemClickListner = itemClickListner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View single_news_item= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_news_item,viewGroup,false);
        return new ViewHolder(single_news_item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.puplished_At.setText("Published At : "+news_ArrayList.get(i).getPublishedAt());
        viewHolder.news_title.setText(news_ArrayList.get(i).getTitle());

        if (!TextUtils.isEmpty(news_ArrayList.get(i).getUrlToImage()))
        {
            Picasso.get().load(news_ArrayList.get(i).getUrlToImage()).into(viewHolder.news_iamge);

        }

    }

    @Override
    public int getItemCount() {
        return news_ArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        protected CardView news_card;
        protected ImageView news_iamge;
        protected TextView news_title,puplished_At;
        private Button preview_news_btn;


        public ViewHolder(final View single_news_item) {
            super(single_news_item);

            news_card = single_news_item.findViewById(R.id.news_card);
            news_iamge = single_news_item.findViewById(R.id.news_image);
            news_title = single_news_item.findViewById(R.id.news_title);
            puplished_At = single_news_item.findViewById(R.id.news_puplished_atTV);
            preview_news_btn = single_news_item.findViewById(R.id.preview_news_btn);


            preview_news_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent preview_intent = new Intent(single_news_item.getContext(),Preview_news.class);
                    preview_intent.putExtra("NewsDetial_Title",news_ArrayList.get(getAdapterPosition()).getTitle());
                    preview_intent.putExtra("NewsDetial_Date",news_ArrayList.get(getAdapterPosition()).getPublishedAt());
                    preview_intent.putExtra("NewsDetial_image",news_ArrayList.get(getAdapterPosition()).getUrlToImage());
                    preview_intent.putExtra("NewsDetial_content",news_ArrayList.get(getAdapterPosition()).getContent());
                    preview_intent.putExtra("NewsDetial_url",news_ArrayList.get(getAdapterPosition()).getUrl());
                    single_news_item.getContext().startActivity(preview_intent);
                }
            });

            news_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (itemClickListner != null)
                    {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION)
                        {
                            itemClickListner.onItemClick(v,getAdapterPosition(),false);
                        }

                    }

                }
            });
        }


    }
}
