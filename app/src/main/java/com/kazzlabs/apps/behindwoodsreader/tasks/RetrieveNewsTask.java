package com.kazzlabs.apps.behindwoodsreader.tasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.kazzlabs.apps.behindwoodsreader.models.NewsDetail;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by kasturip on 8/9/14.
 */
public class RetrieveNewsTask extends AsyncTask<String, Void, NewsDetail> {

    private IAsyncCallback<NewsDetail> callback;

    public RetrieveNewsTask(IAsyncCallback<NewsDetail> callback) {
        this.callback = callback;
    }

    @Override
    protected NewsDetail doInBackground(String... params) {
        JSONObject newsObject = null;
        String newsLink = params[0];
        NewsDetail news = null;

        try {
            HttpURLConnection urlConnection;
            URL url = new URL("https://salty-sierra-1910.herokuapp.com/api/news/" + URLEncoder.encode(newsLink, "UTF-8"));
            urlConnection = (HttpURLConnection) url.openConnection();
            String newsString;

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                newsString = null;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                newsString = null;
            }
            newsString = buffer.toString();

            Log.v("", "NewsDetail: " + newsString);

            newsObject = new JSONObject(newsString);

            String imageURL = "http://www.behindwoods.com/tamil-movies-cinema-news-15/" + newsObject.getString("image");
            Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(imageURL).getContent());
            news = new NewsDetail(bitmap, newsObject.getString("article"));

        } catch (Exception e) {
            Log.e("", e.getMessage());
            e.printStackTrace();
        }

        return news;
    }

    @Override
    protected void onPostExecute(NewsDetail news) {
        super.onPostExecute(news);
        callback.onComplete(news);
    }
}
