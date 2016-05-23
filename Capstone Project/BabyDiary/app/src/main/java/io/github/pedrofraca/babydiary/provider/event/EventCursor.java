package io.github.pedrofraca.babydiary.provider.event;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Date;

import io.github.pedrofraca.babydiary.provider.baby.BabyColumns;
import io.github.pedrofraca.babydiary.provider.baby.Gender;
import io.github.pedrofraca.babydiary.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code event} table.
 */
public class EventCursor extends AbstractCursor implements EventModel {
    public EventCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(EventColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Event title
     * Can be {@code null}.
     */
    @Nullable
    public String getTitle() {
        String res = getStringOrNull(EventColumns.TITLE);
        return res;
    }

    /**
     * Event Description
     * Can be {@code null}.
     */
    @Nullable
    public String getDescription() {
        String res = getStringOrNull(EventColumns.DESCRIPTION);
        return res;
    }

    /**
     * Latitude
     * Can be {@code null}.
     */
    @Nullable
    public Double getLatitude() {
        Double res = getDoubleOrNull(EventColumns.LATITUDE);
        return res;
    }

    /**
     * Longitude
     * Can be {@code null}.
     */
    @Nullable
    public Double getLongitude() {
        Double res = getDoubleOrNull(EventColumns.LONGITUDE);
        return res;
    }

    /**
     * Media Path
     * Can be {@code null}.
     */
    @Nullable
    public String getMediaPath() {
        String res = getStringOrNull(EventColumns.MEDIA_PATH);
        return res;
    }

    /**
     * Height
     * Can be {@code null}.
     */
    @Nullable
    public Double getHeight() {
        Double res = getDoubleOrNull(EventColumns.HEIGHT);
        return res;
    }

    /**
     * Weight
     * Can be {@code null}.
     */
    @Nullable
    public Double getWeight() {
        Double res = getDoubleOrNull(EventColumns.WEIGHT);
        return res;
    }

    /**
     * Vaccine Name
     * Can be {@code null}.
     */
    @Nullable
    public String getVaccineName() {
        String res = getStringOrNull(EventColumns.VACCINE_NAME);
        return res;
    }

    /**
     * Vaccine Description
     * Can be {@code null}.
     */
    @Nullable
    public String getVaccineDescription() {
        String res = getStringOrNull(EventColumns.VACCINE_DESCRIPTION);
        return res;
    }

    /**
     * Get the {@code date} value.
     * Can be {@code null}.
     */
    @Nullable
    public Date getDate() {
        Date res = getDateOrNull(EventColumns.DATE);
        return res;
    }

    /**
     * Get the {@code type} value.
     * Cannot be {@code null}.
     */
    @NonNull
    public EventType getType() {
        Integer intValue = getIntegerOrNull(EventColumns.TYPE);
        if (intValue == null)
            throw new NullPointerException("The value of 'type' in the database was null, which is not allowed according to the model definition");
        return EventType.values()[intValue];
    }

    /**
     * Get the {@code baby_id} value.
     */
    public long getBabyId() {
        Long res = getLongOrNull(EventColumns.BABY_ID);
        if (res == null)
            throw new NullPointerException("The value of 'baby_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Name of the baby.
     * Can be {@code null}.
     */
    @Nullable
    public String getBabyName() {
        String res = getStringOrNull(BabyColumns.NAME);
        return res;
    }

    /**
     * Get the {@code date_of_birth} value.
     * Can be {@code null}.
     */
    @Nullable
    public Date getBabyDateOfBirth() {
        Date res = getDateOrNull(BabyColumns.DATE_OF_BIRTH);
        return res;
    }

    /**
     * Get the {@code gender} value.
     * Cannot be {@code null}.
     */
    @NonNull
    public Gender getBabyGender() {
        Integer intValue = getIntegerOrNull(BabyColumns.GENDER);
        if (intValue == null)
            throw new NullPointerException("The value of 'gender' in the database was null, which is not allowed according to the model definition");
        return Gender.values()[intValue];
    }
}
