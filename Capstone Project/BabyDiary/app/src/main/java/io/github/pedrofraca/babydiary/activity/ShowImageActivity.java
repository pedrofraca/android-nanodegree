package io.github.pedrofraca.babydiary.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import io.github.pedrofraca.babydiary.R;

/**
 * Created by pedrofraca on 23/05/16.
 */
public class ShowImageActivity extends AppCompatActivity {

    public static final String ATTR_IMAGE_URI = "attr_image_uri";

    public static Intent newIntent(Activity caller, String uri){
        Intent intent = new Intent(caller,ShowImageActivity.class);
        intent.putExtra(ATTR_IMAGE_URI,uri);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);
        SubsamplingScaleImageView imageView = (SubsamplingScaleImageView)findViewById(R.id.imageView);

        imageView.setImage(ImageSource.uri(getIntent().getStringExtra(ATTR_IMAGE_URI)));
    }
}
