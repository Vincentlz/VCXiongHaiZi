package hai.xiong.redchilden.activity;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import hai.xiong.redchilden.R;
import hai.xiong.redchilden.adapter.BaseListViewAdapter;
import hai.xiong.redchilden.bean.OrderListBean;
import hai.xiong.redchilden.util.Log;
import hai.xiong.redchilden.util.NetUrl;
import hai.xiong.redchilden.util.NetUtil;

import static hai.xiong.redchilden.bean.OrderListBean.*;


/**
 * Created by spring on 2016/8/8.
 */
public class MyOrderActivity extends BaseActivity {

    private MyOrderBaseAdapter adapter;

    private class MyOrderOnItemClickListener implements OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            OrderlistBean orderlistBean = datas.get(i);
            Intent intent = new Intent(mContext, OrderDetailActivity.class);
            intent.putExtra("id", orderlistBean.orderId);
            startActivity(intent);
        }
    }

    //近一个月订单
    @ViewInject(R.id.tv_my_order_month)
    private TextView tv_my_order_month;
    //  一个月前订单
    @ViewInject(R.id.tv_my_order_month_before)
    private TextView tv_my_order_month_before;
    //已取消订单
    @ViewInject(R.id.my_order_canceled)
    private TextView my_order_canceled;
    @ViewInject(R.id.lv_my_order_list)
    private ListView lv_my_order_list;
    @ViewInject(R.id.tv_my_order_null)
    private TextView tv_my_order_null;

    @ViewInject(R.id.back)
    private ImageView back;

    private ArrayList<TextView> orderTitle;
    private List<OrderlistBean> datas = new ArrayList<OrderlistBean>();

    private int type;

    private int index = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_order;
    }

    @Override
    protected void initCreate() {

    }

    @Override
    protected void initView() {
        x.view().inject(this);
        orderTitle = new ArrayList<TextView>();
        orderTitle.add(tv_my_order_month);
        orderTitle.add(tv_my_order_month_before);
        orderTitle.add(my_order_canceled);
    }

    @Override
    protected void initListener() {
        back.setOnClickListener(this);
        tv_my_order_month.setOnClickListener(this);
        tv_my_order_month_before.setOnClickListener(this);
        my_order_canceled.setOnClickListener(this);
        //listview
        lv_my_order_list.setOnItemClickListener(new MyOrderOnItemClickListener());
    }

    @Override
    protected void onInnerClick(View view) {
        super.onInnerClick(view);
        switch (view.getId()) {
            case R.id.tv_my_order_month:

                index = 0;

                break;
            case R.id.tv_my_order_month_before:
                Log.hb(" R.id.tv_my_order_month_before:");
                index = 1;

                break;
            case R.id.my_order_canceled:
                Log.hb("R.id.my_order_canceled:");
                index = 2;

                break;
        }
        type = index + 1;
        getData(type);
        updateBackground(index);

    }

    private void updateBackground(int index) {
        for (int i = 0; i < orderTitle.size(); i++) {
            if (i == index) {
                orderTitle.get(i).setBackgroundResource(R.drawable.segment_selected_1_bg);
                orderTitle.get(i).setTextColor(Color.WHITE);

            } else {
                orderTitle.get(i).setBackgroundResource(R.drawable.segment_normal_2_bg);
                orderTitle.get(i).setTextColor(Color.BLACK);
            }
        }
    }

    @Override
    protected void initData() {
        getData(1);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(adapter != null){

        adapter.notifyDataSetChanged();
        }
    }

    class MyOrderBaseAdapter extends BaseListViewAdapter<OrderListBean.OrderlistBean> {

        private OrderlistBean orderlistBean;

        /**
         * ListViewAdapter
         *
         * @param dataList 数据列表
         */
        public MyOrderBaseAdapter(List<OrderListBean.OrderlistBean> dataList) {
            super(dataList);
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
           // Log.hb("getViewgetView");
            MyOrderViewHolder holder = null;
            if (view == null) {
                holder = new MyOrderViewHolder();
                view = View.inflate(mContext, R.layout.listview_item_my_order, null);
                holder.tv_order_number = (TextView) view.findViewById(R.id.tv_order_number);
                holder.tv_total_price = (TextView) view.findViewById(R.id.tv_total_price);
                holder.tv_state = (TextView) view.findViewById(R.id.tv_state);
                holder.tv_time = (TextView) view.findViewById(R.id.tv_time);
                holder.tv_arrow = (TextView) view.findViewById(R.id.tv_arrow);
                view.setTag(holder);
            } else {
                holder = (MyOrderViewHolder) view.getTag();
            }
            //填充数据

            orderlistBean = datas.get(i);
            holder.tv_order_number.setText(orderlistBean.orderId);
            holder.tv_total_price.setText("¥"+orderlistBean.price);
            //  holder.tv_state.setText(orderlistBean.status);
            holder.tv_time.setText(orderlistBean.time);
            holder.tv_arrow.setVisibility(View.VISIBLE);

            int status = Integer.parseInt(orderlistBean.status);
            if (status == 1) {
                holder.tv_state.setText("未处理");
            } else if (status == 2) {
                holder.tv_state.setText("交易成功");
            } else if (status == 3) {
                holder.tv_state.setText("已完成");
            } else if (status == 4) {
                holder.tv_state.setText("已取消");
            }

            return view;
        }
    }

    class MyOrderViewHolder {
        TextView tv_order_number, tv_total_price, tv_state, tv_time, tv_arrow;
    }

    public void getData(int type) {
        Log.hb("getData");
        String url = NetUrl.BASE_URL + "/orderlist?type="+type+"&page=1&pageNum=10&userId=2";
        NetUtil.newGetRequest(url, new NetUtil.NetworkCallBack(mContext,false,true,true) {
            @Override
            public void onCache(String cache) {

            }

            @Override
            public void onSucces(String result) {
                parserGson(result);
            }

            @Override
            public void onConnectError() {

            }
        });

    }

    private void parserGson(String result) {
        Gson gson = new Gson();
        OrderListBean orderListBean = gson.fromJson(result, OrderListBean.class);
        datas = orderListBean.orderList;
        Log.hb("datas" + datas);
        if (datas.size() == 0) {
            Log.hb("datas.size() == 0");
            tv_my_order_null.setVisibility(View.VISIBLE);
            lv_my_order_list.setVisibility(View.GONE);

        }
        if (datas.size() > 0) {
            Log.hb("datas.size() > 0");
            tv_my_order_null.setVisibility(View.GONE);
            lv_my_order_list.setVisibility(View.VISIBLE);
            if (adapter == null) {
                adapter = new MyOrderBaseAdapter(datas);
                lv_my_order_list.setAdapter(adapter);
            }
            adapter.notifyDataSetChanged();
        }

    }


}
