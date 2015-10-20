package it.jaschke.alexandria.barcode;

import android.util.Log;

import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.barcode.Barcode;

/**
 * Created by pedrofraca on 20/10/15.
 */
public class BarcodeTracker extends Tracker<Barcode> {
    private BarcodeCaptureActivity theCallerActivity;

    public BarcodeTracker(BarcodeCaptureActivity callerActivity) {

        theCallerActivity = callerActivity;
    }

    /**
     * Start tracking the detected item instance within the item overlay.
     */
    @Override
    public void onNewItem(int id, Barcode item) {
        theCallerActivity.onBarcodeDetected(item);
    }
}
