package hai.xiong.redchilden.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/8/6 0006.
 */
public class HomeRoll {

    /**
     * home_banner : [{"id":1,"pic":"/images/home/image1.jpg","title":"纸尿裤当家做主 低至0.9元"},{"id":2,"pic":"/images/home/image2.jpg","title":"完达山满300立减50"},{"id":3,"pic":"/images/home/image3.jpg","title":"品牌狂欢 低至七折"},{"id":4,"pic":"/images/home/image4.jpg","title":"营养辅食 疯抢盛会来袭"},{"id":5,"pic":"/images/home/image5.jpg","title":"'8'一'8' YOURS新年愿望"}]
     * response : home
     */

    public String response;
    /**
     * id : 1
     * pic : /images/home/image1.jpg
     * title : 纸尿裤当家做主 低至0.9元
     */

    public List<HomeBannerBean> home_banner;

    public static class HomeBannerBean {
        public int id;
        public String pic;
        public String title;
    }
}

