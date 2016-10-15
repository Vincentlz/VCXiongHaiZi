package hai.xiong.redchilden.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/**
 * BaseFragment
 * Created by _Ms on 2016/8/5.
 */
public abstract class BaseFragment extends Fragment implements
        View.OnClickListener {

    /**
     * Context
     */
    protected Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();

        View view = View.inflate(mContext, getLayoutId(), null);

        initCreate();
        initView(view);
        initListener();
        initData();

        return view;
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
     * @param rootView    指定LayoutId转换成的View
     */
    protected abstract void initView(View rootView);

    /**
     * 初始化监听器
     */
    protected abstract void initListener();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    @Override
    public void onClick(View v) {

    }

}
