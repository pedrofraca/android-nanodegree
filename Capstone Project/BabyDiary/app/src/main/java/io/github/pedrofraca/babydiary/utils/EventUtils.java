package io.github.pedrofraca.babydiary.utils;

import io.github.pedrofraca.babydiary.R;
import io.github.pedrofraca.babydiary.provider.event.EventType;

/**
 * Created by pedrofraca on 23/05/16.
 */
public class EventUtils {

    public static int getIconForEvent(EventType event){
        if(event == EventType.PHOTO){
            return R.drawable.ic_photo_camera_white_24dp;
        } else if(event == EventType.MEASURE){
            return R.drawable.ic_change_history_white_24dp;
        } else if(event == EventType.VACCINE){
            return R.drawable.ic_local_hospital_white_24dp;
        }
        return -1;
    }
}
