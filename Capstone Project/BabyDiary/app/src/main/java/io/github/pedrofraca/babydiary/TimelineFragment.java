package io.github.pedrofraca.babydiary;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.github.pedrofraca.babydiary.provider.event.EventColumns;

/**
 * Created by pedrofraca on 19/05/16.
 */
public class TimelineFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int EVENTS_URL_LOADER = 0;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private TimelineAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_timeline, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        getActivity().getSupportLoaderManager().initLoader(EVENTS_URL_LOADER, null, this);
        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                getActivity(),
                EventColumns.CONTENT_URI,
                null,
                EventColumns.BABY_ID + "=?",
                new String[]{String.valueOf(BabyUtils.getCurrentActiveBabyId(getActivity()))},
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // specify an adapter (see also next example)
        mAdapter = new TimelineAdapter(data);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
