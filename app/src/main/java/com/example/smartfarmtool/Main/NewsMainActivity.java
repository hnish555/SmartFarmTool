package com.example.smartfarmtool.Main;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartfarmtool.ApiClient.ApiClient;
import com.example.smartfarmtool.ApiClient.ApiInterface;
import com.example.smartfarmtool.Login.HomeActivity;
import com.example.smartfarmtool.Model.Articles;
import com.example.smartfarmtool.Model.News;
import com.example.smartfarmtool.NavigationDMenu;
import com.example.smartfarmtool.R;
import com.example.smartfarmtool.Util.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewsMainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    public static final String API_KEY="70b0bdbb279a4fffacd462701206ccf6";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Articles> articles=new ArrayList<>();
    private NewsAdapter adapter;
    private String TAG=NewsMainActivity.class.getSimpleName();
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView topheadlines;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_main);


        swipeRefreshLayout=findViewById(R.id.refreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));

        topheadlines=findViewById(R.id.topheadLines);
        recyclerView=findViewById(R.id.recyclerview);
        layoutManager=new LinearLayoutManager(NewsMainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        onLoadinSwipRefresh("crop farming India");

    }
    public void LoadJson(final  String keyword){
        topheadlines.setVisibility(View.INVISIBLE);
        swipeRefreshLayout.setRefreshing(true);

        ApiInterface apiInterface= ApiClient.getApiClient().create(ApiInterface.class);

        String country= Utils.getCountry();
        String language=Utils.getLanguage();
        Call<News> call;

        if (keyword.length()>0){
            call=apiInterface.getNewsSearch(keyword,language,"publishedAt",API_KEY);

        }
        else {
            call=apiInterface.getNews(country,API_KEY);

        }
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful() && response.body().getArticles()!=null){
                    if (!articles.isEmpty()){
                        articles.clear();
                    }
                    articles=response.body().getArticles();
                    adapter=new NewsAdapter(articles,NewsMainActivity.this);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    initListener();
                    topheadlines.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setRefreshing(false);
                }
                else {
                    topheadlines.setVisibility(View.INVISIBLE);
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(NewsMainActivity.this,"No Result",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                topheadlines.setVisibility(View.INVISIBLE);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }
    private void initListener(){
        adapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(NewsMainActivity.this,NewsDetailActivity.class);
                Articles article=articles.get(position);
                intent.putExtra("url",article.getUrl());
                intent.putExtra("title",article.getTitle());
                intent.putExtra("img",article.getUrlToImage());
                intent.putExtra("date",article.getPublishedAt());
//                intent.putExtra("source",article.getSource().getName());
                intent.putExtra("author",article.getAuthor());

                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        SearchManager searchManager= (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView  searchView= (SearchView) menu.findItem(R.id.newsMenu_action_search).getActionView();
        MenuItem searchMenuItem=menu.findItem(R.id.newsMenu_action_search);

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Search  Latest news...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (s.length()>2){
                    onLoadinSwipRefresh("crop farming India");
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchMenuItem.getIcon().setVisible(false,false);
        return true;
    }

    @Override
    public void onRefresh() {
        LoadJson("crop farming India");
    }
    private void onLoadinSwipRefresh(final String keyword){
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                LoadJson(keyword);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(NewsMainActivity.this, NavigationDMenu.class));
    }
}
