package hai.xiong.redchilden.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import hai.xiong.redchilden.R;

public class SplshActivity extends BaseActivity {
    private static final int SPLSH_TIME = 100;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splsh;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int Whit = msg.what;
            if (Whit == SPLSH_TIME) {
                Intent intent = new Intent(SplshActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    };

    @Override
    protected void initCreate() {

    }

    @ViewInject(R.id.iv_splsh)
    public ImageView imageView;

    @Override
    protected void initView() {
        View view = View.inflate(mContext, R.layout.activity_main, null);
        //注册控件
        x.view().inject(this);
        //初始化闪屏页面
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageResource(R.drawable.welcome);
        Animation a = new ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        a.setFillAfter(true);
        a.setDuration(500);
        imageView.startAnimation(a);

    }

    @Override
    protected void initListener() {
        // TODO 闪屏测试处理减少时间
        handler.sendEmptyMessageDelayed(SPLSH_TIME, 2000);
    }

    @Override
    protected void initData() {
    }


}
