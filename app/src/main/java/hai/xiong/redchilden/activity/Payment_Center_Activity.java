package hai.xiong.redchilden.activity;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import hai.xiong.redchilden.R;
import hai.xiong.redchilden.adapter.BaseListViewAdapter;
import hai.xiong.redchilden.bean.Common_Bean;
import hai.xiong.redchilden.bean.ShoppingCarBean;
import hai.xiong.redchilden.bean.extortBean;
import hai.xiong.redchilden.bean.pay_way_bean;
import hai.xiong.redchilden.util.Constant;
import hai.xiong.redchilden.util.GlobalConfig;
import hai.xiong.redchilden.util.Log;
import hai.xiong.redchilden.util.NetUrl;
import hai.xiong.redchilden.util.NetUtil;
import hai.xiong.redchilden.util.SpSkuUtils;

import static hai.xiong.redchilden.util.NetUrl.BASE_URL;

/**
 * Created by 徐心意 on 2016/8/6.
 */
public class Payment_Center_Activity extends BaseActivity {

    //返回按钮
    @ViewInject(R.id.back)
    private Button mBack;
    //listView
    @ViewInject(R.id.listview_goods)
    private ListView mlistview_goods;
    // private My_Goods_Holder adapter;
    // private MyHoldedr holdedr;
    private pay_way_bean paybean;
    private Myhodler myhodler;
    private View view;
    private View foot_view;
    private HashMap<String, String> contentMap;
    private HashMap<String, Integer> positionMap;
    private HashMap<String, String> paramsMap;
    private Random mRandom;
    private ShoppingCarBean mShoppingCarBean;
    private Gson mGson;
    private String[] mSizeStrs = new String[]{
            "M", "X", "XL", "XXL", "XXXL"
    };
    private String[] mColorStrs = new String[]{
            "红色", "蓝色", "绿色", "灰色", "橙色", "橘色", "黑色"
    };
    private ShoppingCarBean dataBean;
    private ShoppingCarListViewAdapter listViewAdapter;
    private Common_Bean common_bean;
    private int position;

    public static final int REQUEST_CODE_PAY_WAY = 99;
    public static final int REQUEST_CODE_SEND_TIME = 100;
    public static final int REQUEST_CODE_PAY_LESS = 88;
    public static final int REQUEST_CODE_INVODE = 98;
    public static final int REQUEST_CODE_ADDRESS_LIST = 89;
    private String[] updateItemsKey;


    private class My_Store_OnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            Intent intent_store = new Intent(mContext, ProductDetailsActivity.class);
            int id = paybean.productList.get(position - 1).id;
            intent_store.putExtra("id", id);
            startActivity(intent_store);
        }
    }

    private class My_Data_OnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            paramsMap.put("userId", String.valueOf(GlobalConfig.getLoginUserBean(mContext).userId));
            paramsMap.put("sku", Constant.SKU);

            // 详情填写完整度都判断

            if (contentMap.size() != 5) {
                Toast.makeText(mContext, "请补全订单信息", Toast.LENGTH_SHORT).show();
                return;
            }

            NetUtil.newPostRequest(NetUrl.BASE_URL + "/ordersumbit", paramsMap, new NetUtil.NetworkCallBack(mContext, false, true, true) {
                @Override
                public void onCache(String cache) {

                }

                @Override
                public void onSucces(String result) {
                    parsejson(result);

                    Log.xxy("解释成功" + result);
                }

                @Override
                public void onConnectError() {

                }
            });
        }


    }

    private void parsejson(String result) {

        Gson gson = new Gson();
        common_bean = gson.fromJson(result, Common_Bean.class);


        Intent submit_intent = new Intent(mContext, Submit_Success_Activity.class);
        String paymentType = common_bean.orderInfo.paymentType;
        String orderId = common_bean.orderInfo.userId;
        String price = common_bean.orderInfo.price;
        Log.jyy("orderId" + orderId);
        submit_intent.putExtra("paymentType", paymentType);
        submit_intent.putExtra("price", price);
        submit_intent.putExtra("orderId", orderId);
        Log.xxy("货到付款" + common_bean.orderInfo);

        startActivity(submit_intent);
        new SpSkuUtils(mContext).clear();
        finish();

    }

    private class Pay_wayCacheCallback implements Callback.CacheCallback<String> {
        @Override
        public boolean onCache(String result) {
            return false;
        }

        @Override
        public void onSuccess(String result) {
            Log.xxy("result:" + result);
            parseJson(result);
        }

        @Override
        public void onError(Throwable ex, boolean isOnCallback) {

        }

        @Override
        public void onCancelled(CancelledException cex) {

        }

        @Override
        public void onFinished() {

        }
    }

    private void parseJson(String result) {
        Gson gson = new Gson();
        paybean = gson.fromJson(result, pay_way_bean.class);
        Log.xxy("paybean" + paybean.productList.get(0).name);
       /* adapter = new My_Goods_Holder();
        mlistview_goods.setAdapter(adapter);*/
    }

    @Override
    protected int getLayoutId() {
        return R.layout.payment_center_activity;
    }

    @Override
    protected void initCreate() {
        positionMap = new HashMap<>();
        contentMap = new HashMap<>();
        paramsMap = new HashMap<>();
        Intent intent = getIntent();
        dataBean = (ShoppingCarBean) getIntent().getSerializableExtra("bean");
        mRandom = new Random();

    }

    @Override
    protected void initView() {
        x.view().inject(this);

        //listview的头

        view = View.inflate(mContext, R.layout.listview_head_jiesuan, null);
        myhodler = new Myhodler();
        //Textview
        // x.view().inject(myhodler, view);
        myhodler.tv_reap = (TextView) view.findViewById(R.id.tv_reap);
        myhodler.tv_pay = (TextView) view.findViewById(R.id.tv_pay);
        myhodler.tv_send_time = (TextView) view.findViewById(R.id.tv_send_time);
        myhodler.tv_less = (TextView) view.findViewById(R.id.tv_less);
        myhodler.tv_index = (TextView) view.findViewById(R.id.tv_index);
        //RelativeLayout
        myhodler.rl_reap = (RelativeLayout) view.findViewById(R.id.rl_reap);
        myhodler.rl_pay = (RelativeLayout) view.findViewById(R.id.rl_pay);
        myhodler.rl_goods = (RelativeLayout) view.findViewById(R.id.rl_goods);
        myhodler.rl_goods_way = (RelativeLayout) view.findViewById(R.id.rl_goods_way);
        myhodler.rl_preferential = (RelativeLayout) view.findViewById(R.id.rl_preferential);
        myhodler.rl_receipt = (RelativeLayout) view.findViewById(R.id.rl_receipt);
        foot_view = View.inflate(mContext, R.layout.listview_foot_jiesuan, null);
        myhodler.bt_submit_goods = (Button) foot_view.findViewById(R.id.bt_submit_goods);

        updateItemsKey = new String[]{
                "addressId",
                "paymentType",
                "deliveryType",
                "lipinka",
                "invoice"
        };
        updateTextViews = new TextView[]{
                myhodler.tv_reap, // 收货人
                myhodler.tv_pay, // 支付方式
                myhodler.tv_send_time, // 发送时间
                myhodler.tv_less, // 礼品卡
                myhodler.tv_index, // 发票
        };
    }

    @Override
    protected void initListener() {
        mBack.setOnClickListener(this);
        myhodler.rl_pay.setOnClickListener(this);
        myhodler.rl_goods.setOnClickListener(this);
        myhodler.rl_preferential.setOnClickListener(this);
        myhodler.rl_receipt.setOnClickListener(this);
        myhodler.rl_reap.setOnClickListener(this);
        myhodler.bt_submit_goods.setOnClickListener(new My_Data_OnClickListener());
        mlistview_goods.setOnItemClickListener(new My_Store_OnItemClickListener());
        listViewAdapter = new ShoppingCarListViewAdapter(dataBean.cart);
        mlistview_goods.setAdapter(listViewAdapter);
    }

    @Override
    protected void initData() {
        mlistview_goods.addHeaderView(view, null, false);
        // mlistview_goods.ad
        mlistview_goods.addFooterView(foot_view);
        // 请求参数传递
        NetUtil.getRequest(NetUrl.BASE_URL + "/checkout", new Pay_wayCacheCallback());

    }

    @Override
    protected void onInnerClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.rl_pay:
                intent = new Intent(mContext, Pay_Way_Activity.class);
                intent.putExtra("position", positionMap.get("paymentType"));
                startActivityForResult(intent, REQUEST_CODE_PAY_WAY);
                break;
            case R.id.rl_goods:
                intent = new Intent(mContext, Send_Time_Activity.class);
                intent.putExtra("position", positionMap.get("deliveryType"));
                startActivityForResult(intent, REQUEST_CODE_SEND_TIME);
                break;
            case R.id.rl_preferential:
                intent = new Intent(mContext, Pay_Less_Activity.class);
                intent.putExtra("position", positionMap.get("lipinka"));
                startActivityForResult(intent, REQUEST_CODE_PAY_LESS);
                break;
            case R.id.rl_receipt:
                intent = new Intent(mContext, Write_Message_Activity.class);
                intent.putExtra("position", positionMap.get("invoice"));
                startActivityForResult(intent, REQUEST_CODE_INVODE);
                break;
            case R.id.rl_reap:
                intent = new Intent(mContext, AddressListActivity.class);
                intent.putExtra("position", positionMap.get("addressId"));
                startActivityForResult(intent, REQUEST_CODE_ADDRESS_LIST);
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (intent == null) {
            return;
        }

        // 获取页面传输数据
        String item = intent.getStringExtra("item");
        int position = intent.getIntExtra("position", 1);
        String content = intent.getStringExtra("content");
        String params = intent.getStringExtra("params");

        // TODO 礼品卡，最终去除

        switch (requestCode) {
            case REQUEST_CODE_INVODE: // 发票

                extortBean.InvoiceListBean invoiceBean = (extortBean.InvoiceListBean) intent.getSerializableExtra("content");
                paramsMap.put("invoiceType", String.valueOf(invoiceBean.type));
                paramsMap.put("invoiceTitle", String.valueOf(invoiceBean.title));
                paramsMap.put("invoiceContent", String.valueOf(invoiceBean.content));

                contentMap.put("invoice", (invoiceBean.type == 1 ? "个人" : "单位") + "/" + invoiceBean.title);
                break;
            default:
                contentMap.put(item, content);
                positionMap.put(item, position); // 发票，地址页面需要处理
                paramsMap.put(item, params); // 发票，地址需要处理
                break;
        }

        updateContent();
    }


    private TextView[] updateTextViews;

    /**
     * 更新显示内容
     */
    private void updateContent() {
        for (int x = 0; x < updateItemsKey.length; x++) {
            updateTextViews[x].setText(contentMap.get(updateItemsKey[x]));
        }
    }

    private class ShoppingCarListViewAdapter extends BaseListViewAdapter<ShoppingCarBean.CartBean> {
        public ShoppingCarListViewAdapter(List<ShoppingCarBean.CartBean> dataList) {
            super(dataList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.listview_item_product_list_show, null);

                ViewHolder holder = new ViewHolder();
                convertView.setTag(holder);

                x.view().inject(holder, convertView);
            }

            ViewHolder holder = (ViewHolder) convertView.getTag();

            ShoppingCarBean.CartBean cartBean = (ShoppingCarBean.CartBean) getItem(position);


            ImageOptions options = new ImageOptions.Builder().setImageScaleType(ImageView.ScaleType.FIT_XY).build();
            x.image().bind(holder.mIv_product, String.format("%s/%s", BASE_URL, cartBean.product.pic), options);

            holder.mTv_product_num.setText(String.valueOf(cartBean.prodNum));

            int randomInt = mRandom.nextInt(mColorStrs.length);

            holder.mTv_product_color.setText(mColorStrs[randomInt]);

            holder.mTv_product_size.setText(mSizeStrs[mRandom.nextInt(mSizeStrs.length)]);
            holder.mTv_product_price.setText("￥" + cartBean.product.price);
            holder.mTv_product_title.setText(cartBean.product.name);
            holder.mTv_product_xiaoji.setText("￥" + (cartBean.product.price * cartBean.prodNum));

            int grayColor = getResources().getColor(R.color.darkgray);
            int blackColor = getResources().getColor(R.color.black);
            int redColor = getResources().getColor(R.color.bg_title);

            boolean isEmpty = cartBean.product.number == 0;

            holder.mTv_no_product_show_bar.setVisibility(View.GONE);

            int textColor = isEmpty ? grayColor : blackColor;
            int textRedColor = isEmpty ? grayColor : redColor;

            // 是否有货，改变字体颜色
            holder.mTv_product_num.setTextColor(textColor);
            holder.mTv_product_color.setTextColor(textColor);
            holder.mTv_product_size.setTextColor(textColor);
            holder.mTv_product_price.setTextColor(textRedColor);
            holder.mTv_product_title.setTextColor(textColor);
            holder.mTv_product_xiaoji.setTextColor(textRedColor);

            return convertView;
        }
    }

   /* class My_Goods_Holder extends BaseAdapter {


        @Override
        public int getCount() {
            return paybean.productList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            holdedr = new MyHoldedr();
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.listview_item_product_list_show, null);
                holdedr.tv_product_title = (TextView) convertView.findViewById(R.id.tv_product_title);
                holdedr.iv_product = (ImageView) convertView.findViewById(R.id.iv_product);
                holdedr.tv_product_num = (TextView) convertView.findViewById(R.id.tv_product_num);
                holdedr.tv_product_color = (TextView) convertView.findViewById(R.id.tv_product_color);
                holdedr.tv_product_xiaoji = (TextView) convertView.findViewById(R.id.tv_product_xiaoji);
                holdedr.tv_product_size = (TextView) convertView.findViewById(R.id.tv_product_size);
                holdedr.tv_product_price = (TextView) convertView.findViewById(R.id.tv_product_price);
                convertView.setTag(holdedr);
            } else {
                holdedr = (MyHoldedr) convertView.getTag();
            }

            pay_way_bean.ProductListBean productListBean = paybean.productList.get(position);
            x.image().bind(holdedr.iv_product, NetUrl.BASE_URL + paybean.productList.get(position).pic.picUrl);
            holdedr.tv_product_title.setText(productListBean.name);
            holdedr.tv_product_num.setText(productListBean.prodNum + "");
            holdedr.tv_product_price.setText(productListBean.price + "");
            holdedr.tv_product_xiaoji.setText((productListBean.prodNum * productListBean.price) + "");
            return convertView;
        }
    }
*/
   /* class MyHoldedr {
        TextView tv_product_title;
        ImageView iv_product;
        TextView tv_product_num;//数量
        TextView tv_product_color;//颜色
        TextView tv_product_xiaoji;//小计
        TextView tv_product_size;//尺寸
        TextView tv_product_price;//价格

    }*/

    class Myhodler {
        //listview的头
        //收货人
        TextView tv_reap;
        RelativeLayout rl_reap;
        //支付
        TextView tv_pay;
        RelativeLayout rl_pay;
        //送货
        TextView tv_send_time;
        RelativeLayout rl_goods;
        //送货方式
        RelativeLayout rl_goods_way;
        //优惠券
        TextView tv_less;
        RelativeLayout rl_preferential;
        //发票
        TextView tv_index;
        RelativeLayout rl_receipt;

        //提交订单
        Button bt_submit_goods;
    }

    class ViewHolder {
        @ViewInject(R.id.iv_product)
        ImageView mIv_product; // 商品图

        @ViewInject(R.id.tv_product_title)
        TextView mTv_product_title; // 商品标题

        @ViewInject(R.id.tv_product_num)
        TextView mTv_product_num; // 商品数量

        @ViewInject(R.id.tv_product_size)
        TextView mTv_product_size; // 商品尺码

        @ViewInject(R.id.tv_product_color)
        TextView mTv_product_color; // 商品颜色

        @ViewInject(R.id.tv_product_price)
        TextView mTv_product_price; // 商品价格

        @ViewInject(R.id.tv_product_xiaoji)
        TextView mTv_product_xiaoji; //商品小计

        @ViewInject(R.id.tv_no_product_show_bar)
        TextView mTv_no_product_show_bar; // 无货商品显示头
    }
}

