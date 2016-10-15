package hai.xiong.redchilden.util;

import android.content.Context;
import android.content.SharedPreferences;

import hai.xiong.redchilden.bean.LoginBean;

/**
 * 全局设置/获取工具类
 * Created by _Ms on 2016/8/5.
 */

public class GlobalConfig  {

    /**
     * 判断用户登录状态SP_KEY
     */
    public static final String SP_KEY_USER_LOGIN_ID = "user_login_id";
    public static final String SP_KEY_USER_LOGIN_USERNAME = "user_login_username";
    public static final String SP_KEY_IS_AUTO_LOGIN = "is_auto_login";

    /***
     * 记录当前用户登录状态&id
     */
    public static int localLoginUserId = -1;

    /**
     * 记录SP
     */
    private static SharedPreferences mSP;

    /**
     * SP File
     */
    private static final String SP_CONFIG_FILE = "config";

    /**
     * 判断当前用户是否已登录
     *
     * @param context context
     * @return 是否登录 true: 已登录, false: 未登录
     */
    public static boolean isLogin(Context context) {
        initSP(context);
        return getLoginUserBean(context) != null;
    }

    /**
     * 获取当前用户登录ID
     *
     * @param context Context
     * @return 用户登录的ID, 如果没有登录则返回-1
     */
    public static LoginBean.UserInfoBean getLoginUserBean(Context context) {
        initSP(context);

        int result = -1;

        if (localLoginUserId != -1) {
            result = localLoginUserId;
        } else if (mSP.getBoolean(SP_KEY_IS_AUTO_LOGIN, false)) {
            result = localLoginUserId = mSP.getInt(SP_KEY_USER_LOGIN_ID, -1);
        } else {
            result = -1;
        }

        if (result == -1) {
            return null;
        }
        LoginBean.UserInfoBean userBean = new LoginBean.UserInfoBean();
        userBean.userId = result;
        userBean.userName = mSP.getString(SP_KEY_USER_LOGIN_USERNAME,"将雨洋");
        return userBean;
    }

    /**
     * 注销用户
     *
     * @param context Context
     */
    public static void loginOutUser(Context context) {
        setLoginState(context, -1, "");
    }

    /**
     * 设置用户登录状态, -1: 注销, >1 已登录用户的id
     *
     * @param context Context
     * @param userId  登录用户的ID
     */
    public static void setLoginState(Context context, int userId, String userName) {
        initSP(context);

        localLoginUserId = userId;
        if (userId == -1) {
            setLoginAutoLoginState(context, false);
        }
        SharedPreferences.Editor edit = mSP.edit();
        //判断是否自动登录
        if (mSP.getBoolean(SP_KEY_IS_AUTO_LOGIN, false)) {
            edit.putInt(SP_KEY_USER_LOGIN_ID, userId);
        }
        edit.putString(SP_KEY_USER_LOGIN_USERNAME, userName).commit();
    }

    /**
     * 设置是否自动登录
     *
     * @param context
     * @param isAutoLogin
     */
    public static void setLoginAutoLoginState(Context context, boolean isAutoLogin) {
        initSP(context);

        mSP.edit().putBoolean(SP_KEY_IS_AUTO_LOGIN, isAutoLogin).commit();
    }


    /**
     * 判断当前是否存在自动登录
     *
     * @param context Context
     * @return 是否自動登錄
     */
    public static boolean isAutoLogin(Context context) {
        initSP(context);
        return mSP.getBoolean(SP_KEY_IS_AUTO_LOGIN, false);
    }


    /**
     * 初始化SP
     *
     * @param context Context
     */
    private static void initSP(Context context) {
        if (mSP == null) {
            mSP = context.getSharedPreferences(SP_CONFIG_FILE, Context.MODE_PRIVATE);
        }
    }

}
