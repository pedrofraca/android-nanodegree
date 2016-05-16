package io.github.pedrofraca.babydiary.provider.baby;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.github.pedrofraca.babydiary.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code baby} table.
 */
public class BabyContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return BabyColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable BabySelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable BabySelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Name of the baby.
     */
    public BabyContentValues putName(@Nullable String value) {
        mContentValues.put(BabyColumns.NAME, value);
        return this;
    }

    public BabyContentValues putNameNull() {
        mContentValues.putNull(BabyColumns.NAME);
        return this;
    }

    public BabyContentValues putDateOfBirth(@Nullable Date value) {
        mContentValues.put(BabyColumns.DATE_OF_BIRTH, value == null ? null : value.getTime());
        return this;
    }

    public BabyContentValues putDateOfBirthNull() {
        mContentValues.putNull(BabyColumns.DATE_OF_BIRTH);
        return this;
    }

    public BabyContentValues putDateOfBirth(@Nullable Long value) {
        mContentValues.put(BabyColumns.DATE_OF_BIRTH, value);
        return this;
    }

    public BabyContentValues putGender(@NonNull Gender value) {
        if (value == null) throw new IllegalArgumentException("gender must not be null");
        mContentValues.put(BabyColumns.GENDER, value.ordinal());
        return this;
    }

}
