package io.github.pedrofraca.babydiary.async;

import android.os.AsyncTask;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

/**
 * Created by pedrofraca on 22/05/16.
 */
public class GetLocationForLatLng extends AsyncTask<Void,Void,Exception> {
    private LatLng mLocation;
    private OnGetLocationForLatLng mListener;
    private String mResult;

    public GetLocationForLatLng(LatLng location){
        mLocation = location;
    }

    public GetLocationForLatLng listenedBy(OnGetLocationForLatLng listener){
        mListener = listener;
        return this;
    }

    @Override
    protected Exception doInBackground(Void... voids) {
        GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyBwwSO7aK9oaQsXO4sKky5x0naYONPu-l4");
        GeocodingResult[] results = new GeocodingResult[0];
        try {
            results = GeocodingApi.reverseGeocode(context,mLocation).await();
        } catch (Exception e) {
            return e;
        }
        mResult = results[0].formattedAddress;
        return null;
    }

    @Override
    protected void onPostExecute(Exception e) {
        super.onPostExecute(e);
        if(mListener!=null){
            if(e!=null){
                mListener.onGetLocationError(e);
            } else {
                mListener.onGetLocationSuccess(mResult);
            }
        }
    }

    public interface OnGetLocationForLatLng {
        void onGetLocationSuccess(String bestLocation);
        void onGetLocationError(Exception e);
    }
}
