package hai.xiong.redchilden.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

/**
 * 网络请求工具类
 * Created by _Ms on 2016/8/5.
 */
public class NetUtil {

    /**
     * Get方式请求
     *
     * @param url            请求URL
     * @param commonCallBack 网络结果回调
     */
    public static void getRequest(String url, Callback.CommonCallback<? extends String> commonCallBack) {
        RequestParams params = new RequestParams(url);
        x.http().request(HttpMethod.GET, params, commonCallBack);
    }


    /**
     * 增强GET请求
     *
     * @param url      请求URL
     * @param callBack 请求回调
     */
    public static void newGetRequest(String url, NetworkCallBack callBack) {
        if (url == null) {
            throw new NullPointerException();
        }

        if (callBack == null) {
            throw new NullPointerException();
        }

        RequestParams params = new RequestParams(url);
        params.setConnectTimeout(1000 * 5);
        callBack.mUrl = url;
        x.http().request(HttpMethod.GET, params, callBack);
    }

    /**
     * 增强Post方式请求
     *
     * @param url            请求URL
     * @param params         POST请求封装参数
     * @param callBack 网络结果回调
     */
    public static void newPostRequest(String url, HashMap<String, String> params, NetworkCallBack callBack) {

        if (url == null) {
            throw new NullPointerException();
        }

        if (callBack == null) {
            throw new NullPointerException();
        }

        RequestParams xParams = new RequestParams(url);
        xParams.setConnectTimeout(1000 * 5);
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                xParams.addBodyParameter(key, value);
            }
        }

        x.http().request(HttpMethod.POST, xParams, callBack);
    }

    /**
     * 增强网络回调抽象类
     */
    public static abstract class NetworkCallBack implements Callback.CommonCallback<String> {

        private String mUrl;
        private ProgressDialog mDialog;
        private Context mContext;
        private boolean mIsShowDialog;
        private boolean mIsCache;
        private boolean mIsShowErrorMessage;

        /**
         * 构造
         *
         * @param context 用于创建对话框
         */
        public NetworkCallBack(Context context, boolean isCache, boolean isShowDialog, boolean isShowErrorMessage) {
            if (context == null) {
                throw new NullPointerException();
            }

            mContext = context;
            mIsShowDialog = isShowDialog;
            mIsCache = isCache;
            mIsShowErrorMessage = isShowErrorMessage;

            if (isShowDialog) {
                mDialog = ProgressDialog.show(context, null, "请求服务器~");
                mDialog.show();
            }

            if (isCache) {
                String cache = CacheUtils.getCache(mContext, mUrl);
                if (cache != null) {
                    onCache(cache);
                }
            }

        }

        @Override
        public void onSuccess(String result) {

            if (mIsCache) {
                CacheUtils.saveCache(mContext, mUrl, result);
            }
            onSucces(result);
        }

        @Override
        public void onError(Throwable ex, boolean isOnCallback) {
            ex.printStackTrace();
            if (mIsShowErrorMessage) {
                Toast.makeText(mContext, "网络无连接~", Toast.LENGTH_SHORT).show();
            }
            onConnectError();
        }

        @Override
        public void onCancelled(CancelledException cex) {
            cex.printStackTrace();
            if (mIsShowErrorMessage) {
                Toast.makeText(mContext, "网络无连接~", Toast.LENGTH_SHORT).show();
            }
            onConnectError();
        }

        @Override
        public void onFinished() {
            if (mIsShowDialog) {
                mDialog.dismiss();
            }
        }

        /**
         * 获取到Cache
         *
         * @param cache 为null时代表当前没有caches
         */
        public abstract void onCache(String cache);

        /**
         * 连接成功回调方法
         *
         * @param result
         */
        public abstract void onSucces(String result);

        /**
         * 当连接失败时的回调方法
         */
        public abstract void onConnectError();

    }

    /**
     * Post方式请求
     *
     * @param url            请求URL
     * @param params         POST请求封装参数
     * @param commonCallBack 网络结果回调
     */
    public static void postRequest(String url, HashMap<String, String> params, Callback.CommonCallback<? extends String> commonCallBack) {

        RequestParams xParams = new RequestParams(url);

        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                xParams.addBodyParameter(key, value);
            }
        }

        x.http().request(HttpMethod.POST, xParams, commonCallBack);
    }

}
