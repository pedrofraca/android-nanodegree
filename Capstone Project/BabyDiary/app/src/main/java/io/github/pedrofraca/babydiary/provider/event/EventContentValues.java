package io.github.pedrofraca.babydiary.provider.event;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.github.pedrofraca.babydiary.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code event} table.
 */
public class EventContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return EventColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable EventSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable EventSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Event title
     */
    public EventContentValues putTitle(@Nullable String value) {
        mContentValues.put(EventColumns.TITLE, value);
        return this;
    }

    public EventContentValues putTitleNull() {
        mContentValues.putNull(EventColumns.TITLE);
        return this;
    }

    /**
     * Event Description
     */
    public EventContentValues putDescription(@Nullable String value) {
        mContentValues.put(EventColumns.DESCRIPTION, value);
        return this;
    }

    public EventContentValues putDescriptionNull() {
        mContentValues.putNull(EventColumns.DESCRIPTION);
        return this;
    }

    /**
     * Event Location
     */
    public EventContentValues putLocation(@Nullable String value) {
        mContentValues.put(EventColumns.LOCATION, value);
        return this;
    }

    public EventContentValues putLocationNull() {
        mContentValues.putNull(EventColumns.LOCATION);
        return this;
    }

    /**
     * Media Path
     */
    public EventContentValues putMediaPath(@Nullable String value) {
        mContentValues.put(EventColumns.MEDIA_PATH, value);
        return this;
    }

    public EventContentValues putMediaPathNull() {
        mContentValues.putNull(EventColumns.MEDIA_PATH);
        return this;
    }

    /**
     * Height
     */
    public EventContentValues putHeight(@Nullable Double value) {
        mContentValues.put(EventColumns.HEIGHT, value);
        return this;
    }

    public EventContentValues putHeightNull() {
        mContentValues.putNull(EventColumns.HEIGHT);
        return this;
    }

    /**
     * Weight
     */
    public EventContentValues putWeight(@Nullable Double value) {
        mContentValues.put(EventColumns.WEIGHT, value);
        return this;
    }

    public EventContentValues putWeightNull() {
        mContentValues.putNull(EventColumns.WEIGHT);
        return this;
    }

    /**
     * Vaccine Name
     */
    public EventContentValues putVaccineName(@Nullable Double value) {
        mContentValues.put(EventColumns.VACCINE_NAME, value);
        return this;
    }

    public EventContentValues putVaccineNameNull() {
        mContentValues.putNull(EventColumns.VACCINE_NAME);
        return this;
    }

    /**
     * Vaccine Description
     */
    public EventContentValues putVaccineDescription(@Nullable Double value) {
        mContentValues.put(EventColumns.VACCINE_DESCRIPTION, value);
        return this;
    }

    public EventContentValues putVaccineDescriptionNull() {
        mContentValues.putNull(EventColumns.VACCINE_DESCRIPTION);
        return this;
    }

    public EventContentValues putDate(@Nullable Date value) {
        mContentValues.put(EventColumns.DATE, value == null ? null : value.getTime());
        return this;
    }

    public EventContentValues putDateNull() {
        mContentValues.putNull(EventColumns.DATE);
        return this;
    }

    public EventContentValues putDate(@Nullable Long value) {
        mContentValues.put(EventColumns.DATE, value);
        return this;
    }

    public EventContentValues putType(@NonNull EventType value) {
        if (value == null) throw new IllegalArgumentException("type must not be null");
        mContentValues.put(EventColumns.TYPE, value.ordinal());
        return this;
    }


    public EventContentValues putBabyId(long value) {
        mContentValues.put(EventColumns.BABY_ID, value);
        return this;
    }

}
