package hai.xiong.redchilden.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import hai.xiong.redchilden.R;
import hai.xiong.redchilden.adapter.BaseListViewAdapter;
import hai.xiong.redchilden.bean.ProductDetails;
import hai.xiong.redchilden.util.Constant;
import hai.xiong.redchilden.util.Log;
import hai.xiong.redchilden.util.NetUrl;
import hai.xiong.redchilden.util.NetUtil;
import hai.xiong.redchilden.util.SPListUtils;

/**
 * 浏览记录
 * Created by LvYou on 2016/8/8.
 */
public class ScanHistoryActivity extends BaseActivity {

    private class MyOnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            //点击商品历史跳转到商品详情
            Intent intent = new Intent(mContext, ProductDetailsActivity.class);
            intent.putExtra("id", Integer.parseInt(mStrings.get(i)));
            startActivityForResult(intent, 0);
            mAdapter.notifyDataSetChanged();
        }
    }

    //返回
    @ViewInject(R.id.back)
    private ImageView back;

    //清空
    @ViewInject(R.id.btn_exit)
    private Button btn_exit;

    //浏览记录为空
    @ViewInject(R.id.tv_faverite_empty)
    private TextView tv_faverite_empty;

    //浏览记录列表
    @ViewInject(R.id.rl_listview_scan_history)
    private LinearLayout rl_listview_scan_history;

    //空浏览记录展示
    @ViewInject(R.id.ll_favarite_empty)
    private LinearLayout ll_favarite_empty;

    //浏览历史大标题
    @ViewInject(R.id.tv_title)
    private TextView tv_title;

    //商品列表
    @ViewInject(R.id.address_list_list)
    private ListView address_list_list;

    private SPListUtils mVSpListUtils;

    //浏览历史产品id集合
    private List<String> mStrings;

    private List<ProductDetails.ProductBean> product;
    private Gson mGson;
    private MyBaseAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        //复用收藏夹布局
        return R.layout.activity_faverite;
    }

    @Override
    protected void initCreate() {

    }

    @Override
    protected void initView() {
        x.view().inject(this);
        btn_exit.setText("");
        btn_exit.setBackgroundResource(R.drawable.delete);
    }

    @Override
    protected void initListener() {
        back.setOnClickListener(this);
        btn_exit.setOnClickListener(this);
        ll_favarite_empty.setOnClickListener(this);
        tv_faverite_empty.setOnClickListener(this);
        rl_listview_scan_history.setOnClickListener(this);
        mAdapter = new MyBaseAdapter(mStrings);
        address_list_list.setAdapter(mAdapter);
        address_list_list.setOnItemClickListener(new MyOnItemClickListener());
    }

    @Override
    protected void initData() {
        tv_title.setText("浏览记录");

        mVSpListUtils = new SPListUtils(mContext, Constant.SP_KEY_VISIT_HISTORY, 10);

        mStrings = mVSpListUtils.get();

        mAdapter.setDataList(mStrings);

        //判段浏览历史是否为空
        if (mStrings.size() == 0){

            Log.ly("进入浏览历史是否为空判断");
            btn_exit.setVisibility(View.INVISIBLE);
            //浏览记录清空
            rl_listview_scan_history.setVisibility(View.GONE);
            ll_favarite_empty.setVisibility(View.VISIBLE);
            tv_faverite_empty.setText("您的浏览记录为空~~~");
            mAdapter.notifyDataSetChanged();
            return;
        }

    }

    //解析数据
    private void parseJson(MyHolder mHolder, String result, int fTag) {



        //判断holder的tag值与回掉的tag是否相等
        if (mHolder.tag != fTag) {
            return;
        }

        if (mGson == null) {
            mGson = new Gson();
        }

        ProductDetails productDetails = mGson.fromJson(result, ProductDetails.class);


        //商品图片
        x.image().bind(mHolder.imgIcon, NetUrl.BASE_URL + productDetails.product.get(1).pic);

        //商品标题
        mHolder.textClothesName.setText(productDetails.product.get(1).name);
        //商品价格
        mHolder.textClothesPrice.setText("￥"+String.valueOf(productDetails.product.get(1).price));

        //商品市场价格
        mHolder.textMarketPrice.setText("￥"+String.valueOf(productDetails.product.get(1).marketprice));
        //商品市场价格设置删除线
        mHolder.textMarketPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        //商品评论数
        mHolder.textProductCommentNum.setText(String.valueOf(productDetails.product.get(1).commentcount));

    }

    class MyBaseAdapter extends BaseListViewAdapter<String> {

        /**
         * ListViewAdapter
         *
         * @param dataList 数据列表
         */
        public MyBaseAdapter(List dataList) {
            super(dataList);
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            MyHolder mHolder = null;
            if (view == null) {
                view = View.inflate(mContext, R.layout.listview_item_product, null);
                mHolder = new MyHolder();
                x.view().inject(mHolder, view);
                view.setTag(mHolder);
            } else {
                mHolder = (MyHolder) view.getTag();

            }

            mHolder.tag = i;
            //获取对应的商品pid值
            String pid = mStrings.get(i);
            NetUtil.newGetRequest(NetUrl.BASE_URL + "/product?pId=" + pid, new MyNetworkCallBack(mContext, true, false, true, mHolder, i));
//            NetUtil.getRequest(NetUrl.BASE_URL + "/product?pId=" + pid, new MyCommonCallback(mHolder, i));

            return view;
        }


        private class MyNetworkCallBack extends NetUtil.NetworkCallBack {


            /**
             * 构造
             *
             * @param context            用于创建对话框
             * @param isCache
             * @param isShowDialog
             * @param isShowErrorMessage
             */
            public MyNetworkCallBack(Context context, boolean isCache, boolean isShowDialog, boolean isShowErrorMessage, MyHolder holder, int tag) {
                super(context, isCache, isShowDialog, isShowErrorMessage);
                fHolder = holder;
                fTag = tag;
            }

            private MyHolder fHolder;
            private int fTag;

            @Override
            public void onCache(String cache) {
                parseJson(fHolder, cache, fTag);
                btn_exit.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSucces(String result) {
                parseJson(fHolder, result, fTag);
                btn_exit.setVisibility(View.VISIBLE);
            }

            @Override
            public void onConnectError() {

            }
        }
    }

    class MyHolder {
        int tag;

        @ViewInject(R.id.imgIcon)
        ImageView imgIcon;
        @ViewInject(R.id.textClothesName)
        TextView textClothesName;
        @ViewInject(R.id.textClothesPrice)
        TextView textClothesPrice;
        @ViewInject(R.id.textMarketPrice)
        TextView textMarketPrice;
        @ViewInject(R.id.textProductCommentNum)
        TextView textProductCommentNum;
    }

//    private class MyCommonCallback implements Callback.CommonCallback<String> {
//
//        private MyHolder fHolder;
//        private int fTag;
//
//        public MyCommonCallback(MyHolder holder, int tag) {
//            fHolder = holder;
//            fTag = tag;
//        }
//
//        @Override
//        public void onSuccess(String result) {
//            parseJson(fHolder, result, fTag);
//            btn_exit.setVisibility(View.VISIBLE);
//        }
//
//        @Override
//        public void onError(Throwable ex, boolean isOnCallback) {
//
//        }
//
//        @Override
//        public void onCancelled(CancelledException cex) {
//
//        }
//
//        @Override
//        public void onFinished() {
//
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    @Override
    protected void onInnerClick(View view) {
        super.onInnerClick(view);
        switch (view.getId()){
            //清空浏览纪录
            case R.id.btn_exit:
                //清空浏览历史
                mVSpListUtils.clear();
                tv_faverite_empty.setText("您的浏览记录为空~~~");
                btn_exit.setVisibility(View.INVISIBLE);
                //浏览记录清空
                rl_listview_scan_history.setVisibility(View.GONE);
                //显示空浏览记录布局
                ll_favarite_empty.setVisibility(View.VISIBLE);
                mAdapter.notifyDataSetChanged();
                break;
            default:
                break;

        }
    }
}
