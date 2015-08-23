package io.github.pedrofraca.spotifystreamer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import io.github.pedrofraca.spotifystreamer.R;
import io.github.pedrofraca.spotifystreamer.listener.OnSpotifyArtistListener;
import io.github.pedrofraca.spotifystreamer.model.ItemToDraw;

/**
 * Created by pedrofraca on 23/06/15.
 */
public class SpotifyArtistAdapter extends RecyclerView.Adapter<SpotifyArtistAdapter.ViewHolder> {
    private List<ItemToDraw> mDataset;
    private Context mContext;
    private OnSpotifyArtistListener mListener;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mSubTextView;
        public TextView mTextView;
        public ImageView mImageView;
        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.item_layout_title);
            mSubTextView = (TextView) v.findViewById(R.id.item_layout_sub_title);
            mImageView = (ImageView) v.findViewById(R.id.item_layout_image);
        }
    }

    public SpotifyArtistAdapter(List<ItemToDraw> myDataset,
                                Context context,
                                OnSpotifyArtistListener listener) {
        mDataset = myDataset;
        mContext = context;
        mListener = listener;
    }

    @Override
    public SpotifyArtistAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(SpotifyArtistAdapter.ViewHolder holder, final int i) {
        holder.mTextView.setText(mDataset.get(i).getTitle());
        Picasso.with(mContext).load(mDataset.get(i).getImgUrl()).into(holder.mImageView);
        if(mDataset.get(i).getSubtitle()!=null){
            holder.mSubTextView.setVisibility(View.VISIBLE);
            holder.mSubTextView.setText(mDataset.get(i).getSubtitle());
        } else {
            holder.mSubTextView.setVisibility(View.GONE);
        }
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onArtistClickListener(mDataset.get(i));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}