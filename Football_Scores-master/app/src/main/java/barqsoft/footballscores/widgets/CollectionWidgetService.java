package barqsoft.footballscores.widgets;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViewsService;

/**
 * Created by katsw on 29/02/2016.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class CollectionWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        CollectionWidgetProvider provider=new CollectionWidgetProvider(getApplicationContext(),intent);
        return provider;
    }
}
