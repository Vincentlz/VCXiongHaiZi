package hai.xiong.redchilden.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by _Ms on 2016/8/6.
 */
public class ShoppingCarBean implements Serializable {


    /**
     * cart : [{"prodNum":"3","product":{"id":3,"isgift":"false","userName":"伊利QQ星","number":3333,"pic":"/images/12.jpg","price":100,"product_property":[],"prom":[],"uplimit":"5678"}},{"prodNum":"2","product":{"id":5,"isgift":"false","userName":"伊利金装","number":33,"pic":"/images/14.jpg","price":78,"product_property":[],"prom":[],"uplimit":"20"}}]
     * response : cart
     * totalCount : 5
     * totalPoint : 1554
     * totalPrice : 456
     */

    public String response;
    public int totalCount;
    public int totalPoint;
    public int totalPrice;
    /**
     * prodNum : 3
     * product : {"id":3,"isgift":"false","userName":"伊利QQ星","number":3333,"pic":"/images/12.jpg","price":100,"product_property":[],"prom":[],"uplimit":"5678"}
     */

    public List<CartBean> cart;

    public static class CartBean implements Serializable {
        public int prodNum;
        /**
         * id : 3
         * isgift : false
         * userName : 伊利QQ星
         * number : 3333
         * pic : /images/12.jpg
         * price : 100
         * product_property : []
         * prom : []
         * uplimit : 5678
         */

        public ProductBean product;

        public static class ProductBean implements Serializable {
            public int id;
            public String isgift;
            public String name;
            public int number;
            public String pic;
            public int price;
            public String uplimit;
            public List<?> product_property;
            public List<?> prom;
        }
    }
}
