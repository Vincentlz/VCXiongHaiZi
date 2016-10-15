package hai.xiong.redchilden.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hai.xiong.redchilden.R;
import hai.xiong.redchilden.bean.MyBrandBean;
import hai.xiong.redchilden.bean.OrderDetailBean2;
import hai.xiong.redchilden.bean.ProductDetails;
import hai.xiong.redchilden.bean.pay_way_bean.ProductListBean;
import hai.xiong.redchilden.bean.OrderDetailBean;
import hai.xiong.redchilden.bean.pay_way_bean;
import hai.xiong.redchilden.util.Log;
import hai.xiong.redchilden.util.NetUrl;
import hai.xiong.redchilden.util.NetUtil;

/**
 * Created by spring on 2016/8/8.
 */
public class OrderDetailActivity extends BaseActivity {
    //物流查询
    @ViewInject(R.id.rl_deliver_query)
    private RelativeLayout rl_deliver_query;
    @ViewInject(R.id.tv_order_id)
    private TextView tv_order_id;
    @ViewInject(R.id.tv_name)
    private TextView tv_name;
    @ViewInject(R.id.tv_phone)
    private TextView tv_phone;
    @ViewInject(R.id.tv_address_detail)
    private TextView tv_address_detail;

    @ViewInject(R.id.tv_order_state)
    private TextView tv_order_state;
    //送货方式
    @ViewInject(R.id.tv_send_method)
    private TextView tv_send_method;
    //支付方式
    @ViewInject(R.id.tv_payment)
    private TextView tv_payment;
    @ViewInject(R.id.tv_ordered_time)
    private TextView tv_ordered_time;
    @ViewInject(R.id.tv_deliver_time)
    private TextView tv_deliver_time;
    //是否开具发票
    @ViewInject(R.id.tv_is_invoice)
    private TextView tv_is_invoice;
    //发票抬头
    @ViewInject(R.id.tv_bill)
    private TextView tv_bill;
    //送货要求
    @ViewInject(R.id.tv_send_request)
    private TextView tv_send_request;
   /* //商品名称
    @ViewInject(R.id.product_name)
    private TextView product_name;
    @ViewInject(R.id.product_color)
    private TextView product_color;
    @ViewInject(R.id.product_size)
    private TextView product_size;
    //数量
    @ViewInject(R.id.product_counts)
    private TextView product_counts;
    @ViewInject(R.id.product_price)
    private TextView product_price;*/
    //数量总计
    @ViewInject(R.id.tv_product_count)
    private TextView tv_product_count;
    //原始金额
    @ViewInject(R.id.product_original_price)
    private TextView product_original_price;
    //运费
    @ViewInject(R.id.product_deliver_cost)
    private TextView product_deliver_cost;
    //促销金额
    @ViewInject(R.id.promotion_price)
    private TextView promotion_price;
    //应支付金额(不含运费)
    @ViewInject(R.id.product_price_no_extra)
    private TextView product_price_no_extra;
    //取消订单
    @ViewInject(R.id.btn_cancel_order)
    private Button btn_cancel_order;
    @ViewInject(R.id.back)
    private ImageView back;
    @ViewInject(R.id.ll_product_list)
    private LinearLayout ll_product_list;
    private String orderId;

    // 记录商品的总数量
    public int count = 0;
    //总价
    public float countPrice = 0;
    private OrderDetailBean2 orderDetailBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_order_detail;
    }

    @Override
    protected void initCreate() {
        x.view().inject(this);
        ProgressDialog progressDialog;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {
        back.setOnClickListener(this);
        rl_deliver_query.setOnClickListener(this);
        btn_cancel_order.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        orderId = getIntent().getStringExtra("id");
        Log.hb("orderId::" + orderId);
        String url = NetUrl.BASE_URL + "/orderdetail?orderId=" + orderId;
        Log.hb("urlurlurl::"+url);
        doRequest(url);
    }

    private void doRequest(String url) {
        NetUtil.newGetRequest(url, new NetUtil.NetworkCallBack(mContext,false,true,true) {
                    @Override
                    public void onCache(String cache) {

                    }

                    @Override
                    public void onSucces(String result) {
                        parseGson(result);
                    }

                    @Override
                    public void onConnectError() {

                    }
            });

    }

    private void parseGson(String result) {

        Gson gson = new Gson();

        //Log.fms("result:" + result);

        orderDetailBean = gson.fromJson(result, OrderDetailBean2.class);
       // int delivery_info_type = orderDetailBean.delivery_info.type;

        int addressId = orderDetailBean.order_info.addressId;
      //  String delivery_info = orderDetailBean.order_info.delivery_info;
        int flag = orderDetailBean.order_info.flag;
        int orderId = orderDetailBean.order_info.orderId;
        Log.hb("orderId"+orderId);
        String paymentInfo = orderDetailBean.order_info.payment_info;
        int price = orderDetailBean.order_info.price;
        String productList = orderDetailBean.order_info.productList;
        int status = orderDetailBean.order_info.status;
        String time = orderDetailBean.order_info.time;
        int userId = orderDetailBean.order_info.userId;
        int payment_info_type = orderDetailBean.payment_info.type;


        tv_order_id.setText(orderDetailBean.order_info.orderId+"");

        if(orderDetailBean.order_info.status == 1){
            tv_order_state.setText("未处理");
        }else if(orderDetailBean.order_info.status == 2){
            tv_order_state.setText("交易成功");
            //交易成功不显示取消订单
            btn_cancel_order.setVisibility(View.GONE);
        }else if(orderDetailBean.order_info.status == 3){
            tv_order_state.setText("已完成");
            btn_cancel_order.setVisibility(View.GONE);

        }else if(orderDetailBean.order_info.status == 4){
            tv_order_state.setText("已取消");
            btn_cancel_order.setVisibility(View.GONE);

        }
        String payment_info = orderDetailBean.order_info.payment_info;
        Log.hb("payment_info:" + payment_info);
        tv_payment.setText(payment_info);//支付方式
        tv_ordered_time.setText(orderDetailBean.order_info.time);//订单生成时间
        Log.hb("orderDetailBean.order_info.time:" + orderDetailBean.order_info.time);
        tv_deliver_time.setText(orderDetailBean.order_info.time);//发货时间

        setProductList(productList);
    }

    public void setProductList(final String productList) {
        final String[] split = productList.split("\\|");

        for (int i = 0; i < split.length; i++) {//遍历多少商品
            final View view = View.inflate(mContext,R.layout.listview_item_product_list_show,null);
            final TextView product_name = (TextView) view.findViewById(R.id.tv_product_title);
            final TextView product_color = (TextView) view.findViewById(R.id.tv_product_color);
            final TextView product_size = (TextView) view.findViewById(R.id.tv_product_size);
            final TextView product_count = (TextView) view.findViewById(R.id.tv_product_num);
            final TextView product_price = (TextView) view.findViewById(R.id.tv_product_price);
            final TextView product_add = (TextView) view.findViewById(R.id.tv_product_xiaoji);
            final ImageView iv_product = (ImageView) view.findViewById(R.id.iv_product);
            final String productUrl = NetUrl.BASE_URL + "/checkout?sku="+split[i];
            Log.hb("productUrlproductUrlproductUrl:"+productUrl);
            NetUtil.newGetRequest(productUrl, new NetUtil.NetworkCallBack(mContext,false,true,true) {
                @Override
                public void onCache(String cache) {

                }

                @Override
                public void onSucces(String result) {
                    // Log.hb("productUrl"+result);
                    Gson gson = new Gson();
                    pay_way_bean bean = gson.fromJson(result, pay_way_bean.class);

                    List<ProductListBean> list = bean.productList;

                    count += list.size();//商品总数量
                    countPrice += list.size()*list.get(0).price;//商品总价

                    product_name.setText(list.get(0).name);
                    product_color.setText("红色");
                    product_size.setText("M");
                    product_count.setText(list.size()+"");
                    product_price.setText(list.get(0).price+"");
                    product_add.setText(list.size()*list.get(0).price+"");
                    // Log.hb("list.get(0).pic.picUrl"+list.get(0).pic.picUrl);

                    ImageOptions options = new ImageOptions.Builder().setImageScaleType(ImageView.ScaleType.FIT_XY).build();
                    x.image().bind(iv_product,NetUrl.BASE_URL+list.get(0).pic.picUrl, options);
                    //x.image().bind(iv_product,list.get(0).pic.picUrl);

                  /*  //数量总计
                    private TextView tv_product_count;
                    //原始金额
                    private TextView product_original_price;
                    //运费
                    private TextView product_deliver_cost;
                    //促销金额
                    private TextView promotion_price;
                    //应支付金额(不含运费)
                    private TextView product_price_no_extra;
*/
                    tv_product_count.setText(count + "件");
                    product_original_price.setText("￥" + (int)countPrice + ".00");
                    product_deliver_cost.setText("￥" +"10.00");
                    promotion_price.setText("-￥"+ "0.00");
                    int money = (int) (countPrice+10);
                    product_price_no_extra.setText("￥" + money + ".00");

                    // 跳转到对应的商品详情界面
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(mContext, ProductDetailsActivity.class);
                            intent.putExtra("id",Integer.parseInt(split[0].split(":")[0]));
                            Log.hb("split[0].split(\":\")[0]:::"+split[0].split(":")[0]);
                            startActivity(intent);
                        }
                    });
                    ll_product_list.addView(view);

                }

                @Override
                public void onConnectError() {

                }
            });

        }

    }

    @Override
    protected void onInnerClick(View view) {
        super.onInnerClick(view);
        switch (view.getId()) {
            case R.id.rl_deliver_query:
                Intent intent = new Intent(this, WuliuSearchActivity.class);
                intent.putExtra("orderId", orderId);
                startActivity(intent);

                break;
            case R.id.btn_cancel_order:
                Log.hb("btn_cancel_order"+btn_cancel_order);
                AlertDialog.Builder adb = new AlertDialog.Builder(mContext);
                adb.setTitle("提示");
                adb.setMessage("您确定要取消订单吗?");
                adb.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        CancelOrderRequest();
                    }
                });
                adb.setNegativeButton("取消", null);
                adb.show();
                break;


        }
    }

    private void CancelOrderRequest() {
        String orderCancelurl = NetUrl.BASE_URL + "/ordercancel";
        Log.hb("orderDetailBean.order_info.status before::"+orderDetailBean.order_info.status);
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("orderId", orderId);
        NetUtil.newPostRequest(orderCancelurl, params, new NetUtil.NetworkCallBack(mContext,false,true,true) {
            @Override
            public void onCache(String cache) {

            }

            @Override
            public void onSucces(String result) {
             /* "response": "ordercancel",
                        "result": true*/
                JSONObject object = new JSONObject();
                boolean isSuccess = object.optBoolean(result);
                Log.hb("orderDetailBean.order_info.status  after::"+orderDetailBean.order_info.status);
                finish();
            }

            @Override
            public void onConnectError() {

            }
        });

    }
}