package hai.xiong.redchilden.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hai.xiong.redchilden.R;
import hai.xiong.redchilden.spinner.DBManager;
import hai.xiong.redchilden.spinner.MyAdapter;
import hai.xiong.redchilden.spinner.MyListItem;
import hai.xiong.redchilden.util.Log;
import hai.xiong.redchilden.util.NetUrl;
import hai.xiong.redchilden.util.NetUtil;

/**
 * Created by spring on 2016/8/6.
 * 添加地址界面
 */
public class AddAddressActivity extends BaseActivity {

    @ViewInject(R.id.et_add_address_name)
    private EditText et_add_address_name;
    @ViewInject(R.id.et_add_address_mobile)
    private EditText et_add_address_mobile;
    @ViewInject(R.id.et_add_address_area)
    private EditText et_add_address_area;
    @ViewInject(R.id.et_add_address_area)
    private EditText et_add_address_detail_address;
    @ViewInject(R.id.tv_add_address_top)
    private TextView tv_add_address_top;
    @ViewInject(R.id.btn_save_address)
    private Button btn_save_address;
    @ViewInject(R.id.back)
    private Button back;

  //  @ViewInject(R.id.sp_sheng)
  //  private Spinner sp_sheng;

    private String name;
    private String number;
    private String area;
    private String address;

    /** Called when the activity is first created. */
    private DBManager dbm;
    private SQLiteDatabase db;
    private Spinner spinner1 = null;
    private Spinner spinner2=null;
    private Spinner spinner3=null;
    private String province=null;
    private String city=null;
    private String district=null;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_address;
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
        btn_save_address.setOnClickListener(this);
        back.setOnClickListener(this);
        et_add_address_area.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        tv_add_address_top.setText("新增地址");
        name = et_add_address_name.getText().toString().trim();
        number = et_add_address_mobile.getText().toString().trim();
        area = et_add_address_area.getText().toString().trim();
        address = et_add_address_detail_address.getText().toString().trim();
        spinner1=(Spinner)findViewById(R.id.spinner1);
        spinner2=(Spinner)findViewById(R.id.spinner2);
        spinner3=(Spinner)findViewById(R.id.spinner3);
        spinner1.setPrompt("省");
        spinner2.setPrompt("城市");
        spinner3.setPrompt("地区");

        initSpinner1();

       /*
       else if(TextUtils.isEmpty(number)){
            Toast.makeText(mContext,"手机不能为空",Toast.LENGTH_SHORT).show();
            return;
        }else if(TextUtils.isEmpty(address)) {
            Toast.makeText(mContext, "详细地址为空", Toast.LENGTH_SHORT).show();
            return;
        }
        }*/

    }


    @Override
    protected void onInnerClick(View view) {
        super.onInnerClick(view);
        initData();
        switch (view.getId()){
            case R.id.btn_save_address:
                Log.lyh(name+"-->"+number+"--->"+address);
                if(TextUtils.isEmpty(name)||TextUtils.isEmpty(number)||TextUtils.isEmpty(address)){
                    Toast.makeText(mContext,"请完善信息",Toast.LENGTH_SHORT).show();
                    return;
                }else {

                    //        Log.b("name:" + name);
                    HashMap<String,String> params = new HashMap<String,String>();
                    params.put("name",name);
                    params.put("phoneNumber",number);
                    params.put("province","广东省");
                    params.put("city","广州市");
                    params.put("area","广州一区");
                    params.put("addressDetail",address);
                    params.put("zipCode","000000");
                    NetUtil.newPostRequest(NetUrl.BASE_URL + "/addresssave", params, new NetUtil.NetworkCallBack(mContext,false,true,true) {
                        @Override
                        public void onCache(String cache) {

                        }

                        @Override
                        public void onSucces(String result) {
                            Toast.makeText(AddAddressActivity.this, "请求成功", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onConnectError() {
                            Toast.makeText(AddAddressActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                break;
            case R.id.et_add_address_area:


                break;
        }
    }

    public void initSpinner1(){
        dbm = new DBManager(AddAddressActivity.this);
        dbm.openDatabase();
        db = dbm.getDatabase();
        List<MyListItem> list = new ArrayList<MyListItem>();

        try {
            String sql = "select * from province";
            Cursor cursor = db.rawQuery(sql,null);
            cursor.moveToFirst();
            while (!cursor.isLast()){
                String code=cursor.getString(cursor.getColumnIndex("code"));
                byte bytes[]=cursor.getBlob(2);
                String name=new String(bytes,"gbk");
                MyListItem myListItem=new MyListItem();
                myListItem.setName(name);
                myListItem.setPcode(code);
                list.add(myListItem);
                cursor.moveToNext();
            }
            String code=cursor.getString(cursor.getColumnIndex("code"));
            byte bytes[]=cursor.getBlob(2);
            String name=new String(bytes,"gbk");
            MyListItem myListItem=new MyListItem();
            myListItem.setName(name);
            myListItem.setPcode(code);
            list.add(myListItem);

        } catch (Exception e) {
        }
        dbm.closeDatabase();
        //db.close();

        MyAdapter myAdapter = new MyAdapter(AddAddressActivity.this,list);
        spinner1.setAdapter(myAdapter);
        spinner1.setOnItemSelectedListener(new SpinnerOnSelectedListener1());
    }
    public void initSpinner2(String pcode){
        dbm = new DBManager(AddAddressActivity.this);
        dbm.openDatabase();
        db = dbm.getDatabase();
        List<MyListItem> list = new ArrayList<MyListItem>();

        try {
            String sql = "select * from city where pcode='"+pcode+"'";
            Cursor cursor = db.rawQuery(sql,null);
            cursor.moveToFirst();
            while (!cursor.isLast()){
                String code=cursor.getString(cursor.getColumnIndex("code"));
                byte bytes[]=cursor.getBlob(2);
                String name=new String(bytes,"gbk");
                MyListItem myListItem=new MyListItem();
                myListItem.setName(name);
                myListItem.setPcode(code);
                list.add(myListItem);
                cursor.moveToNext();
            }
            String code=cursor.getString(cursor.getColumnIndex("code"));
            byte bytes[]=cursor.getBlob(2);
            String name=new String(bytes,"gbk");
            MyListItem myListItem=new MyListItem();
            myListItem.setName(name);
            myListItem.setPcode(code);
            list.add(myListItem);

        } catch (Exception e) {
        }
        dbm.closeDatabase();
        db.close();

        MyAdapter myAdapter = new MyAdapter(AddAddressActivity.this,list);
        spinner2.setAdapter(myAdapter);
        spinner2.setOnItemSelectedListener(new SpinnerOnSelectedListener2());
    }
    public void initSpinner3(String pcode){
        dbm = new DBManager(AddAddressActivity.this);
        dbm.openDatabase();
        db = dbm.getDatabase();
        List<MyListItem> list = new ArrayList<MyListItem>();

        try {
            String sql = "select * from district where pcode='"+pcode+"'";
            Cursor cursor = db.rawQuery(sql,null);
            cursor.moveToFirst();
            while (!cursor.isLast()){
                String code=cursor.getString(cursor.getColumnIndex("code"));
                byte bytes[]=cursor.getBlob(2);
                String name=new String(bytes,"gbk");
                MyListItem myListItem=new MyListItem();
                myListItem.setName(name);
                myListItem.setPcode(code);
                list.add(myListItem);
                cursor.moveToNext();
            }
            String code=cursor.getString(cursor.getColumnIndex("code"));
            byte bytes[]=cursor.getBlob(2);
            String name=new String(bytes,"gbk");
            MyListItem myListItem=new MyListItem();
            myListItem.setName(name);
            myListItem.setPcode(code);
            list.add(myListItem);

        } catch (Exception e) {
        }
        dbm.closeDatabase();
        db.close();

        MyAdapter myAdapter = new MyAdapter(AddAddressActivity.this,list);
        spinner3.setAdapter(myAdapter);
        spinner3.setOnItemSelectedListener(new SpinnerOnSelectedListener3());
    }

    class SpinnerOnSelectedListener1 implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> adapterView, View view, int position,
                                   long id) {
            province=((MyListItem) adapterView.getItemAtPosition(position)).getName();
            String pcode =((MyListItem) adapterView.getItemAtPosition(position)).getPcode();
            initSpinner2(pcode);
            initSpinner3(pcode);
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
            // TODO Auto-generated method stub
        }
    }
    class SpinnerOnSelectedListener2 implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> adapterView, View view, int position,
                                   long id) {
            city=((MyListItem) adapterView.getItemAtPosition(position)).getName();
            String pcode =((MyListItem) adapterView.getItemAtPosition(position)).getPcode();

            initSpinner3(pcode);
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
            // TODO Auto-generated method stub
        }
    }

    class SpinnerOnSelectedListener3 implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> adapterView, View view, int position,
                                   long id) {
            district=((MyListItem) adapterView.getItemAtPosition(position)).getName();
            Toast.makeText(AddAddressActivity.this, province+" "+city+" "+district, Toast.LENGTH_LONG).show();
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
            // TODO Auto-generated method stub
        }
    }




}
