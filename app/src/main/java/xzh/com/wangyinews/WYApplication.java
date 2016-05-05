package xzh.com.wangyinews;

import android.app.Application;
import android.database.sqlite.SQLiteOpenHelper;

import xzh.com.wangyinews.db.SQLHelper;
import xzh.com.wangyinews.utils.imagecache.WYImageLoader;
import xzh.com.wangyinews.utils.sql.SqlLiteHelper;


public class WYApplication extends Application {

    private static WYApplication mInstance = null;

    public static synchronized WYApplication getInstance() {
        if (mInstance == null) {
            mInstance = new WYApplication();
        }
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        init();
    }

    private void init() {
        WYImageLoader.init();
        SqlLiteHelper.getSQLHelper();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        WYImageLoader.clearMemoryCache();
        SqlLiteHelper.closeSql();
    }


}
