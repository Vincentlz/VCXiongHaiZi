package hai.xiong.redchilden.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import hai.xiong.redchilden.R;
import hai.xiong.redchilden.util.Log;

/**
 * 用户反馈
 * Created by LvYou on 2016/8/6.
 */
public class FadeBackActivity extends BaseActivity {
    // 更多
    @ViewInject(R.id.back)
    private TextView back;
    @ViewInject(R.id.btn_exit)
    private Button btn_exit;

    //用户评论
    @ViewInject(R.id.tv_comment)
    private EditText tv_comment;

    //用户联系方式
    @ViewInject(R.id.tv_connect)
    private EditText tv_connect;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feed_back;
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
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onInnerClick(View view) {
        super.onInnerClick(view);
        switch (view.getId()) {
            case R.id.btn_exit:
                //用户评论和联系方式
                String userComment = tv_comment.getText().toString().trim();
                String userConnect = tv_connect.getText().toString().trim();
                Log.ly("mUserComment评论内容" + userComment);
                Log.ly("mUserConnect联系方式" + userConnect);
                if (TextUtils.isEmpty(userComment) || TextUtils.isEmpty(userConnect)) {
                    Toast.makeText(getApplicationContext(), "美妞，你还没有输入评论或联系方式哦~~~",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(mContext, "美妞，我们已经收到你的信息了，不要玩系统了。。。", Toast.LENGTH_SHORT).show();
                finish();
                break;
            default:
                break;
        }
    }
}
