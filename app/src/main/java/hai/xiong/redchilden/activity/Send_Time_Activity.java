package hai.xiong.redchilden.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;

import hai.xiong.redchilden.R;
import hai.xiong.redchilden.bean.pay_way_bean;
import hai.xiong.redchilden.util.Log;
import hai.xiong.redchilden.util.NetUrl;
import hai.xiong.redchilden.util.NetUtil;

/**
 * Created by 徐心意 on 2016/8/7.
 */
public class Send_Time_Activity extends BaseActivity {
    @ViewInject(R.id.listview_time)
    private ListView listview_time;
    //返回
    @ViewInject(R.id.back)
    private Button mBack;
    private pay_way_bean mpaybean;
    public SharedPreferences sp;
    private int defaultPositon;
    private MyPay_timeadapter adapter;

    private class MyPay_TimeOnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            Intent intent = new Intent();
            pay_way_bean.DeliveryListBean DListBean = mpaybean.deliveryList.get(position);
            String dListBean = DListBean.des;
            intent.putExtra("item", "deliveryType");
            intent.putExtra("position", position);
            intent.putExtra("content", dListBean);
            setResult(Activity.RESULT_OK, intent);
            adapter.notifyDataSetChanged();
            finish();
        }
    }

    private class Pay_wayCacheCallback implements Callback.CacheCallback<String> {
        @Override
        public boolean onCache(String result) {
            return false;
        }

        @Override
        public void onSuccess(String result) {
            Log.xxy("result:" + result);
            parseJson(result);
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
    }

    @Override
    protected int getLayoutId() {
        return R.layout.pay_goods_time;
    }

    @Override
    protected void initCreate() {
        defaultPositon = getIntent().getIntExtra("position", -1);
    }

    @Override
    protected void initView() {
        x.view().inject(this);
    }

    @Override
    protected void initListener() {
        mBack.setOnClickListener(this);
        listview_time.setOnItemClickListener(new MyPay_TimeOnItemClickListener());
    }

    @Override
    protected void initData() {
        HashMap<String, String> map = new HashMap<String, String>();
        NetUtil.postRequest(NetUrl.BASE_URL + "/checkout", map, new Pay_wayCacheCallback());
    }

    private void parseJson(String result) {
        Gson gson = new Gson();
        mpaybean = gson.fromJson(result, pay_way_bean.class);
        Log.xxy("paybean" + mpaybean.deliveryList.get(0).des);
        ;
        adapter = new MyPay_timeadapter();
        listview_time.setAdapter(adapter);
    }

    class MyPay_timeadapter extends BaseAdapter {


        @Override
        public int getCount() {
            return mpaybean.deliveryList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            MyPayHolder holder = new MyPayHolder();
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.listview_pay_receipt, null);
                holder.tv_pay_way = (TextView) convertView.findViewById(R.id.tv_pay_way);
                holder.iv_duihao = (ImageView) convertView.findViewById(R.id.iv_duihao);
                convertView.setTag(holder);
            } else {
                holder = (MyPayHolder) convertView.getTag();
            }
            pay_way_bean.DeliveryListBean deliveryListBean = mpaybean.deliveryList.get(position);
            holder.tv_pay_way.setText(deliveryListBean.des);
            if (position == defaultPositon && defaultPositon != -1) {
                holder.iv_duihao.setVisibility(View.VISIBLE);
                holder.iv_duihao.setBackgroundResource(R.drawable.ok);
            } else {
                holder.iv_duihao.setVisibility(View.GONE);
            }
            return convertView;
        }
    }

    class MyPayHolder {
        TextView tv_pay_way;
        ImageView iv_duihao;
    }
}
