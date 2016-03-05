package barqsoft.footballscores.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.text.SimpleDateFormat;
import java.util.Date;

import barqsoft.footballscores.DatabaseContract;
import barqsoft.footballscores.R;
import barqsoft.footballscores.Utilies;

/**
 * Created by katsw on 29/02/2016.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class CollectionWidgetProvider implements RemoteViewsService.RemoteViewsFactory {

    Context mContext = null;
    Cursor cursor;

    public CollectionWidgetProvider(Context context,Intent intent)
    {
        this.mContext=context;
    }

    @Override
    public void onCreate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        cursor=mContext.getContentResolver().query(DatabaseContract.BASE_CONTENT_URI,SingleWidgetProvider.WidgetProjection,DatabaseContract.scores_table.DATE_COL +" = ? ", new String[]{dateFormat.format(date)} , DatabaseContract.scores_table.DATE_COL +" DESC");
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews views=new RemoteViews(mContext.getPackageName(), R.layout.widget_single);
        if(cursor.move(position))
        {
            views.setTextViewText(R.id.widget_away_name,cursor.getString(SingleWidgetProvider.AWAY_COL));
            views.setTextViewText(R.id.widget_home_name,cursor.getString(SingleWidgetProvider.HOME_COL));
            views.setImageViewResource(R.id.widget_away_crest, Utilies.getTeamCrestByTeamName(cursor.getString(SingleWidgetProvider.AWAY_COL)));
            views.setImageViewResource(R.id.widget_home_crest, Utilies.getTeamCrestByTeamName(cursor.getString(SingleWidgetProvider.HOME_COL)));
            views.setTextViewText(R.id.widget_data_textview, cursor.getString(SingleWidgetProvider.TIME_COL));
            views.setTextViewText(R.id.widget_score_textview, Utilies.getScores(cursor.getInt(SingleWidgetProvider.HOME_GOALS_COL),cursor.getInt(SingleWidgetProvider.AWAY_COL)));
        }

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return new RemoteViews(mContext.getPackageName(), R.layout.widget_single);
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        if(cursor.move(position)) {
            return cursor.getInt(SingleWidgetProvider.ID_COL);
        }
        return -1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
