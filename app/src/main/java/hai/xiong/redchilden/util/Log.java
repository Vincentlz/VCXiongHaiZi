package hai.xiong.redchilden.util;


/**
 * Created by _Ms on 2016/8/5.
 */

public class Log {

    private static final boolean IS_DEBUG = true;

    /**
     * 成员名称
     */
    private static final String[] MEMBERS = new String[]{
            "冯茂森", "蒋雨洋", "贺壁", "徐心意", "王冲", "李淑燕", "李玉辉", "吕游"
    };

    public static void fms(CharSequence message) {
        showLog(message, 0);
    }

    public static void jyy(CharSequence message) {
        showLog(message, 1);
    }

    public static void hb(CharSequence message) {
        showLog(message, 2);
    }

    public static void xxy(CharSequence message) {
        showLog(message, 3);
    }

    public static void wc(CharSequence message) {
        showLog(message, 4);
    }

    public static void lsy(CharSequence message) {
        showLog(message, 5);
    }

    public static void lyh(CharSequence message) {
        showLog(message, 6);
    }

    public static void ly(CharSequence message) {
        showLog(message, 7);
    }


    private static void showLog(CharSequence message, int memberIndex) {
        if (IS_DEBUG) {

            android.util.Log.i(
                    MEMBERS[memberIndex],
                    String.format(
                            ":\n::------------------------------------------\n---> %s\n------------------------------------------::",
                            message.toString()
                    )
            );
        }
    }

}
