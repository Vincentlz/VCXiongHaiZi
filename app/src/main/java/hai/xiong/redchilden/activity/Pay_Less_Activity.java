package hai.xiong.redchilden.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import hai.xiong.redchilden.R;

/**
 * Created by 徐心意 on 2016/8/7.
 */
public class Pay_Less_Activity extends BaseActivity {
    @ViewInject(R.id.listview_pay_less)
    private ListView listview_pay_less;
    @ViewInject(R.id.back)
    private Button mBack;
    private MyPayHolder holder;
    private List<String> Text_Item = new ArrayList<String>();
    private int checkPosition = -1;
    private MyAdapter adapter;
    private int defaultPositon;


    //listview的监听
    private class MyPay_wayOnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            checkPosition = position;
            Intent intent = new Intent();
            String Item = Text_Item.get(position);
            intent.putExtra("content", Item);
            intent.putExtra("item", "lipinka");
            intent.putExtra("position", position);
            setResult(Activity.RESULT_OK, intent);
            adapter.notifyDataSetChanged();
            finish();

        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.pay_gift;
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
        listview_pay_less.setOnItemClickListener(new MyPay_wayOnItemClickListener());
        mBack.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        Text_Item.add("九月惊喜50元礼券");
        Text_Item.add("国庆节80元礼券");
        Text_Item.add("圣诞节大放送80元礼券");
        adapter = new MyAdapter();
        listview_pay_less.setAdapter(adapter);
    }

    @Override
    protected void onInnerClick(View view) {

    }


    class MyAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return Text_Item.size();
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            holder = new MyPayHolder();
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.listview_pay_receipt, null);
                holder.tv_pay_way = (TextView) convertView.findViewById(R.id.tv_pay_way);
                holder.iv_duihao = (ImageView) convertView.findViewById(R.id.iv_duihao);
                convertView.setTag(holder);
            } else {
                holder = (MyPayHolder) convertView.getTag();
            }
            holder.tv_pay_way.setText(Text_Item.get(position));

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

