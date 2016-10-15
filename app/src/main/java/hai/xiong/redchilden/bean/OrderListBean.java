package hai.xiong.redchilden.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by spring on 2016/8/6.
 */
public class OrderListBean {

    /**
     * orderList : [{"flag":"1","orderId":"412423145","paymentType":"支付宝","price":"1234.23","status":"4","time":"2011/10/10"},{"flag":"1","orderId":"412423145","paymentType":"支付宝","price":"1234.23","status":"交易成功","time":"2011/10/100 12:16:40"},{"flag":"1","orderId":"412423145","paymentType":"支付宝","price":"1234.23","status":"已完成","time":"2011/10/100 12:16:40"}]
     * response : orderlist
     */

    public String response;
    /**
     * flag : 1
     * orderId : 412423145
     * paymentType : 支付宝
     * price : 1234.23
     * status : 4
     * time : 2011/10/10
     */

    public List<OrderlistBean> orderList ;

    public static class OrderlistBean implements Serializable{
        public String flag;
        public String orderId;
        public String paymentType;
        public String price;
        public String status;
        public String time;
    }
}
