package hai.xiong.redchilden.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;

import hai.xiong.redchilden.R;
import hai.xiong.redchilden.bean.AddressListBean;
import hai.xiong.redchilden.util.GlobalConfig;
import hai.xiong.redchilden.util.NetUrl;
import hai.xiong.redchilden.util.NetUtil;

/**
 * Created by spring on 2016/8/7.
 * 修改地址界面
 */
public class AddressModifyActivity extends BaseActivity{

    @ViewInject(R.id.et_add_address_name)
    private EditText et_add_address_name;
    @ViewInject(R.id.et_add_address_mobile)
    private EditText et_add_address_mobile;
    @ViewInject(R.id.et_add_address_area)
    private EditText et_add_address_area;
    @ViewInject(R.id.et_add_address_area)
    private EditText et_add_address_detail_address;
    //标题
    @ViewInject(R.id.tv_add_address_top)
    private TextView tv_add_address_top;
    //保存
    @ViewInject(R.id.btn_save_address)
    private Button btn_save_address;
    @ViewInject(R.id.back)
    private ImageView back;
    //设为默认地址
    @ViewInject(R.id.btn_set_default_address)
    private Button btn_set_default_address;
    //删除此地址
    @ViewInject(R.id.btn_delete_address)
    private Button btn_delete_address;

   // @ViewInject(R.id.sp_sheng)
   //private Spinner sp_sheng;
   // @ViewInject(R.id.sp_shi)
    //private Spinner sp_shi;
    private AddressListBean.AddresslistBean address;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_modify_address;
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
        btn_save_address.setOnClickListener(this);
        btn_delete_address.setOnClickListener(this);
        btn_set_default_address.setOnClickListener(this);
        et_add_address_area.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        address = (AddressListBean.AddresslistBean) getIntent().getSerializableExtra("address");
        et_add_address_name .setText(address.name);
        et_add_address_mobile.setText(address.phoneNumber);
        et_add_address_area.setText(address.area);
        et_add_address_detail_address.setText(address.addressDetail);

    }

    @Override
    protected void onInnerClick(View view) {
        super.onInnerClick(view);
        switch (view.getId()) {
            case R.id.btn_delete_address:

                String url = NetUrl.BASE_URL + "/addressdelete?id="+address.id;
                NetUtil.newGetRequest(url, new NetUtil.NetworkCallBack(mContext,false,true,true) {
                    @Override
                    public void onCache(String cache) {

                    }

                    @Override
                    public void onSucces(String result) {
                        Toast.makeText(AddressModifyActivity.this,"请求成功",Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onConnectError() {
                        Toast.makeText(AddressModifyActivity.this,"请求失败",Toast.LENGTH_SHORT).show();
                    }
                });

                break;
            case R.id.btn_set_default_address:
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("id",String.valueOf(address.id));
                params.put("name",String.valueOf(address.name));
                params.put("phoneNumber",String.valueOf(address.phoneNumber));
                params.put("city",String.valueOf(address.cityId));
                params.put("province",String.valueOf(address.provinceId));
                params.put("area",String.valueOf(address.areaId));
                params.put("addressDetail",String.valueOf(address.addressDetail));
                params.put("zipCode",String.valueOf(address.zipCode));
                params.put("isDefault",String.valueOf(1));
                params.put("fixedtel",String.valueOf(0));
                params.put("userId",String.valueOf(GlobalConfig.localLoginUserId));

                NetUtil.newPostRequest(NetUrl.BASE_URL + "/addressupdate", params, new NetUtil.NetworkCallBack(mContext,false,true,true) {
                    @Override
                    public void onCache(String cache) {

                    }

                    @Override
                    public void onSucces(String result) {
                        Toast.makeText(AddressModifyActivity.this,"请求成功",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onConnectError() {
                        //Toast.makeText(AddressModifyActivity.this,"请求失败",Toast.LENGTH_SHORT).show();

                    }
                });

                break;
            case R.id.btn_save_address:
                initData();
                String url3 = NetUrl.BASE_URL+"/addresssave";
                params = new HashMap<String,String>();
                params.put("id",1001+"");
                params.put("name",address.name);
                params.put("phoneNumber",address.phoneNumber);
                params.put("province","广东省");
                params.put("city","广州市");
                params.put("area",address.area);
                params.put("addressDetail",address.addressDetail);
                params.put("zipCode","100195");
                NetUtil.newPostRequest(url3,params, new NetUtil.NetworkCallBack(mContext, false, true, true) {
                    @Override
                    public void onCache(String cache) {

                    }

                    @Override
                    public void onSucces(String result) {
                        Toast.makeText(AddressModifyActivity.this,"请求成功",Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onConnectError() {
                        Toast.makeText(AddressModifyActivity.this,"请求失败",Toast.LENGTH_SHORT).show();

                    }
                });

               break;
           /* case R.id.et_add_address_area:
                String url4 = NetUrl.BASE_URL + "/addressarea?id=1";
                NetUtil.newGetRequest(url4, new NetUtil.NetworkCallBack(mContext,false,true,true) {
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

                break;*/
        }


    }
   /* ArrayAdapter<String> adapter ;
    ArrayAdapter<String> adapter1 ;
    private void parseJson(String result) {
        Gson gson = new Gson();
        SpinnerBean spinnerBean = gson.fromJson(result, SpinnerBean.class);
        List<SpinnerBean.AreaListBean> areaList = spinnerBean.areaList;
        List<String> child;

        List<String> list = new ArrayList<String>();
        List<String> list1 = new ArrayList<String>();
        for (SpinnerBean.AreaListBean aBean : areaList) {


            child = getChild(areaList, aBean.id);
            if (child.size() != 0) {
                System.out.println(aBean.value);
                list.add(aBean.value);


            }
            for (String string : child) {
                // System.out.println("\t:" + string);
                list1.add(string);
            }
            adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, list);
            sp_sheng.setAdapter(adapter);
            adapter1 = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, list1);
//            sp_shi.setAdapter(adapter1);
            sp_sheng.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                   adapter.notifyDataSetChanged();
                    String selectedItem = sp_sheng.getSelectedItem().toString();
                    Log.xxy("+++"+selectedItem);
                    et_add_address_area.setText(selectedItem);

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
           *//* sp_shi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    // adapter.notifyDataSetChanged();
                    String selectedItem = sp_sheng.getSelectedItem().toString();
                    Log.xxy("+++"+selectedItem);
                    et_add_address_area.setText(selectedItem);

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });*//*
*/


  /*  private List<String> getChild(List<SpinnerBean.AreaListBean> beanList, int id) {
        List<String> strList = new ArrayList<String>();

        for (SpinnerBean.AreaListBean bean : beanList) {
            if (bean.parentId == id) {
                strList.add(bean.value);
            }
        }
        return strList;
    }*/
}
