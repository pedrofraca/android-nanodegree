package it.jaschke.alexandria;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;
import com.squareup.picasso.Picasso;

import it.jaschke.alexandria.barcode.BarcodeCaptureActivity;
import it.jaschke.alexandria.data.AlexandriaContract;
import it.jaschke.alexandria.data.BookProviderHelper;
import it.jaschke.alexandria.services.BookServiceListener;
import it.jaschke.alexandria.services.GetVolumeInfo;
import it.jaschke.alexandria.services.model.VolumeInfo;


public class AddBook extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, BookServiceListener{
    private static final String TAG = "INTENT_TO_SCAN_ACTIVITY";
    private EditText ean;
    private final int LOADER_ID = 1;
    private View rootView;
    private final String EAN_CONTENT="eanContent";

    private static final int BARCODE_REQUEST_CODE=23;

    private TextView mErrorMessage;
    private View mProgressBarView;


    public AddBook(){
    }

    public void showProgressBar(){
        mProgressBarView.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar(){
        mProgressBarView.setVisibility(View.GONE);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(ean!=null) {
            outState.putString(EAN_CONTENT, ean.getText().toString());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==Activity.RESULT_OK) {
            if(requestCode==BARCODE_REQUEST_CODE){
                Barcode theBarcode = data.getParcelableExtra(BarcodeCaptureActivity.BARCODE);
                ean.setText(theBarcode.rawValue);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_add_book, container, false);
        ean = (EditText) rootView.findViewById(R.id.ean);
        mErrorMessage = (TextView) rootView.findViewById(R.id.errorMessage);
        mProgressBarView = rootView.findViewById(R.id.progress_bar);
        ean.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //no need
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //no need
            }

            @Override
            public void afterTextChanged(Editable s) {
                String ean =s.toString();
                //catch isbn10 numbers
                if(ean.length()==10 && !ean.startsWith("978")){
                    ean="978"+ean;
                }
                if(ean.length()<13){
                    clearFields();
                    return;
                }
                BookProviderHelper bookProviderHelper = new BookProviderHelper();
                if(!bookProviderHelper.bookAlreadyOnDB(getActivity().getContentResolver(),ean)){
                    new GetVolumeInfo(AddBook.this,ean).execute();
                    showProgressBar();
                }

                AddBook.this.restartLoader();
            }
        });

        rootView.findViewById(R.id.scan_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent barcodeCaptureActivtyIntent = new Intent(getActivity(),BarcodeCaptureActivity.class);
                barcodeCaptureActivtyIntent.putExtra(BarcodeCaptureActivity.AutoFocus, true);
                startActivityForResult(barcodeCaptureActivtyIntent, BARCODE_REQUEST_CODE);
            }
        });

        rootView.findViewById(R.id.save_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ean.setText("");
            }
        });

        rootView.findViewById(R.id.delete_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BookProviderHelper helper = new BookProviderHelper();
                helper.deleteBook(getActivity().getContentResolver(), ean.getText().toString());
                ean.setText("");
            }
        });

        if(savedInstanceState!=null){
            ean.setText(savedInstanceState.getString(EAN_CONTENT));
            ean.setHint("");
        }

        return rootView;
    }

    private void restartLoader(){
        getLoaderManager().restartLoader(LOADER_ID, null, this);
    }

    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if(ean.getText().length()==0){
            return null;
        }
        String eanStr= ean.getText().toString();
        if(eanStr.length()==10 && !eanStr.startsWith("978")){
            eanStr="978"+eanStr;
        }
        return new CursorLoader(
                getActivity(),
                AlexandriaContract.BookEntry.buildFullBookUri(Long.parseLong(eanStr)),
                null,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
        if (!data.moveToFirst()) {
            return;
        }

        VolumeInfo volumeInfo = new BookProviderHelper().fromCursorToVolumeInfo(data);

        ((TextView) rootView.findViewById(R.id.bookTitle)).setText(volumeInfo.title());
        ((TextView) rootView.findViewById(R.id.bookSubTitle)).setText(volumeInfo.subtitle());

        ((TextView) rootView.findViewById(R.id.authors)).setLines(volumeInfo.authors().size());
        String authorsJoined = TextUtils.join("\n", volumeInfo.authors());
        ((TextView) rootView.findViewById(R.id.authors)).setText(authorsJoined);

        if(Patterns.WEB_URL.matcher(volumeInfo.imageLinks().thubmnail()).matches()){
            Picasso.with(getActivity()).load(volumeInfo.imageLinks().thubmnail()).into(((ImageView) rootView.findViewById(R.id.bookCover)));
            rootView.findViewById(R.id.bookCover).setVisibility(View.VISIBLE);
        }
        String categoriesString = TextUtils.join(",",volumeInfo.categories());
        ((TextView) rootView.findViewById(R.id.categories)).setText(categoriesString);

        rootView.findViewById(R.id.save_button).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.delete_button).setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {

    }

    private void clearFields(){
        ((TextView) rootView.findViewById(R.id.bookTitle)).setText("");
        ((TextView) rootView.findViewById(R.id.bookSubTitle)).setText("");
        ((TextView) rootView.findViewById(R.id.authors)).setText("");
        ((TextView) rootView.findViewById(R.id.categories)).setText("");
        rootView.findViewById(R.id.bookCover).setVisibility(View.INVISIBLE);
        rootView.findViewById(R.id.save_button).setVisibility(View.INVISIBLE);
        rootView.findViewById(R.id.delete_button).setVisibility(View.INVISIBLE);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        activity.setTitle(R.string.scan);
    }

    @Override
    public void onBookFound(String ean, VolumeInfo info) {
        hideProgressBar();
        if(info==null){
            setErrorMessage(getResources().getString(R.string.no_book_found_error,ean));
        } else {
            BookProviderHelper bookProviderHelper = new BookProviderHelper();
            bookProviderHelper.writeBackBook(getActivity().getContentResolver(),
                    ean,
                    info);
        }
    }

    @Override
    public void onBookFoundError(@BookApiServiceError int error) {
        hideProgressBar();
        if(error==BookServiceListener.NO_ROUTE_TO_SERVER_ERROR){
            if(!Utility.isNetworAvailable(getActivity())){
                setErrorMessage(getResources().getString(R.string.no_internet_error));
            } else {
                setErrorMessage(getResources().getString(R.string.server_down_error));    
            }
            
        } else if(error==BookServiceListener.SERVER_ERROR){
            setErrorMessage(getResources().getString(R.string.server_error_string));
        } else {
            setErrorMessage(getResources().getString(R.string.unknown_error));
        }
    }

    public void setErrorMessage(String message){
        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
    }
}
