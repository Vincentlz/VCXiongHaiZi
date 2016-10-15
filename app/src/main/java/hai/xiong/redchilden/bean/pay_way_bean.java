package hai.xiong.redchilden.bean;

import java.util.List;

/**
 * Created by 徐心意 on 2016/8/6.
 */
public class pay_way_bean {

    /**
     * deliveryList : [{"des":"周一到周五送货","type":"1"},{"des":"双休日及公众假期送货","type":"2"},{"des":"时间不限，工作日双休日及公众假期均可送货","type":"3"}]
     * paymentList : [{"des":"货到付款","type":"1"},{"des":"POS机付款","type":"2"},{"des":"支付宝","type":"3"}]
     * productList : [{"available":false,"gift":false,"id":1102539,"marketPrick":0,"userName":"雅培金装","number":0,"pic":{"picUrl":"/images/12.jpg"},"price":89,"prodNum":83},{"available":false,"gift":false,"id":1102539,"marketPrick":0,"userName":"雅培金装","number":0,"pic":{"picUrl":"/images/12.jpg"},"price":89,"prodNum":10}]
     * response : checkOut
     */

    public String response;
    /**
     * des : 周一到周五送货
     * type : 1
     */

    public List<DeliveryListBean> deliveryList;
    /**
     * des : 货到付款
     * type : 1
     */

    public List<PaymentListBean> paymentList;
    /**
     * available : false
     * gift : false
     * id : 1102539
     * marketPrick : 0
     * userName : 雅培金装
     * number : 0
     * pic : {"picUrl":"/images/12.jpg"}
     * price : 89
     * prodNum : 83
     */

    public List<ProductListBean> productList;

    public static class DeliveryListBean {
        public String des;
        public String type;
    }

    public static class PaymentListBean {
        public String des;
        public String type;
    }

    public static class ProductListBean {
        public boolean available;
        public boolean gift;
        public int id;
        public int marketPrick;
        public String name;
        public int number;
        /**
         * picUrl : /images/12.jpg
         */

        public PicBean pic;
        public int price;
        public int prodNum;

        public static class PicBean {
            public String picUrl;
        }
    }

}

