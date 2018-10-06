package me.akshanshjain.kwik.Widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.widget.RemoteViews;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import me.akshanshjain.kwik.R;

/**
 * Implementation of App Widget functionality.
 */
public class KwikWidget extends AppWidgetProvider {

    private SharedPreferences latestEvent;
    private static final String LATEST_EVENT_SHARED_PREF = "latestevent";
    private static final String EVENT_NAME = "EVENT_NAME";
    private static final String EVENT_DATE_TIME = "EVENT_DATE_TIME";
    private static final String EVENT_LOCATION = "EVENT_LOCATION";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.kwik_widget);

        views.setTextViewText(R.id.event_name_widget, "");

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        String eventName, eventDateTime = "", eventLocation, eventEta = "";

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.kwik_widget);

        SharedPreferences sharedPreferences = context.getSharedPreferences(LATEST_EVENT_SHARED_PREF, Context.MODE_PRIVATE);
        eventName = sharedPreferences.getString(EVENT_NAME, "");
        String dateTime = sharedPreferences.getString(EVENT_DATE_TIME, "");
        eventLocation = sharedPreferences.getString(EVENT_LOCATION, "");

        String[] dateTimeSplit = new String[0];
        if (!TextUtils.isEmpty(dateTime)) {

            if (!dateTime.equals("Decide\nLater")) {
                dateTimeSplit = dateTime.split("\n");
                eventDateTime = dateTimeSplit[0] + " ● " + dateTimeSplit[1];
            } else {
                eventDateTime = "Decide\nLater";
            }

            eventEta = calcTimeDifference(dateTimeSplit[0], dateTimeSplit[1]) + "\nHours";
        }

        views.setTextViewText(R.id.event_name_widget, eventName);
        views.setTextViewText(R.id.event_location_widget, eventLocation);
        views.setTextViewText(R.id.event_date_time_widget, eventDateTime);
        views.setTextViewText(R.id.eta_widget, eventEta);

        AppWidgetManager.getInstance(context).updateAppWidget(
                new ComponentName(context, KwikWidget.class), views);
    }

    /*
    Calculating the ETA in hours to be displayed in the TextView.
    Here we find the different between the event date and the current date.
    This way we get approximate number of hours as ETA.
    */
    private String calcTimeDifference(String date, String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date now = new Date();
        String currentDateFormat = sdf.format(now);
        String eventDateFormat = date + " " + time;

        Date currentDate = null, eventDate = null;

        try {
            currentDate = sdf.parse(currentDateFormat);
            eventDate = sdf.parse(eventDateFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long diff = 0;
        if (!TextUtils.isEmpty(date)) {
            if (!date.equals("Not set")) {
                long differenceInMillis = Math.abs(eventDate.getTime() - currentDate.getTime());
                diff = TimeUnit.HOURS.convert(differenceInMillis, TimeUnit.MILLISECONDS);
            }
        }

        return String.valueOf(diff);
    }
}

