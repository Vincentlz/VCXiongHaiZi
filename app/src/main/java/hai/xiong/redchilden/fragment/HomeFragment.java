package hai.xiong.redchilden.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import hai.xiong.redchilden.HotSingle;
import hai.xiong.redchilden.R;
import hai.xiong.redchilden.activity.BrandActivity;
import hai.xiong.redchilden.activity.CuXiao;
import hai.xiong.redchilden.activity.LimitTimeActivity;
import hai.xiong.redchilden.activity.ProductListActivity;
import hai.xiong.redchilden.bean.HomeRoll;
import hai.xiong.redchilden.util.Log;
import hai.xiong.redchilden.util.NetUrl;
import hai.xiong.redchilden.util.NetUtil;
import hai.xiong.redchilden.util.SPListUtils;
import hai.xiong.redchilden.view.HomeView;

import static hai.xiong.redchilden.fragment.SearchFragment.SEARCH_RESULT;

/**
 * Created by Administrator on 2016/8/6 0006.
 */
public class HomeFragment extends BaseFragment {
    //传递数据的list
    public List<HomeRoll.HomeBannerBean> list;

    //轮播图下面的listview的数据源
    public String[] strings = {"限时抢购", "促销快报", "新品上架", "热门单品", "推荐品牌"};
    public int[] images = {R.drawable.home_classify_01, R.drawable.home_classify_02, R.drawable.home_classify_03, R.drawable.home_classify_04, R.drawable.home_classify_05};
    private SPListUtils vVSpListUtils;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home_fragment;
    }

    @Override
    protected void initCreate() {
        vVSpListUtils = new SPListUtils(mContext, SEARCH_RESULT, 5);
    }

    //注册控件
    @ViewInject(R.id.et_home_search)
    private EditText editText;
    @ViewInject(R.id.ho_list)
    private ListView listView;
    @ViewInject(R.id.ib_search)
    private ImageButton ib_search;//搜索按钮
    @ViewInject(R.id.et_home_search)
    private EditText et_home_search;//搜索框
    @ViewInject(R.id.ll_top_news_viewpager)
    public LinearLayout layout;
    @ViewInject(R.id.ll_dots)
    public LinearLayout out;
    View rollView;
    @Override
    protected void initView(View rootView) {
        x.view().inject(this, rootView);
         rollView = View.inflate(mContext, R.layout.mylist_header, null);
        x.view().inject(this,rollView);
        listView.addHeaderView(rollView);
    }

    @Override
    protected void initListener() {
        //搜索按钮监听
        ib_search.setOnClickListener(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                i= i-listView.getHeaderViewsCount();
                switch (i) {
                    case 0:
                        String string = strings[i];
                        Intent intent = new Intent(mContext, LimitTimeActivity.class);
                        intent.putExtra("title", string);
                        startActivity(intent);
                        break;
                    case 1:
                        String string2 = strings[i];
                        Intent intent2 = new Intent(mContext, CuXiao.class);
                        intent2.putExtra("title", string2);
                        startActivity(intent2);
                        break;
                    case 2:
                        String string3 = strings[i];
                        String url = NetUrl.BASE_URL +"/productlist?page=1&pageNum=10&cid=1";
                        Intent intent3 = new Intent(mContext, ProductListActivity.class);
                        intent3.putExtra("URL",url);
                        intent3.putExtra("title",string3);
                        startActivity(intent3);
                        break;
                    case 3:
                        String string5 = strings[i];
                        Intent intent5 = new Intent(mContext, HotSingle.class);
                        intent5.putExtra("title", string5);
                        startActivity(intent5);
                        break;
                    case 4:
                        String string4 = strings[i];
                        Intent intent4 = new Intent(mContext, BrandActivity.class);
                        intent4.putExtra("title", string4);
                        startActivity(intent4);
                        break;
                }

            }
        });

    }
    public  HomeView hoheview;
    @Override
    protected void initData() {
        NetUtil.newGetRequest(NetUrl.BASE_URL + "/home", new NetUtil.NetworkCallBack(mContext, true, false, true) {
            @Override
            public void onCache(String cache) {
                Gson gson = new Gson();
                HomeRoll homeRoll = gson.fromJson(cache, HomeRoll.class);
                list = homeRoll.home_banner;
                addPoint();
                hoheview = new HomeView(mContext, list, out);
                layout.addView(hoheview);
                addText();
            }

            @Override
            public void onSucces(String result) {
                Gson gson = new Gson();
                HomeRoll homeRoll = gson.fromJson(result, HomeRoll.class);
                list = homeRoll.home_banner;
                addPoint();
                hoheview = new HomeView(mContext, list, out);
                layout.addView(hoheview);
                addText();
            }

            @Override
            public void onConnectError() {

            }



        });
        //listView添加条目（适配器）
        final MyHomeAdapter adapter = new MyHomeAdapter();
        listView.setAdapter(adapter);
    }
        //联网获取数据

//        NetUtil.getRequest(NetUrl.BASE_URL + "/home", new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {


//                Gson gson = new Gson();
//                HomeRoll homeRoll = gson.fromJson(result, HomeRoll.class);
//                list = homeRoll.home_banner;
//                addPoint();
//                hoheview=new HomeView(mContext, list,out);
//                layout .addView(hoheview);
//                addText();
        //    }

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
        //listView添加条目（适配器）
//        final MyHomeAdapter adapter = new MyHomeAdapter();
//        listView.setAdapter(adapter);

    //}

    private void addText() {
        hoheview.setOnItemClickListener(new HomeView.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
//            if (position==0){
//                    Toast.makeText(mContext,list.get(3).title,Toast.LENGTH_SHORT).show();
//                Intent intent=new Intent(mContext,ProductListActivity.class);
//                intent.putExtra("title","商品列表");
//                String url = NetUrl.BASE_URL +"/productlist?page=1&pageNum=10&cid=1";
//                intent.putExtra("URL",url);
//                startActivity(intent);
//                }
//                Toast.makeText(mContext,list.get(position-1).title,Toast.LENGTH_SHORT).show();
                Intent intent2=new Intent(mContext,ProductListActivity.class);
                intent2.putExtra("title","商品列表");
                String url = NetUrl.BASE_URL +"/productlist?page=1&pageNum=10&cid=1";
                intent2.putExtra("URL",url);
                startActivity(intent2);
            }
        });
    }

    private void addPoint() {
        for (int i=0;i<list.size();i++){
            ImageView iv=new ImageView(getActivity());
            if (i==0){
                iv.setImageResource(R.drawable.slide_adv_selected);
            }else {
                iv.setImageResource(R.drawable.slide_adv_normal);
            }
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin=5;
            out.addView(iv,params);
        }
    }

    class MyHomeAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return strings.length;
        }

        @Override
        public Object getItem(int i) {
            return strings[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            HoderView hoderView = null;
            if (view == null) {
                hoderView = new HoderView();
                view = View.inflate(mContext, R.layout.home_listview, null);
                hoderView.iv_photo = (ImageView) view.findViewById(R.id.iv_picture);
                hoderView.tv_textview = (TextView) view.findViewById(R.id.tv_text);
                hoderView.iv_photo2 = (ImageView) view.findViewById(R.id.iv_picture2);
                view.setTag(hoderView);
            } else {
                hoderView = (HoderView) view.getTag();
            }
            //为控件赋值
            hoderView.iv_photo.setImageResource(images[i]);
            hoderView.tv_textview.setText(strings[i]);
            hoderView.iv_photo2.setImageResource(R.drawable.arrow);
            return view;
        }
    }

    class HoderView {
        public ImageView iv_photo;
        public TextView tv_textview;
        public ImageView iv_photo2;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.ib_search:

                String vKeyword=et_home_search.getText().toString().trim();
                if (TextUtils.isEmpty(vKeyword)){
                    Toast.makeText(mContext, "请输入要搜索的内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                vVSpListUtils.add(vKeyword);
                jump2product(vKeyword);
                break;
        }
    }
    public void jump2product(String keyword) {

        //搜索的商品url
        String searchuil = NetUrl.BASE_URL + "/search?keyword=" + keyword;
        //TODO 跳转搜索结果逻辑实现，共用
        Intent intent = new Intent(mContext, ProductListActivity.class);
        intent.putExtra("title", "搜索结果");
        intent.putExtra("URL", searchuil);
        startActivity(intent);
    }
}
