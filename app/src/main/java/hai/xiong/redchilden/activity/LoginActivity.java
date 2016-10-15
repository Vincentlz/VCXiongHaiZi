package hai.xiong.redchilden.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;

import hai.xiong.redchilden.R;
import hai.xiong.redchilden.bean.LoginBean;
import hai.xiong.redchilden.util.GlobalConfig;
import hai.xiong.redchilden.util.NetUrl;
import hai.xiong.redchilden.util.NetUtil;


/**
 * Created by 14144 on 2016/8/5.
 */
public class LoginActivity extends BaseActivity {

    //登录名字和密码
    @ViewInject(R.id.et_username)
    EditText et_username;
    @ViewInject(R.id.et_password)
    EditText et_password;
    @ViewInject(R.id.tv_forgot_psw)
    TextView tv_forgot_psw;

    //登录和注册按钮
    @ViewInject(R.id.btn_login)
    private TextView btn_login;
    @ViewInject(R.id.btn_register)
    private TextView btn_register;
    @ViewInject(R.id.back)
    private TextView back;

    @ViewInject(R.id.login_auto)
    CheckBox login_auto;

    @ViewInject(R.id.title)
    private TextView title;

    private String username;
    private String password;
    private Gson gson;


    @Override
    protected int getLayoutId() {

        return R.layout.activity_login;
    }

    @Override
    protected void initCreate() {

        gson = new Gson();
    }

    @Override
    protected void initView() {
        x.view().inject(this);

        title.setText("登录");
    }

    @Override
    protected void initListener() {

        btn_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        tv_forgot_psw.setOnClickListener(this);
        back.setOnClickListener(this);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onInnerClick(View view) {
        super.onInnerClick(view);

        switch (view.getId()) {
            case R.id.btn_login: // 登录
                loginUser();
                break;
            case R.id.btn_register: // 注册
                startActivity(new Intent(getBaseContext(), RegisterActivity.class));
                break;
            case R.id.tv_forgot_psw: // 找回密码
                Toast.makeText(mContext, "姑娘，此生不再有悔意", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    private void loginUser() {

        username = et_username.getText().toString().trim();
        Intent intent=new Intent();
        intent.putExtra("username",username);
        password = et_password.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(getApplicationContext(), "用户名不能为空",
                    Toast.LENGTH_SHORT).show();


            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "密码不能为空",
                    Toast.LENGTH_SHORT).show();

            return;
        }




        final HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", username);
        params.put("password", password);
        String url = NetUrl.BASE_URL + "/login";

        NetUtil.newPostRequest(url, params, new NetUtil.NetworkCallBack(mContext,false,true,true) {
            @Override
            public void onCache(String cache) {

            }

            @Override
            public void onSucces(String result) {

                LoginBean loginbean = gson.fromJson(result,LoginBean.class);

                if (!"login".equals(loginbean.response)) {
                    Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_LONG).show();
                    return;
                }


                int userId = loginbean.userInfo.userId;

                GlobalConfig.setLoginState(mContext, userId, username);
                if (login_auto.isChecked()) {
                    GlobalConfig.setLoginAutoLoginState(mContext, true );
                }

                finish();

            }

            @Override
            public void onConnectError() {

            }
        });




    }

}
