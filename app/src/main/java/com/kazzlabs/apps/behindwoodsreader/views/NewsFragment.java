package com.kazzlabs.apps.behindwoodsreader.views;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.kazzlabs.apps.behindwoodsreader.models.NewsDetail;
import com.kazzlabs.apps.behindwoodsreader.tasks.IAsyncCallback;
import com.kazzlabs.apps.behindwoodsreader.R;
import com.kazzlabs.apps.behindwoodsreader.models.NewsItem;
import com.kazzlabs.apps.behindwoodsreader.tasks.RetrieveNewsTask;

/**
 * This fragment renders an image and the article news.
 * This is encompassed in a Scroll view to allow scrolling.
 *
 * TODO: This should be enhanced to allow swipe left and right to access the other news items.
 *
 * Created by kasturip on 8/9/14.
 */
public class NewsFragment extends Fragment implements IAsyncCallback<NewsDetail> {

    private ScrollView mNewsView;

    private String mLink;

    private NewsItemClickHandler mHandler;

    private int index;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mNewsView = (ScrollView) inflater.inflate(R.layout.fragment_news, container, false);
        return mNewsView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("", "Creating news view");

        RetrieveNewsTask task = new RetrieveNewsTask(this);
        task.execute(getArguments().getString("link"));
    }

    @Override
    public void onComplete(NewsDetail result) {
        if (result == null) return;

        LinearLayout layout = (LinearLayout) mNewsView.getChildAt(0);

        ImageView imageView = (ImageView) layout.getChildAt(0);
        imageView.setImageBitmap(result.getImage());

        TextView textView = (TextView) layout.getChildAt(1);
        textView.setText(result.getArticle());
    }

    public void addHandler(NewsItemClickHandler handler) {
        this.mHandler = handler;
    }


    public interface NewsItemClickHandler {
        public void onNewsItemClicked(NewsItem item);
    }
}
