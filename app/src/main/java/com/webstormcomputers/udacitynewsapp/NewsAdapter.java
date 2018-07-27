package com.webstormcomputers.udacitynewsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {
    public NewsAdapter(Context context, List<News> news) {
        super(context, 0, news);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        News currentNews = getItem(position);

        TextView txtTitle = (TextView) listItemView.findViewById(R.id.title);
        TextView txtNameOfSection = (TextView) listItemView.findViewById(R.id.name_of_section);
        txtTitle.setText(currentNews.getTitle());
        txtNameOfSection.setText(currentNews.getNameOfSection());
        TextView author = (TextView) listItemView.findViewById(R.id.author);
        if (currentNews.getAuthor() != null) {
            author.setText(currentNews.getAuthor());
        } else {
            author.setVisibility(View.GONE);
        }
        TextView datePublished = (TextView) listItemView.findViewById(R.id.date_published);
        if (currentNews.getDatePublished() != null) {
            datePublished.setText(currentNews.getDatePublished());
        } else {
            datePublished.setVisibility(View.GONE);
        }


        return listItemView;
    }


}
