package hai.xiong.redchilden.activity;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

import hai.xiong.redchilden.R;
import hai.xiong.redchilden.adapter.BaseListViewAdapter;
import hai.xiong.redchilden.bean.Comment;
import hai.xiong.redchilden.util.Log;
import hai.xiong.redchilden.util.NetUrl;
import hai.xiong.redchilden.util.NetUtil;

/**
 * Created by dell on 2016/8/7.
 */
public class CommentActivity extends BaseActivity {

    private int productId;
    @ViewInject(R.id.linRank)
    private LinearLayout linRank;
    @ViewInject(R.id.filtTv)
    private TextView filtTv;
    @ViewInject(R.id.textTitle)
    private TextView textTitle;
    private Comment comment;
    @ViewInject(R.id.productLv)
    private ListView productLv;
    @ViewInject(R.id.back)
    private TextView back;
    @ViewInject(R.id.textNull)
    private TextView textNull;
    @ViewInject(R.id.qiehuan)
    private ImageView qiehuan;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_product_list;
    }

    @Override
    protected void initCreate() {
        x.view().inject(this);
        productId = getIntent().getIntExtra("id", 0);
        linRank.setVisibility(View.GONE);
        filtTv.setVisibility(View.GONE);
        qiehuan.setVisibility(View.GONE);
        textTitle.setText("用户评论");
    }

    @Override
    protected void initView() {
        productLv.setBackgroundResource(R.drawable.border_80);
        productLv.setPadding(5, 5, 5, 5);
    }

    @Override
    protected void initListener() {
        back.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        NetUtil.newGetRequest(NetUrl.BASE_URL + "/product/comment" + "?pId=" + productId + "&page=1&pageNum=10", new NetUtil.NetworkCallBack(mContext, false, true, true) {
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

    private void parseJson(String result) {
        Gson gson = new Gson();
        comment = gson.fromJson(result, Comment.class);
        if (comment.comment.size() == 0) {
            //没有评论
            productLv.setVisibility(View.GONE);
            textNull.setVisibility(View.VISIBLE);
            textNull.setText("暂无评论");
            textNull.setTextSize(20);
        } else {
            if (adapter == null) {
                adapter = new MyAdapter(comment.comment);
                productLv.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
            productLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(mContext, "记得给好评哦，亲", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private MyAdapter adapter;

    private class MyAdapter extends BaseListViewAdapter<Comment.CommentBean> {

        /**
         * ListViewAdapter
         *
         * @param dataList 数据列表
         */
        public MyAdapter(List<Comment.CommentBean> dataList) {
            super(dataList);
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder vh = null;
            if (view == null) {
                vh = new ViewHolder();
                view = View.inflate(mContext, R.layout.listview_items_comment, null);
                vh.tv_content = (TextView) view.findViewById(R.id.tv_content);
                vh.tv_time = (TextView) view.findViewById(R.id.tv_time);
                vh.tv_title = (TextView) view.findViewById(R.id.tv_title);
                vh.tv_name = (TextView) view.findViewById(R.id.tv_name);
                view.setTag(vh);
            } else {
                vh = (ViewHolder) view.getTag();
            }
            Comment.CommentBean commentBean = comment.comment.get(i);
            vh.tv_content.setText(commentBean.content);
            vh.tv_time.setText(commentBean.time);
            vh.tv_title.setText(commentBean.title);
            vh.tv_name.setText(commentBean.username);
            if (commentBean.username.equals("匿名")) {
                vh.tv_name.setTextColor(Color.LTGRAY);
            } else {
                vh.tv_name.setTextColor(Color.BLUE);
            }
            return view;
        }
    }

    private class ViewHolder {
        public TextView tv_time;
        public TextView tv_title;
        public TextView tv_content;
        public TextView tv_name;
    }

}
