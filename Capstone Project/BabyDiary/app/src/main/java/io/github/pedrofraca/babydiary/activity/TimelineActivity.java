package io.github.pedrofraca.babydiary.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import io.github.pedrofraca.babydiary.AddEventFragment;
import io.github.pedrofraca.babydiary.CreateEventDialogFragment;
import io.github.pedrofraca.babydiary.R;
import io.github.pedrofraca.babydiary.TimelineFragment;
import io.github.pedrofraca.babydiary.provider.baby.BabyCursor;
import io.github.pedrofraca.babydiary.provider.baby.BabySelection;
import io.github.pedrofraca.babydiary.provider.event.EventType;

/**
 * Created by pedrofraca on 17/05/16.
 */
public class TimelineActivity extends AppCompatActivity implements AddEventFragment.AddEventFragmentListener{
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private CharSequence mTitle;
    ArrayList<String> mBabiesNames = null;
    private ActionBarDrawerToggle mDrawerToggle;
    private FloatingActionButton mAddFab;

    public static Intent newIntent(Activity caller) {
        return new Intent(caller, TimelineActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        BabyCursor allBabies = new BabySelection().query(this);
        if (allBabies != null && allBabies.moveToFirst()) {
            mBabiesNames = new ArrayList<>(allBabies.getCount());
            do {
                mBabiesNames.add(new BabyCursor(allBabies).getName());
            } while (allBabies.moveToNext());
        }
        if (mBabiesNames != null) {
            // Set the adapter for the list view
            mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, mBabiesNames));
            // Set the list's click listener
            mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        }
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        mAddFab = (FloatingActionButton) findViewById(R.id.add_fab);
        mAddFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = AddEventFragment.newInstance();
                getSupportFragmentManager().beginTransaction().add(R.id.buttons_container, fragment).addToBackStack("").commit();
                view.setVisibility(View.INVISIBLE);
            }
        });

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
    public void onVideoEventClicked() {
        DialogFragment newFragment = CreateEventDialogFragment.newInstance(EventType.VIDEO);
        newFragment.show(getSupportFragmentManager(), "dialog");
        mAddFab.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAudioEventClicked() {

        DialogFragment newFragment = CreateEventDialogFragment.newInstance(EventType.AUDIO);
        newFragment.show(getSupportFragmentManager(), "dialog");
        mAddFab.setVisibility(View.VISIBLE);
    }

    @Override
    public void onTextEventClicked() {
        DialogFragment newFragment = CreateEventDialogFragment.newInstance(EventType.TEXT);
        newFragment.show(getSupportFragmentManager(), "dialog");
        mAddFab.setVisibility(View.VISIBLE);
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

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    /**
     * Swaps fragments in the main content view
     */
    private void selectItem(int position) {
        // Create a new fragment and specify the planet to show based on position
        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mBabiesNames.get(position));
        mDrawerLayout.closeDrawer(mDrawerList);
    }


    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        if(getActionBar()!=null){
            getActionBar().setTitle(mTitle);
        }
    }

}
