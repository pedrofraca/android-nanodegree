package io.github.pedrofraca.spotifystreamer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import io.github.pedrofraca.spotifystreamer.R;
import io.github.pedrofraca.spotifystreamer.listener.OnSpotifySongListener;
import io.github.pedrofraca.spotifystreamer.model.ItemToDraw;

public class SpotifySongAdapter extends RecyclerView.Adapter<SpotifySongAdapter.ViewHolder> {

    private ArrayList<ItemToDraw> mDataset;
    private Context mContext;
    private OnSpotifySongListener mListener;
    private String mArtistName;

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

    public SpotifySongAdapter(ArrayList<ItemToDraw> myDataset,
                              Context context,
                              OnSpotifySongListener listener, String title) {
        mDataset = myDataset;
        mContext = context;
        mListener = listener;
        mArtistName = title;
    }

    @Override
    public SpotifySongAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.song_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(SpotifySongAdapter.ViewHolder holder, final int i) {
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
                mListener.onSongClicked(mDataset,mArtistName,i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}