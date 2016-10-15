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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;

import hai.xiong.redchilden.R;
import hai.xiong.redchilden.bean.extortBean;
import hai.xiong.redchilden.util.Log;
import hai.xiong.redchilden.util.NetUrl;
import hai.xiong.redchilden.util.NetUtil;

/**
 * Created by 徐心意 on 2016/8/7.
 */
public class Write_Message_Activity extends BaseActivity {
    @ViewInject(R.id.listview_write)
    private ListView listview_write;
    //返回
    @ViewInject(R.id.back)
    private Button mBack;
    private hai.xiong.redchilden.bean.extortBean extortBean;
    private My_Write_Holder holder;
    private View view;
    private TextView mTv_person;
    private TextView mTv_unit;
    String[] StrName = {"个人", "单位"};
    private ImageView mIv_show1;
    private ImageView mIv_show;
    private RelativeLayout mRl_person;
    private RelativeLayout mRl_unit;
    private int defaultPositon;


    private class My_Write_OnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            int headCount = listview_write.getHeaderViewsCount();
            if (position < headCount) {
                return;
            } else {
                position -= headCount;
            }


            Intent intent = new Intent();
            String mContent = extortBean.invoiceList.get(position).content;
            String mTitle = extortBean.invoiceList.get(position).title;
            String mCommon = mContent;
            intent.putExtra("item", "invoce");
            // TODO ！！！添加类型，暂时写死

            extortBean.invoiceList.get(position).type = index;
            intent.putExtra("content", extortBean.invoiceList.get(position));
            intent.putExtra("position", position);
            setResult(Activity.RESULT_OK, intent);
            finish();

        }
    }

    private class String_WriteCacheCallback implements Callback.CacheCallback<String> {
        @Override
        public boolean onCache(String result) {
            return false;
        }

        @Override
        public void onSuccess(String result) {
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

    public void parseJson(String result) {
        Gson gson = new Gson();
        extortBean = gson.fromJson(result, extortBean.class);
        Log.xxy("" + extortBean.invoiceList.get(1).title);
        My_Write_Adapter adapter = new My_Write_Adapter();
        listview_write.setAdapter(adapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.pay_write_message;
    }

    @Override
    protected void initCreate() {
        defaultPositon = getIntent().getIntExtra("position", -1);
    }

    @Override
    protected void initView() {
        x.view().inject(this);
        view = View.inflate(mContext, R.layout.listview_head_layout, null);
        mTv_person = (TextView) view.findViewById(R.id.tv_person);
        mTv_unit = (TextView) view.findViewById(R.id.tv_unit);
        mIv_show1 = (ImageView) view.findViewById(R.id.iv_show1);
        mIv_show = (ImageView) view.findViewById(R.id.iv_show);
        mRl_person = (RelativeLayout) view.findViewById(R.id.rl_person);
        mRl_unit = (RelativeLayout) view.findViewById(R.id.rl_unit);

    }

    @Override
    protected void initListener() {
        mBack.setOnClickListener(this);
        listview_write.setOnItemClickListener(new My_Write_OnItemClickListener());
        mRl_person.setOnClickListener(this);
        mRl_unit.setOnClickListener(this);
    }

    @Override
    protected void initData() {

        mTv_person.setText(StrName[0]);
        mTv_unit.setText(StrName[1]);
        listview_write.addHeaderView(view, null, false);
        HashMap<String, String> map = new HashMap<String, String>();
        // map.put("userId", GlobalConfig.getLoginUserId(mContext)+"");
        NetUtil.postRequest(NetUrl.BASE_URL + "/invoice?userId=1", map, new String_WriteCacheCallback());

    }
    int index = 0;
    @Override
    protected void onInnerClick(View view) {

        switch (view.getId()) {
            case R.id.rl_person:
                mIv_show.setVisibility(View.GONE);
                mIv_show1.setVisibility(View.VISIBLE);
                index = 1;

                break;
            case R.id.rl_unit:
                mIv_show.setVisibility(View.VISIBLE);
                mIv_show1.setVisibility(View.GONE);
                index = 2;
                break;
            default:

        }

    }

    class My_Write_Adapter extends BaseAdapter {

        @Override
        public int getCount() {
            return extortBean.invoiceList.size();
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

            holder = new My_Write_Holder();
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.listview_pay_receipt, null);
                holder.tv_pay_way = (TextView) convertView.findViewById(R.id.tv_pay_way);
                holder.iv_duihao = (ImageView) convertView.findViewById(R.id.iv_duihao);
                convertView.setTag(holder);
            } else {
                holder = (My_Write_Holder) convertView.getTag();
            }
            extortBean.InvoiceListBean invoicelistBean = extortBean.invoiceList.get(position);
            holder.tv_pay_way.setText(invoicelistBean.title + "\n" + invoicelistBean.content);
            Log.xxy("111111111111" + invoicelistBean.title + "/" + invoicelistBean.content);
            return convertView;
        }
    }

    class My_Write_Holder {
        TextView tv_pay_way;
        ImageView iv_duihao;
    }


}
