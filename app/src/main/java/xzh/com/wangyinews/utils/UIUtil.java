package xzh.com.wangyinews.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class UIUtil {

    public static SystemBarTintManager initTintMgr(Activity context) {
        return initTintMgr(context, null);
    }

    public static SystemBarTintManager initTintMgr(Activity context, View view) {
        SystemBarTintManager mTintManager = null;
        setTranslucentStatus(true, context);
        mTintManager = new SystemBarTintManager(context);
        mTintManager.setStatusBarTintEnabled(true);
        mTintManager.setNavigationBarTintEnabled(true);
        if (view != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                view.setFitsSystemWindows(true);
            }
        }
        mTintManager.setStatusBarTintColor(Color.parseColor("#ffd5d5d5"));
        return mTintManager;
    }


    @TargetApi(19)
    public static void setTranslucentStatus(boolean on, Activity context) {
        Window win = context.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}
