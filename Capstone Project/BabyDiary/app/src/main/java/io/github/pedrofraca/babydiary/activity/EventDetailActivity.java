package io.github.pedrofraca.babydiary.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import io.github.pedrofraca.babydiary.R;
import io.github.pedrofraca.babydiary.fragment.EventDetailFragment;
import io.github.pedrofraca.babydiary.provider.event.EventCursor;
import io.github.pedrofraca.babydiary.provider.event.EventSelection;

/**
 * Created by pedrofraca on 23/05/16.
 */
public class EventDetailActivity extends AppCompatActivity {

    public static final String ATTR_EVENT_ID="attr_event_id";

    public static Intent newIntent(Activity caller, long eventId){
        Intent intent = new Intent(caller,EventDetailActivity.class);
        intent.putExtra(ATTR_EVENT_ID,eventId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail_event);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.MyToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        EventCursor event = new EventSelection()
                .id(getIntent().getLongExtra(ATTR_EVENT_ID,-1))
                .query(this);

        if(event.moveToFirst()){
            getSupportActionBar().setTitle(event.getTitle());
        }


        getSupportFragmentManager().beginTransaction().add(R.id.container, EventDetailFragment.newInstance(getIntent().getLongExtra(ATTR_EVENT_ID,-1))).commit();

    }
}
