package barqsoft.footballscores.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Date;

import barqsoft.footballscores.DatabaseContract;
import barqsoft.footballscores.MainActivity;
import barqsoft.footballscores.R;
import barqsoft.footballscores.ScoresDBHelper;
import barqsoft.footballscores.Utilies;

/**
 * Created by katsw on 28/01/2016.
 */
public class SingleWidgetProvider extends AppWidgetProvider {


    public static  String[] WidgetProjection=
            {
              DatabaseContract.scores_table.HOME_COL,
                    DatabaseContract.scores_table.HOME_GOALS_COL,
                    DatabaseContract.scores_table.AWAY_COL,
                    DatabaseContract.scores_table.AWAY_GOALS_COL,
                    DatabaseContract.scores_table.DATE_COL,
                    DatabaseContract.scores_table.TIME_COL,
                    DatabaseContract.scores_table._ID,
            };
    public static int HOME_COL=0;
    public static int HOME_GOALS_COL=1;
    public static int AWAY_COL=2;
    public static int AWAY_GOALS_COL=3;
    public static int DATE_COL=4;
    public static int TIME_COL=5;
    public static int ID_COL=6;
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for(int AppId:appWidgetIds)
        {
            RemoteViews views=new RemoteViews(context.getPackageName(), R.layout.widget_single);

            Intent intent=new Intent(context, MainActivity.class);
            PendingIntent pending=PendingIntent.getActivity(context, 0, intent, 0);
            views.setOnClickPendingIntent(R.id.widgetSingle, pending);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();

            Cursor curs=context.getContentResolver().query(DatabaseContract.BASE_CONTENT_URI,WidgetProjection,DatabaseContract.scores_table.DATE_COL +" <= ? ", new String[]{dateFormat.format(date)} , DatabaseContract.scores_table.DATE_COL +" DESC"); //
            if(curs.moveToNext())
            {
                views.setTextViewText(R.id.widget_away_name,curs.getString(AWAY_COL));
                views.setTextViewText(R.id.widget_home_name,curs.getString(HOME_COL));
                views.setImageViewResource(R.id.widget_away_crest, Utilies.getTeamCrestByTeamName(curs.getString(AWAY_COL)));
                views.setImageViewResource(R.id.widget_home_crest, Utilies.getTeamCrestByTeamName(curs.getString(HOME_COL)));
                views.setTextViewText(R.id.widget_data_textview, curs.getString(TIME_COL));
                views.setTextViewText(R.id.widget_score_textview, Utilies.getScores(curs.getInt(HOME_GOALS_COL),curs.getInt(AWAY_COL)));
            }

            appWidgetManager.updateAppWidget(AppId,views);

            curs.close();

        }
    }
}
