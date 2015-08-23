package io.github.pedrofraca.spotifystreamer.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import io.github.pedrofraca.spotifystreamer.R;

/**
 * A placeholder mArtistListFragment containing a simple view.
 */
public class ItemListFragment extends Fragment {

    private RecyclerView.Adapter mAdapter;
    RecyclerView mRecyclerView;
    private TextView mTextViewMessage;
    private ProgressBar mProgressBar;
    private String mEmptyMessage;

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

    public void refresh(){
        hideProgressBar();
        if(mAdapter.getItemCount()==0){
            showMessage(mEmptyMessage);
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
            mTextViewMessage.setVisibility(View.GONE);
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
        if(mProgressBar==null) return;
        mProgressBar.setVisibility(View.GONE);
    }

    public ItemListFragment setAdapter(RecyclerView.Adapter adapter){
        mAdapter = adapter;
        return this;
    }
}
