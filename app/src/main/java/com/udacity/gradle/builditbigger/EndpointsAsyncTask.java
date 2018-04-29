package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.test.espresso.IdlingResource;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.jcr.jokeactivity.JokeActivity;
import com.udacity.gradle.builditbigger.IdlingResource.SimpleIdlingResource;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

import static com.jcr.jokeactivity.JokeActivity.EXTRA_JOKE;

/**
 * Created by Jaume on 29/04/2018.
 */

public class EndpointsAsyncTask extends AsyncTask<SimpleIdlingResource, Void, String> {
    private static MyApi myApiService = null;
    private SimpleIdlingResource mIdlingResource;
    private OnFinishedCallback onFinishedCallback;

    public EndpointsAsyncTask(OnFinishedCallback onFinishedCallback) {
        this.onFinishedCallback = onFinishedCallback;
    }

    @Override
    protected String doInBackground(SimpleIdlingResource... params) {
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }

        mIdlingResource = params[0];
        if (mIdlingResource != null) {
            mIdlingResource.setIdleState(false);
        }
        try {
            return myApiService.tellJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String joke) {
        onFinishedCallback.onFinished(joke);
        if (mIdlingResource != null) {
            mIdlingResource.setIdleState(true);
        }
    }

    public interface OnFinishedCallback {
        void onFinished(String joke);
    }
}
