package hai.xiong.redchilden.activity;

import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hai.xiong.redchilden.R;
import hai.xiong.redchilden.bean.LoginBean;
import hai.xiong.redchilden.bean.WuliuBean;
import hai.xiong.redchilden.util.GlobalConfig;
import hai.xiong.redchilden.util.Log;
import hai.xiong.redchilden.util.NetUrl;
import hai.xiong.redchilden.util.NetUtil;
import hai.xiong.redchilden.util.SpSkuUtils;

/**
 * Created by 14144 on 2016/8/8.
 */
public class WuliuSearchActivity extends BaseActivity {

    @ViewInject(R.id.back)
    private ImageView back;

    //发货方式
    @ViewInject(R.id.tv_send)
    private TextView tv_send;
    //物流编号
    @ViewInject(R.id.tv_number)
    private TextView tv_number;
    //物流公司
    @ViewInject(R.id.tv_company)
    private TextView tv_company;
    //运单号码
    @ViewInject(R.id.tv_list)
    private TextView tv_list;
    //物流跟踪
    @ViewInject(R.id.tv_track)
    private TextView tv_track;

    @ViewInject(R.id.lv_detail)
    private ListView lv_detail;

    private Gson gson;
    private LoginBean.UserInfoBean userId;
    private WuliuBean wuliu;
    private WuliuBean.FollowInfoBean followInfoBean;
    private String followinfo;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_wuliu_search;
    }

    @Override
    protected void initCreate() {
        gson = new Gson();
    }

    @Override
    protected void initView() {
        x.view().inject(this);
        userId = GlobalConfig.getLoginUserBean(mContext);
    }

    @Override
    protected void initListener() {

        back.setOnClickListener(this);

    }

    @Override
    protected void initData() {



        String url = NetUrl.BASE_URL + "/logistics?userId=" + userId;
        NetUtil.newGetRequest(url, new NetUtil.NetworkCallBack(mContext,false,true,true) {
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

        WuliuBean wuliuBean = gson.fromJson(result, WuliuBean.class);

        List<String> dataStr = new ArrayList<String>();
        for (WuliuBean.FollowInfoBean infoBean : wuliuBean.followInfo) {
            dataStr.add(infoBean.followinfo);
        }


        ArrayAdapter adapter = new ArrayAdapter(mContext,android.R.layout.simple_list_item_1,dataStr);
        lv_detail.setAdapter(adapter);

    }


}
