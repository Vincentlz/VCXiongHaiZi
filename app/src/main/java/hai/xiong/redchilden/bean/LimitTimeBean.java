package hai.xiong.redchilden.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/8/7 0007.
 */
public class LimitTimeBean {

    /**
     * listCount : 7
     * productlist : [{"id":2,
     * "lefttime":"3600",
     * "limitprice":20,
     * "userName":"三鹿    ",
     * "pic":"/images/11.jpg",
     *
     * "price":78,"
     * sales":100},{"id":3,"lefttime":"3600",
     * "limitprice":4,"userName":"伊利QQ星",
     * "pic":"/images/12.jpg","price":100,"sales":18},{"id":4,"lefttime":"3600","limitprice":33,"userName":"特仑苏  ","pic":"/images/13.jpg","price":78,"sales":100},{"id":5,"lefttime":"3600","limitprice":4,"userName":"伊利金装","pic":"/images/14.jpg","price":78,"sales":100},{"id":1010,"lefttime":"3600","limitprice":111,"userName":"雅培金装  ","pic":"/images/15.jpg","price":231,"sales":345678},{"id":1021,"lefttime":"3600","limitprice":23,"userName":"七度空间 ","pic":"/images/10.jpg","price":78,"sales":100},{"id":1102542,"lefttime":"3600","limitprice":44,"userName":"超人内裤","pic":"/images/10.jpg","price":332,"sales":0}]
     * response : limitbuy
     */

    public int listCount;
    public String response;
    /**
     * id : 2
     * lefttime : 3600
     * limitprice : 20
     * userName : 三鹿
     * pic : /images/11.jpg
     * price : 78
     * sales : 100
     */

    public List<ProductlistBean> productlist;

    public static class ProductlistBean {
        public int id;
        public String lefttime;
        public int limitprice;
        public String name;
        public String pic;
        public int price;
        public int sales;
    }

}
