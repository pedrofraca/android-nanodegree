package it.jaschke.alexandria.barcode;

import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.barcode.Barcode;

/**
 * Created by pedrofraca on 20/10/15.
 */
public class BarcodeTrackerFactory implements MultiProcessor.Factory<Barcode> {

    private BarcodeCaptureActivity mCallerActivity;

    public BarcodeTrackerFactory(BarcodeCaptureActivity activity){

        mCallerActivity = activity;
    }

    @Override
    public Tracker<Barcode> create(Barcode barcode) {
        return new BarcodeTracker(mCallerActivity);
    }

}