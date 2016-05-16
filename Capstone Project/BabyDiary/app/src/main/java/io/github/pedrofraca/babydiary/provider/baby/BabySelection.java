package io.github.pedrofraca.babydiary.provider.baby;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import io.github.pedrofraca.babydiary.provider.base.AbstractSelection;

/**
 * Selection for the {@code baby} table.
 */
public class BabySelection extends AbstractSelection<BabySelection> {
    @Override
    protected Uri baseUri() {
        return BabyColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code BabyCursor} object, which is positioned before the first entry, or null.
     */
    public BabyCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new BabyCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public BabyCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code BabyCursor} object, which is positioned before the first entry, or null.
     */
    public BabyCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new BabyCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public BabyCursor query(Context context) {
        return query(context, null);
    }


    public BabySelection id(long... value) {
        addEquals("baby." + BabyColumns._ID, toObjectArray(value));
        return this;
    }

    public BabySelection idNot(long... value) {
        addNotEquals("baby." + BabyColumns._ID, toObjectArray(value));
        return this;
    }

    public BabySelection orderById(boolean desc) {
        orderBy("baby." + BabyColumns._ID, desc);
        return this;
    }

    public BabySelection orderById() {
        return orderById(false);
    }

    public BabySelection name(String... value) {
        addEquals(BabyColumns.NAME, value);
        return this;
    }

    public BabySelection nameNot(String... value) {
        addNotEquals(BabyColumns.NAME, value);
        return this;
    }

    public BabySelection nameLike(String... value) {
        addLike(BabyColumns.NAME, value);
        return this;
    }

    public BabySelection nameContains(String... value) {
        addContains(BabyColumns.NAME, value);
        return this;
    }

    public BabySelection nameStartsWith(String... value) {
        addStartsWith(BabyColumns.NAME, value);
        return this;
    }

    public BabySelection nameEndsWith(String... value) {
        addEndsWith(BabyColumns.NAME, value);
        return this;
    }

    public BabySelection orderByName(boolean desc) {
        orderBy(BabyColumns.NAME, desc);
        return this;
    }

    public BabySelection orderByName() {
        orderBy(BabyColumns.NAME, false);
        return this;
    }

    public BabySelection dateOfBirth(Date... value) {
        addEquals(BabyColumns.DATE_OF_BIRTH, value);
        return this;
    }

    public BabySelection dateOfBirthNot(Date... value) {
        addNotEquals(BabyColumns.DATE_OF_BIRTH, value);
        return this;
    }

    public BabySelection dateOfBirth(Long... value) {
        addEquals(BabyColumns.DATE_OF_BIRTH, value);
        return this;
    }

    public BabySelection dateOfBirthAfter(Date value) {
        addGreaterThan(BabyColumns.DATE_OF_BIRTH, value);
        return this;
    }

    public BabySelection dateOfBirthAfterEq(Date value) {
        addGreaterThanOrEquals(BabyColumns.DATE_OF_BIRTH, value);
        return this;
    }

    public BabySelection dateOfBirthBefore(Date value) {
        addLessThan(BabyColumns.DATE_OF_BIRTH, value);
        return this;
    }

    public BabySelection dateOfBirthBeforeEq(Date value) {
        addLessThanOrEquals(BabyColumns.DATE_OF_BIRTH, value);
        return this;
    }

    public BabySelection orderByDateOfBirth(boolean desc) {
        orderBy(BabyColumns.DATE_OF_BIRTH, desc);
        return this;
    }

    public BabySelection orderByDateOfBirth() {
        orderBy(BabyColumns.DATE_OF_BIRTH, false);
        return this;
    }

    public BabySelection gender(Gender... value) {
        addEquals(BabyColumns.GENDER, value);
        return this;
    }

    public BabySelection genderNot(Gender... value) {
        addNotEquals(BabyColumns.GENDER, value);
        return this;
    }


    public BabySelection orderByGender(boolean desc) {
        orderBy(BabyColumns.GENDER, desc);
        return this;
    }

    public BabySelection orderByGender() {
        orderBy(BabyColumns.GENDER, false);
        return this;
    }
}
