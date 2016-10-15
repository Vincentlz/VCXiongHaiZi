package hai.xiong.redchilden.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import hai.xiong.redchilden.R;
import hai.xiong.redchilden.activity.LoginActivity;
import hai.xiong.redchilden.activity.MainActivity;
import hai.xiong.redchilden.activity.Payment_Center_Activity;
import hai.xiong.redchilden.activity.ProductDetailsActivity;
import hai.xiong.redchilden.adapter.BaseListViewAdapter;
import hai.xiong.redchilden.bean.ShoppingCarBean;
import hai.xiong.redchilden.util.GlobalConfig;
import hai.xiong.redchilden.util.Log;
import hai.xiong.redchilden.util.NetUtil;
import hai.xiong.redchilden.util.SpSkuUtils;
import hai.xiong.redchilden.view.ShowListView;

import static hai.xiong.redchilden.R.id.btn_go_home;
import static hai.xiong.redchilden.R.id.rl_empty_shopping_car;
import static hai.xiong.redchilden.util.NetUrl.BASE_URL;

/**
 * 购物车Fragment
 * Created by Administrator on 2016/8/6 0006.
 */
public class ShoppingCarFragment extends BaseFragment {

    public static final int REQUEST_CODE_LOGIN_ACTIVITY = 1;
    private SpSkuUtils spSkuUtils;

    /**
     * 购物车请求回调
     */
    private class RequestCommonCallback implements Callback.CommonCallback<String> {

        @Override
        public void onSuccess(String result) {
            parseJson(result);
        }

        @Override
        public void onError(Throwable ex, boolean isOnCallback) {
            ex.printStackTrace();
        }

        @Override
        public void onCancelled(CancelledException cex) {
            cex.printStackTrace();
        }

        @Override
        public void onFinished() {
            Log.fms("RequestFinished");
        }
    }

    /**
     * ListView条目点击监听器
     */
    private class MyOnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(mContext, ProductDetailsActivity.class);
            intent.putExtra("id", mShoppingCarBean.cart.get(position).product.id);
            startActivityForResult(intent, 1);
        }
    }

    /**
     * MyNetworkCallBack
     */
    private class MyNetworkCallBack extends NetUtil.NetworkCallBack {

        /**
         * 构造
         *
         * @param context            用于创建对话框
         * @param isCache
         * @param isShowDialog
         * @param isShowErrorMessage
         */
        public MyNetworkCallBack(Context context, boolean isCache, boolean isShowDialog, boolean isShowErrorMessage) {
            super(context, isCache, isShowDialog, isShowErrorMessage);
        }

        /**
         * 获取到Cache
         *
         * @param cache 为null时代表当前没有caches
         */
        @Override
        public void onCache(String cache) {
            parseJson(cache);
        }

        /**
         * 连接成功回调方法
         *
         * @param result
         */
        @Override
        public void onSucces(String result) {
            parseJson(result);
        }

        /**
         * 当连接失败时的回调方法
         */
        @Override
        public void onConnectError() {

        }
    }

    /**
     * ShoppingCarListViewAdapter
     */
    private class ShoppingCarListViewAdapter extends BaseListViewAdapter<ShoppingCarBean.CartBean> {

        /**
         * ListViewAdapter
         *
         * @param dataList 数据列表
         */
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

            holder.mTv_no_product_show_bar.setVisibility(isEmpty ? View.VISIBLE : View.GONE);

            int textColor = isEmpty ? grayColor : blackColor;
            int textRedColor = isEmpty ? grayColor : redColor;

            // 是否有货，改变字体颜色
            holder.mTv_product_num.setTextColor(textColor);
            holder.mTv_product_color.setTextColor(textColor);
            holder.mTv_product_size.setTextColor(textColor);
            holder.mTv_product_price.setTextColor(textRedColor);
            holder.mTv_product_title.setTextColor(textColor);
            holder.mTv_product_xiaoji.setTextColor(textRedColor);

            // 是否有货，添加删除删除线
            holder.mTv_product_num.setPaintFlags(isEmpty ? Paint.STRIKE_THRU_TEXT_FLAG : Paint.HINTING_OFF);
            holder.mTv_product_color.setPaintFlags(isEmpty ? Paint.STRIKE_THRU_TEXT_FLAG : Paint.HINTING_OFF);
            holder.mTv_product_size.setPaintFlags(isEmpty ? Paint.STRIKE_THRU_TEXT_FLAG : Paint.HINTING_OFF);
            holder.mTv_product_price.setPaintFlags(isEmpty ? Paint.STRIKE_THRU_TEXT_FLAG : Paint.HINTING_OFF);
            holder.mTv_product_title.setPaintFlags(isEmpty ? Paint.STRIKE_THRU_TEXT_FLAG : Paint.HINTING_OFF);
            holder.mTv_product_xiaoji.setPaintFlags(isEmpty ? Paint.STRIKE_THRU_TEXT_FLAG : Paint.HINTING_OFF);

            return convertView;
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

    /**
     * Gson
     */
    private Gson mGson;

    private ShoppingCarListViewAdapter listViewAdapter;

    @ViewInject(R.id.back)
    private View mBack; // 返回

    @ViewInject(R.id.tv_title)
    private TextView mTvTitle; // 标题

    @ViewInject(R.id.tv_next)
    private TextView mTvNext; // 下一步操作(标题右侧按钮)

    @ViewInject(R.id.tv_shopping_count)
    private TextView mTv_shopping_count; // 购物车商品总数

    @ViewInject(R.id.tv_integrll)
    private TextView mTv_integrll; // 赠送积分

    @ViewInject(R.id.tv_product_total_money)
    private TextView mTv_product_total_money; // 商品总额

    @ViewInject(btn_go_home)
    private Button mBtn_go_home; // 随便逛逛

    @ViewInject(R.id.lv_shopping_car_show)
    private ShowListView mLv_shopping_car_show;

    @ViewInject(rl_empty_shopping_car)
    private View mRl_empty_shopping_car; // 空购物车遮罩层

    @ViewInject(R.id.tv_empty_shopping_car)
    private TextView mTv_empty_shopping_car;

    @ViewInject(R.id.iv_empty_shopping_car)
    private ImageView mIv_empty_shopping_car;

    @ViewInject(R.id.btn_go_commit)
    private Button mBtn_go_commit;

    private Random mRandom;

    private ShoppingCarBean mShoppingCarBean;

    private String[] mSizeStrs = new String[]{
            "M", "X", "XL", "XXL", "XXXL"
    };
    private String[] mColorStrs = new String[]{
            "红色", "蓝色", "绿色", "灰色", "橙色", "橘色", "黑色"
    };

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shopping_car;
    }

    @Override
    protected void initCreate() {
        mGson = new Gson();
        mRandom = new Random();
        spSkuUtils = new SpSkuUtils(mContext);
    }

    private static final int TIPS_SHOPPING_CAR_EMPTY = 0;
    private static final int TIPS_PLEASE_LOGIN = 1;
    private static final int TIPS_SHOW_SHOPPING_CAR = 2;
    private int localTipsStatus;

    /**
     * 切换提示
     *
     * @param tipsMode 提示模式类型
     */
    private void switchTips(int tipsMode) {
        localTipsStatus = tipsMode;

        switch (tipsMode) {
            case TIPS_PLEASE_LOGIN: // 登陆提示
                mRl_empty_shopping_car.setVisibility(View.VISIBLE);
                mTv_empty_shopping_car.setText("亲尚未登录~");
                mBtn_go_home.setText("前往登陆");
                mTvNext.setVisibility(View.GONE);
                mIv_empty_shopping_car.setImageResource(R.drawable.empty_img);

                break;
            case TIPS_SHOPPING_CAR_EMPTY: // 购物车为空
                mRl_empty_shopping_car.setVisibility(View.VISIBLE);
                mTv_empty_shopping_car.setText("您的购物车没有商品\n快去挑件喜欢的商品吧");
                mTvNext.setVisibility(View.GONE);
                mBtn_go_home.setText("随便逛逛");
                mIv_empty_shopping_car.setImageResource(R.drawable.shopcar);
                break;
            case TIPS_SHOW_SHOPPING_CAR: // 显示购物车
                mRl_empty_shopping_car.setVisibility(View.GONE);
                mTvNext.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    protected void initView(View rootView) {
        x.view().inject(this, rootView);
        mTvTitle.setText("购物车");
        mTvNext.setText("");
        mTvNext.setBackgroundResource(R.drawable.delete);
        mTvNext.setVisibility(View.GONE);
        mBack.setVisibility(View.GONE);
    }

    @Override
    protected void initListener() {
        mTvNext.setOnClickListener(this);
        mBtn_go_home.setOnClickListener(this);
        listViewAdapter = new ShoppingCarListViewAdapter(null);
        mLv_shopping_car_show.setAdapter(listViewAdapter);
        mLv_shopping_car_show.setOnItemClickListener(new MyOnItemClickListener());
        mBtn_go_commit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_next: // 清空购物车
                spSkuUtils.clear();
                Toast.makeText(mContext, "已清空购物车", Toast.LENGTH_SHORT).show();
                switchTips(TIPS_SHOPPING_CAR_EMPTY);
                ((MainActivity) mContext).rb_menu_shopping.invalidate();
                onResume();
                break;
            case R.id.btn_go_commit: // 去结算
                Intent intent = new Intent(mContext, Payment_Center_Activity.class);
                intent.putExtra("bean",mShoppingCarBean);
                startActivityForResult(intent, 1);
                break;
            case R.id.btn_go_home: //跳转到主页

                switch (localTipsStatus) {
                    case TIPS_SHOPPING_CAR_EMPTY: // 购物为空
                        ((MainActivity) mContext).FragmentChange(0);
                        break;
                    case TIPS_PLEASE_LOGIN: // 跳转登陆
                        startActivityForResult(new Intent(mContext, LoginActivity.class), 1);
                        break;
                }

                break;
        }
    }

    @Override
    protected void initData() {
    }

    /**
     * 解析Json
     *
     * @param jsonStr json字符串
     */
    private void parseJson(String jsonStr) {

        mShoppingCarBean = mGson.fromJson(jsonStr, ShoppingCarBean.class);
        Collections.sort(mShoppingCarBean.cart, new Comparator<ShoppingCarBean.CartBean>() {
            @Override
            public int compare(ShoppingCarBean.CartBean lhs, ShoppingCarBean.CartBean rhs) {
                return rhs.product.number - lhs.product.number;
            }
        });
        listViewAdapter.setDataList(mShoppingCarBean.cart);
        mLv_shopping_car_show.notifyDataSetChanged();

        // 根据购物车是否有内容显示状态
        switchTips(listViewAdapter.getCount() == 0 ? TIPS_SHOPPING_CAR_EMPTY : TIPS_SHOW_SHOPPING_CAR);
        mTv_shopping_count.setText(String.valueOf(mShoppingCarBean.totalCount));
        mTv_product_total_money.setText("￥" + mShoppingCarBean.totalPrice);
        mTv_integrll.setText(String.valueOf(mShoppingCarBean.totalPoint));
    }

    @Override
    public void onResume() {
        super.onResume();

        // 获取UserID
        if (GlobalConfig.isLogin(mContext)) {
            int userId = GlobalConfig.getLoginUserBean(mContext).userId;
            switchTips(TIPS_SHOPPING_CAR_EMPTY);

            String sku = SpSkuUtils.get(mContext);
            if (!TextUtils.isEmpty(sku)) {
                NetUtil.newGetRequest(String.format("%s/cart?sku=%s,&userid=%s", BASE_URL, sku, userId), new MyNetworkCallBack(mContext, true, true, true));
            }
        } else {
            switchTips(TIPS_PLEASE_LOGIN);
        }

    }
}
