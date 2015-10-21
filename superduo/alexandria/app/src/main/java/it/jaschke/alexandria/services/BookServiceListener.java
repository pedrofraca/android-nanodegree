package it.jaschke.alexandria.services;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import it.jaschke.alexandria.services.model.VolumeInfo;

/**
 * Created by pedrofraca on 19/10/15.
 */
public interface BookServiceListener {


    @Retention(RetentionPolicy.SOURCE)
    @IntDef({NO_ROUTE_TO_SERVER_ERROR, SERVER_ERROR,UNKNOWN})
    public @interface BookApiServiceError {}
    public static final int NO_ROUTE_TO_SERVER_ERROR= 0;
    public static final int SERVER_ERROR= 1;
    public static final int UNKNOWN = 2;
    void onBookFound(String ean, VolumeInfo info);
    void onBookFoundError(@BookApiServiceError int error);
}
