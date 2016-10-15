package hai.xiong.redchilden.bean;

/**
 * Created by spring on 2016/8/8.
 */
public class OrderDetailBean {

    /**
     * type : 1
     */

    public DeliveryInfoBean delivery_info;
    /**
     * addressId : 1
     * delivery_info : 周一至周五送货
     * flag : 4
     * orderId : 9
     * payment_info : POS机刷卡
     * price : 100
     * productList : 101211:2:2,3|10211:2:4,3
     * status : 4
     * time : 2016-06-28
     * userId : 2
     */

    public OrderInfoBean order_info;
    /**
     * type : 2
     */

    public PaymentInfoBean payment_info;
    /**
     * delivery_info : {"type":1}
     * order_info : {"addressId":1,"delivery_info":"周一至周五送货 ","flag":4,"orderId":9,"payment_info":"POS机刷卡","price":100,"productList":"101211:2:2,3|10211:2:4,3","status":4,"time":"2016-06-28","userId":2}
     * payment_info : {"type":2}
     * response : orderdetail
     */

    public String response;

    public static class DeliveryInfoBean {
        public int type;
    }

    public static class OrderInfoBean {
        public int addressId;
        public String delivery_info;
        public int flag;
        public int orderId;
        public String payment_info;
        public int price;
        public String productList;
        public int status;
        public String time;
        public int userId;
    }

    public static class PaymentInfoBean {
        public int type;
    }
}
