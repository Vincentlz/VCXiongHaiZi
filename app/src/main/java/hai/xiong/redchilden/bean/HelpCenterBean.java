package hai.xiong.redchilden.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by LvYou on 2016/8/7.
 */
public class HelpCenterBean implements Serializable{

    /**
     * helpList : [{"id":1,"title":"如何派送"},{"id":3,"title":"帮助3"},{"id":4,"title":"如何退货"},{"id":5,"title":"新增的数据"}]
     * response : help
     * version : 4
     */

    public String response;
    public int version;
    /**
     * id : 1
     * title : 如何派送
     */

    public List<HelpListBean> helpList;

    public static class HelpListBean implements Serializable{
        public int id;
        public String title;
    }
}
