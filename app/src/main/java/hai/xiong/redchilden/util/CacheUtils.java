package hai.xiong.redchilden.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 缓存获取/存储工具
 * Created by _Ms on 2016/8/2.
 */
public class CacheUtils {

    private static SharedPreferences sSp;
    private static final String SP_CACHE_FILE_NAME = "cache";

    /**
     * 根据url获取Cache
     * @param url    url
     * @return cache内容
     */
    public static String getCache(Context context, String url) {
        initSp(context);

        return sSp.getString(url, null);
    }

    /**
     * 根据url存储cache
     * @param url      url
     * @param cache    cache内容
     */
    public static void saveCache(Context context, String url, String cache) {
        initSp(context);

        sSp.edit().putString(url, cache).commit();
    }

    /**
     * 初始化SP
     * @param context
     */
    private static void initSp(Context context) {

        if (context == null) {
            throw new NullPointerException();
        }

        if (sSp == null) {
            sSp = context.getSharedPreferences(SP_CACHE_FILE_NAME, Context.MODE_PRIVATE);
        }
    }

}
