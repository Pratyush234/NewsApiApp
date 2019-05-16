package com.example.praty.newsapiapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.example.praty.newsapiapp.model.News;
import com.example.praty.newsapiapp.model.OfflineNews;

public class NewsDetailsActivity extends AppCompatActivity {

    //ui components
    ImageButton mShare;
    String mUrl=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        //get the url of the article
        if(getIntent().hasExtra("newsobject")) {
            News article = getIntent().getParcelableExtra("newsobject");
            mUrl=article.getUrl();
        }
        else if(getIntent().hasExtra("offlinenewsobject")){
            OfflineNews article=getIntent().getParcelableExtra("offlinenewsobject");
            mUrl=article.getUrl();
        }

        mShare=(ImageButton) findViewById(R.id.shareLink);
        final ProgressBar progressBar=(ProgressBar) findViewById(R.id.webProgress);
        progressBar.setVisibility(View.VISIBLE);
        WebView webView=(WebView) findViewById(R.id.news_web_view);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);  //setting to cache the web pages

        //load the url of the article in a webView
        webView.loadUrl(mUrl);

        webView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);    //stop displaying the progress bar when the page has finished loading

            }
        });

        //Intent to share the article link to other applications on the phone
        mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_SUBJECT, "Link of the article");
                share.putExtra(Intent.EXTRA_TEXT, mUrl);
                startActivity(Intent.createChooser(share, "Share link:"));
            }
        });
    }
}
