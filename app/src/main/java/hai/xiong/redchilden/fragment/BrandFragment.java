package hai.xiong.redchilden.fragment;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import hai.xiong.redchilden.R;
import hai.xiong.redchilden.activity.Level2Activity;
import hai.xiong.redchilden.activity.MainActivity;
import hai.xiong.redchilden.adapter.BaseListViewAdapter;
import hai.xiong.redchilden.bean.Category;
import hai.xiong.redchilden.util.NetUrl;
import hai.xiong.redchilden.util.NetUtil;

/**
 * Created by Administrator on 2016/8/6 0006.
 */
public class BrandFragment extends BaseFragment {
    //版本号
    private final String KEY_VERSION = "version";
    @ViewInject(R.id.listview)
    private ListView listview;
    //一级分类浏览bean对象
    private Category category;
    //一级分类菜单的集合
    private ArrayList<Category.CategoryBean> CategoryList;

    @ViewInject(R.id.title)
    private TextView titlie;
    @ViewInject(R.id.back)
    private TextView back;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_level1;
    }

    @Override
    protected void initCreate() {

    }

    @Override
    protected void initView(View listview) {
        x.view().inject(this, listview);

        titlie.setText("分类浏览");
        back.setVisibility(View.GONE);

    }


    @Override
    protected void initListener() {
        //设置ListView条目点击事件
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Category.CategoryBean categoryBean = CategoryList.get(0);
                Intent intent = new Intent(mContext, Level2Activity.class);
                intent.putExtra("categoryBean",categoryBean);
                intent.putExtra("Category",category);
                startActivity(intent);
                MainActivity mainActivity = (MainActivity) mContext;
                mainActivity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    @Override
    protected void initData() {
        //获得数据
        RequestParams params = new RequestParams(NetUrl.BASE_URL + "/category");
        NetUtil.getRequest(NetUrl.BASE_URL + "/category", new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                String jsonString = result;
               // Log.jyy(jsonString);
                parseJsonString(jsonString);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    /**
     * 解析Json字符串
     */
    private void parseJsonString(String jsonString) {
        Gson gson = new Gson();
        category = gson.fromJson(jsonString, Category.class);
        CategoryList= new ArrayList<>();
        for (int i = 0; i < category.category.size(); i++) {
            if (category.category.get(i).level == 1) {
                //将一级分类的对象添加到集合中
                CategoryList.add(category.category.get(i));
            }
        }
        if (adapter == null) {
            adapter = new MyAdapter(CategoryList);
        }
        listview.setAdapter(adapter);
    }

    private MyAdapter adapter;

    private class MyAdapter extends BaseListViewAdapter<Category.CategoryBean> {
        /**
         * ListViewAdapter
         *
         * @param dataList 数据列表
         */
        public MyAdapter(List dataList) {
            super(dataList);
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder vh = null;
            if (view == null) {
                vh = new ViewHolder();
                view = View.inflate(mContext, R.layout.listview_item_level1, null);
                vh.imgIcon = (ImageView) view.findViewById(R.id.imgIcon);
                vh.textContent = (TextView) view.findViewById(R.id.textContent);
                vh.item_describe = (TextView) view.findViewById(R.id.item_describe);
                view.setTag(vh);
            } else {
                vh = (ViewHolder) view.getTag();
            }
            Category.CategoryBean cb = CategoryList.get(i);
            x.image().bind(vh.imgIcon,NetUrl.BASE_URL+cb.pic);
            vh.textContent.setText(cb.name);
            vh.item_describe.setText(cb.t);
            return view;
        }
    }

    private class ViewHolder {
        public ImageView imgIcon;
        public TextView textContent;
        public TextView item_describe;

    }
}
