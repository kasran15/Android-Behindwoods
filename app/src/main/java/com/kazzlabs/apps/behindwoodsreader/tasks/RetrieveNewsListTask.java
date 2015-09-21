package com.kazzlabs.apps.behindwoodsreader.tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by kasturip on 8/9/14.
 */
public class RetrieveNewsListTask extends AsyncTask<Void, Void, JSONArray> {

    private IAsyncCallback<JSONArray> callback;

    public RetrieveNewsListTask(IAsyncCallback<JSONArray> callback) {
        this.callback = callback;
    }

    @Override
    protected JSONArray doInBackground(Void... voids) {
        JSONArray newsArray = null;
        try {
            HttpURLConnection urlConnection;
            URL url = new URL("https://salty-sierra-1910.herokuapp.com/api/news");
            urlConnection = (HttpURLConnection) url.openConnection();
            String news;

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                news = null;
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
                news = null;
            }
            news = buffer.toString();

            Log.v(RetrieveNewsListTask.class.toString(), "NewsDetail: " + news);

            newsArray = new JSONArray(news);

        } catch (Exception e) {
            Log.e("", e.getMessage());
            e.printStackTrace();
        }

    return newsArray;
}

    @Override
    protected void onPostExecute(JSONArray jsonArray) {
        super.onPostExecute(jsonArray);
        callback.onComplete(jsonArray);
    }
}
