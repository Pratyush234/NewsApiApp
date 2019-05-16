package com.example.praty.newsapiapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.praty.newsapiapp.adapter.MyAdapter;
import com.example.praty.newsapiapp.adapter.MyOfflineAdapter;
import com.example.praty.newsapiapp.helper.ItemClickListener;
import com.example.praty.newsapiapp.model.News;
import com.example.praty.newsapiapp.model.OfflineNews;
import com.example.praty.newsapiapp.viewmodel.NewsActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity {

    //reyclerview
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    List<News> mArticles;
    List<OfflineNews> mOfflineArticles;

    //viewmodel
    NewsActivityViewModel mNewsActivityViewModel;

    //ui components
    ProgressBar mProgress;
    SwipeRefreshLayout mSwipeContainer;

    boolean isConnected=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        mArticles=new ArrayList<>();
        mOfflineArticles=new ArrayList<>();

        mProgress=(ProgressBar) findViewById(R.id.recycler_progress);
        mSwipeContainer=(SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        checkForInternet();
        setupRecyclerView();

        //Instantaniating the viewmodel
        mNewsActivityViewModel= ViewModelProviders.of(this).get(NewsActivityViewModel.class);
        mNewsActivityViewModel.queryRepo("in", getString(R.string.api_key), this);
        mNewsActivityViewModel.offlineRepo(this);

        //checking if Internet is connected
        if(isConnected)
            getNewsArticles();   //if connected, load the data using retrofit
        else getOfflineNewsArticles(); //if not, load the data using room

        //swipeRefresh listener
        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if(isConnected){
                    mNewsActivityViewModel.queryRepo("in", getString(R.string.api_key), NewsActivity.this);
                    getNewsArticles();
                }
                else{
                    Toast.makeText(NewsActivity.this, "You're offline", Toast.LENGTH_SHORT).show();
                    mSwipeContainer.setRefreshing(false);
                }
            }

        });

    }

    //method to retrieve data from room database and notifying the adapter
    private void getOfflineNewsArticles() {
        mNewsActivityViewModel.getmOfflineArticles().observe(this, new Observer<List<OfflineNews>>() {
            @Override
            public void onChanged(@Nullable List<OfflineNews> offlineNews) {
                if(mOfflineArticles!=null)
                    mOfflineArticles.clear();

                if(offlineNews!=null)
                    mOfflineArticles.addAll(offlineNews);

                mAdapter.notifyDataSetChanged();
                mProgress.setVisibility(View.GONE);

            }
        });
    }

    //method to retrieve data using retrofit and notifying the adapter
    private void getNewsArticles() {
        mNewsActivityViewModel.getNews().observe(this, new Observer<List<News>>() {
            @Override
            public void onChanged(@Nullable List<News> news) {
                if(mArticles.size()>0)
                    mArticles.clear();

                if(news!=null)
                    mArticles.addAll(news);

                mAdapter.notifyDataSetChanged();
                mProgress.setVisibility(View.GONE);
                mSwipeContainer.setRefreshing(false);
            }
        });
    }

    //method to setup the recyclerView
    private void setupRecyclerView() {
        mRecyclerView=(RecyclerView) findViewById(R.id.my_recycler);
        mLayoutManager=new GridLayoutManager(this,1);
        mRecyclerView.setLayoutManager(mLayoutManager);

        if(isConnected) {
            mAdapter = new MyAdapter(this, mArticles, new ItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    Intent intent = new Intent(NewsActivity.this, NewsDetailsActivity.class);
                    intent.putExtra("newsobject", mArticles.get(position));
                    startActivity(intent);
                }
            });
        }
        else{
            mAdapter=new MyOfflineAdapter(this, mOfflineArticles, new ItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    Intent intent = new Intent(NewsActivity.this, NewsDetailsActivity.class);
                    intent.putExtra("offlinenewsobject", mOfflineArticles.get(position));
                    startActivity(intent);
                }
            });
        }

        mRecyclerView.setAdapter(mAdapter);
    }

    //method to check for the internet
    private void checkForInternet(){
        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if(!isConnected){
            //alter message to tell the user that he's not connected to the Internet
            AlertDialog.Builder builder=new AlertDialog.Builder(NewsActivity.this);

            builder.setMessage("You are not connected to the Internet")
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            final AlertDialog dialog=builder.create();
            dialog.show();
        }
    }
}
