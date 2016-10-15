package hai.xiong.redchilden.bean;

import java.util.List;

/**
 * Created by 14144 on 2016/8/8.
 */
public class WuliuBean {


    /**
     * followInfo : [{"followinfo":"2013-10-27 14:25:23 上海航空转接点"},
     * {"followinfo":"2013-10-28 18:55:26 上海嘉定公司 露洲服务部KH发往豫SE1467"},
     * {"followinfo":"2013-10-28 20:45:11 北京中转站"},
     * {"followinfo":"2013-10-28 21:34:48 北京中转站进行中转,并发往北京丰台区广安门公司"},
     * {"followinfo":"2013-10-28 23:36:16 北京丰台区广安门公司派送,由钱树彬 签收"}]
     * response : logistics
     */

    public String response;
    /**
     * followinfo : 2013-10-27 14:25:23 上海航空转接点
     */

    public List<FollowInfoBean> followInfo;

    public static class FollowInfoBean {
        public String followinfo;
    }
}
