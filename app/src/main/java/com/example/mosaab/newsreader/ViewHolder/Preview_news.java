package com.example.mosaab.newsreader.ViewHolder;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mosaab.newsreader.R;
import com.squareup.picasso.Picasso;

public class Preview_news extends AppCompatActivity {

    private static final String TAG ="Preview_news";

    private TextView title_tv,date_tv,content_tv;
    private ImageView newss_iamge;
    private Button preview_in_website_btn;
    private WebView newswebView;
    private ConstraintLayout base_laypout;

    private boolean preview_in_web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_news);

        Init_UI();

        if (getIntent() != null)
        {

            title_tv.setText(getIntent().getStringExtra("NewsDetial_Title"));
            date_tv.setText(getIntent().getStringExtra("NewsDetial_Date"));
            content_tv.setText(getIntent().getStringExtra("NewsDetial_content"));

            if (!TextUtils.isEmpty(getIntent().getStringExtra("NewsDetial_image")))
            {
                Picasso.get().load(getIntent().getStringExtra("NewsDetial_image")).into(newss_iamge);

            }

            preview_in_website_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    preview_in_web=true;
                    newswebView.getSettings().setJavaScriptEnabled(true);
                    newswebView.loadUrl(getIntent().getStringExtra("NewsDetial_url"));
                    newswebView.setVisibility(View.VISIBLE);
                    base_laypout.setVisibility(View.GONE);
                    Log.d(TAG, "onCreate: "+getIntent().getStringExtra("NewsDetial_url"));

                }
            });
        }

    }

    private void Init_UI()
    {

        base_laypout = findViewById(R.id.base_layout);
        newswebView = findViewById(R.id.newswebView);
        title_tv = findViewById(R.id.news_detial_title_tv);
        date_tv = findViewById(R.id.news_detial_Date_tv);
        content_tv = findViewById(R.id.news_detial_content_tv);
        newss_iamge = findViewById(R.id.news_detial_image);
        preview_in_website_btn = findViewById(R.id.preview_website_news_btn);
    }
    
    
   

    @Override
    public void onBackPressed() {

        Log.d(TAG, "onBackPressed: ");
        if (preview_in_web == false)
        {
            super.onBackPressed();
            return;
        }
        else {
             preview_in_web = false ;
            newswebView.setVisibility(View.GONE);
            base_laypout.setVisibility(View.VISIBLE);
        }
    }
}
