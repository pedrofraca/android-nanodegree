package it.jaschke.alexandria.services;

import android.os.AsyncTask;

import java.lang.ref.WeakReference;

import it.jaschke.alexandria.services.model.BookResponse;
import retrofit.RetrofitError;

/**
 * Created by pedrofraca on 19/10/15.
 */
public class GetVolumeInfo extends AsyncTask<Void,Void,Exception> {
    WeakReference<BookServiceListener> listenerWeakReference;
    BookResponse mVolumeInfoResponse;
    private String mEan;

    public GetVolumeInfo(BookServiceListener listener, String ean){
        mEan = ean;
        listenerWeakReference = new WeakReference<>(listener);
    }

    @Override
    protected Exception doInBackground(Void... voids) {
        BookAPI bookAPIService = new BookAPIFactory().getBookAPIService();
        try {
            mVolumeInfoResponse = bookAPIService.getBook(mEan);
        } catch (RetrofitError e) {
            return e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Exception exception) {
        BookServiceListener listener = listenerWeakReference.get();
        if(listener==null){
            return;
        }
        if(exception!=null){
            listener.onBookFoundError(exception);
        } else {
            listener.onBookFound(mEan,mVolumeInfoResponse.items.get(0).volumeInfo);
        }
    }
}
