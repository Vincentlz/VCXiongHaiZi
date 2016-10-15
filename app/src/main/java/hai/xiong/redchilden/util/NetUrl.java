package hai.xiong.redchilden.util;

/**
 * Created by _Ms on 2016/8/5.
 */

public class NetUrl {

    /**
     * 服务器地址
     */
    private static final String SERVER_ADDRESS= "http://192.168.18.29";

    /**
     * 服务器端口
     */
    private static final int SERVER_PORT = 8080;

    /**
     * 服务器主机地址
     */
    private static final String SERVER_HOST = String.format("%s:%s", SERVER_ADDRESS, SERVER_PORT);

    /**
     * 项目·服务器URL
     */
    public static final String BASE_URL = String.format("%s/%s", SERVER_HOST, "ECServicez8");

}
