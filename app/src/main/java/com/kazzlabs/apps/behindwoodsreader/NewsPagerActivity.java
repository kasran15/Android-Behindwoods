package com.kazzlabs.apps.behindwoodsreader;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.ViewGroup;

import com.kazzlabs.apps.behindwoodsreader.models.NewsDetail;
import com.kazzlabs.apps.behindwoodsreader.models.NewsItem;
import com.kazzlabs.apps.behindwoodsreader.models.NewsList;
import com.kazzlabs.apps.behindwoodsreader.views.NewsFragment;

/**
 * Activity that supports horizontal pagination for the news.
 *
 * Created by kasturip on 9/20/15.
 */
public class NewsPagerActivity extends FragmentActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_pager);

        int index = getIntent().getIntExtra("index", 0);

        final ViewPager pager = (ViewPager) findViewById(R.id.newsPager);

        pager.setOffscreenPageLimit(2);

        Log.v(NewsPagerActivity.class.toString(), "Pager fragment create view. index: " + index);
        final int size = NewsList.getInstance().getNewsList().size();


        pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return size;
            }

            @Override
            public Fragment getItem(int position) {
                NewsFragment fragment = new NewsFragment();
                Bundle bundle = new Bundle();
                NewsItem newsItem= NewsList.getInstance().getNewsList().get(position);
                bundle.putString("link", newsItem.getLink());
                bundle.putString("title", newsItem.getTitle());
                fragment.setArguments(bundle);
                Log.v(NewsPagerActivity.class.toString(), "Fetching news article for pager. position - " + position);
                return fragment;
            }

        });

        // IMPORTANT: this should be invoked after setting the adapter
        pager.setCurrentItem(index, false);

    }


}
