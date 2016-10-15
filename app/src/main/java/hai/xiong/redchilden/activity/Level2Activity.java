package hai.xiong.redchilden.activity;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import hai.xiong.redchilden.R;
import hai.xiong.redchilden.adapter.BaseListViewAdapter;
import hai.xiong.redchilden.bean.Category;

/**
 * Created by dell on 2016/8/6.
 */
public class Level2Activity extends BaseActivity {

    private Category.CategoryBean categoryBean;
    @ViewInject(R.id.listview)
    private ListView listview;
    //返回键
    @ViewInject(R.id.back)
    private TextView iv_back;
    //标题
    @ViewInject(R.id.title)
    private TextView tv_title;
    private Category category;
    private int positionindex=-1;
    //二级菜单数据集合
    private ArrayList<Category.CategoryBean> categoryList;
    @Override
    protected int getLayoutId() {
        return  R.layout.fragment_level1;
    }

    @Override
    protected void initCreate() {
        x.view().inject(this);
        iv_back.setVisibility(View.VISIBLE);
        categoryBean = (Category.CategoryBean) getIntent().getSerializableExtra("categoryBean");
        //所有数据的JAVEBean对象
        category = (Category) getIntent().getSerializableExtra("Category");

    }

    @Override
    protected void initView() {
        x.view().inject(this);
    }

    @Override
    protected void initListener() {
        iv_back.setOnClickListener(this);
        //设置ListView的条目点击事件

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
              //  ViewHolder vh = (ViewHolder) view.getTag();
                categoryBean =categoryList.get(5);
                adapter.notifyDataSetChanged();
                Intent intent = new Intent(mContext, Level3Activity.class);
                intent.putExtra("categoryBean",categoryBean);
                intent.putExtra("Category",category);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    @Override
    protected void initData() {

        tv_title.setText(categoryBean.name);
        categoryList= new ArrayList<>();
        for (int i = 0; i < category.category.size(); i++) {
            if(categoryBean.id ==category.category.get(i).parentID){
                //将对应的二级菜单数据添加到集合中
                categoryList.add(category.category.get(i));
            }
        }
        if(adapter ==null){
            adapter = new MyAdapter(categoryList);
        }else{
            adapter.notifyDataSetChanged();
        }
        listview.setAdapter(adapter);
    }
    private MyAdapter adapter;
    private class MyAdapter extends BaseListViewAdapter<Category.CategoryBean>{

        /**
         * ListViewAdapter
         *
         * @param dataList 数据列表
         */
        public MyAdapter(List<Category.CategoryBean> dataList) {
            super(dataList);
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder vh = null;
            if(view == null ){
                vh = new ViewHolder();
                view = View.inflate(mContext,R.layout.listview_item_level2,null);
                vh.tv_content = (TextView) view.findViewById(R.id.tv_content);
                vh.iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
                view.setTag(vh);
            }else{
                vh = (ViewHolder) view.getTag();
            }



            Category.CategoryBean categoryBean = categoryList.get(i);
            vh.tv_content.setText(categoryBean.name);
            vh.iv_icon.setImageResource(R.drawable.arrow);

            return view;
        }
    }
    private class ViewHolder{
        public TextView tv_content;
        public ImageView iv_icon;
    }
}
