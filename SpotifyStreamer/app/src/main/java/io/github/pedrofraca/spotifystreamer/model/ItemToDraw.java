package io.github.pedrofraca.spotifystreamer.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class to model a item to draw an a list view. It will be used to show the artists as to show
 * the songs.
 */
public class ItemToDraw implements Parcelable {

    private String mTitle;
    private String mImgUrl;
    private String mId;
    private String mSubtitle = null;

    public ItemToDraw(String title,String imgUrl,String id){
        mTitle = title;
        mImgUrl = imgUrl;
        mId=id;
    }

    public ItemToDraw(String title,String imgUrl,String id,String subtitle){
        mTitle = title;
        mImgUrl = imgUrl;
        mId=id;
        mSubtitle=subtitle;
    }

    public String getTitle(){
        return mTitle;
    }

    public String getId(){
        return mId;
    }

    public String getSubtitle(){
        return mSubtitle;
    }

    public String getImgUrl(){
        return mImgUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(mTitle);
        out.writeString(mImgUrl);
    }

    public static final Parcelable.Creator<ItemToDraw> CREATOR
            = new Parcelable.Creator<ItemToDraw>() {
        public ItemToDraw createFromParcel(Parcel in) {
            return new ItemToDraw(in);
        }

        public ItemToDraw[] newArray(int size) {
            return new ItemToDraw[size];
        }
    };

    private ItemToDraw(Parcel in) {
        mTitle=in.readString();
        mImgUrl=in.readString();
    }
}
