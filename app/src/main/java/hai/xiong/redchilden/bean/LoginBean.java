package hai.xiong.redchilden.bean;

/**
 * Created by 14144 on 2016/8/6.
 */
public class LoginBean {


    /**
     * response : login
     * userInfo : {"bonus":101,"level":"金牌用户","userId":2}
     */

    public String response;
    /**
     * bonus : 101
     * level : 金牌用户
     * userId : 2
     */

    public UserInfoBean userInfo;

    public static class UserInfoBean {
        public int bonus;
        public String level;
        public int userId;
        public String userName;
    }
}

