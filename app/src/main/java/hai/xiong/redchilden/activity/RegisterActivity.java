package hai.xiong.redchilden.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;

import hai.xiong.redchilden.R;
import hai.xiong.redchilden.util.Log;
import hai.xiong.redchilden.util.NetUrl;
import hai.xiong.redchilden.util.NetUtil;

/**
 * Created by 14144 on 2016/8/6.
 */
public class RegisterActivity extends BaseActivity {


    @ViewInject(R.id.et_account)
    EditText et_username;
    @ViewInject(R.id.et_psw)
    EditText et_password;
    @ViewInject(R.id.et_re_psw)
    EditText et_re_pwd;

    @ViewInject(R.id.btn_register)
    private TextView btn_register;
    @ViewInject(R.id.back)
    private TextView btn_back;

    @ViewInject(R.id.title)
    private TextView title;

    private String username;
    private String password;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_regist;
    }

    @Override
    protected void initCreate() {

    }

    @Override
    protected void initView() {

        x.view().inject(this);
        title.setText("注册");
    }

    @Override
    protected void onInnerClick(View view) {
        super.onInnerClick(view);

        switch (view.getId()) {
            case R.id.btn_register: // 注册按钮
                registerUser();
                break;
        }
    }

    private void registerUser() {
        username = et_username.getText().toString().trim();
        password = et_password.getText().toString().trim();
        String repassword = et_re_pwd.getText().toString().trim();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)
                || TextUtils.isEmpty(repassword)) {
            Toast.makeText(getApplicationContext(), "用户名或密码不能为空",
                    Toast.LENGTH_SHORT).show();
            return;
        } else if (!password.endsWith(repassword)) {
            Toast.makeText(getApplicationContext(), "两次密码不一致",
                    Toast.LENGTH_SHORT).show();
            return;
        }


        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", username);
        params.put("password", password);
        String url = NetUrl.BASE_URL + "/register";

        NetUtil.newPostRequest(url, params, new NetUtil.NetworkCallBack(mContext,false,true,true) {
            @Override
            public void onCache(String cache) {

            }

            @Override
            public void onSucces(String result) {

                Log.lsy("result:" + result);

                boolean registerResult = false;
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    registerResult = "register".equals(jsonObject.getString("response"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                if (registerResult) {
                    Toast.makeText(mContext, "注册成功, 快快登录买买买吧",
                            Toast.LENGTH_SHORT).show();
                    finish();

                } else {
                    Toast.makeText(mContext, "注册失败, 用户名已存在",
                            Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onConnectError() {

                Toast.makeText(mContext, "世界上遥远的距离不是天涯海角，而是木有网", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    protected void initListener() {

        btn_back.setOnClickListener(this);

        btn_register.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }


}
