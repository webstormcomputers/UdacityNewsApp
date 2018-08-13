package com.webstormcomputers.udacitynewsapp;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import static android.provider.Settings.System.getString;

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

        List<News> listOfNews = null;
        listOfNews = QueryList.getNews(mUrl);
        return listOfNews;
    }
}