package com.kaichen.mynews;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Kai on 1/21/2018.
 */

public class FashionFragment extends Fragment {
    public FashionFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.news_list, container, false);

        ArrayList<NewsItem> news = new ArrayList<>();

        NewsAdapter adapter = new NewsAdapter(getActivity(), news);
        ListView listView = (ListView) rootView.findViewById(R.id.news_list);
        listView.setAdapter(adapter);

        String apiURL = "https://content.guardianapis.com/search?tag=fashion/fashion&api-key=7083623e-fe2f-4c3f-8624-5a5029e7cae0";
        RequestNews requestNews = new RequestNews(adapter, apiURL);
        requestNews.execute();


        return listView;
    }
}
