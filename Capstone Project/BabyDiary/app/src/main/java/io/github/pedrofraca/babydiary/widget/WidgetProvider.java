package io.github.pedrofraca.babydiary.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.github.pedrofraca.babydiary.R;
import io.github.pedrofraca.babydiary.activity.TimelineActivity;
import io.github.pedrofraca.babydiary.provider.baby.BabyCursor;
import io.github.pedrofraca.babydiary.provider.baby.BabySelection;
import io.github.pedrofraca.babydiary.utils.BabyUtils;

/**
 * Created by pedrofraca on 23/05/16.
 */
public class WidgetProvider  extends AppWidgetProvider {

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;

        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];

            // Create an Intent to launch ExampleActivity
            Intent intent = new Intent(context, TimelineActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            // Get the layout for the App Widget and attach an on-click listener
            // to the button
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
            views.setOnClickPendingIntent(R.id.root_layout,pendingIntent);
            BabyCursor allBabies = new BabySelection().id(BabyUtils.getCurrentActiveBabyId(context)).query(context);
            if (allBabies != null && allBabies.moveToFirst()) {
                views.setTextViewText(R.id.baby_name,allBabies.getName());
                views.setTextViewText(R.id.count_down_to_bday,context.getString(R.string.bday_countdown, daysOfLife(allBabies.getDateOfBirth())));
            }

            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    private long daysOfLife(Date dob){
        long diff = System.currentTimeMillis() - dob.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }
}