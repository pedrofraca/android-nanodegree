package io.github.pedrofraca.babydiary.utils;

import android.content.Context;

import io.github.pedrofraca.babydiary.provider.baby.BabyCursor;
import io.github.pedrofraca.babydiary.provider.baby.BabySelection;

/**
 * Created by pedrofraca on 22/05/16.
 */
public class BabyUtils {

    public static long getCurrentActiveBabyId(Context context){
        BabyCursor allBabies = new BabySelection().query(context);
        if (allBabies != null && allBabies.moveToFirst()) {
            return allBabies.getId();
        }
        return -1;
    }

    public static String getCurrentActiveBabyName(Context context){
        BabyCursor allBabies = new BabySelection().query(context);
        if (allBabies != null && allBabies.moveToFirst()) {
            return allBabies.getName();
        }
        return "";
    }
}
