package hai.xiong.redchilden.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/8/7 0007.
 */
public class CuXiaoBean {

    /**
     * response : topic
     * topic : [{"id":1,"userName":"暖奶器/保温杯壶/浴室用品特价5折起",
     * "pic":"/images/topic/1.jpg"},
     * {"id":2,"userName":"年货礼全齐备","
     * pic":"/images/topic/2.jpg"},
     * {"id":3,"userName":"如何刷两颗小牙","pic":"/images/topic/3.jpg"},{"id":4,"userName":"子雅贝贝全场 双重优惠好礼巨献","pic":"/images/topic/4.jpg"}]
     */

    public String response;
    /**
     * id : 1
     * userName : 暖奶器/保温杯壶/浴室用品特价5折起
     * pic : /images/topic/1.jpg
     */

    public List<TopicBean> topic;

    public static class TopicBean {
        public int id;
        public String name;
        public String pic;
    }
}
