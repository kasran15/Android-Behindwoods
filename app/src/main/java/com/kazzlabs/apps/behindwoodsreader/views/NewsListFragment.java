package com.kazzlabs.apps.behindwoodsreader.views;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.kazzlabs.apps.behindwoodsreader.models.NewsList;
import com.kazzlabs.apps.behindwoodsreader.tasks.IAsyncCallback;
import com.kazzlabs.apps.behindwoodsreader.R;
import com.kazzlabs.apps.behindwoodsreader.tasks.RetrieveNewsListTask;
import com.kazzlabs.apps.behindwoodsreader.models.NewsItem;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Arrays;

/**
 * This fragment is the home page and lists out all the news items.
 *
 * Created by kasturip on 8/9/14.
 */
public class NewsListFragment extends android.support.v4.app.Fragment implements IAsyncCallback<JSONArray>, AdapterView.OnItemClickListener {

    private ListView mNewsListView;

    private NewsItem[] newsItems;

    private NewsItemClickHandler mHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mNewsListView = (ListView) inflater.inflate(R.layout.fragment_news_list, container, false);
        mNewsListView.setOnItemClickListener(this);

        super.onCreate(savedInstanceState);

        if (newsItems == null) {
            RetrieveNewsListTask task = new RetrieveNewsListTask(this);
            task.execute();
        } else {
            mNewsListView.setAdapter(new ArrayAdapter<NewsItem>(
                    getActivity().getApplicationContext(),
                    R.layout.news_list_item,
                    android.R.id.text1,
                    newsItems));
        }

        return mNewsListView;
    }

    @Override
    public void onComplete(JSONArray result) {
        if (result == null) return;
        NewsItem[] news = new NewsItem[result.length()];

        for (int i = 0; i < result.length(); i++) {
            try {
                news[i] = new NewsItem(result.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        mNewsListView.setAdapter(new ArrayAdapter<NewsItem>(
                getActivity().getApplicationContext(),
                R.layout.news_list_item,
                android.R.id.text1,
                news) {

        });

        NewsList.getInstance().setNewsList(Arrays.asList(news));
    }

    public void addHandler(NewsItemClickHandler handler) {
        this.mHandler = handler;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Log.v("", "Position of the click is " + position);
        if (mHandler != null) {
            NewsItem item = (NewsItem) adapterView.getItemAtPosition(position);
            mHandler.onNewsItemClicked(item, position);
        }
    }

    public interface NewsItemClickHandler {
        public void onNewsItemClicked(NewsItem item, int index);
    }
}
