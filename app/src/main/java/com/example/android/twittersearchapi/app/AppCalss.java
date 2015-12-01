package com.example.android.twittersearchapi.app;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.android.twittersearchapi.utill.LruBitmapCache;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.tweetui.TweetUi;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Duke on 11/29/2015.
 */
public class AppCalss extends Application {


    public static final String TAG = AppCalss.class.getSimpleName();

    private RequestQueue mRequestQueue;

    private static AppCalss mInstance;
    private ImageLoader mImageLoader;


    @Override
    public void onCreate() {
        super.onCreate();


        TwitterAuthConfig authConfig = new TwitterAuthConfig("YRW5P6c08TdilvToEijd4ESb2", "4x3YHasoaljQxEfC1liT7amwUwvnaSGYFare50IcMv811CP35W");
        Fabric.with(this, new TwitterCore(authConfig));

        Fabric.with(this, new TwitterCore(authConfig), new TweetUi());
        mInstance = this;

    }


    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }


    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public static synchronized AppCalss getInstance() {
        return mInstance;
    }


    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }


}
