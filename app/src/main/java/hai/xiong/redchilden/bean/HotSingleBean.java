package hai.xiong.redchilden.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/8/8 0008.
 */
public class HotSingleBean {

    /**
     * listCount : 5
     * productlist : [
     * {"id":1102542,"marketprice":115,"name":"超人内裤","pic":"/images/8.jpg","price":25,"sales":0},
     * {"id":3,"marketprice":89,"name":"安慕希","pic":"/images/12.jpg","price":25,"sales":18}
     * {"id":4,"marketprice":112,"name":"特仑苏  ","pic":"/images/13.jpg","price":50,"sales":100},
     * {"id":1020,"marketprice":79,"name":"心心相印抽纸","pic":"/images/16.jpg","price":78,"sales":100},
     * {"id":10211,"marketprice":18,"name":"酷炫手机壳","pic":"/images/6.jpg","price":12,"sales":100}]
     * response : hotproduct
     */

    public int listCount;
    public String response;
    /**
     * id : 1102542
     * marketprice : 115
     * name : 超人内裤
     * pic : /images/8.jpg
     * price : 25
     * sales : 0
     */

    public List<ProductlistBean> productlist;

    public static class ProductlistBean {
        public int id;
        public int marketprice;
        public String name;
        public String pic;
        public int price;
        public int sales;
    }
}
