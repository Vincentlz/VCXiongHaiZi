package hai.xiong.redchilden;

import android.app.Application;

import org.xutils.x;

/**
 * BaseApplication
 * Created by _Ms on 2016/8/5.
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        /**
         * 初始化xUtils
         */
        x.Ext.init(this);

    }
}
