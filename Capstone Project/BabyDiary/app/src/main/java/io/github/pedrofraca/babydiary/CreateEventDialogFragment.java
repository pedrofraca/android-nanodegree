package io.github.pedrofraca.babydiary;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import io.github.pedrofraca.babydiary.activity.SelectLocationActivity;
import io.github.pedrofraca.babydiary.provider.event.EventColumns;
import io.github.pedrofraca.babydiary.provider.event.EventContentValues;
import io.github.pedrofraca.babydiary.provider.event.EventType;

/**
 * Created by pedrofraca on 19/05/16.
 */
public class CreateEventDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    public static final String ATTR_EVENT_TYPE = "event_type";
    private static final int REQUEST_WRITE_PERMISSIONS = 3;
    private Button mDatePickerButton;
    private Button mAddLocation;
    String mCurrentMediaPath;

    private static final int SELECT_LOCATION_REQUEST_CODE=1;
    static final int REQUEST_IMAGE_CAPTURE = 2;

    private Button mTakePhotoButton;
    private ImageView mImageView;
    private ImageButton mSaveButton;
    private TextInputEditText mTitle;
    private TextInputEditText mDescription;
    private EventType mEvent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_create_event, container, false);
        mDatePickerButton = (Button) rootView.findViewById(R.id.datePicker);

        mSaveButton = (ImageButton) rootView.findViewById(R.id.save_button);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });

        mDatePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date = new Date(System.currentTimeMillis());
                Calendar calendar = GregorianCalendar.getInstance();
                calendar.setTime(date);
                DatePickerDialog birthDatePicker = new DatePickerDialog(getActivity(),
                        CreateEventDialogFragment.this,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                birthDatePicker.getDatePicker().setSpinnersShown(true);
                birthDatePicker.show();
            }
        });

        mTakePhotoButton = (Button) rootView.findViewById(R.id.add_photo_button);

        mTakePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    dispatchTakePictureIntent();
                }
            }
        });

        mAddLocation = (Button) rootView.findViewById(R.id.add_location_button);
        mAddLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(SelectLocationActivity.newIntent(getActivity()),SELECT_LOCATION_REQUEST_CODE);
            }
        });

        mImageView = (ImageView) rootView.findViewById(R.id.image);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        mDatePickerButton.setText(sdf.format(new Date(System.currentTimeMillis())));

        setDialogTitle((TextView)rootView.findViewById(R.id.create_event_title));

        mTitle = (TextInputEditText) rootView.findViewById(R.id.title_input);
        mDescription = (TextInputEditText) rootView.findViewById(R.id.description_input);

        return rootView;
    }

    private void save() {
        boolean valid = true;
        if(mTitle.getText().toString().isEmpty()){
            mTitle.setError(getActivity().getString(R.string.need_provide_title));
            valid = false;
        }

        if(mDescription.getText().toString().isEmpty()){
            mDescription.setError(getActivity().getString(R.string.need_provide_description));
            valid = false;
        }

        if(mCurrentMediaPath.isEmpty()){
            Snackbar.make(mDescription,"A photo needs to be taken", Snackbar.LENGTH_SHORT).show();
            valid = false;
        }

        if(valid){
            EventContentValues event = new EventContentValues();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            try {
                Date parsedDate = sdf.parse(mDatePickerButton.getText().toString());
                event.putDate(parsedDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            event.putType(mEvent);
            event.putDescription(mDescription.getText().toString());
            event.putTitle(mTitle.getText().toString());
            event.putMediaPath(mCurrentMediaPath);
            event.putBabyId(BabyUtils.getCurrentActiveBabyId(getActivity()));
            getActivity().getContentResolver().insert(EventColumns.CONTENT_URI,event.values());
            dismiss();
        }

    }

    private void setDialogTitle(TextView text) {
        mEvent = EventType.valueOf(getArguments().getString(ATTR_EVENT_TYPE));
        switch (mEvent){
            case AUDIO:
                text.setText(R.string.create_audio_event);
                break;
            case MEASURE:
                text.setText(R.string.create_measure_event);
                break;
            case PHOTO:
                text.setText(R.string.create_photo_event);
                break;
            case TEXT:
                text.setText(R.string.create_text_event);
                break;
            case VACCINE:
                text.setText(R.string.create_vaccine_event);
                break;
            case VIDEO:
                text.setText(R.string.create_video_event);
                break;
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_WRITE_PERMISSIONS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }

            return null;
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp;
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"babyDiary");

        // Create the storage directory if it does not exist
        if (!storageDir.exists()) {
            if (!storageDir.mkdirs()) {
                Log.e("Create", "failed to create directory");
                return null;
            }
        }

        File image = new File(storageDir.getPath()+File.separator+imageFileName+".jpg");

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentMediaPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==REQUEST_WRITE_PERMISSIONS){
            boolean denied=false;
            for (int i = 0; i < grantResults.length; i++) {
                if(grantResults[i]==PackageManager.PERMISSION_DENIED) {
                    denied=true;
                }
            }
            if(!denied){
                dispatchTakePictureIntent();
            } else {

            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==SELECT_LOCATION_REQUEST_CODE && resultCode== Activity.RESULT_OK){
            LatLng currentLocation = data.getParcelableExtra(SelectLocationActivity.RESULT_DATA_LOCATION);
            Log.d("data",currentLocation.longitude+"");
        } else  if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            //galleryAddPic();
            new BabyDiaryImage().loadImageOnImageView(mCurrentMediaPath,mImageView);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
            // Store access variables for window and blank point
            Window window = getDialog().getWindow();
            Point size = new Point();
            // Store dimensions of the screen in `size`
            Display display = window.getWindowManager().getDefaultDisplay();
            display.getSize(size);
            // Set the width of the dialog proportional to 75% of the screen width
            window.setLayout((int) (size.x * 0.98), (int) (size.y * 0.98));
            window.setGravity(Gravity.CENTER);
            // Call super onResume after sizing
            super.onResume();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date parsedData = sdf.parse(day + "-" + (month + 1) + "-" + year);
            mDatePickerButton.setText(sdf.format(parsedData));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentMediaPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        getActivity().sendBroadcast(mediaScanIntent);
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }



    public static DialogFragment newInstance(EventType type) {
        Bundle bundle = new Bundle();
        bundle.putString(ATTR_EVENT_TYPE,type.toString());
        CreateEventDialogFragment createEventDialogFragment = new CreateEventDialogFragment();
        createEventDialogFragment.setArguments(bundle);
        return createEventDialogFragment;
    }
}
