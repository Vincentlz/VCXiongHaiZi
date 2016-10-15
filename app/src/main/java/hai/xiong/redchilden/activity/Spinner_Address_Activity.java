/*
package hai.xiong.redchilden.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import hai.xiong.redchilden.R;
import hai.xiong.redchilden.bean.SpinnerBean;
import hai.xiong.redchilden.util.Log;
import hai.xiong.redchilden.util.NetUrl;
import hai.xiong.redchilden.util.NetUtil;

*/
/**
 * Created by 徐心意 on 2016/8/10.
 *//*

public class Spinner_Address_Activity extends BaseActivity {
    @ViewInject(R.id.sp_sheng)
    private Spinner mSp_sheng;
    @ViewInject(R.id.sp_shi)
    private Spinner mSp_shi;
  ewInject(R.id.sp_xian)
   private Gson gson;
    @ViewInject(R.id.editText)
    private TextView editText;
    private SpinnerBean spinnerBean;

    View view;
    TextView tv_sheng;

    */
/* String value;
     int parentId;
     int id;*//*

    private class String_Spinner_CommonCallback implements Callback.CommonCallback<String> {
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
    ArrayAdapter<String> adapter ;
    ArrayAdapter<String> adapter1 ;
    private void parseJson(String result) {

        gson = new Gson();
        spinnerBean = gson.fromJson(result, SpinnerBean.class);
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
            mSp_sheng.setAdapter(adapter);
            adapter1 = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, list1);
            mSp_shi.setAdapter(adapter1);
            mSp_sheng.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    adapter.notifyDataSetChanged();
                    String selectedItem = mSp_sheng.getSelectedItem().toString();
                    Log.xxy("+++"+selectedItem);
                    editText.setText(selectedItem);

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        }
        ;


    }

    private List<String> getChild(List<SpinnerBean.AreaListBean> beanList, int id) {
        List<String> strList = new ArrayList<String>();

        for (SpinnerBean.AreaListBean bean : beanList) {
            if (bean.parentId == id) {
                strList.add(bean.value);
            }
        }
        return strList;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_spinner_address;
    }

    @Override
    protected void initCreate() {


    }

    @Override
    protected void initView() {
        x.view().inject(this);

        view = View.inflate(mContext, R.layout.listview_spinner, null);

        tv_sheng = (TextView) view.findViewById(R.id.tv_sheng);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        String url = NetUrl.BASE_URL + "/addressarea?id=1";
        NetUtil.getRequest(url, new String_Spinner_CommonCallback());

    }

  */
/*  class Myadapter extends BaseAdapter {



        @Override
        public int getCount() {
            return spinnerBean.areaList.size();
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
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {

                view = View.inflate(mContext, R.layout.listview_spinner, null);
                tv_sheng = (TextView) view.findViewById(R.id.tv_sheng);

            }

            id = spinnerBean.areaList.get(i).id;
             parentId = spinnerBean.areaList.get(i).parentId;

            value = spinnerBean.areaList.get(i).value;


            return view;
        }
    }*//*



}

*/
