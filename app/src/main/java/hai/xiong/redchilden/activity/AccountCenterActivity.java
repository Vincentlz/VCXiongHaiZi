package hai.xiong.redchilden.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Random;

import hai.xiong.redchilden.R;
import hai.xiong.redchilden.bean.AccountCenterBean;
import hai.xiong.redchilden.bean.LoginBean;
import hai.xiong.redchilden.util.GlobalConfig;
import hai.xiong.redchilden.util.Log;
import hai.xiong.redchilden.util.NetUrl;
import hai.xiong.redchilden.util.NetUtil;

/**
 * 账户中心
 * Created by LvYou on 2016/8/5.
 */
public class AccountCenterActivity extends BaseActivity {

    // 返回
    @ViewInject(R.id.back)
    private ImageView back;

    //退出登录
    @ViewInject(R.id.btn_exit)
    private Button btn_exit;

    //我的订单
    @ViewInject(R.id.rl_my_order)
    private RelativeLayout rb_my_order;

    //地址管理
    @ViewInject(R.id.rl_address_manager)
    private RelativeLayout rl_address_manager;

    //优惠券、礼品卡
    @ViewInject(R.id.rl_discount_gift)
    private RelativeLayout rl_discount_gift;

    //收藏夹
    @ViewInject(R.id.rl_collection)
    private RelativeLayout rl_collection;

    //json封装javaBean
    private AccountCenterBean mAccountCenterBean;

    //用户名
    @ViewInject(R.id.tv_user_msg)
    private TextView tv_user_msg;

    //会员等级
    @ViewInject(R.id.tv_member_rank)
    private TextView tv_member_rank;

    //总积分
    @ViewInject(R.id.tv_all_grade)
    private TextView tv_all_grade;

    //我的订单
    @ViewInject(R.id.tv_mine_orders)
    private TextView tv_mine_orders;

    //优惠券、礼品卡
    @ViewInject(R.id.tv_gift_card)
    private TextView tv_gift_card;

    //收藏夹
    @ViewInject(R.id.tv_faverite)
    private TextView tv_faverite;
    private LoginBean.UserInfoBean userBean;

    //用户id
    private int mLoginUserId;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_accountcenter;
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
        btn_exit.setOnClickListener(this);
        rb_my_order.setOnClickListener(this);
        rl_address_manager.setOnClickListener(this);
        rl_discount_gift.setOnClickListener(this);
        rl_collection.setOnClickListener(this);
    }

    @Override
    protected void initData() {

        if (!GlobalConfig.isLogin(mContext)) {
            startActivityForResult(new Intent(mContext, LoginActivity.class), 100);
        } else {
            getUserInfor();
        }

    }

    private void getUserInfor() {
        userBean = GlobalConfig.getLoginUserBean(mContext);
        mLoginUserId = userBean.userId;
        Log.ly(mLoginUserId +"mLoginUserId");
        NetUtil.newGetRequest(NetUrl.BASE_URL + "/userinfo?userId=" + mLoginUserId, new NetUtil.NetworkCallBack(mContext,true,true,true) {
            @Override
            public void onCache(String cache) {
                parseJson(cache);
                Log.ly("解析成功"+cache);
            }

            @Override
            public void onSucces(String result) {
                parseJson(result);
                Log.ly("解析成功"+result);
            }

            @Override
            public void onConnectError() {

            }
        });
//        NetUtil.getRequest(NetUrl.BASE_URL + "/userinfo?userId=" + mLoginUserId, new Callback.CommonCallback<String>() {
//
//            @Override
//            public void onSuccess(String result) {
//
////                Log.ly(result);
//                parseJson(result);
//                Log.xxy("解析成功"+result);
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                ex.printStackTrace();
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//                cex.printStackTrace();
//            }
//
//            @Override
//            public void onFinished() {
//
//
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (GlobalConfig.isLogin(mContext)) {
            getUserInfor();
        } else {
            finish();
        }
    }

    /**
     * 解析数据
     *
     * @param result
     */
    private void parseJson(String result) {
        Gson gson = new Gson();
        mAccountCenterBean = gson.fromJson(result, AccountCenterBean.class);
//        Log.ly("解析成功：" + mAccountCenterBean.userInfo.level);

        tv_user_msg.setText("用户名：" + userBean.userName);
        tv_member_rank.setText("会员等级：" + mAccountCenterBean.userInfo.level);
        tv_all_grade.setText("总积分：" + mAccountCenterBean.userInfo.bonus);
        tv_gift_card.setText("优惠券/礼品卡：(3)");
        if (mAccountCenterBean.userInfo.orderCount == 0){
            tv_mine_orders.setText("我的订单：(10)");
        }else {
            tv_mine_orders.setText("我的订单：(" + mAccountCenterBean.userInfo.orderCount + ")");
        }

        if (mAccountCenterBean.userInfo.favoritesCount == 0){
            tv_faverite.setText("收藏夹：(4)");
        }else {
            tv_faverite.setText("收藏夹：(" + mAccountCenterBean.userInfo.favoritesCount + ")");
        }


    }

    @Override
    protected void onInnerClick(View view) {
        super.onInnerClick(view);

        switch (view.getId()) {
            //退出登录
            case R.id.btn_exit:
                GlobalConfig.loginOutUser(mContext);
                startActivity(new Intent(mContext, LoginActivity.class));
                finish();
                break;

            case R.id.rl_my_order:
                //订单管理
                startActivity(new Intent(mContext, MyOrderActivity.class));
                break;

            case R.id.rl_address_manager:
                //地址管理
                startActivity(new Intent(mContext, AddressListActivity.class));
                break;
            case R.id.rl_discount_gift:
                //优惠券礼品卡
                startActivity(new Intent(mContext, Pay_Less_Activity.class));
                break;

            case R.id.rl_collection:
                //收藏夹
                Intent intent = new Intent(mContext, FaveriteActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }

}
