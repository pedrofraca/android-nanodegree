package io.github.pedrofraca.babydiary.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import io.github.pedrofraca.babydiary.R;
import io.github.pedrofraca.babydiary.activity.ShowImageActivity;
import io.github.pedrofraca.babydiary.provider.event.EventColumns;
import io.github.pedrofraca.babydiary.provider.event.EventCursor;
import io.github.pedrofraca.babydiary.provider.event.EventSelection;
import io.github.pedrofraca.babydiary.provider.event.EventType;
import io.github.pedrofraca.babydiary.utils.BabyDiaryImage;
import io.github.pedrofraca.babydiary.utils.BabyUtils;

/**
 * Created by pedrofraca on 23/05/16.
 */
public class EventDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    public static final String ATTR_EVENT_ID = "attr_event_id";
    private int EVENTS_URL_LOADER = 1;
    private TextView mDescription;
    private TextView mDate;
    private TextView mHeight;
    private TextView mWeight;
    private TextView mVaccine;
    private ImageView mImage;
    private String mMediaPath;

    public static EventDetailFragment newInstance(long id){
        EventDetailFragment fragment = new EventDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(ATTR_EVENT_ID,id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_event_detail, container, false);
        mDescription = (TextView) rootView.findViewById(R.id.description);
        mDate = (TextView) rootView.findViewById(R.id.date);
        mHeight = (TextView) rootView.findViewById(R.id.height);
        mWeight = (TextView) rootView.findViewById(R.id.weight);
        mVaccine = (TextView) rootView.findViewById(R.id.vaccine);
        mImage = (ImageView) rootView.findViewById(R.id.image);
        rootView.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new EventSelection().id(getArguments().getLong(ATTR_EVENT_ID)).delete(getActivity());
                getActivity().finish();
            }
        });

        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(ShowImageActivity.newIntent(getActivity(),mMediaPath), ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity()).toBundle());
            }
        });

        getActivity().getSupportLoaderManager().initLoader(EVENTS_URL_LOADER, getArguments(), this);

        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if(args!=null){
            return new CursorLoader(
                    getActivity(),
                    EventColumns.CONTENT_URI,
                    null,
                    EventColumns.BABY_ID + "=? and "+ EventColumns.TABLE_NAME+"."+EventColumns._ID +"=?",
                    new String[]{String.valueOf(BabyUtils.getCurrentActiveBabyId(getActivity())), String.valueOf(args.getLong(ATTR_EVENT_ID))},
                    null);
        }
        return null;

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        EventCursor eventCursor = new EventCursor(data);
        if(eventCursor.getCount() > 0 ){
            eventCursor.moveToFirst();
            mDescription.setText(eventCursor.getDescription());
            mDate.setText(eventCursor.getDate().toString());
            if(eventCursor.getType() == EventType.MEASURE){
                mHeight.setText(String.valueOf(eventCursor.getHeight()));
                mWeight.setText(String.valueOf(eventCursor.getWeight()));
            }
            if(eventCursor.getType()==EventType.VACCINE){
                mVaccine.setText(eventCursor.getVaccineName());
            }

            mMediaPath = eventCursor.getMediaPath();
            new BabyDiaryImage().loadImageOnImageView(eventCursor.getMediaPath(),mImage);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
