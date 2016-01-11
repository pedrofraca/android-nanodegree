package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;
import java.lang.ref.WeakReference;

import io.github.pedrofraca.joketelling.backend.myApi.MyApi;

public class GetJokeAsyncTask extends AsyncTask<Void, Void, String> {

    private static MyApi myApiService = null;
    WeakReference<GetJokeAsyncTaskListener> mListener;
    Exception mError;

    public GetJokeAsyncTask(GetJokeAsyncTaskListener listener){
        mListener = new WeakReference<GetJokeAsyncTaskListener>(listener);
    }

    @Override
    protected String doInBackground(Void... params) {
        if (myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://192.168.57.1:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            myApiService = builder.build();
        }

        try {
            return myApiService.joke().execute().getData();
        } catch (IOException e) {
            mError=e;
            return null;
        }
    }


    @Override
    protected void onPostExecute(String result) {
        GetJokeAsyncTaskListener mListener = this.mListener.get();
        if(result!=null){
            if(mListener!=null){
                mListener.onTokenReceived(result);
            }
        } else {
            if(mListener!=null){
                mListener.onTokenError(mError);
            }

        }
    }
}