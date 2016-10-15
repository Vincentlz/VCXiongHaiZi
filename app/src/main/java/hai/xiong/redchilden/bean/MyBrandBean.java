package hai.xiong.redchilden.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/8/7 0007.
 */
public class MyBrandBean {
    /**
     * brand :
     * [{"key":"孕妈专区",
     * "value":[{"id":1,"userName":"周生生","pic":"/images/brand/zhoushengsheng.png"},
     * {"id":2,"userName":"雅培","pic":"/images/brand/yapei.png"},
     * {"id":3,"userName":"钢铁侠奶粉","pic":"/images/brand/3.jpg"}]}
     * ,{"key":"营养食品","value":[{"id":4,"userName":"喜羊羊","pic":"/images/brand/4.jpg"},{"id":5,"userName":"灰太狼","pic":"/images/brand/5.jpg"}]}]
     * response : brand
     */

    public String response;
    /**
     * key : 孕妈专区
     * value : [{"id":1,"userName":"周生生","pic":"/images/brand/zhoushengsheng.png"},{"id":2,"userName":"雅培","pic":"/images/brand/yapei.png"},{"id":3,"userName":"钢铁侠奶粉","pic":"/images/brand/3.jpg"}]
     */

    public List<BrandBean> brand;

    public static class BrandBean {
        public String key;
        /**
         * id : 1
         * userName : 周生生
         * pic : /images/brand/zhoushengsheng.png
         */

        public List<ValueBean> value;

        public static class ValueBean {
            public int id;
            public String name;
            public String pic;
        }
    }
}
