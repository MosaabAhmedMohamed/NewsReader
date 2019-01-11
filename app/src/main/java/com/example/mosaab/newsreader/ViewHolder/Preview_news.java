package com.example.mosaab.newsreader.ViewHolder;

import android.app.Dialog;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.mosaab.newsreader.Common.Common;
import com.example.mosaab.newsreader.R;
import com.squareup.picasso.Picasso;

public class Preview_news extends AppCompatActivity {

    private static final String TAG ="Preview_news";

    private TextView title_tv,date_tv,content_tv;
    private ImageView newss_iamge;
    private Button preview_in_website_btn;
    private WebView newswebView;
    private ConstraintLayout base_laypout;
    private ProgressBar progressBar;

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
        progressBar = findViewById(R.id.progress_circular);


        preview_in_website_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Common.isConnectedToInternet())
                {
                    preview_in_web=true;
                    newswebView.getSettings().setJavaScriptEnabled(true);
                    newswebView.loadUrl(getIntent().getStringExtra("NewsDetial_url"));
                    newswebView.setVisibility(View.VISIBLE);
                    base_laypout.setVisibility(View.GONE);
                    Log.d(TAG, "onCreate: "+getIntent().getStringExtra("NewsDetial_url"));
                    progressBar.setVisibility(View.VISIBLE);
                }
                else
                {
                    Show_alert_dialog();

                }


                newswebView.setWebChromeClient(new WebChromeClient() {
                    public void onProgressChanged(WebView view, int progress) {
                        if (progress == 100) {
                            //do your task
                            Log.d(TAG, "onProgressChanged: ");
                            progressBar.setVisibility(View.GONE);

                        }
                    }
                });





            }
        });
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

    public void Show_alert_dialog()
    {
        final Dialog builder = new Dialog(this);

        View view = LayoutInflater.from(this).inflate(R.layout.no_internet_dialog, null);


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
}
