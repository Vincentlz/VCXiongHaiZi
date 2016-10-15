package hai.xiong.redchilden.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import hai.xiong.redchilden.R;
import hai.xiong.redchilden.bean.LoginBean;
import hai.xiong.redchilden.bean.ProductDetails;
import hai.xiong.redchilden.util.Constant;
import hai.xiong.redchilden.util.GlobalConfig;
import hai.xiong.redchilden.util.Log;
import hai.xiong.redchilden.util.NetUrl;
import hai.xiong.redchilden.util.NetUtil;
import hai.xiong.redchilden.util.SPListUtils;
import hai.xiong.redchilden.util.SpSkuUtils;
import hai.xiong.redchilden.view.GalleryFlow;

/**
 * Created by dell on 2016/8/7.
 * 商品详情界面
 */
public class ProductDetailsActivity extends BaseActivity {
    //传递到此界面的商品
    private int productId;
    //标题
    @ViewInject(R.id.title)
    private TextView title;
    //商品名称
    @ViewInject(R.id.textProductNameValue)
    private TextView textProductNameValue;
    //商品编号
    @ViewInject(R.id.textProductIdValue)
    private TextView textProductIdValue;
    //市场价
    @ViewInject(R.id.textOriginalPriceValue)
    private TextView textOriginalPriceValue;
    //售价
    @ViewInject(R.id.textPriceValue)
    private TextView textPriceValue;
    //数量输入框
    @ViewInject(R.id.prodNumValue)
    private EditText prodNumValue;
    //加入购物车
    @ViewInject(R.id.textPutIntoShopcar)
    private TextView textPutIntoShopcar;
    //加入收藏
    @ViewInject(R.id.textProdToCollect)
    private TextView textProdToCollect;
    //商品描述
    @ViewInject(R.id.relDescription)
    private RelativeLayout relDescription;
    //库存布局
    @ViewInject(R.id.relProdStock)
    private RelativeLayout relProdStock;
    //查看库存
    @ViewInject(R.id.textProdIsStock)
    private TextView textProdIsStock;
    //评论条数
    @ViewInject(R.id.textProductCommentNum)
    private TextView textProductCommentNum;
    //购买评论
    @ViewInject(R.id.relProductComment)
    private RelativeLayout relProductComment;
    //商品描述
    @ViewInject(R.id.textProductInfoIsNull)
    private TextView textProductInfoIsNull;
    //显示的ScrollView
    @ViewInject(R.id.scrollview)
    private ScrollView scrollview;
    //图片展示的画廊
    @ViewInject(R.id.productGallery)
    private GalleryFlow productGallery;
    //返回键
    @ViewInject(R.id.btn_back)
    private TextView btn_back;
    //订购电话
    @ViewInject(R.id.orderTelTv)
    private TextView orderTelTv;
    private ProductDetails.ProductBean productBean;
    private ProductDetails productDetails;
    private boolean isProductInfo = false;
    private LoginBean.UserInfoBean userId;
    private SpSkuUtils skuUtils;
    private String edit_text;
    private int num;
    private boolean isShow = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_product_detail;
    }

    @Override
    protected void initCreate() {
        x.view().inject(this);
        //获得商品的ID
        productId = getIntent().getIntExtra("id", 0);

        SPListUtils spUtils = new SPListUtils(mContext, Constant.SP_KEY_VISIT_HISTORY, 10);
        spUtils.add(String.valueOf(productId));

        skuUtils = new SpSkuUtils(mContext);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {
        btn_back.setOnClickListener(this);
        relDescription.setOnClickListener(this);
        orderTelTv.setOnClickListener(this);
        relProductComment.setOnClickListener(this);
        textPutIntoShopcar.setOnClickListener(this);
        textProdToCollect.setOnClickListener(this);
        relProdStock.setOnClickListener(this);
    }

    @Override
    protected void onInnerClick(View view) {
        super.onInnerClick(view);
        switch (view.getId()) {
            case R.id.relDescription:
                if (!isProductInfo) {
                    //不是商品页面，跳入商品详情页面
                    scrollview.setVisibility(View.GONE);
                    textProductInfoIsNull.setVisibility(View.VISIBLE);
                    textProductInfoIsNull.setText(productBean.productdesc);
                    title.setText("商品描述");
                    isProductInfo = true;
                    isShow = true;
                }
                break;
            case R.id.btn_back:
                back();
                isShow = true;
                break;
            case R.id.orderTelTv:
                //订购热线
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + "037166666666"));
                startActivity(intent);
                isShow = true;
                break;
            case R.id.relProductComment:
                //用户评论
                Intent i = new Intent(mContext, CommentActivity.class);
                i.putExtra("id", productId);
                startActivity(i);
                isShow = true;
                break;
            case R.id.relProdStock:
                //点击库存
                if (productBean.number == 0) {
                    Toast.makeText(mContext, "无货！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "有货！库存剩余:" + productBean.number, Toast.LENGTH_SHORT).show();
                }
                isShow = true;
                break;
        }
        if (!isShow) {
            userId = GlobalConfig.getLoginUserBean(mContext);
            edit_text = prodNumValue.getText().toString().trim();
            if (edit_text.isEmpty()) {
                Toast.makeText(mContext, "请输入数量", Toast.LENGTH_SHORT).show();
                return;
            } else {
                num = Integer.parseInt(edit_text);
                if (num <= 0) {
                    Toast.makeText(mContext, "请输入正确数量", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            switch (view.getId()) {
                case R.id.textPutIntoShopcar:
                    //点击购物车
                    addShoppCar();
                    break;
                case R.id.textProdToCollect:
                    //点击收藏
                    if (userId == null) {
                        Toast.makeText(mContext, "请先登录，亲", Toast.LENGTH_SHORT).show();
                        startActivityForResult(new Intent(mContext, LoginActivity.class), 2000);
                        // TODO
                    } else {
                        Toast.makeText(mContext, "收藏成功", Toast.LENGTH_SHORT).show();
                        addfavorites(productId, userId.userId);
                    }

                    break;
            }
        }
        isShow = false;
    }

    //添加到购物车
    private void addShoppCar() {

        if (userId == null) {
            Toast.makeText(mContext, "请先登录，亲", Toast.LENGTH_SHORT).show();
            startActivityForResult(new Intent(mContext, LoginActivity.class), 1000);
        } else {
            Toast.makeText(mContext, "加入购物车成功", Toast.LENGTH_SHORT).show();
            addToSku(num);
        }
    }

    private void addToSku(int num) {
        for (int i = 0; i < num; i++) {
            skuUtils.add(productId);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        userId = GlobalConfig.getLoginUserBean(mContext);
        if (GlobalConfig.isLogin(mContext)) {
            if (requestCode == 1000) {
                Toast.makeText(mContext, "加入购物车成功", Toast.LENGTH_SHORT).show();
                addToSku(num);
            } else if (requestCode == 2000) {
                Toast.makeText(mContext, "收藏成功", Toast.LENGTH_SHORT).show();
                addfavorites(productId, userId.userId);
            }
        }
    }

    //添加到收藏夹,发送到服务器
    private void addfavorites(int productId, int userID) {
        NetUtil.newGetRequest(NetUrl.BASE_URL + "/addfavorites" + "?productId=" + productId + "&userId=" + userID, new NetUtil.NetworkCallBack(mContext, false, true, true) {
            @Override
            public void onCache(String cache) {

            }

            @Override
            public void onSucces(String result) {

            }

            @Override
            public void onConnectError() {

            }
        });
    }

    @Override
    protected void initData() {
        NetUtil.newGetRequest(NetUrl.BASE_URL + "/product" + "?pId=" + productId, new NetUtil.NetworkCallBack(mContext, false, true, true) {
            @Override
            public void onCache(String cache) {

            }

            @Override
            public void onSucces(String result) {
                parseJson(result);
            }

            @Override
            public void onConnectError() {

            }
        });
    }

    /**
     * 解析Json
     */
    private void parseJson(String result) {
        Gson gson = new Gson();
        productDetails = gson.fromJson(result, ProductDetails.class);
        productBean = productDetails.product.get(1);
        //设置商品名称
        textProductNameValue.setText(productBean.name);
        //商品编号
        textProductIdValue.setText(productBean.brandid + "");
        //市场价
        textOriginalPriceValue.setText("￥" + productBean.marketprice + "");
        textOriginalPriceValue.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        //售价
        textPriceValue.setText("￥" + productBean.price);
        //库存
        textProdIsStock.setText("(" + productBean.number + ")");
        //评论条数
        textProductCommentNum.setText(productBean.commentcount + "");


        //画廊设置适配器
        if (adapter == null) {
            adapter = new ImageAdapter();
            productGallery.setAdapter(adapter);
        }
    }

    private ImageAdapter adapter;

    private class ImageAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return productDetails.product.get(0).bigpic.size();
        }

        @Override
        public Object getItem(int i) {
            return productDetails.product.get(0).bigpic.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = new ImageView(mContext);
            }
            // Log.jyy(NetUrl.BASE_URL+productDetails.product.get(0).bigpic.get(i));
            x.image().bind((ImageView) view, NetUrl.BASE_URL + productDetails.product.get(0).bigpic.get(i));
            ((ImageView) view).setScaleType(ImageView.ScaleType.FIT_XY);

            return view;
        }
    }

    private void back() {
        if (isProductInfo) {
            //如果是详情，就退出到展示页面
            scrollview.setVisibility(View.VISIBLE);
            textProductInfoIsNull.setVisibility(View.GONE);
            title.setText("商品详情");
            isProductInfo = false;
        } else {
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            //返回键
            back();
        }

        return true;
    }
}