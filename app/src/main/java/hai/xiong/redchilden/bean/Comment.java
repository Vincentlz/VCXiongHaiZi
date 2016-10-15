package hai.xiong.redchilden.bean;

import java.util.List;

/**
 * Created by dell on 2016/8/7.
 */
public class Comment {

    /**
     * comment : [{"content":"好评","time":"2016年4月8日 12:29:00","title":"好评,32个赞!","username":"匿名"},{"content":"好评","time":"2016年4月8日 12:29:00","title":"好评,32个赞!","username":"匿名"},{"content":"好评","time":"2016年4月8日 12:29:00","title":"好评,32个赞!","username":"匿名"},{"content":"好评","time":"2016年4月8日 12:29:00","title":"好评,32个赞!","username":"匿名"}]
     * listCount : 4
     * response : product_comment
     */

    public int listCount;
    public String response;
    /**
     * content : 好评
     * time : 2016年4月8日 12:29:00
     * title : 好评,32个赞!
     * username : 匿名
     */

    public List<CommentBean> comment;

    public static class CommentBean {
        public String content;
        public String time;
        public String title;
        public String username;
    }
}
