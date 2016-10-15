package hai.xiong.redchilden.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
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

import java.util.List;

import hai.xiong.redchilden.R;
import hai.xiong.redchilden.bean.AddressListBean;
import hai.xiong.redchilden.util.Log;
import hai.xiong.redchilden.util.NetUrl;
import hai.xiong.redchilden.util.NetUtil;

public class AddressListActivity extends BaseActivity {


    private int defaultPositon;

    private class MyOnItemLongClickListener implements OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

            if (!isLongClick) {//
                isLongClick = true;
            } else {//
                isLongClick = false;
            }
            checkPosition = -1;//避免长按之后改变
            adapter.notifyDataSetChanged();
            return true;
        }
    }

    private class MyAddressListOnItemClickListener implements OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            AddressListBean.AddresslistBean addresslistBean = addresslist.get(i);
            checkPosition = i;
            if (!isLongClick) {//点击
                Intent intent = new Intent();

                intent.putExtra("item", "addressId");
                intent.putExtra("params", String.valueOf(addresslistBean.id));
                intent.putExtra("content", addresslistBean.name +":"+ addresslistBean.phoneNumber + "\n" + addresslistBean.area);
                // TODO 对接？
                intent.putExtra("position", -1);

                setResult(Activity.RESULT_OK, intent);
                //将当前选中地址信息传递过去
                adapter.notifyDataSetChanged();
                finish();

            } else {
                //长按,跳转到修改地址页面
                Intent intent = new Intent(AddressListActivity.this, AddressModifyActivity.class);
                intent.putExtra("address", addresslist.get(i));
                startActivity(intent);
            }

        }

    }

    @ViewInject(R.id.rl_address_list_top)
    private RelativeLayout rl_address_list_top;
    //返回按钮
    @ViewInject(R.id.back)
    private ImageView iv_back;
    //新增地址按钮
    @ViewInject(R.id.btn_add_address)
    private Button btn_add_address;
    //空地址
    @ViewInject(R.id.address_list_null_text)
    private TextView address_list_null_text;
    //空图片
    @ViewInject(R.id.iv_empty_img)
    private ImageView iv_empty_img;
    //livtView
    @ViewInject(R.id.listview_address)
    private ListView listview_address;


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private AddressListBean addressListBean;
    private MyBaseAdapter adapter;
    private List<AddressListBean.AddresslistBean> addresslist;

    public boolean isLongClick = false;
    //点击的位置
    private int checkPosition = -1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_address_list;
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
        Log.hb("initListener()");
        listview_address.setOnItemClickListener(new MyAddressListOnItemClickListener());
        listview_address.setOnItemLongClickListener(new MyOnItemLongClickListener());

        iv_back.setOnClickListener(this);
        btn_add_address.setOnClickListener(this);

    }

    @Override
    protected void initData() {
       NetUtil.newGetRequest(NetUrl.BASE_URL + "/addresslist", new NetUtil.NetworkCallBack(mContext,false,true,true) {
           @Override
           public void onCache(String cache) {

           }

           @Override
           public void onSucces(String result) {
                parseJson(result);
           }

           @Override
           public void onConnectError() {
               address_list_null_text.setVisibility(View.VISIBLE);
               listview_address.setVisibility(View.GONE);
           }
       });

    }

    private void parseJson(String result) {
        Gson gson = new Gson();
        addressListBean = gson.fromJson(result, AddressListBean.class);
        Log.hb("解析成功" + addressListBean.addresslist.get(1).name);
        addresslist = addressListBean.addresslist;

        if (addresslist.size() == 0) {
            listview_address.setVisibility(View.GONE);
            address_list_null_text.setVisibility(View.VISIBLE);
            iv_empty_img.setVisibility(View.VISIBLE);
            // Log.hb("addresslist.size() == 0");

        }
        if (addresslist.size() > 0) {
            listview_address.setVisibility(View.VISIBLE);
            address_list_null_text.setVisibility(View.GONE);
            iv_empty_img.setVisibility(View.GONE);

            if (adapter == null) {

                adapter = new MyBaseAdapter();
                listview_address.setAdapter(adapter);
            }
            adapter.notifyDataSetChanged();
            //Log.hb("addresslist.size() > 0");
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
        isLongClick = false;
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }


    }


    class MyBaseAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            // Log.hb("addresslist.size():" + addresslist.size());
            return addresslist.size();
        }

        @Override
        public Object getItem(int i) {
            // Log.hb("getItem:" );
            return addresslist.get(i);

        }

        @Override
        public long getItemId(int i) {
            // Log.hb("getItemId:" );
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            AddressListBeanHolder holder = null;
            //Log.hb("执行了GetView方法");
            if (view == null) {
                view = View.inflate(mContext, R.layout.listview_item_address, null);
                holder = new AddressListBeanHolder();
                holder.tv_address_item_name = (TextView) view.findViewById(R.id.tv_address_item_name);
                holder.tv_address_item_number = (TextView) view.findViewById(R.id.tv_address_item_number);
                holder.tv_address_item_address = (TextView) view.findViewById(R.id.tv_address_item_address);
                holder.iv_address_item_select = (ImageView) view.findViewById(R.id.iv_address_item_select);

                view.setTag(holder);
            } else {
                holder = (AddressListBeanHolder) view.getTag();
            }
            //填充数据

            holder.tv_address_item_name.setText(addressListBean.addresslist.get(i).name);

            holder.tv_address_item_number.setText(addressListBean.addresslist.get(i).phoneNumber);
            holder.tv_address_item_address.setText(addressListBean.addresslist.get(i).addressDetail);
            // holder.iv_address_item_select.setBackgroundResource(R.id.iv_address_item_select);

            if (isLongClick) {// 长按状态,进入编辑状态 改变右侧的对号为箭头
                holder.iv_address_item_select.setVisibility(View.VISIBLE);
                holder.iv_address_item_select.setImageResource(R.drawable.arrow_right);
            } else {
                if (i == defaultPositon && defaultPositon != -1) {
                    holder.iv_address_item_select.setVisibility(View.VISIBLE);
                    holder.iv_address_item_select.setImageResource(R.drawable.ok);
                } else {
                    holder.iv_address_item_select.setVisibility(View.GONE);
                }

            }


            return view;
        }
    }

    class AddressListBeanHolder {
        TextView tv_address_item_name, tv_address_item_number, tv_address_item_address;
        ImageView iv_address_item_select;
    }

    @Override
    protected void onInnerClick(View view) {
        Intent intent = new Intent(this, AddAddressActivity.class);
        startActivity(intent);
    }
}
