package io.github.pedrofraca.babydiary.provider.event;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import io.github.pedrofraca.babydiary.provider.base.AbstractSelection;
import io.github.pedrofraca.babydiary.provider.baby.*;

/**
 * Selection for the {@code event} table.
 */
public class EventSelection extends AbstractSelection<EventSelection> {
    @Override
    protected Uri baseUri() {
        return EventColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code EventCursor} object, which is positioned before the first entry, or null.
     */
    public EventCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new EventCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public EventCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code EventCursor} object, which is positioned before the first entry, or null.
     */
    public EventCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new EventCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public EventCursor query(Context context) {
        return query(context, null);
    }


    public EventSelection id(long... value) {
        addEquals("event." + EventColumns._ID, toObjectArray(value));
        return this;
    }

    public EventSelection idNot(long... value) {
        addNotEquals("event." + EventColumns._ID, toObjectArray(value));
        return this;
    }

    public EventSelection orderById(boolean desc) {
        orderBy("event." + EventColumns._ID, desc);
        return this;
    }

    public EventSelection orderById() {
        return orderById(false);
    }

    public EventSelection title(String... value) {
        addEquals(EventColumns.TITLE, value);
        return this;
    }

    public EventSelection titleNot(String... value) {
        addNotEquals(EventColumns.TITLE, value);
        return this;
    }

    public EventSelection titleLike(String... value) {
        addLike(EventColumns.TITLE, value);
        return this;
    }

    public EventSelection titleContains(String... value) {
        addContains(EventColumns.TITLE, value);
        return this;
    }

    public EventSelection titleStartsWith(String... value) {
        addStartsWith(EventColumns.TITLE, value);
        return this;
    }

    public EventSelection titleEndsWith(String... value) {
        addEndsWith(EventColumns.TITLE, value);
        return this;
    }

    public EventSelection orderByTitle(boolean desc) {
        orderBy(EventColumns.TITLE, desc);
        return this;
    }

    public EventSelection orderByTitle() {
        orderBy(EventColumns.TITLE, false);
        return this;
    }

    public EventSelection description(String... value) {
        addEquals(EventColumns.DESCRIPTION, value);
        return this;
    }

    public EventSelection descriptionNot(String... value) {
        addNotEquals(EventColumns.DESCRIPTION, value);
        return this;
    }

    public EventSelection descriptionLike(String... value) {
        addLike(EventColumns.DESCRIPTION, value);
        return this;
    }

    public EventSelection descriptionContains(String... value) {
        addContains(EventColumns.DESCRIPTION, value);
        return this;
    }

    public EventSelection descriptionStartsWith(String... value) {
        addStartsWith(EventColumns.DESCRIPTION, value);
        return this;
    }

    public EventSelection descriptionEndsWith(String... value) {
        addEndsWith(EventColumns.DESCRIPTION, value);
        return this;
    }

    public EventSelection orderByDescription(boolean desc) {
        orderBy(EventColumns.DESCRIPTION, desc);
        return this;
    }

    public EventSelection orderByDescription() {
        orderBy(EventColumns.DESCRIPTION, false);
        return this;
    }

    public EventSelection latitude(Double... value) {
        addEquals(EventColumns.LATITUDE, value);
        return this;
    }

    public EventSelection latitudeNot(Double... value) {
        addNotEquals(EventColumns.LATITUDE, value);
        return this;
    }

    public EventSelection latitudeGt(double value) {
        addGreaterThan(EventColumns.LATITUDE, value);
        return this;
    }

    public EventSelection latitudeGtEq(double value) {
        addGreaterThanOrEquals(EventColumns.LATITUDE, value);
        return this;
    }

    public EventSelection latitudeLt(double value) {
        addLessThan(EventColumns.LATITUDE, value);
        return this;
    }

    public EventSelection latitudeLtEq(double value) {
        addLessThanOrEquals(EventColumns.LATITUDE, value);
        return this;
    }

    public EventSelection orderByLatitude(boolean desc) {
        orderBy(EventColumns.LATITUDE, desc);
        return this;
    }

    public EventSelection orderByLatitude() {
        orderBy(EventColumns.LATITUDE, false);
        return this;
    }

    public EventSelection longitude(Double... value) {
        addEquals(EventColumns.LONGITUDE, value);
        return this;
    }

    public EventSelection longitudeNot(Double... value) {
        addNotEquals(EventColumns.LONGITUDE, value);
        return this;
    }

    public EventSelection longitudeGt(double value) {
        addGreaterThan(EventColumns.LONGITUDE, value);
        return this;
    }

    public EventSelection longitudeGtEq(double value) {
        addGreaterThanOrEquals(EventColumns.LONGITUDE, value);
        return this;
    }

    public EventSelection longitudeLt(double value) {
        addLessThan(EventColumns.LONGITUDE, value);
        return this;
    }

    public EventSelection longitudeLtEq(double value) {
        addLessThanOrEquals(EventColumns.LONGITUDE, value);
        return this;
    }

    public EventSelection orderByLongitude(boolean desc) {
        orderBy(EventColumns.LONGITUDE, desc);
        return this;
    }

    public EventSelection orderByLongitude() {
        orderBy(EventColumns.LONGITUDE, false);
        return this;
    }

    public EventSelection mediaPath(String... value) {
        addEquals(EventColumns.MEDIA_PATH, value);
        return this;
    }

    public EventSelection mediaPathNot(String... value) {
        addNotEquals(EventColumns.MEDIA_PATH, value);
        return this;
    }

    public EventSelection mediaPathLike(String... value) {
        addLike(EventColumns.MEDIA_PATH, value);
        return this;
    }

    public EventSelection mediaPathContains(String... value) {
        addContains(EventColumns.MEDIA_PATH, value);
        return this;
    }

    public EventSelection mediaPathStartsWith(String... value) {
        addStartsWith(EventColumns.MEDIA_PATH, value);
        return this;
    }

    public EventSelection mediaPathEndsWith(String... value) {
        addEndsWith(EventColumns.MEDIA_PATH, value);
        return this;
    }

    public EventSelection orderByMediaPath(boolean desc) {
        orderBy(EventColumns.MEDIA_PATH, desc);
        return this;
    }

    public EventSelection orderByMediaPath() {
        orderBy(EventColumns.MEDIA_PATH, false);
        return this;
    }

    public EventSelection height(Double... value) {
        addEquals(EventColumns.HEIGHT, value);
        return this;
    }

    public EventSelection heightNot(Double... value) {
        addNotEquals(EventColumns.HEIGHT, value);
        return this;
    }

    public EventSelection heightGt(double value) {
        addGreaterThan(EventColumns.HEIGHT, value);
        return this;
    }

    public EventSelection heightGtEq(double value) {
        addGreaterThanOrEquals(EventColumns.HEIGHT, value);
        return this;
    }

    public EventSelection heightLt(double value) {
        addLessThan(EventColumns.HEIGHT, value);
        return this;
    }

    public EventSelection heightLtEq(double value) {
        addLessThanOrEquals(EventColumns.HEIGHT, value);
        return this;
    }

    public EventSelection orderByHeight(boolean desc) {
        orderBy(EventColumns.HEIGHT, desc);
        return this;
    }

    public EventSelection orderByHeight() {
        orderBy(EventColumns.HEIGHT, false);
        return this;
    }

    public EventSelection weight(Double... value) {
        addEquals(EventColumns.WEIGHT, value);
        return this;
    }

    public EventSelection weightNot(Double... value) {
        addNotEquals(EventColumns.WEIGHT, value);
        return this;
    }

    public EventSelection weightGt(double value) {
        addGreaterThan(EventColumns.WEIGHT, value);
        return this;
    }

    public EventSelection weightGtEq(double value) {
        addGreaterThanOrEquals(EventColumns.WEIGHT, value);
        return this;
    }

    public EventSelection weightLt(double value) {
        addLessThan(EventColumns.WEIGHT, value);
        return this;
    }

    public EventSelection weightLtEq(double value) {
        addLessThanOrEquals(EventColumns.WEIGHT, value);
        return this;
    }

    public EventSelection orderByWeight(boolean desc) {
        orderBy(EventColumns.WEIGHT, desc);
        return this;
    }

    public EventSelection orderByWeight() {
        orderBy(EventColumns.WEIGHT, false);
        return this;
    }

    public EventSelection vaccineName(String... value) {
        addEquals(EventColumns.VACCINE_NAME, value);
        return this;
    }

    public EventSelection vaccineNameNot(String... value) {
        addNotEquals(EventColumns.VACCINE_NAME, value);
        return this;
    }

    public EventSelection vaccineNameLike(String... value) {
        addLike(EventColumns.VACCINE_NAME, value);
        return this;
    }

    public EventSelection vaccineNameContains(String... value) {
        addContains(EventColumns.VACCINE_NAME, value);
        return this;
    }

    public EventSelection vaccineNameStartsWith(String... value) {
        addStartsWith(EventColumns.VACCINE_NAME, value);
        return this;
    }

    public EventSelection vaccineNameEndsWith(String... value) {
        addEndsWith(EventColumns.VACCINE_NAME, value);
        return this;
    }

    public EventSelection orderByVaccineName(boolean desc) {
        orderBy(EventColumns.VACCINE_NAME, desc);
        return this;
    }

    public EventSelection orderByVaccineName() {
        orderBy(EventColumns.VACCINE_NAME, false);
        return this;
    }

    public EventSelection vaccineDescription(String... value) {
        addEquals(EventColumns.VACCINE_DESCRIPTION, value);
        return this;
    }

    public EventSelection vaccineDescriptionNot(String... value) {
        addNotEquals(EventColumns.VACCINE_DESCRIPTION, value);
        return this;
    }

    public EventSelection vaccineDescriptionLike(String... value) {
        addLike(EventColumns.VACCINE_DESCRIPTION, value);
        return this;
    }

    public EventSelection vaccineDescriptionContains(String... value) {
        addContains(EventColumns.VACCINE_DESCRIPTION, value);
        return this;
    }

    public EventSelection vaccineDescriptionStartsWith(String... value) {
        addStartsWith(EventColumns.VACCINE_DESCRIPTION, value);
        return this;
    }

    public EventSelection vaccineDescriptionEndsWith(String... value) {
        addEndsWith(EventColumns.VACCINE_DESCRIPTION, value);
        return this;
    }

    public EventSelection orderByVaccineDescription(boolean desc) {
        orderBy(EventColumns.VACCINE_DESCRIPTION, desc);
        return this;
    }

    public EventSelection orderByVaccineDescription() {
        orderBy(EventColumns.VACCINE_DESCRIPTION, false);
        return this;
    }

    public EventSelection date(Date... value) {
        addEquals(EventColumns.DATE, value);
        return this;
    }

    public EventSelection dateNot(Date... value) {
        addNotEquals(EventColumns.DATE, value);
        return this;
    }

    public EventSelection date(Long... value) {
        addEquals(EventColumns.DATE, value);
        return this;
    }

    public EventSelection dateAfter(Date value) {
        addGreaterThan(EventColumns.DATE, value);
        return this;
    }

    public EventSelection dateAfterEq(Date value) {
        addGreaterThanOrEquals(EventColumns.DATE, value);
        return this;
    }

    public EventSelection dateBefore(Date value) {
        addLessThan(EventColumns.DATE, value);
        return this;
    }

    public EventSelection dateBeforeEq(Date value) {
        addLessThanOrEquals(EventColumns.DATE, value);
        return this;
    }

    public EventSelection orderByDate(boolean desc) {
        orderBy(EventColumns.DATE, desc);
        return this;
    }

    public EventSelection orderByDate() {
        orderBy(EventColumns.DATE, false);
        return this;
    }

    public EventSelection type(EventType... value) {
        addEquals(EventColumns.TYPE, value);
        return this;
    }

    public EventSelection typeNot(EventType... value) {
        addNotEquals(EventColumns.TYPE, value);
        return this;
    }


    public EventSelection orderByType(boolean desc) {
        orderBy(EventColumns.TYPE, desc);
        return this;
    }

    public EventSelection orderByType() {
        orderBy(EventColumns.TYPE, false);
        return this;
    }

    public EventSelection babyId(long... value) {
        addEquals(EventColumns.BABY_ID, toObjectArray(value));
        return this;
    }

    public EventSelection babyIdNot(long... value) {
        addNotEquals(EventColumns.BABY_ID, toObjectArray(value));
        return this;
    }

    public EventSelection babyIdGt(long value) {
        addGreaterThan(EventColumns.BABY_ID, value);
        return this;
    }

    public EventSelection babyIdGtEq(long value) {
        addGreaterThanOrEquals(EventColumns.BABY_ID, value);
        return this;
    }

    public EventSelection babyIdLt(long value) {
        addLessThan(EventColumns.BABY_ID, value);
        return this;
    }

    public EventSelection babyIdLtEq(long value) {
        addLessThanOrEquals(EventColumns.BABY_ID, value);
        return this;
    }

    public EventSelection orderByBabyId(boolean desc) {
        orderBy(EventColumns.BABY_ID, desc);
        return this;
    }

    public EventSelection orderByBabyId() {
        orderBy(EventColumns.BABY_ID, false);
        return this;
    }

    public EventSelection babyName(String... value) {
        addEquals(BabyColumns.NAME, value);
        return this;
    }

    public EventSelection babyNameNot(String... value) {
        addNotEquals(BabyColumns.NAME, value);
        return this;
    }

    public EventSelection babyNameLike(String... value) {
        addLike(BabyColumns.NAME, value);
        return this;
    }

    public EventSelection babyNameContains(String... value) {
        addContains(BabyColumns.NAME, value);
        return this;
    }

    public EventSelection babyNameStartsWith(String... value) {
        addStartsWith(BabyColumns.NAME, value);
        return this;
    }

    public EventSelection babyNameEndsWith(String... value) {
        addEndsWith(BabyColumns.NAME, value);
        return this;
    }

    public EventSelection orderByBabyName(boolean desc) {
        orderBy(BabyColumns.NAME, desc);
        return this;
    }

    public EventSelection orderByBabyName() {
        orderBy(BabyColumns.NAME, false);
        return this;
    }

    public EventSelection babyDateOfBirth(Date... value) {
        addEquals(BabyColumns.DATE_OF_BIRTH, value);
        return this;
    }

    public EventSelection babyDateOfBirthNot(Date... value) {
        addNotEquals(BabyColumns.DATE_OF_BIRTH, value);
        return this;
    }

    public EventSelection babyDateOfBirth(Long... value) {
        addEquals(BabyColumns.DATE_OF_BIRTH, value);
        return this;
    }

    public EventSelection babyDateOfBirthAfter(Date value) {
        addGreaterThan(BabyColumns.DATE_OF_BIRTH, value);
        return this;
    }

    public EventSelection babyDateOfBirthAfterEq(Date value) {
        addGreaterThanOrEquals(BabyColumns.DATE_OF_BIRTH, value);
        return this;
    }

    public EventSelection babyDateOfBirthBefore(Date value) {
        addLessThan(BabyColumns.DATE_OF_BIRTH, value);
        return this;
    }

    public EventSelection babyDateOfBirthBeforeEq(Date value) {
        addLessThanOrEquals(BabyColumns.DATE_OF_BIRTH, value);
        return this;
    }

    public EventSelection orderByBabyDateOfBirth(boolean desc) {
        orderBy(BabyColumns.DATE_OF_BIRTH, desc);
        return this;
    }

    public EventSelection orderByBabyDateOfBirth() {
        orderBy(BabyColumns.DATE_OF_BIRTH, false);
        return this;
    }

    public EventSelection babyGender(Gender... value) {
        addEquals(BabyColumns.GENDER, value);
        return this;
    }

    public EventSelection babyGenderNot(Gender... value) {
        addNotEquals(BabyColumns.GENDER, value);
        return this;
    }


    public EventSelection orderByBabyGender(boolean desc) {
        orderBy(BabyColumns.GENDER, desc);
        return this;
    }

    public EventSelection orderByBabyGender() {
        orderBy(BabyColumns.GENDER, false);
        return this;
    }
}
