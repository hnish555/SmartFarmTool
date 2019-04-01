package com.example.smartfarmtool.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.smartfarmtool.R;
import com.example.smartfarmtool.Util.Utils;

public class NewsDetailActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener{
    private ImageView imageView;
    private TextView appbar_title,appbar_subtitle,date,time,title;
    private boolean isHideToBar=false;
    private LinearLayout titleAppbar;
    private FrameLayout date_behaviour;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private String mUrl,mImg,mTitle,mDate,mSource,mAuthor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        toolbar=findViewById(R.id._details_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final CollapsingToolbarLayout collapsingToolbarLayout=findViewById(R.id.details_collapsing_toolbar);
        collapsingToolbarLayout.setTitle("");

        appBarLayout=findViewById(R.id.details_appbar);
        appBarLayout.addOnOffsetChangedListener((AppBarLayout.BaseOnOffsetChangedListener) NewsDetailActivity.this);
        date_behaviour=findViewById(R.id.details_date_behavior);
        titleAppbar=findViewById(R.id.details_title_appbar);
        imageView=findViewById(R.id.details_backdrop);
        appbar_title=findViewById(R.id.details_title_on_appbar);
        appbar_subtitle=findViewById(R.id.details_subtitle_on_appbar);
        date=findViewById(R.id.details_date);
        time=findViewById(R.id.details_time);
        title=findViewById(R.id.details_title);
        Intent intent=getIntent();
        mUrl=intent.getStringExtra("url");
        mImg=intent.getStringExtra("img");
        mTitle=intent.getStringExtra("title");
        mSource=intent.getStringExtra("source");
        mAuthor=intent.getStringExtra("author");
        mDate=intent.getStringExtra("date");
        RequestOptions requestOptions=new RequestOptions();
        requestOptions.error(Utils.getRandomDrawbleColor());

        Glide.with(this)
                .load(mImg)
                .apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);


        appbar_title.setText(mSource);
        appbar_subtitle.setText(mUrl);
        date.setText(mDate);
        title.setText(mTitle);


        String author=null;


        if (mAuthor!=null||mAuthor!=""){
            mAuthor="\u2022"+mAuthor;
        }
        else {
            author="";
        }
        time.setText(mSource+author+String.format("⠢%s", Utils.DateToTimeFormat(mDate)));
        initWebView(mUrl);



    }

    private  void initWebView(String url){
        WebView webView=findViewById(R.id.details_webView);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        supportFinishAfterTransition();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;

    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int maxScroll=appBarLayout.getTotalScrollRange();
        float percentage=Math.abs(verticalOffset)/maxScroll;
        if (percentage==1f && isHideToBar){
            date_behaviour.setVisibility(View.GONE);
            titleAppbar.setVisibility(View.VISIBLE);
            isHideToBar=!isHideToBar;
        }
        else if(percentage <1f && isHideToBar){
            date_behaviour.setVisibility(View.VISIBLE);
            titleAppbar.setVisibility(View.GONE);
            isHideToBar=!isHideToBar;
        }
    }
}
