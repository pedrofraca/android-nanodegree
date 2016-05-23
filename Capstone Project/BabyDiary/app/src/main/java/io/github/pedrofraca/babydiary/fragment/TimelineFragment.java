package io.github.pedrofraca.babydiary.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import io.github.pedrofraca.babydiary.R;
import io.github.pedrofraca.babydiary.activity.EventDetailActivity;
import io.github.pedrofraca.babydiary.adapter.TimelineAdapter;
import io.github.pedrofraca.babydiary.provider.event.EventColumns;
import io.github.pedrofraca.babydiary.utils.BabyUtils;

/**
 * Created by pedrofraca on 19/05/16.
 */
public class TimelineFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>,TimelineAdapter.OnEventClickedListener {

    private static final int EVENTS_URL_LOADER = 0;
    private static final String ATT_LOADER_SEARCH_TERM = "search_term";
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("search",newText);
                Bundle loaderBundle = new Bundle();
                loaderBundle.putString(ATT_LOADER_SEARCH_TERM,newText);
                getActivity().getSupportLoaderManager().restartLoader(EVENTS_URL_LOADER,loaderBundle,TimelineFragment.this);
                //doSearch(newText);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if(args!=null){
            String searchTerm = args.getString(ATT_LOADER_SEARCH_TERM);
            if (!searchTerm.isEmpty()){
                return new CursorLoader(
                        getActivity(),
                        EventColumns.CONTENT_URI,
                        null,
                        EventColumns.BABY_ID + "=? and "+ EventColumns.DESCRIPTION +" like ?",
                        new String[]{String.valueOf(BabyUtils.getCurrentActiveBabyId(getActivity())),searchTerm+"%"},
                        null);
            }
        }
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
        mAdapter = new TimelineAdapter(data,TimelineFragment.this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onEventClicked(long id) {
        startActivity(EventDetailActivity.newIntent(getActivity(),id), ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity()).toBundle());
    }
}
