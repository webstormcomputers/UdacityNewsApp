package com.webstormcomputers.udacitynewsapp;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements SwipeRefreshLayout.OnRefreshListener, LoaderCallbacks<List<News>> {

    private NewsAdapter nAdapter;
    SwipeRefreshLayout swipe;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        swipe.setOnRefreshListener(this);

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
        }
        else {
            TextView loadingMessage = (TextView) findViewById(R.id.loading_message);
            loadingMessage.setText(com.webstormcomputers.udacitynewsapp.R.string.Refresh_suggestion);
            loadingMessage.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {
        swipe.setRefreshing(false);
        if (data != null) {
            nAdapter.setNotifyOnChange(false);
            nAdapter.clear();
            nAdapter.setNotifyOnChange(true);
            nAdapter.addAll(data);
        }
    }

    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle){
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        // getString retrieves a String value from the preferences. The second parameter is the default value for this preference.
        String minPages = sharedPrefs.getString(
                getString(R.string.settings_min_pages_label),
                getString(R.string.settings_min_pages_default));

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .encodedAuthority("content.guardianapis.com")
                .appendPath("search")
                .appendQueryParameter("order-by", "newest")
                .appendQueryParameter("show-references", "author")
                .appendQueryParameter("show-tags", "contributor")
                .appendQueryParameter("q", "homebirth")
                .appendQueryParameter("page-size",minPages)
                .appendQueryParameter("api-key", getString(com.webstormcomputers.udacitynewsapp.R.string.api_key));
        String url =  builder.build().toString();

        return new NewsLoader(MainActivity.this, url);

    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        // Loader reset, so we can clear out our existing data.
        nAdapter.clear();
    }

    @Override
    public void onRefresh() {
        TextView loadingMessage = (TextView) findViewById(R.id.loading_message);
        loadingMessage.setVisibility(View.GONE);
        getLoaderManager().initLoader(0, null, this).forceLoad();
    }
}