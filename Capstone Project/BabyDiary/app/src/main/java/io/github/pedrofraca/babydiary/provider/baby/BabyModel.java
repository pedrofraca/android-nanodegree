package io.github.pedrofraca.babydiary.provider.baby;

import io.github.pedrofraca.babydiary.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Entity to store babies.
 */
public interface BabyModel extends BaseModel {

    /**
     * Name of the baby.
     * Can be {@code null}.
     */
    @Nullable
    String getName();

    /**
     * Get the {@code date_of_birth} value.
     * Can be {@code null}.
     */
    @Nullable
    Date getDateOfBirth();

    /**
     * Get the {@code gender} value.
     * Cannot be {@code null}.
     */
    @NonNull
    Gender getGender();
}
