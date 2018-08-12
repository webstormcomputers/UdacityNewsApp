package com.webstormcomputers.udacitynewsapp;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 *
 */
public class NewsLoader extends AsyncTaskLoader<List<News>> {

    /** Tag for log messages. */
    private static final String LOG_TAG = NewsLoader.class.getName();

    /** Query URL. */
    private String mUrl;

    /**
     * Constructs a new {@link NewsLoader}.
     *
     * @param context of the activity.
     * @param url to load data from.
     */
    public NewsLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<News> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        // String orderBy = sharedPrefs.getString(R.string.settings_order_oldest);
        List<News> listOfNews = null;

        URL mUrl = QueryList.createJsonQuery();
        listOfNews = QueryList.getNews(mUrl);

        return listOfNews;
    }
}