package com.webstormcomputers.udacitynewsapp;


import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryList {

    public static final String LOG_TAG = QueryList.class.getSimpleName();

    public static List<News> getNews(String mUrl) {

        URL queryUrl = createUrl(mUrl);

        String guardianJsonResponse = null;
        try {
            guardianJsonResponse = makehttpRequest(queryUrl);
        } catch (IOException e) {
            Log.e(LOG_TAG, "There was an error requesting the url", e);
        }
        List<News> jsonResult = extractNews(guardianJsonResponse);
        return jsonResult;
    }
    private static URL createUrl(String queryUrl){
        URL url = null;
        try {
            url = new URL(queryUrl);
        } catch (MalformedURLException mue) {
            Log.e("Malformed Url", "createUrl: Problem creating URL", mue);
        }

        return url;
    }

    private static String makehttpRequest(URL url) throws IOException{
        String jsonResponse = "";
        if(url == null){
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error in connection!! Bad Response ");
            }

        }catch (IOException e){
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }
            if(inputStream != null){
                inputStream.close();
            }
        }

        return jsonResponse;

    }

    private static String readFromStream(InputStream newsStream) throws IOException{
        StringBuilder output = new StringBuilder();
        if(newsStream != null) {
            InputStreamReader newsStreamReader = new InputStreamReader(newsStream, Charset.forName("UTF-8"));
            BufferedReader newsReader = new BufferedReader(newsStreamReader);
            String line = newsReader.readLine();
            while (line != null){
                output.append(line);
                line = newsReader.readLine();
            }
        }
        return output.toString();
    }
    private static List<News> extractNews( String newsJson){
        ArrayList<News> news = new ArrayList<>();
        try {
            JSONObject jsonRootObject = new JSONObject(newsJson);
            JSONArray jsonarray = jsonRootObject.getJSONObject("response").getJSONArray("results");

            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonObject = jsonarray.getJSONObject(i);
                String title = jsonObject.getString("webTitle");
                String nameOfSection = jsonObject.optString("sectionName");
                JSONArray tags = jsonObject.getJSONArray("tags");
                String datetime = jsonObject.getString("webPublicationDate");
                String webUrl = jsonObject.optString("webUrl").toString();
                String author;
                JSONArray tagsArray = jsonObject.getJSONArray("tags");
                if (tagsArray.length() > 0 )
                {
                    JSONObject tabsObject = tags.getJSONObject(0);
                    author = tabsObject.getString("webTitle");
                }
                else {
                    author = null;
                }
                news.add(new News(title, nameOfSection, author, datetime, webUrl));
            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem passing the Guardian News Json results", e);
        }
        return news;
    }
}