package hai.xiong.redchilden.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

import hai.xiong.redchilden.bean.ShoppingSKU;

/**
 * SP_SKU_Utils
 * Created by _Ms on 2016/8/9.
 */
public class SpSkuUtils {

    private static final String SP_SKU_CONFIG_NAME = "sku";

    private static SharedPreferences mSP;

    public SpSkuUtils(Context context) {
        initSP(context);
    }

    /**
     * 添加数据到SP
     *
     * @param id
     */
    public void add(int id) {
        List<ShoppingSKU> skuList = string2list(get(null));

        for (ShoppingSKU sku : skuList) {
            if (sku.id == id) {
                sku.num += 1;
                write(list2string(skuList)); // 存储
                return;
            }
        }

        write(String.format("%s:%s:%s,%s|", id, 1, 1, 2) + list2string(skuList));
    }

    /**
     * 清空数据
     */
    public void clear() {
        write("");
    }

    /**
     * 读取数据
     *
     * @return
     */
    public static String get(Context context) {
        initSP(context);
        String result = mSP.getString(SP_SKU_CONFIG_NAME, "");
        if (result.length() != 0) {
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }

    /**
     * 获取购物车当前数量
     * @param context    Contextt
     * @return
     */
    public static int getNum(Context context) {
        initSP(context);
        return string2list(get(context)).size();
    }

    /**
     * 写入文件
     *
     * @param str
     */
    private static void write(String str) {
        mSP.edit().putString(SP_SKU_CONFIG_NAME, str).commit();
    }

    /**
     * string2list
     *
     * @param strs strs
     * @return skuList
     */
    private static List<ShoppingSKU> string2list(String strs) {

        List<ShoppingSKU> skuList = new ArrayList<ShoppingSKU>();
        if (strs == null) {
            return skuList;
        }

        String[] much = strs.split("\\|");

        for (String string : much) {
            if (string != null && string.length() != 0) {
                skuList.add(str2bean(string));
            }
        }

        return skuList;
    }

    /**
     * SkuList2String
     *
     * @param skuList SkuList
     * @return String
     */
    private static String list2string(List<ShoppingSKU> skuList) {

        StringBuffer sb = new StringBuffer();
        for (ShoppingSKU sku : skuList) {
            sb.append(bean2str(sku) + "|");
        }

        return sb.toString();
    }

    /**
     * str2bean
     *
     * @param str
     * @return
     */
    private static ShoppingSKU str2bean(String str) {

        ShoppingSKU sku = new ShoppingSKU();
        String[] strArray = str.split("[:\\,]");

        try {
            sku.id = Integer.parseInt(strArray[0]);
            sku.num = Integer.parseInt(strArray[1]);
            sku.pro1 = Integer.parseInt(strArray[2]);
            sku.pro2 = Integer.parseInt(strArray[3]);
        } catch (Exception e) {
        }

        return sku;
    }

    /**
     * Bean2Str
     *
     * @param sku BeanSKU
     * @return SKU2String
     */
    private static String bean2str(ShoppingSKU sku) {
        return String.format("%s:%s:%s,%s", sku.id, sku.num, sku.pro1, sku.pro2);
    }


    /**
     * 初始化SP
     *
     * @param context Context
     */
    private static void initSP(Context context) {
        if (mSP == null) {
            mSP = context.getSharedPreferences(SP_SKU_CONFIG_NAME, Context.MODE_PRIVATE);
        }
    }
}
