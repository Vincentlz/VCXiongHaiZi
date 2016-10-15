package hai.xiong.redchilden.bean;

import java.util.List;

/**
 * Created by 徐心意 on 2016/8/10.
 */
public class SpinnerBean {

    /**
     * areaList : [{"id":1,"parentId":0,"value":"北京市"},{"id":2,"parentId":1,"value":"海淀区"},{"id":3,"parentId":1,"value":"朝阳区"},{"id":4,"parentId":1,"value":"丰台区"},{"id":5,"parentId":0,"value":"广东省"},{"id":6,"parentId":5,"value":"广州市"},{"id":7,"parentId":6,"value":"广州一区"},{"id":8,"parentId":6,"value":"广州二区"},{"id":9,"parentId":5,"value":"深圳市"},{"id":10,"parentId":9,"value":"深圳一区"},{"id":11,"parentId":9,"value":"深圳二区"},{"id":12,"parentId":0,"value":"山东省"},{"id":13,"parentId":12,"value":"济南市"},{"id":14,"parentId":13,"value":"济南一区"},{"id":15,"parentId":13,"value":"济南二区"},{"id":16,"parentId":12,"value":"青岛市"},{"id":17,"parentId":16,"value":"青岛一区"},{"id":18,"parentId":16,"value":"青岛二区"},{"id":19,"parentId":0,"value":"1"},{"id":20,"parentId":19,"value":"12"},{"id":21,"parentId":20,"value":"112"}]
     * response : addressArea
     */

    public String response;
    /**
     * id : 1
     * parentId : 0
     * value : 北京市
     */

    public List<AreaListBean> areaList;

    public static class AreaListBean {
        public int id;
        public int parentId;
        public String value;
    }
}
