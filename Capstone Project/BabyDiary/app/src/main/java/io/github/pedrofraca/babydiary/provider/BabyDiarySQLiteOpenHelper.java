package io.github.pedrofraca.babydiary.provider;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.DefaultDatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import io.github.pedrofraca.babydiary.BuildConfig;
import io.github.pedrofraca.babydiary.provider.baby.BabyColumns;
import io.github.pedrofraca.babydiary.provider.event.EventColumns;

public class BabyDiarySQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = BabyDiarySQLiteOpenHelper.class.getSimpleName();

    public static final String DATABASE_FILE_NAME = "baby_diary.db";
    private static final int DATABASE_VERSION = 1;
    private static BabyDiarySQLiteOpenHelper sInstance;
    private final Context mContext;
    private final BabyDiarySQLiteOpenHelperCallbacks mOpenHelperCallbacks;

    // @formatter:off
    public static final String SQL_CREATE_TABLE_BABY = "CREATE TABLE IF NOT EXISTS "
            + BabyColumns.TABLE_NAME + " ( "
            + BabyColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + BabyColumns.NAME + " TEXT, "
            + BabyColumns.DATE_OF_BIRTH + " INTEGER, "
            + BabyColumns.GENDER + " INTEGER NOT NULL "
            + " );";

    public static final String SQL_CREATE_TABLE_EVENT = "CREATE TABLE IF NOT EXISTS "
            + EventColumns.TABLE_NAME + " ( "
            + EventColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + EventColumns.TITLE + " TEXT, "
            + EventColumns.DESCRIPTION + " TEXT, "
            + EventColumns.LOCATION + " TEXT, "
            + EventColumns.MEDIA_PATH + " TEXT, "
            + EventColumns.HEIGHT + " REAL, "
            + EventColumns.WEIGHT + " REAL, "
            + EventColumns.VACCINE_NAME + " REAL, "
            + EventColumns.VACCINE_DESCRIPTION + " REAL, "
            + EventColumns.DATE + " INTEGER, "
            + EventColumns.TYPE + " INTEGER NOT NULL, "
            + EventColumns.BABY_ID + " INTEGER NOT NULL "
            + ", CONSTRAINT fk_baby_id FOREIGN KEY (" + EventColumns.BABY_ID + ") REFERENCES baby (_id) ON DELETE CASCADE"
            + " );";

    // @formatter:on

    public static BabyDiarySQLiteOpenHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = newInstance(context.getApplicationContext());
        }
        return sInstance;
    }

    private static BabyDiarySQLiteOpenHelper newInstance(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return newInstancePreHoneycomb(context);
        }
        return newInstancePostHoneycomb(context);
    }


    /*
     * Pre Honeycomb.
     */
    private static BabyDiarySQLiteOpenHelper newInstancePreHoneycomb(Context context) {
        return new BabyDiarySQLiteOpenHelper(context);
    }

    private BabyDiarySQLiteOpenHelper(Context context) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
        mContext = context;
        mOpenHelperCallbacks = new BabyDiarySQLiteOpenHelperCallbacks();
    }


    /*
     * Post Honeycomb.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static BabyDiarySQLiteOpenHelper newInstancePostHoneycomb(Context context) {
        return new BabyDiarySQLiteOpenHelper(context, new DefaultDatabaseErrorHandler());
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private BabyDiarySQLiteOpenHelper(Context context, DatabaseErrorHandler errorHandler) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION, errorHandler);
        mContext = context;
        mOpenHelperCallbacks = new BabyDiarySQLiteOpenHelperCallbacks();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onCreate");
        mOpenHelperCallbacks.onPreCreate(mContext, db);
        db.execSQL(SQL_CREATE_TABLE_BABY);
        db.execSQL(SQL_CREATE_TABLE_EVENT);
        mOpenHelperCallbacks.onPostCreate(mContext, db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            setForeignKeyConstraintsEnabled(db);
        }
        mOpenHelperCallbacks.onOpen(mContext, db);
    }

    private void setForeignKeyConstraintsEnabled(SQLiteDatabase db) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            setForeignKeyConstraintsEnabledPreJellyBean(db);
        } else {
            setForeignKeyConstraintsEnabledPostJellyBean(db);
        }
    }

    private void setForeignKeyConstraintsEnabledPreJellyBean(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setForeignKeyConstraintsEnabledPostJellyBean(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mOpenHelperCallbacks.onUpgrade(mContext, db, oldVersion, newVersion);
    }
}
