package hai.xiong.redchilden.bean;

/**
 * Created by 徐心意 on 2016/8/8.
 */
public class Common_Bean {

    /**
     * response :  ordersumbit
     * orderInfo : {"orderId":"1112111111","price":"230"," paymentType ":"货到付款"}
     */

    public String response;
    /**
     * orderId : 1112111111
     * price : 230
     *  paymentType  : 货到付款
     */

    public OrderInfoBean orderInfo;

    public static class OrderInfoBean {
        public String userId;
        public String price;
        public String paymentType;
    }
}
