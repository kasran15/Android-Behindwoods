package com.kazzlabs.apps.behindwoodsreader.views;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
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
public class NewsFragment extends android.support.v4.app.Fragment implements IAsyncCallback<NewsDetail> {

    private FrameLayout mNewsView;

    private String mLink;

    private NewsItemClickHandler mHandler;

    private int index;

    private NewsDetail result;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mNewsView = (FrameLayout) inflater.inflate(R.layout.fragment_news, container, false);
        render();
        return mNewsView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(NewsFragment.class.toString(), "Creating news view");
        RetrieveNewsTask task = new RetrieveNewsTask(this);
        task.execute(getArguments().getString("link"),
                getArguments().getString("title"));
    }

    @Override
    public void onComplete(NewsDetail result) {
        if (result == null) return;
        this.result = result;
        render();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void render() {
        if (result == null) return;
        ScrollView scrollView = (ScrollView) mNewsView.getChildAt(0);
        LinearLayout layout = (LinearLayout) scrollView.getChildAt(0);

        TextView titleTextView = (TextView) layout.getChildAt(1);
        Log.v(this.getClass().getSimpleName(), "Title: " + result.getTitle());
        titleTextView.setText(result.getTitle());

        TextView textView = (TextView) layout.getChildAt(2);
        textView.setText(result.getArticle());
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        Bitmap croppedBitmap = ThumbnailUtils.extractThumbnail(
                result.getImage(),
                metrics.widthPixels,
                metrics.heightPixels/2);
        BitmapDrawable bg = new BitmapDrawable(getResources(), croppedBitmap);

        ImageView imageView = (ImageView) layout.getChildAt(0);
        imageView.getLayoutParams().height = metrics.heightPixels/2;
        imageView.setBackground(bg);

    }

    public void addHandler(NewsItemClickHandler handler) {
        this.mHandler = handler;
    }


    public interface NewsItemClickHandler {
        public void onNewsItemClicked(NewsItem item);
    }
}
