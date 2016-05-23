package io.github.pedrofraca.babydiary.adapter;

import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import io.github.pedrofraca.babydiary.R;
import io.github.pedrofraca.babydiary.provider.event.EventCursor;
import io.github.pedrofraca.babydiary.utils.BabyDiaryImage;
import io.github.pedrofraca.babydiary.utils.EventUtils;

/**
 * Created by pedrofraca on 19/05/16.
 */
public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.ViewHolder> {

    private EventCursor mData;
    private OnEventClickedListener mListener;

    public TimelineAdapter(Cursor data, OnEventClickedListener listener) {
        mListener = listener;
        mData = new EventCursor(data);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_timeline, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        mData.moveToPosition(position);
        if(mData.getMediaPath()!=null && !mData.getMediaPath().isEmpty()){
            new BabyDiaryImage().loadImageOnImageView(mData.getMediaPath(),
                    holder.mEventImageView);
        }
        holder.mEventTitle.setText(mData.getTitle());
        holder.mEventDescription.setText(mData.getDescription());
        holder.mEventIcon.setImageResource(EventUtils.getIconForEvent(mData.getType()));
        holder.mEventCardView.setTag(mData.getId());
        holder.mEventCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onEventClicked((Long) view.getTag());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.getCount();
    }


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mEventImageView;
        ImageView mEventIcon;
        TextView mEventTitle;
        TextView mEventDescription;
        CardView mEventCardView;
        public ViewHolder(View v) {
            super(v);
            mEventDescription = (TextView) v.findViewById(R.id.event_description);
            mEventIcon = (ImageView) v.findViewById(R.id.event_icon);
            mEventImageView = (ImageView) v.findViewById(R.id.event_image);
            mEventTitle = (TextView) v.findViewById(R.id.event_title);
            mEventCardView = (CardView) v.findViewById(R.id.event_card_view);
        }
    }

    public interface OnEventClickedListener {
        void onEventClicked(long id);
    }
}
