package hai.xiong.redchilden.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import hai.xiong.redchilden.R;
import hai.xiong.redchilden.bean.LimitTimeBean;
import hai.xiong.redchilden.util.NetUrl;
import hai.xiong.redchilden.util.NetUtil;

/**
 * Created by Administrator on 2016/8/7 0007.
 */
public class LimitTimeActivity extends BaseActivity {

    //注册控件
    @ViewInject(R.id.textTitle)
    public TextView textView;
    @ViewInject(R.id.back)
    public ImageView button;
    @ViewInject(R.id.lv_list)
    public ListView listView;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_limittime_list;
    }

    @Override
    protected void initCreate() {

    }

    //获取数据源
    List<LimitTimeBean.ProductlistBean> mlist;

    @Override
    protected void initView() {
        //注册
        View view = View.inflate(mContext, R.layout.activity_limittime_list, null);
        x.view().inject(this);
        //联网获取数据
        GetUrl();
    }

    protected void GetUrl() {
        NetUtil.newGetRequest(NetUrl.BASE_URL + "/limitbuy?page=1&pageNum=10", new NetUtil.NetworkCallBack(mContext,true,true,true) {
            @Override
            public void onCache(String cache) {
                //解析Gson
                Gson gson = new Gson();
                LimitTimeBean limitTimeBean = gson.fromJson(cache, LimitTimeBean.class);
                //获取数据源
                mlist = limitTimeBean.productlist;
                //为listview添加适配器
                MyLimitAdapter adapter = new MyLimitAdapter();
                listView.setAdapter(adapter);
                //listview添加条目点击事件
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        LimitTimeBean.ProductlistBean productlistBean = mlist.get(i);
                        Intent intent = new Intent(mContext, ProductDetailsActivity.class);
                        intent.putExtra("id", productlistBean.id);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onSucces(String result) {
                //解析Gson
                Gson gson = new Gson();
                LimitTimeBean limitTimeBean = gson.fromJson(result, LimitTimeBean.class);
                //获取数据源
                mlist = limitTimeBean.productlist;
                //为listview添加适配器
                MyLimitAdapter adapter = new MyLimitAdapter();
                listView.setAdapter(adapter);
                //listview添加条目点击事件
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        LimitTimeBean.ProductlistBean productlistBean = mlist.get(i);
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
//        //联网获取数据
//        NetUtil.getRequest(NetUrl.BASE_URL + "/limitbuy?page=1&pageNum=10", new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//                //解析Gson
//                Gson gson = new Gson();
//                LimitTimeBean limitTimeBean = gson.fromJson(result, LimitTimeBean.class);
//                //获取数据源
//                mlist = limitTimeBean.productlist;
//                //为listview添加适配器
//                MyLimitAdapter adapter=new MyLimitAdapter();
//                listView.setAdapter(adapter);
//                //listview添加条目点击事件
//                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                        LimitTimeBean.ProductlistBean productlistBean = mlist.get(i);
//                        Intent intent = new Intent(mContext, ProductDetailsActivity.class);
//                        intent.putExtra("id",productlistBean.id);
//                        startActivity(intent);
//                    }
//                });
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                ex.printStackTrace();
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//                cex.printStackTrace();
//            }
//
//            @Override
//            public void onFinished() {
//                Log.wc("Finished");
//            }
//        });
//    }




    @Override
    protected void initListener() {

        button.setOnClickListener(this);

    }
    HoderView hoderview=null;
    @Override
    protected void initData() {
        //获取跳转页面携带的数据
        Intent intent = mContext.getIntent();
        String string = intent.getStringExtra("title");
        //为标题赋值
        textView.setText(string);

    }
    class MyLimitAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return mlist.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        public View getView(int i, View view, ViewGroup viewGroup) {

            if (view==null){

                hoderview=new HoderView();
                view=View.inflate(mContext,R.layout.limittime_list,null);
                hoderview.imageview= (ImageView) view.findViewById(R.id.iv_product);
                hoderview.textview= (TextView) view.findViewById(R.id.tv_product_title);
                hoderview.textview2= (TextView) view.findViewById(R.id.tv_last);
                hoderview.textview3= (TextView) view.findViewById(R.id.tv_time);
                hoderview.textview4= (TextView) view.findViewById(R.id.tv_time2);
                hoderview.textButton= (TextView) view.findViewById(R.id.tv_button);
                view.setTag(hoderview);
            }else{
                hoderview= (HoderView) view.getTag();
            }
            //获取bean对象
            LimitTimeBean.ProductlistBean productlistBean = mlist.get(i);
            hoderview.textview2.setText(productlistBean.price+"");//过去价格
            hoderview.textview2.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            hoderview.textview.setText(productlistBean.name);//品牌名
            hoderview.textButton.setText("马上抢购");
            hoderview.textview3.setText("限时特价：￥"+productlistBean.limitprice+"");
            int time=Integer.parseInt(productlistBean.lefttime);
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
            String format = sdf.format(new Date(time*1000));
            hoderview.textview4.setText("剩余时间 "+format);

            x.image().bind(hoderview.imageview,NetUrl.BASE_URL+productlistBean.pic);
            return view;
        }
    }
    class HoderView{
        public ImageView imageview;
        public TextView textview;
        public TextView textview2;
        public TextView textview3;
        public TextView textview4;
        public TextView textButton;
    }
}