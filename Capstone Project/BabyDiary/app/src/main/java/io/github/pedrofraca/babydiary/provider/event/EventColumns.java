package io.github.pedrofraca.babydiary.provider.event;

import android.net.Uri;
import android.provider.BaseColumns;

import io.github.pedrofraca.babydiary.provider.BabyDiaryProvider;
import io.github.pedrofraca.babydiary.provider.baby.BabyColumns;
import io.github.pedrofraca.babydiary.provider.event.EventColumns;

/**
 * Entity to store events related with a baby.
 */
public class EventColumns implements BaseColumns {
    public static final String TABLE_NAME = "event";
    public static final Uri CONTENT_URI = Uri.parse(BabyDiaryProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    /**
     * Event title
     */
    public static final String TITLE = "title";

    /**
     * Event Description
     */
    public static final String DESCRIPTION = "description";

    /**
     * Latitude
     */
    public static final String LATITUDE = "latitude";

    /**
     * Longitude
     */
    public static final String LONGITUDE = "longitude";

    /**
     * Media Path
     */
    public static final String MEDIA_PATH = "media_path";

    /**
     * Height
     */
    public static final String HEIGHT = "height";

    /**
     * Weight
     */
    public static final String WEIGHT = "weight";

    /**
     * Vaccine Name
     */
    public static final String VACCINE_NAME = "vaccine_name";

    /**
     * Vaccine Description
     */
    public static final String VACCINE_DESCRIPTION = "vaccine_description";

    public static final String DATE = "date";

    public static final String TYPE = "type";

    public static final String BABY_ID = "baby_id";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            TITLE,
            DESCRIPTION,
            LATITUDE,
            LONGITUDE,
            MEDIA_PATH,
            HEIGHT,
            WEIGHT,
            VACCINE_NAME,
            VACCINE_DESCRIPTION,
            DATE,
            TYPE,
            BABY_ID
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(TITLE) || c.contains("." + TITLE)) return true;
            if (c.equals(DESCRIPTION) || c.contains("." + DESCRIPTION)) return true;
            if (c.equals(LATITUDE) || c.contains("." + LATITUDE)) return true;
            if (c.equals(LONGITUDE) || c.contains("." + LONGITUDE)) return true;
            if (c.equals(MEDIA_PATH) || c.contains("." + MEDIA_PATH)) return true;
            if (c.equals(HEIGHT) || c.contains("." + HEIGHT)) return true;
            if (c.equals(WEIGHT) || c.contains("." + WEIGHT)) return true;
            if (c.equals(VACCINE_NAME) || c.contains("." + VACCINE_NAME)) return true;
            if (c.equals(VACCINE_DESCRIPTION) || c.contains("." + VACCINE_DESCRIPTION)) return true;
            if (c.equals(DATE) || c.contains("." + DATE)) return true;
            if (c.equals(TYPE) || c.contains("." + TYPE)) return true;
            if (c.equals(BABY_ID) || c.contains("." + BABY_ID)) return true;
        }
        return false;
    }

    public static final String PREFIX_BABY = TABLE_NAME + "__" + BabyColumns.TABLE_NAME;
}
