package hai.xiong.redchilden;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import hai.xiong.redchilden.activity.BaseActivity;
import hai.xiong.redchilden.activity.ProductDetailsActivity;
import hai.xiong.redchilden.adapter.BaseListViewAdapter;
import hai.xiong.redchilden.bean.HotSingleBean;
import hai.xiong.redchilden.util.NetUrl;
import hai.xiong.redchilden.util.NetUtil;

/**
 * Created by Administrator on 2016/8/8 0008.
 */
public class HotSingle extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_hot;
    }

    @Override
    protected void initCreate() {

    }

    //获取控件
    @ViewInject(R.id.back)
    public ImageView button;
    @ViewInject(R.id.textRankSale)
    public TextView textView;
    @ViewInject(R.id.textRankPrice)
    public TextView getTextView;
    @ViewInject(R.id.productLv)
    public ListView listView;
    @ViewInject(R.id.textTitle)
    public TextView texttile;
    @Override
    protected void initView() {
        x.view().inject(this);
        Intent intent = getIntent();
        texttile.setText(intent.getStringExtra("title"));
        //设置点击事件
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = NetUrl.BASE_URL + "/hotproduct?page=1&pageNum=10&orderby=sales";
                textView.setTextColor(Color.WHITE);
                getTextView.setTextColor(Color.BLACK);
                textView.setBackgroundResource(R.drawable.segment_selected_1_bg);
                getTextView.setBackgroundResource(R.drawable.segment_normal_2_bg);
                getUrl(url);
            }
        });
        getTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = NetUrl.BASE_URL + "/hotproduct?page=1&pageNum=10&orderby=price";
                textView.setTextColor(Color.BLACK);
                getTextView.setTextColor(Color.WHITE);
                getTextView.setBackgroundResource(R.drawable.segment_selected_1_bg);
                textView.setBackgroundResource(R.drawable.segment_normal_2_bg);
                getUrl(url);
            }
        });

    }

    @Override
    protected void initListener() {
        button.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        getUrl( NetUrl.BASE_URL + "/hotproduct?page=1&pageNum=10&orderby=sales");
    }

    public void getUrl(String string) {
        NetUtil.newGetRequest(string, new NetUtil.NetworkCallBack(mContext, true, true, true) {
            @Override
            public void onCache(String cache) {
                //解析字符串
                Gson gson = new Gson();
                HotSingleBean hotSingleBean = gson.fromJson(cache, HotSingleBean.class);
                final List<HotSingleBean.ProductlistBean> productlist = hotSingleBean.productlist;
                //设置适配器
                MyHotAdapter adapter = new MyHotAdapter(productlist);
                listView.setAdapter(adapter);
                //listview添加条目点击事件
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        HotSingleBean.ProductlistBean productlistBean = productlist.get(i);
                        Intent intent = new Intent(mContext, ProductDetailsActivity.class);
                        intent.putExtra("id", productlistBean.id);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onSucces(String result) {
                //解析字符串
                Gson gson = new Gson();
                HotSingleBean hotSingleBean = gson.fromJson(result, HotSingleBean.class);
                final List<HotSingleBean.ProductlistBean> productlist = hotSingleBean.productlist;
                //设置适配器
                MyHotAdapter adapter = new MyHotAdapter(productlist);
                listView.setAdapter(adapter);
                //listview添加条目点击事件
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        HotSingleBean.ProductlistBean productlistBean = productlist.get(i);
                        Intent intent = new Intent(mContext, ProductDetailsActivity.class);
                        intent.putExtra("id", productlistBean.id);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onConnectError() {

            }
        });
    }
//            //联网获取数据
//        NetUtil.getRequest(string, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//                //解析字符串
//                Gson gson=new Gson();
//                HotSingleBean hotSingleBean = gson.fromJson(result, HotSingleBean.class);
//                final List<HotSingleBean.ProductlistBean> productlist = hotSingleBean.productlist;
//                //设置适配器
//                MyHotAdapter adapter=new MyHotAdapter(productlist);
//                listView.setAdapter(adapter);
//                //listview添加条目点击事件
//                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                        HotSingleBean.ProductlistBean productlistBean = productlist.get(i);
//                        Intent intent = new Intent(mContext, ProductDetailsActivity.class);
//                        intent.putExtra("id",productlistBean.id);
//                        startActivity(intent);
//                    }
//                });
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//
//            }
//
//            @Override
//            public void onFinished() {
//
//            }
//        });
//    }
    class MyHotAdapter extends BaseListViewAdapter<HotSingleBean.ProductlistBean>{

        /**
         * ListViewAdapter
         *
         * @param dataList 数据列表
         */
        public MyHotAdapter(List<HotSingleBean.ProductlistBean> dataList) {
            super(dataList);
        }
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
                ViewHoder viewhoder=null;
            if (view==null){
                viewhoder=new ViewHoder();
                view=View.inflate(mContext,R.layout.hot_sing_list,null);
                viewhoder.imag= (ImageView) view.findViewById(R.id.goodsIconIv);
                viewhoder.tetx= (TextView) view.findViewById(R.id.textClothesName);
                viewhoder.text2= (TextView) view.findViewById(R.id.textClothesPrice);
                viewhoder.text3= (TextView) view.findViewById(R.id.textProductComment);
                view.setTag(viewhoder);
            }else{
                viewhoder= (ViewHoder) view.getTag();
            }
            //获取对应的bean
            HotSingleBean.ProductlistBean productlistBean = mDataList.get(i);
            viewhoder.tetx.setText(productlistBean.name);
            viewhoder.text2.setText("原价：￥ "+productlistBean.marketprice+"");
            viewhoder.text3.setText("会员价：￥ "+productlistBean.price+"");
            x.image().bind(viewhoder.imag,NetUrl.BASE_URL+productlistBean.pic);
            return view;
        }
    }
    class ViewHoder{
        public ImageView imag;
        public TextView tetx;
        public TextView text2;
        public TextView text3;
    }
}
