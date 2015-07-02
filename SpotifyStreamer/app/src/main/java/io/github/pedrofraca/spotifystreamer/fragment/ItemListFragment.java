package io.github.pedrofraca.spotifystreamer.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import io.github.pedrofraca.spotifystreamer.R;
import io.github.pedrofraca.spotifystreamer.adapter.SpotifyAdapter;
import io.github.pedrofraca.spotifystreamer.listener.ItemListFragmentInterface;
import io.github.pedrofraca.spotifystreamer.model.ItemToDraw;

/**
 * A placeholder mFragment containing a simple view.
 */
public class ItemListFragment extends Fragment {

    private SpotifyAdapter mAdapter;
    RecyclerView mRecyclerView;
    private TextView mTextViewMessage;
    private ProgressBar mProgressBar;
    private List<ItemToDraw> mDatasetToDraw;
    private String mEmptyMessage;
    private ItemListFragmentInterface mListener;

    public ItemListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_main, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_list_recycler);
        mTextViewMessage = (TextView) rootView.findViewById(R.id.fragment_main_message);
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.fragment_main_progress_bar);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        return rootView;
    }

    public ItemListFragment emtyDatasetMessage(String emptyMessage){
        mEmptyMessage = emptyMessage;
        return this;
    }

    public ItemListFragment dataset(List<ItemToDraw> dataset){
        mDatasetToDraw = dataset;
        return this;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (ItemListFragmentInterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement ItemListFragmentInterface");
        }
    }

    public void refresh(){
        hideProgressBar();
        if(mDatasetToDraw.isEmpty()){
            showMessage(mEmptyMessage);
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
            mTextViewMessage.setVisibility(View.GONE);
            mAdapter = new SpotifyAdapter(mDatasetToDraw,getActivity(),mListener.allowClicksOnItems());
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    public void showMessage(String message){
        hideProgressBar();
        mTextViewMessage.setVisibility(View.VISIBLE);
        mTextViewMessage.setText(message);
    }

    public void showProgressBar(){
        mRecyclerView.setVisibility(View.INVISIBLE);
        mTextViewMessage.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar(){
        mProgressBar.setVisibility(View.GONE);
    }
}
