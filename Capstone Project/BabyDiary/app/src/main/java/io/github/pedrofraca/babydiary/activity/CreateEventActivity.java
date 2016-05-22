package io.github.pedrofraca.babydiary.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import io.github.pedrofraca.babydiary.R;

/**
 * Created by pedrofraca on 21/05/16.
 */
public class CreateEventActivity extends AppCompatActivity {

    public static Intent newIntent(Activity caller){
        return new Intent(caller, CreateEventActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
    }
}
