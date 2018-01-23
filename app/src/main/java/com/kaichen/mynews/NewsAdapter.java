package com.kaichen.mynews;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.List;

/**
 * Created by Kai on 1/21/2018.
 */

public class NewsAdapter extends ArrayAdapter<NewsItem> {
    public NewsAdapter(@NonNull Context context, @NonNull List<NewsItem> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.news_item, parent, false);
        }

        final NewsItem news = getItem(position);
        TextView english = (TextView) listItemView.findViewById(R.id.newsTitle);
        english.setText(news.getTitle());

        ImageView  imageView = (ImageView) listItemView.findViewById(R.id.newsImage);
        imageView.setImageBitmap(news.getImage());

        TextView newsDate = (TextView) listItemView.findViewById(R.id.newsDate);
        newsDate.setText(news.getSection() + "\n" + news.getDate());

        TextView readTitle = (TextView) listItemView.findViewById(R.id.readNewsTitle);
        final TextView readBody = (TextView) listItemView.findViewById(R.id.newsBody);

        //readBody.setText(news.getBody());
        //readTitle.setText(news.getTitle());

        listItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //opens another activity show the news
                //Intent browserIntent = new Intent("android.intent.action.VIEW", Uri.parse(news.getUrl()));
                //view.getContext().startActivity(browserIntent);

                Intent readIntent = new Intent(view.getContext(), ReadActivity.class);
                readIntent.putExtra("data", new String[]{news.getTitle(), news.getBody(), news.getDate()});
                view.getContext().startActivity(readIntent);
            }
        });



        return listItemView;
    }
}
