package hai.xiong.redchilden.activity;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import hai.xiong.redchilden.R;
import hai.xiong.redchilden.bean.HelpContentBean;
import hai.xiong.redchilden.util.Log;
import hai.xiong.redchilden.util.NetUrl;
import hai.xiong.redchilden.util.NetUtil;

/**
 * 帮助内容
 * Created by LvYou on 2016/8/8.
 */
public class HelpContentActivity extends BaseActivity{
//返回
    @ViewInject(R.id.back)
    private ImageView back;

    @ViewInject(R.id.tv_help_list)
    private TextView tv_help_list;

//帮助内容标题
    @ViewInject(R.id.tv_help_title)
    private TextView tv_help_title;

    //帮助内容
    @ViewInject(R.id.tv_help_content)
    private TextView tv_help_content;


    //解析帮助内容数据
    private HelpContentBean mHelpContentBean;

    //商品列表标题
    private String mTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_help_content;
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
    }

    @Override
    protected void initData() {
        //获取帮助列表传递的userid
        Intent intent = getIntent();
        String userId = intent.getStringExtra("id");
        mTitle = intent.getStringExtra("title");
        NetUtil.getRequest(NetUrl.BASE_URL + "/help_detail?id=1" , new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {

                Log.ly("解析解析帮助内容数据成功"+result);
                parseJson(result);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.ly("解析帮助内容数据失败" + ex);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.ly("解析帮助内容数据取消" + cex);
            }

            @Override
            public void onFinished() {
                Log.ly("解析帮助内容数据结束");

            }
        });
    }

    //解析帮助内容数据
    private void parseJson(String result) {
        Gson gson = new Gson();
        mHelpContentBean = gson.fromJson(result, HelpContentBean.class);
        Log.ly("解析帮助内容数据成功：" + mHelpContentBean.help.get(0).title);
        if (mHelpContentBean.help.size() == 0){
            Toast.makeText(mContext,"帮助中心无数据",Toast.LENGTH_SHORT).show();
            finish();
        }

//        Toast.makeText(mContext, mHelpContentBean.help.get(0).content, Toast.LENGTH_SHORT).show();

        //解析数据展示
        tv_help_list.setText(mTitle);//展示商品列表标题
        tv_help_title.setText("1.问："+mHelpContentBean.help.get(0).title + "？");
        tv_help_content.setText("答："+mHelpContentBean.help.get(0).content);


    }

}
