package hai.xiong.redchilden.bean;

/**
 * Created by spring on 2016/8/8.
 */
public class OrderDetailBean2 {

    /**
     * addressDetail : 中关村99号
     * id : 7
     * name : 小芳
     * phoneNumber : 13599996666
     * zipCode : 100000
     */

    public AddressInfoBean address_info;
    /**
     * addressId : 7
     * flag : 1
     * invoice_info : 公司单位
     * orderId : 186
     * payment_info : POS机刷卡
     * price : 175
     * productList : 4:3:1,2|1020:10:1,2|3:7:1,2
     * status : 1
     * time : 2016-08-10
     * userId : 2
     */

    public OrderInfoBean order_info;
    /**
     * type : 2
     */

    public PaymentInfoBean payment_info;
    /**
     * address_info : {"addressDetail":"中关村99号","id":7,"name":"小芳","phoneNumber":"13599996666","zipCode":"100000"}
     * order_info : {"addressId":7,"flag":1,"invoice_info":"公司单位","orderId":186,"payment_info":"POS机刷卡","price":175,"productList":"4:3:1,2|1020:10:1,2|3:7:1,2","status":1,"time":"2016-08-10","userId":2}
     * payment_info : {"type":2}
     * response : orderdetail
     */

    public String response;

    public static class AddressInfoBean {
        public String addressDetail;
        public int id;
        public String name;
        public String phoneNumber;
        public String zipCode;
    }

    public static class OrderInfoBean {
        public int addressId;
        public int flag;
        public String invoice_info;
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
