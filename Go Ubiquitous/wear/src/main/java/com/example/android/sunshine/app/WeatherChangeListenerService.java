package com.example.android.sunshine.app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * Created by pedrofraca on 24/04/16.
 */
public class WeatherChangeListenerService extends WearableListenerService {
    public static final String MIN_TEMP = "min_temp";
    public static final String MAX_TEMP = "max_temp";
    public static final String WEATHER_ICON = "weather_icon";

    public static final String BROADCAST = "sunshine_broadcast";

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        for (DataEvent event : dataEvents){
            if (event.getType() == DataEvent.TYPE_CHANGED) {
                // DataItem changed
                DataItem item = event.getDataItem();
                if (item.getUri().getPath().compareTo("/sunshine") == 0) {
                    DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();

                    Intent intent = new Intent(BROADCAST);
                    Bundle extras = new Bundle();
                    extras.putDouble(MIN_TEMP, dataMap.getDouble(MIN_TEMP));
                    extras.putDouble(MAX_TEMP, dataMap.getDouble(MAX_TEMP));
                    Asset asset = dataMap.getAsset(WEATHER_ICON);
                    extras.putParcelable(WEATHER_ICON,loadBitmapFromAsset(asset));
                    intent.putExtras(extras);
                    sendBroadcast(intent);
                }
            } else if (event.getType() == DataEvent.TYPE_DELETED) {
                // DataItem deleted
            }
        }
        super.onDataChanged(dataEvents);
    }

    private Bitmap loadBitmapFromAsset(Asset asset) {
        GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .build();
        if (asset == null) {
            throw new IllegalArgumentException("Asset must be non-null");
        }
        ConnectionResult result =
                mGoogleApiClient.blockingConnect(100, TimeUnit.MILLISECONDS);
        if (!result.isSuccess()) {
            return null;
        }
        // convert asset into a file descriptor and block until it's ready
        InputStream assetInputStream = Wearable.DataApi.getFdForAsset(
                mGoogleApiClient, asset).await().getInputStream();
        mGoogleApiClient.disconnect();

        if (assetInputStream == null) {
            return null;
        }
        // decode the stream into a bitmap
        return BitmapFactory.decodeStream(assetInputStream);
    }

    @Override
    public void onCreate() {
        Log.d("service","init");
        super.onCreate();
    }
}
