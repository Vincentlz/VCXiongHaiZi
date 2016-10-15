package hai.xiong.redchilden.activity;

import android.content.Intent;
import android.graphics.Paint;
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
import hai.xiong.redchilden.bean.FaveriteFullBean;
import hai.xiong.redchilden.util.GlobalConfig;
import hai.xiong.redchilden.util.Log;
import hai.xiong.redchilden.util.NetUrl;
import hai.xiong.redchilden.util.NetUtil;

/**
 * 收藏夹
 * Created by LvYou on 2016/8/6.
 */
public class FaveriteActivity extends BaseActivity {

    @ViewInject(R.id.back)
    private ImageView back;

    @ViewInject(R.id.btn_exit)
    private Button btn_exit;

    @ViewInject(R.id.address_list_list)
    private ListView address_list_list;

    @ViewInject(R.id.ll_favarite_empty)
    private LinearLayout ll_favarite_empty;


//    //列表
//    @ViewInject(R.id.rl_listview_scan_history)
//    private LinearLayout rl_listview_scan_history;


    //json封装javaBean
    private FaveriteFullBean mFaveriteFullBean;

    private MyBaseAdapter mAdapter;

    private List<FaveriteFullBean.ProductlistBean> productlist;

    private class MyListViewOnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            //跳转到商品详情页面，并将商品id传递。
            Intent intent = new Intent(FaveriteActivity.this, ProductDetailsActivity.class);
            intent.putExtra("id", mFaveriteFullBean.productlist.get(i).id);

            startActivity(intent);
        }
    }

    @Override
    protected int getLayoutId() {
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
        address_list_list.setOnItemClickListener(new MyListViewOnItemClickListener());
    }


    @Override
    protected void initData() {

        int loginUserId = GlobalConfig.getLoginUserBean(mContext).userId;

        NetUtil.newGetRequest(NetUrl.BASE_URL + "/favorites?page=1&pageNum=10&userId=" + loginUserId, new NetUtil.NetworkCallBack(mContext,true,false,true) {
            @Override
            public void onCache(String cache) {
                parseJson(cache);
                btn_exit.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSucces(String result) {
                Log.ly("解析成功"+result);
                parseJson(result);
                btn_exit.setVisibility(View.VISIBLE);
            }

            @Override
            public void onConnectError() {

            }
        });

    }

    private void parseJson(String result) {
        Gson gson = new Gson();
        mFaveriteFullBean = gson.fromJson(result, FaveriteFullBean.class);
        productlist = mFaveriteFullBean.productlist;
        //收藏夹数量为空,显示空页面
        if (mFaveriteFullBean.listCount == 0){
            ll_favarite_empty.setVisibility(View.VISIBLE);
            btn_exit.setVisibility(View.INVISIBLE);
            return;
        }
        Log.ly("解析成功：" + mFaveriteFullBean.productlist.get(1).name);
        mAdapter = new MyBaseAdapter(productlist);
        address_list_list.setAdapter(mAdapter);

    }


    class MyBaseAdapter extends BaseListViewAdapter {

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
            MyFaveriteHolder holder = null;
            if (view == null) {
                view = View.inflate(mContext, R.layout.favarete_listview, null);
                holder = new MyFaveriteHolder();
                x.view().inject(holder, view);
                view.setTag(holder);
            } else {
                holder = (MyFaveriteHolder) view.getTag();
            }

            x.image().bind(holder.iv_product, NetUrl.BASE_URL + mFaveriteFullBean.productlist.get(i).pic);
            holder.tv_product_title.setText(mFaveriteFullBean.productlist.get(i).name);
            holder.textClothesPrice.setText("￥" + mFaveriteFullBean.productlist.get(i).price + ".00");
            holder.textMarketPrice.setText("￥" + mFaveriteFullBean.productlist.get(i).marketprice + ".00");
            //设置删除线
            holder.textMarketPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            holder.textProductCommentNum.setText("已有"+mFaveriteFullBean.productlist.get(i).commentcount+"人评价" );


            if (mFaveriteFullBean.productlist.get(i).isgift) {
                holder.tv_gift.setVisibility(View.VISIBLE);
            } else {
                holder.tv_gift.setVisibility(View.INVISIBLE);
            }
            return view;
        }

        class MyFaveriteHolder {


            @ViewInject(R.id.iv_product)
            ImageView iv_product;
            @ViewInject(R.id.tv_product_title)
            TextView tv_product_title;
            @ViewInject(R.id.tv_gift)
            private TextView tv_gift;
            @ViewInject(R.id.textClothesPrice)
            TextView textClothesPrice;
            @ViewInject(R.id.textMarketPrice)
            TextView textMarketPrice;
            @ViewInject(R.id.textProductCommentNum)
            TextView textProductCommentNum;
        }

    }

    @Override
    protected void onInnerClick(View view) {
        super.onInnerClick(view);
        switch (view.getId()) {
            //清空
            case R.id.btn_exit:
                productlist.clear();
                ll_favarite_empty.setVisibility(View.VISIBLE);
                btn_exit.setVisibility(View.INVISIBLE);
                mAdapter.notifyDataSetChanged();
                break;

            default:
                break;
        }
    }
}
