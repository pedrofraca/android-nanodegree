package io.github.pedrofraca.babydiary.provider;

import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import java.util.Arrays;

import io.github.pedrofraca.babydiary.BuildConfig;
import io.github.pedrofraca.babydiary.provider.baby.BabyColumns;
import io.github.pedrofraca.babydiary.provider.base.BaseContentProvider;
import io.github.pedrofraca.babydiary.provider.event.EventColumns;

public class BabyDiaryProvider extends BaseContentProvider {
    private static final String TAG = BabyDiaryProvider.class.getSimpleName();

    private static final boolean DEBUG = BuildConfig.DEBUG;

    private static final String TYPE_CURSOR_ITEM = "vnd.android.cursor.item/";
    private static final String TYPE_CURSOR_DIR = "vnd.android.cursor.dir/";

    public static final String AUTHORITY = "io.github.pedrofraca.babydiary.provider";
    public static final String CONTENT_URI_BASE = "content://" + AUTHORITY;

    private static final int URI_TYPE_BABY = 0;
    private static final int URI_TYPE_BABY_ID = 1;

    private static final int URI_TYPE_EVENT = 2;
    private static final int URI_TYPE_EVENT_ID = 3;



    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTHORITY, BabyColumns.TABLE_NAME, URI_TYPE_BABY);
        URI_MATCHER.addURI(AUTHORITY, BabyColumns.TABLE_NAME + "/#", URI_TYPE_BABY_ID);
        URI_MATCHER.addURI(AUTHORITY, EventColumns.TABLE_NAME, URI_TYPE_EVENT);
        URI_MATCHER.addURI(AUTHORITY, EventColumns.TABLE_NAME + "/#", URI_TYPE_EVENT_ID);
    }

    @Override
    protected SQLiteOpenHelper createSqLiteOpenHelper() {
        return BabyDiarySQLiteOpenHelper.getInstance(getContext());
    }

    @Override
    protected boolean hasDebug() {
        return DEBUG;
    }

    @Override
    public String getType(Uri uri) {
        int match = URI_MATCHER.match(uri);
        switch (match) {
            case URI_TYPE_BABY:
                return TYPE_CURSOR_DIR + BabyColumns.TABLE_NAME;
            case URI_TYPE_BABY_ID:
                return TYPE_CURSOR_ITEM + BabyColumns.TABLE_NAME;

            case URI_TYPE_EVENT:
                return TYPE_CURSOR_DIR + EventColumns.TABLE_NAME;
            case URI_TYPE_EVENT_ID:
                return TYPE_CURSOR_ITEM + EventColumns.TABLE_NAME;

        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (DEBUG) Log.d(TAG, "insert uri=" + uri + " values=" + values);
        return super.insert(uri, values);
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        if (DEBUG) Log.d(TAG, "bulkInsert uri=" + uri + " values.length=" + values.length);
        return super.bulkInsert(uri, values);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (DEBUG) Log.d(TAG, "update uri=" + uri + " values=" + values + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs));
        return super.update(uri, values, selection, selectionArgs);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        if (DEBUG) Log.d(TAG, "delete uri=" + uri + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs));
        return super.delete(uri, selection, selectionArgs);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (DEBUG)
            Log.d(TAG, "query uri=" + uri + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs) + " sortOrder=" + sortOrder
                    + " groupBy=" + uri.getQueryParameter(QUERY_GROUP_BY) + " having=" + uri.getQueryParameter(QUERY_HAVING) + " limit=" + uri.getQueryParameter(QUERY_LIMIT));
        return super.query(uri, projection, selection, selectionArgs, sortOrder);
    }

    @Override
    protected QueryParams getQueryParams(Uri uri, String selection, String[] projection) {
        QueryParams res = new QueryParams();
        String id = null;
        int matchedId = URI_MATCHER.match(uri);
        switch (matchedId) {
            case URI_TYPE_BABY:
            case URI_TYPE_BABY_ID:
                res.table = BabyColumns.TABLE_NAME;
                res.idColumn = BabyColumns._ID;
                res.tablesWithJoins = BabyColumns.TABLE_NAME;
                res.orderBy = BabyColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_EVENT:
            case URI_TYPE_EVENT_ID:
                res.table = EventColumns.TABLE_NAME;
                res.idColumn = EventColumns._ID;
                res.tablesWithJoins = EventColumns.TABLE_NAME;
                if (BabyColumns.hasColumns(projection)) {
                    //res.tablesWithJoins += " LEFT OUTER JOIN " + BabyColumns.TABLE_NAME + " AS " + EventColumns.PREFIX_BABY + " ON " + EventColumns.TABLE_NAME + "." + EventColumns.BABY_ID + "=" + EventColumns.PREFIX_BABY + "." + BabyColumns._ID;
                }
                res.orderBy = EventColumns.DEFAULT_ORDER;
                break;

            default:
                throw new IllegalArgumentException("The uri '" + uri + "' is not supported by this ContentProvider");
        }

        switch (matchedId) {
            case URI_TYPE_BABY_ID:
            case URI_TYPE_EVENT_ID:
                id = uri.getLastPathSegment();
        }
        if (id != null) {
            if (selection != null) {
                res.selection = res.table + "." + res.idColumn + "=" + id + " and (" + selection + ")";
            } else {
                res.selection = res.table + "." + res.idColumn + "=" + id;
            }
        } else {
            res.selection = selection;
        }
        return res;
    }
}
