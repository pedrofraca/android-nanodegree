/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package barqsoft.footballscores.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;

import barqsoft.footballscores.DatabaseContract;
import barqsoft.footballscores.R;

public class StackWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}

class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private int mCount = 10;
    private List<WidgetItem> mWidgetItems = new ArrayList<WidgetItem>();
    private Context mContext;
    private int mAppWidgetId;

    public StackRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    public void onCreate() {
        Cursor matches = mContext.getContentResolver().query(
                DatabaseContract.BASE_CONTENT_URI,null,null,null,null);
        mCount=matches.getCount();
        // In onCreate() you setup any connections / cursors to your data source. Heavy lifting,
        // for example downloading or creating content etc, should be deferred to onDataSetChanged()
        // or getViewAt(). Taking more than 20 seconds in this call will result in an ANR.
        while (matches.moveToNext()){
            String  homeTeam = matches.getString(matches.getColumnIndex(DatabaseContract.scores_table.HOME_COL));
            String awayTeam = matches.getString(matches.getColumnIndex(DatabaseContract.scores_table.AWAY_COL));
            String matchDay = matches.getString(matches.getColumnIndex(DatabaseContract.scores_table.DATE_COL));
            String time = matches.getString(matches.getColumnIndex(DatabaseContract.scores_table.TIME_COL));
            String homeGoals = matches.getString(matches.getColumnIndex(DatabaseContract.scores_table.HOME_GOALS_COL));
            String awayGoals = matches.getString(matches.getColumnIndex(DatabaseContract.scores_table.AWAY_GOALS_COL));
            mWidgetItems.add(new WidgetItem(homeTeam,awayTeam,matchDay,time,homeGoals,awayGoals));
        }

        // We sleep for 3 seconds here to show how the empty view appears in the interim.
        // The empty view is set in the StackWidgetProvider and should be a sibling of the
        // collection view.
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void onDestroy() {
        // In onDestroy() you should tear down anything that was setup for your data source,
        // eg. cursors, connections, etc.
        mWidgetItems.clear();
    }

    public int getCount() {
        return mCount;
    }

    public RemoteViews getViewAt(int position) {
        // position will always range from 0 to getCount() - 1.

        // We construct a remote views item based on our widget item xml file, and set the
        // text based on the position.
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        rv.setTextViewText(R.id.home_team, mWidgetItems.get(position).homeTeam);
        rv.setTextViewText(R.id.away_team,mWidgetItems.get(position).awayTeam);
        rv.setTextViewText(R.id.date_time,mWidgetItems.get(position).matchDay +
                " " + mWidgetItems.get(position).time);
        if(mWidgetItems.get(position).homeGoals.equals("-1")){
            rv.setTextViewText(R.id.score_home,"0");
        } else {
            rv.setTextViewText(R.id.score_home,mWidgetItems.get(position).homeGoals);
        }
        if(mWidgetItems.get(position).awayGoals.equals("-1")){
            rv.setTextViewText(R.id.score_away,"0");
        } else {
            rv.setTextViewText(R.id.score_away,mWidgetItems.get(position).awayGoals);
        }


        // Next, we set a fill-intent which will be used to fill-in the pending intent template
        // which is set on the collection view in StackWidgetProvider.
        Intent fillInIntent = new Intent();
        rv.setOnClickFillInIntent(R.id.root_widget, fillInIntent);

        // Return the remote views object.
        return rv;
    }

    public RemoteViews getLoadingView() {
        return null;
    }

    public int getViewTypeCount() {
        return 1;
    }

    public long getItemId(int position) {
        return position;
    }

    public boolean hasStableIds() {
        return true;
    }

    public void onDataSetChanged() {
        // This is triggered when you call AppWidgetManager notifyAppWidgetViewDataChanged
        // on the collection view corresponding to this factory. You can do heaving lifting in
        // here, synchronously. For example, if you need to process an image, fetch something
        // from the network, etc., it is ok to do it here, synchronously. The widget will remain
        // in its current state while work is being done here, so you don't need to worry about
        // locking up the widget.
    }
}