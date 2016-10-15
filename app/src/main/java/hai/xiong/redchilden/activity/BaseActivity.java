package hai.xiong.redchilden.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import hai.xiong.redchilden.R;

/**
 * BaseActivity
 * Created by _Ms on 2016/8/5.
 */
public abstract class BaseActivity extends FragmentActivity implements View.OnClickListener {

    /**
     * Context
     */
    protected Activity mContext;

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.back: // 统一简单返回键处理
                finish();

                break;

            default: // 非统一处理onClick
                onInnerClick(v);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        setContentView(getLayoutId());

        initCreate();
        initView();
        initListener();
        initData();
    }

    /**
     * 设置ActivityLayout
     * @return Layout ID
     */
    protected abstract int getLayoutId();

    /**
     * 初始化配置
     */
    protected abstract void initCreate();

    /**
     * 初始化View
     * 处理
     */
    protected abstract void initView();

    /**
     * 初始化监听器
     */
    protected abstract void initListener();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 抽取处理 onClick 方法
     * @param view    被点击的View
     */
    protected void onInnerClick(View view) {}


}
