package io.github.pedrofraca.babydiary.provider.baby;

import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.github.pedrofraca.babydiary.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code baby} table.
 */
public class BabyCursor extends AbstractCursor implements BabyModel {
    public BabyCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(BabyColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Name of the baby.
     * Can be {@code null}.
     */
    @Nullable
    public String getName() {
        String res = getStringOrNull(BabyColumns.NAME);
        return res;
    }

    /**
     * Get the {@code date_of_birth} value.
     * Can be {@code null}.
     */
    @Nullable
    public Date getDateOfBirth() {
        Date res = getDateOrNull(BabyColumns.DATE_OF_BIRTH);
        return res;
    }

    /**
     * Get the {@code gender} value.
     * Cannot be {@code null}.
     */
    @NonNull
    public Gender getGender() {
        Integer intValue = getIntegerOrNull(BabyColumns.GENDER);
        if (intValue == null)
            throw new NullPointerException("The value of 'gender' in the database was null, which is not allowed according to the model definition");
        return Gender.values()[intValue];
    }
}
