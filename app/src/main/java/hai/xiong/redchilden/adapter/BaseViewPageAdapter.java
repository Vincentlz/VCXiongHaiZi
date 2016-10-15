package hai.xiong.redchilden.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * BaseViewPageAdapter
 * Created by _Ms on 2016/8/1.
 */

public abstract class BaseViewPageAdapter<T> extends PagerAdapter {

    protected List<T> mDataList;

    /**
     * BaseViewPageAdapter
     * @param dataList    数据源
     */
    public BaseViewPageAdapter(List<T> dataList) {
        mDataList = dataList;
    }

    public void setDataList(List<T> dataList) {
        mDataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = initView(position);
        container.addView(view);
        return view;
    }

    /**
     * 初始化View
     * @param position ViewPage初始化Page Positon
     * @return 显示到ViewPage的View
     */
    public abstract View initView(int position);

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
