package io.github.pedrofraca.babydiary.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import io.github.pedrofraca.babydiary.fragment.AddEventFragment;
import io.github.pedrofraca.babydiary.utils.BabyUtils;
import io.github.pedrofraca.babydiary.fragment.CreateEventDialogFragment;
import io.github.pedrofraca.babydiary.R;
import io.github.pedrofraca.babydiary.fragment.TimelineFragment;
import io.github.pedrofraca.babydiary.provider.event.EventType;

/**
 * Created by pedrofraca on 17/05/16.
 */
public class TimelineActivity extends AppCompatActivity implements AddEventFragment.AddEventFragmentListener{
    private CharSequence mTitle;
    private FloatingActionButton mAddFab;

    public static Intent newIntent(Activity caller) {
        return new Intent(caller, TimelineActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        mAddFab = (FloatingActionButton) findViewById(R.id.add_fab);
        mAddFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = AddEventFragment.newInstance();
                getSupportFragmentManager().beginTransaction().add(R.id.buttons_container, fragment).addToBackStack("").commit();
                view.setVisibility(View.INVISIBLE);
            }
        });

        final Toolbar toolbar = (Toolbar) findViewById(R.id.MyToolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(getString(R.string.baby_diary_title, BabyUtils.getCurrentActiveBabyName(this)));

        getSupportFragmentManager().beginTransaction().add(R.id.container, new TimelineFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount()>0){
            mAddFab.setVisibility(View.VISIBLE);
        }

        super.onBackPressed();
    }

    @Override
    public void onPhotoEventClicked() {
        mAddFab.setVisibility(View.VISIBLE);
        DialogFragment newFragment = CreateEventDialogFragment.newInstance(EventType.PHOTO);
        newFragment.show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public void onMeasureEventClicked() {
        DialogFragment newFragment = CreateEventDialogFragment.newInstance(EventType.MEASURE);
        newFragment.show(getSupportFragmentManager(), "dialog");
        mAddFab.setVisibility(View.VISIBLE);
    }

    @Override
    public void onVaccineEventClicked() {
        DialogFragment newFragment = CreateEventDialogFragment.newInstance(EventType.VACCINE);
        newFragment.show(getSupportFragmentManager(), "dialog");
        mAddFab.setVisibility(View.VISIBLE);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        if(getSupportActionBar()!=null){
            getSupportActionBar().setTitle(mTitle);
        }
    }
}
