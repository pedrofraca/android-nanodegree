package io.github.pedrofraca.babydiary.provider.event;

import io.github.pedrofraca.babydiary.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Entity to store events related with a baby.
 */
public interface EventModel extends BaseModel {

    /**
     * Event title
     * Can be {@code null}.
     */
    @Nullable
    String getTitle();

    /**
     * Event Description
     * Can be {@code null}.
     */
    @Nullable
    String getDescription();

    /**
     * Latitude
     * Can be {@code null}.
     */
    @Nullable
    Double getLatitude();

    /**
     * Longitude
     * Can be {@code null}.
     */
    @Nullable
    Double getLongitude();

    /**
     * Media Path
     * Can be {@code null}.
     */
    @Nullable
    String getMediaPath();

    /**
     * Height
     * Can be {@code null}.
     */
    @Nullable
    Double getHeight();

    /**
     * Weight
     * Can be {@code null}.
     */
    @Nullable
    Double getWeight();

    /**
     * Vaccine Name
     * Can be {@code null}.
     */
    @Nullable
    String getVaccineName();

    /**
     * Vaccine Description
     * Can be {@code null}.
     */
    @Nullable
    String getVaccineDescription();

    /**
     * Get the {@code date} value.
     * Can be {@code null}.
     */
    @Nullable
    Date getDate();

    /**
     * Get the {@code type} value.
     * Cannot be {@code null}.
     */
    @NonNull
    EventType getType();

    /**
     * Get the {@code baby_id} value.
     */
    long getBabyId();
}
