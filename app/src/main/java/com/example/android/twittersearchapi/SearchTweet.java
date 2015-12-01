package com.example.android.twittersearchapi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.android.twittersearchapi.app.AppCalss;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TweetUi;
import com.twitter.sdk.android.tweetui.TweetUtils;
import com.twitter.sdk.android.tweetui.TweetView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Duke on 12/1/2015.
 */
public class SearchTweet extends Activity {

    public static final String APP_SEARCH_LINK = "https://api.twitter.com/1.1/search/tweets.json";

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tweets_search);

        TwitterAuthConfig authConfig = new TwitterAuthConfig("YRW5P6c08TdilvToEijd4ESb2", "4x3YHasoaljQxEfC1liT7amwUwvnaSGYFare50IcMv811CP35W");
        Fabric.with(this, new TwitterCore(authConfig));

        Fabric.with(this, new TwitterCore(authConfig), new TweetUi());

      //  new LoadTweets().execute();


        final LinearLayout myLayout
                = (LinearLayout) findViewById(R.id.tweet_layout);

        final List<Long> tweetIds = Arrays.asList(510908133917487104L);
        TweetUtils.loadTweets(tweetIds, new Callback<Tweet>() {
            @Override
            public void success(Result<Tweet> result) {

                Result<Tweet> result1 = result;

                for (Tweet tweet : result1.data) {
                    myLayout.addView(new TweetView(SearchTweet.this, tweet));
                }
            }

            @Override
            public void failure(TwitterException exception) {
                // Toast.makeText(...).show();
            }
        });

    }

    private class LoadTweets extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(SearchTweet.this);
            pd.setTitle("Loading");

        }

        @Override
        protected Void doInBackground(Void... params) {


            String tag_string_req = "api_hit";


            StringRequest strReq = new StringRequest(Request.Method.GET, APP_SEARCH_LINK, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {


                        JSONObject jsonObject = new JSONObject(response);

                        JSONArray jsonArry = jsonObject.getJSONArray("statuses");

                        int len = jsonArry.length();

                        Toast.makeText(SearchTweet.this,"Lenth is "+ len ,Toast.LENGTH_SHORT).show();




                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                    Log.d("Error", volleyError.getMessage());

                    Toast.makeText(SearchTweet.this, volleyError.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }){
                @Override
                protected Map<String, String> getParams() {

                    Map<String, String> params = new HashMap<>();
                    params.put("q", "freebandnames");
                    return params;
                }
            };

            AppCalss.getInstance().addToRequestQueue(strReq, tag_string_req);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
