package hai.xiong.redchilden.bean;

/**
 * Created by LvYou on 2016/8/7.
 */
public class AccountCenterBean {


    /**
     * response : userinfo
     * userInfo : {"bonus":101,"favoritesCount":20,"level":"金牌用户","orderCount":20,"userId":2}
     */

    public String response;
    /**
     * bonus : 101
     * favoritesCount : 20
     * level : 金牌用户
     * orderCount : 20
     * userId : 2
     */

    public UserInfoBean userInfo;

    public static class UserInfoBean {
        public int bonus;
        public int favoritesCount;
        public String level;
        public int orderCount;
        public int userId;
    }
}
