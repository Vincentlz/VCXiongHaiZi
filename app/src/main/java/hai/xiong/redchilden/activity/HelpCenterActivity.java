package hai.xiong.redchilden.activity;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import hai.xiong.redchilden.R;
import hai.xiong.redchilden.adapter.BaseListViewAdapter;
import hai.xiong.redchilden.bean.HelpCenterBean;
import hai.xiong.redchilden.bean.HelpCenterBean.HelpListBean;
import hai.xiong.redchilden.bean.HelpContentBean;
import hai.xiong.redchilden.util.Log;
import hai.xiong.redchilden.util.NetUrl;
import hai.xiong.redchilden.util.NetUtil;

/**
 * 帮助中心
 * TODO 增量更新
 * Created by LvYou on 2016/8/6.
 */
public class HelpCenterActivity extends BaseActivity {

    // 返回
    @ViewInject(R.id.back)
    private ImageView back;

    @ViewInject(R.id.listview)
    private ListView listview;

    @ViewInject(R.id.bt_cus_tel)
    private Button bt_cus_tel;
    //帮助内容解析数据
    private HelpCenterBean mHelpCenterBean;

    public List<HelpListBean> helpList;
    private MyBaseAdapter mAdapter;

    //
    private class MyListViewOnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            //跳转到帮助内容界面，并把帮助列表项的id传递过去
            Intent intent = new Intent(HelpCenterActivity.this,HelpContentActivity.class);
            intent.putExtra("id",mHelpCenterBean.helpList.get(i).id);
            intent.putExtra("title",mHelpCenterBean.helpList.get(i).title);
            Log.ly("id的值" +mHelpCenterBean.helpList.get(i).id );
            startActivity(intent);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_help_center;
    }

    @Override
    protected void initCreate() {

    }

    @Override
    protected void initView() {
        x.view().inject(this);
    }

    @Override
    protected void initListener() {
        back.setOnClickListener(this);
        bt_cus_tel.setOnClickListener(this);
        //条目点击事件
        listview.setOnItemClickListener(new MyListViewOnItemClickListener());

    }

    @Override
    protected void initData() {
        NetUtil.getRequest(NetUrl.BASE_URL + "/help?version=0", new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {

                Log.ly(result);
                parseJson(result);
                bt_cus_tel.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.ly("请求失败" + ex);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.ly("请求取消" + cex);
            }

            @Override
            public void onFinished() {
                Log.ly("请求结束");

            }
        });

    }

    private void parseJson(String result) {
        Gson gson = new Gson();
        mHelpCenterBean = gson.fromJson(result, HelpCenterBean.class);
        Log.ly("解析成功" + mHelpCenterBean.helpList.get(0).title);
        helpList = mHelpCenterBean.helpList;
        mAdapter = new MyBaseAdapter(helpList);
        listview.setAdapter(mAdapter);
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
            MyHolder holder= null;
            if (view == null){
                view = View.inflate(mContext,R.layout.activity_help_list,null);
                holder = new MyHolder();
               holder.tv_help_center = (TextView) view.findViewById(R.id.tv_help_center);
                view.setTag(holder);
            }else{
               holder = (MyHolder) view.getTag();
            }

            holder.tv_help_center.setText(mHelpCenterBean.helpList.get(i).title);
            return view;
        }

        class MyHolder {
            TextView tv_help_center;
        }
    }

    @Override
    protected void onInnerClick(View view) {
        super.onInnerClick(view);
        switch (view.getId()){
            case R.id.bt_cus_tel:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+"037166666666"));
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
