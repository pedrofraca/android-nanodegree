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
import io.github.pedrofraca.spotifystreamer.activity.TopSongsActivity;
import io.github.pedrofraca.spotifystreamer.model.ItemToDraw;

/**
 * Created by pedrofraca on 23/06/15.
 */
public class SpotifyAdapter extends RecyclerView.Adapter<SpotifyAdapter.ViewHolder> {
    private List<ItemToDraw> mDataset;
    private Context mContext;
    private boolean mClickable;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
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

    public SpotifyAdapter(List<ItemToDraw> myDataset,Context context,boolean clickable) {
        mDataset = myDataset;
        mContext = context;
        mClickable = clickable;
    }

    @Override
    public SpotifyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(SpotifyAdapter.ViewHolder holder, final int i) {
        holder.mTextView.setText(mDataset.get(i).getTitle());
        Picasso.with(mContext).load(mDataset.get(i).getImgUrl()).into(holder.mImageView);
        if(mDataset.get(i).getSubtitle()!=null){
            holder.mSubTextView.setVisibility(View.VISIBLE);
            holder.mSubTextView.setText(mDataset.get(i).getSubtitle());
        } else {
            holder.mSubTextView.setVisibility(View.GONE);
        }
        if(mClickable) {
            holder.mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TopSongsActivity.launch(mContext,
                            mDataset.get(i).getTitle().toString(),
                            mDataset.get(i).getId());
                }
            });
        }
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}