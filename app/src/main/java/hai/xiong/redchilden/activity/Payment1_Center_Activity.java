package hai.xiong.redchilden.activity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;
import java.util.Random;

import hai.xiong.redchilden.R;
import hai.xiong.redchilden.adapter.BaseListViewAdapter;
import hai.xiong.redchilden.bean.ShoppingCarBean;

import static hai.xiong.redchilden.util.NetUrl.BASE_URL;

/**
 * Created by 徐心意 on 2016/8/10.
 */
public class Payment1_Center_Activity extends BaseActivity {
    @ViewInject(R.id.listview_goods)
    private ListView mlistview_goods;
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

    @Override
    protected int getLayoutId() {
        return R.layout.payment_center_activity;
    }

    @Override
    protected void initCreate() {
        dataBean = (ShoppingCarBean) getIntent().getSerializableExtra("bean");
        mRandom = new Random();
    }

    @Override
    protected void initView() {
        x.view().inject(this);
    }

    @Override
    protected void initListener() {
        listViewAdapter = new ShoppingCarListViewAdapter(dataBean.cart);
        mlistview_goods.setAdapter(listViewAdapter);
    }

    @Override
    protected void initData() {

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

            holder.mTv_no_product_show_bar.setVisibility( View.GONE);

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
