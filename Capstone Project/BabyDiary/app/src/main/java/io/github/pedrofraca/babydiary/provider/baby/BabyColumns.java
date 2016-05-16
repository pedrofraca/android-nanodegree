package io.github.pedrofraca.babydiary.provider.baby;

import android.net.Uri;
import android.provider.BaseColumns;

import io.github.pedrofraca.babydiary.provider.BabyProvider;
import io.github.pedrofraca.babydiary.provider.baby.BabyColumns;

/**
 * Entity to store babies.
 */
public class BabyColumns implements BaseColumns {
    public static final String TABLE_NAME = "baby";
    public static final Uri CONTENT_URI = Uri.parse(BabyProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    /**
     * Name of the baby.
     */
    public static final String NAME = "name";

    public static final String DATE_OF_BIRTH = "date_of_birth";

    public static final String GENDER = "gender";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            NAME,
            DATE_OF_BIRTH,
            GENDER
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(NAME) || c.contains("." + NAME)) return true;
            if (c.equals(DATE_OF_BIRTH) || c.contains("." + DATE_OF_BIRTH)) return true;
            if (c.equals(GENDER) || c.contains("." + GENDER)) return true;
        }
        return false;
    }

}
