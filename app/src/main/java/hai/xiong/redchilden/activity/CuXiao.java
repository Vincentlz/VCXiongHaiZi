package hai.xiong.redchilden.activity;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import hai.xiong.redchilden.R;
import hai.xiong.redchilden.adapter.BaseListViewAdapter;
import hai.xiong.redchilden.bean.CuXiaoBean;
import hai.xiong.redchilden.util.NetUrl;
import hai.xiong.redchilden.util.NetUtil;

/**
 * Created by Administrator on 2016/8/7 0007.
 */
public class CuXiao extends BaseActivity {
    //初始化布局
    @ViewInject(R.id.lv_cuxiao)
    private ListView listView;
    @ViewInject(R.id.textTitle)
    public TextView textview;
    @ViewInject(R.id.back)
    public ImageView button;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cuxiao;
    }

    @Override
    protected void initCreate() {

    }

    @Override
    protected void initView() {
        View view = View.inflate(mContext, R.layout.activity_cuxiao, null);
        x.view().inject(this);
        Intent intent = getIntent();

        String string = intent.getStringExtra("title");
        textview.setText(string);
    }

    @Override
    protected void initListener() {
        button.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        NetUtil.newGetRequest(NetUrl.BASE_URL + "/topic", new NetUtil.NetworkCallBack(mContext,true,true,true) {
            @Override
            public void onCache(String cache) {
                //解析字符串
                Gson gson = new Gson();
                CuXiaoBean cuXiaoBean = gson.fromJson(cache, CuXiaoBean.class);
                final List<CuXiaoBean.TopicBean> topic = cuXiaoBean.topic;
                //为listview设置适配器
                MyCuXiaoAdapter adapter = new MyCuXiaoAdapter(topic);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        CuXiaoBean.TopicBean topicBean = topic.get(i);
                        Intent intent = new Intent(mContext, ProductListActivity.class);
                        intent.putExtra("URL", NetUrl.BASE_URL + "/brand/plist?page=0&pageNum=10&id=" + topicBean.id);
                        intent.putExtra("title", "专题商品");
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onSucces(String result) {
                //解析字符串
                Gson gson = new Gson();
                CuXiaoBean cuXiaoBean = gson.fromJson(result, CuXiaoBean.class);
                final List<CuXiaoBean.TopicBean> topic = cuXiaoBean.topic;
                //为listview设置适配器
                MyCuXiaoAdapter adapter = new MyCuXiaoAdapter(topic);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        CuXiaoBean.TopicBean topicBean = topic.get(i);
                        Intent intent = new Intent(mContext, ProductListActivity.class);
                        intent.putExtra("URL", NetUrl.BASE_URL + "/brand/plist?page=0&pageNum=10&id=" + topicBean.id);
                        intent.putExtra("title", "专题商品");
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
//        NetUtil.getRequest(NetUrl.BASE_URL + "/topic", new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//                //解析字符串
//                Gson gson = new Gson();
//                CuXiaoBean cuXiaoBean = gson.fromJson(result, CuXiaoBean.class);
//                final List<CuXiaoBean.TopicBean> topic = cuXiaoBean.topic;
//                //为listview设置适配器
//                MyCuXiaoAdapter adapter = new MyCuXiaoAdapter(topic);
//                listView.setAdapter(adapter);
//                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                        CuXiaoBean.TopicBean topicBean = topic.get(i);
//                        Intent intent = new Intent(mContext, ProductListActivity.class);
//                        intent.putExtra("URL", NetUrl.BASE_URL+"/brand/plist?page=0&pageNum=10&id="+topicBean.id);
//                        intent.putExtra("title","专题商品");
//                        startActivity(intent);
//                    }
//                });
//
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
//
//    }

    class MyCuXiaoAdapter extends BaseListViewAdapter<CuXiaoBean.TopicBean> {

        /**
         * ListViewAdapter
         *
         * @param dataList 数据列表
         */
        public MyCuXiaoAdapter(List<CuXiaoBean.TopicBean> dataList) {
            super(dataList);
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            HoderView hoderview = null;
            if (view == null) {
                hoderview = new HoderView();
                view = View.inflate(mContext, R.layout.cuoxiao_listview_item, null);
                hoderview.textview = (TextView) view.findViewById(R.id.tv_item);
                hoderview.imageview = (ImageView) view.findViewById(R.id.iv_item);
                view.setTag(hoderview);
            } else {
                hoderview = (HoderView) view.getTag();
            }

            //获取对应的bean对象
            CuXiaoBean.TopicBean topicBean = mDataList.get(i);
            //为控件赋值
            hoderview.textview.setText(topicBean.name);
            ImageOptions options = new ImageOptions.Builder().setImageScaleType(ImageView.ScaleType.FIT_XY).build();
            x.image().bind(hoderview.imageview, NetUrl.BASE_URL + topicBean.pic, options);
            return view;
        }
    }

    class HoderView {
        public TextView textview;
        public ImageView imageview;
    }
}
