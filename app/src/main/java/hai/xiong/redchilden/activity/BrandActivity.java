package hai.xiong.redchilden.activity;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import hai.xiong.redchilden.R;
import hai.xiong.redchilden.adapter.BaseListViewAdapter;
import hai.xiong.redchilden.bean.MyBrandBean;
import hai.xiong.redchilden.util.NetUrl;
import hai.xiong.redchilden.util.NetUtil;

/**
 * Created by Administrator on 2016/8/7 0007.
 */
public class BrandActivity extends  BaseActivity {
    //注册控件
    @ViewInject(R.id.back)
    public ImageView button;
    @ViewInject(R.id.textTitle)
    public TextView textView;
    @ViewInject(R.id.tv_brand)
    public TextView textView2;
    @ViewInject(R.id.gv_brand)
    public GridView gridView;
    @ViewInject(R.id.tv_brand2)
    public TextView textView3;
    @ViewInject(R.id.gv_brand2)
    public GridView gridView2;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_brand;
    }

    @Override
    protected void initCreate() {

    }

    @Override
    protected void initView() {
        View view=View.inflate(mContext,R.layout.activity_brand,null);
        x.view().inject(this);
    }

    @Override
    protected void initListener() {
                button.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        //获取数据
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        textView.setText(title);
        //联网获取数据
        NetUtil.newGetRequest(NetUrl.BASE_URL + "/brand", new NetUtil.NetworkCallBack(mContext, true, true, true) {
            @Override
            public void onCache(String cache) {
                //gson解析
                Gson gson = new Gson();
                MyBrandBean myBrandBean = gson.fromJson(cache, MyBrandBean.class);
                List<MyBrandBean.BrandBean> brand = myBrandBean.brand;
                //设置标题
                textView2.setText(brand.get(0).key);
                textView3.setText(brand.get(1).key);
                //获取每个GradView的数据源
                final List<MyBrandBean.BrandBean.ValueBean> value = brand.get(0).value;
                final List<MyBrandBean.BrandBean.ValueBean> value1 = brand.get(1).value;
                MyBrandadapter adapter = new MyBrandadapter(value);
                MyBrandadapter adapter1 = new MyBrandadapter(value1);
                gridView.setAdapter(adapter);
                gridView2.setAdapter(adapter1);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        MyBrandBean.BrandBean.ValueBean valueBean = value.get(i);
                        Intent intent = new Intent(mContext, ProductListActivity.class);
                        intent.putExtra("URL", NetUrl.BASE_URL + "/productlist?page=1&pageNum=10&cid=1");
                        intent.putExtra("title", valueBean.name);
                        startActivity(intent);
                    }
                });
                gridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        MyBrandBean.BrandBean.ValueBean valueBean2 = value1.get(i);
                        Intent intent = new Intent(mContext, ProductListActivity.class);
                        intent.putExtra("URL", NetUrl.BASE_URL + "/productlist?page=1&pageNum=10&cid=1");
                        intent.putExtra("title", valueBean2.name);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onSucces(String result) {
                //gson解析
                Gson gson = new Gson();
                MyBrandBean myBrandBean = gson.fromJson(result, MyBrandBean.class);
                List<MyBrandBean.BrandBean> brand = myBrandBean.brand;
                //设置标题
                textView2.setText(brand.get(0).key);
                textView3.setText(brand.get(1).key);
                //获取每个GradView的数据源
                final List<MyBrandBean.BrandBean.ValueBean> value = brand.get(0).value;
                final List<MyBrandBean.BrandBean.ValueBean> value1 = brand.get(1).value;
                MyBrandadapter adapter = new MyBrandadapter(value);
                MyBrandadapter adapter1 = new MyBrandadapter(value1);
                gridView.setAdapter(adapter);
                gridView2.setAdapter(adapter1);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        MyBrandBean.BrandBean.ValueBean valueBean = value.get(i);
                        Intent intent = new Intent(mContext, ProductListActivity.class);
                        intent.putExtra("URL", NetUrl.BASE_URL + "/productlist?page=1&pageNum=10&cid=1");
                        intent.putExtra("title", valueBean.name);
                        startActivity(intent);
                    }
                });
                gridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        MyBrandBean.BrandBean.ValueBean valueBean2 = value1.get(i);
                        Intent intent = new Intent(mContext, ProductListActivity.class);
                        intent.putExtra("URL", NetUrl.BASE_URL + "/productlist?page=1&pageNum=10&cid=1");
                        intent.putExtra("title", valueBean2.name);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onConnectError() {

            }
        });
    }
//        NetUtil.getRequest(NetUrl.BASE_URL + "/brand", new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//                //gson解析
//                Gson gson=new Gson();
//                MyBrandBean myBrandBean = gson.fromJson(result, MyBrandBean.class);
//                List<MyBrandBean.BrandBean> brand = myBrandBean.brand;
//                //设置标题
//                textView2.setText(brand.get(0).key);
//                textView3.setText(brand.get(1).key);
//                //获取每个GradView的数据源
//                final List<MyBrandBean.BrandBean.ValueBean> value = brand.get(0).value;
//                final List<MyBrandBean.BrandBean.ValueBean> value1 = brand.get(1).value;
//                MyBrandadapter adapter=new MyBrandadapter(value);
//                MyBrandadapter adapter1=new MyBrandadapter(value1);
//               gridView.setAdapter(adapter);
//                gridView2.setAdapter(adapter1);
//                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                        MyBrandBean.BrandBean.ValueBean valueBean = value.get(i);
//                        Intent intent=new Intent(mContext,ProductListActivity.class);
//                        intent.putExtra("URL", NetUrl.BASE_URL+"/productlist?page=1&pageNum=10&cid=1");
//                        intent.putExtra("title",valueBean.name);
//                        startActivity(intent);
//                    }
//                });
//                gridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                        MyBrandBean.BrandBean.ValueBean valueBean2 = value1.get(i);
//                        Intent intent=new Intent(mContext,ProductListActivity.class);
//                        intent.putExtra("URL", NetUrl.BASE_URL+"/productlist?page=1&pageNum=10&cid=1");
//                        intent.putExtra("title",valueBean2.name);
//                        startActivity(intent);
//                    }
//                });
//            }
//
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
    class MyBrandadapter extends BaseListViewAdapter<MyBrandBean.BrandBean.ValueBean>{

        /**
         * ListViewAdapter
         *
         * @param dataList 数据列表
         */
        public MyBrandadapter(List<MyBrandBean.BrandBean.ValueBean> dataList) {
            super(dataList);
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            View view1=View.inflate(mContext,R.layout.activity_brand_item,null);
            //获取控件
            ImageView imageView= (ImageView) view1.findViewById(R.id.iv_brand);
            x.image().bind(imageView,NetUrl.BASE_URL+mDataList.get(i).pic);
            return view1;
        }
    }
}
