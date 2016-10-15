package hai.xiong.redchilden.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

/**
 * SharedPreference集合工具类
 * Created by _Ms on 2016/8/8.
 */
public class SPListUtils {

    private int mMaxValue;
    private String mKey;
    private SharedPreferences mSP;

    /**
     * 构造
     * @param context Context
     * @param key   spName
     */
    public SPListUtils(Context context, String key, int maxValue) {
        if (key == null || context == null) {
            throw new NullPointerException();
        }
        mKey = key;
        mSP = context.getSharedPreferences("SPListUtils", Context.MODE_PRIVATE);
        mMaxValue = maxValue;
    }

    /**
     * 添加字符串数据到SP
     * @param str    字符串数据
     */
    public void add(String str) {

        List<String> strList = string2List(mSP.getString(mKey, ""));
        int existIndex =  strList.indexOf(str);
        if (existIndex != -1) {
            strList.remove(existIndex);
        }

        // 最大存储数量
        if (strList.size() >= mMaxValue) {
            strList.remove(mMaxValue - 1);
        }
        String finalStr = str + "#" + list2String(strList);
        mSP.edit().putString(mKey, finalStr).commit();
    }

    /**
     * 获取字符串集合
     * @return
     */
    public List<String> get() {
        return string2List(mSP.getString(mKey, null));
    }

    /**
     * 清空数据
     */
    public void clear() {
        mSP.edit().putString(mKey, "").commit();
    }

    /**
     * 根据字符串获取集合
     * @param str 字符串
     * @return 字符串集合
     */
    private List<String> string2List(String str) {

        List<String> listStr = new ArrayList<String>();

        if (str == null || str.length() == 0) {
            return listStr;
        }

        String[] splitArray = str.split("#");

        for (String s : splitArray) {
            listStr.add(s);
        }

        return listStr;
    }

    /**
     * 根据集合获取字符串
     * @param listStr 集合
     * @return 字符串
     */
    private String list2String(List<String> listStr) {

        StringBuffer sb = new StringBuffer();
        for (String string : listStr) {
            sb.append(string + "#");
        }

        return sb.toString();
    }


}
