package com.webstormcomputers.udacitynewsapp;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<News>> {
    private final String GUARDIAN_URL = "http://content.guardianapis.com/search?q=homebirth&&show-tags=contributor&api-key=c742eda9-d73d-43de-ab7d-4ecb7ecebf7b";

    private NewsAdapter nAdapter;


    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        nAdapter = new NewsAdapter(this, new ArrayList<News>());

        final ListView newsListView = (ListView) findViewById(R.id.list_view);

        newsListView.setAdapter(nAdapter);
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News news = nAdapter.getItem(position);
                Uri newsUri = Uri.parse(news.getUrl());
                Intent webIntent = new Intent(Intent.ACTION_VIEW, newsUri);
                startActivity(webIntent);
            }
        });
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            getLoaderManager().initLoader(0, null, this).forceLoad();

        } else {
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
        }
    }

    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {
        nAdapter.clear();
    }

    public Loader<List<News>> onCreateLoader(int i){
        return new NewsLoader(MainActivity.this, GUARDIAN_URL);
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        // Loader reset, so we can clear out our existing data.
        nAdapter.clear();
    }


}




