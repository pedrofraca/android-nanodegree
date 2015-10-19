package it.jaschke.alexandria;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by fracaped on 18/10/15.
 */
public class Utility {
    static public boolean isNetworAvailable(Context c){
        ConnectivityManager systemService =
                (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = systemService.getActiveNetworkInfo();
        return activeNetworkInfo!=null && activeNetworkInfo.isConnectedOrConnecting();
    }
}
