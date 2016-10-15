package hai.xiong.redchilden.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

/**
 * 长条目展示
 * Created by _Ms on 2016/8/7.
 */

public class ShowListView extends LinearLayout implements View.OnClickListener {

    public ShowListView(Context context) {
        super(context);
        initView();
    }

    public ShowListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ShowListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private BaseAdapter mAdapter;
    private AdapterView.OnItemClickListener mItemClickListener;

    /**
     * 设置适配器
     * @param adapter    适配器
     */
    public void setAdapter(BaseAdapter adapter) {
        mAdapter = adapter;
        notifyDataSetChanged();
    }

    /**
     * 刷新数据
     */
    public void notifyDataSetChanged() {
        addLinearLayout();
    }

    /**
     * 设置条目点击监听器
     */
    public void setOnItemClickListener(AdapterView.OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    /**
     * 绑定布局
     */
    public void addLinearLayout() {

        if (mAdapter == null) {
            return;
        }

        removeAllViews();

        for (int x = 0; x < mAdapter.getCount(); x++) {
            View view = mAdapter.getView(x, null, null);
            view.setOnClickListener(this);
            view.setTag(x);
            addView(view, x);
        }

    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        if (mItemClickListener != null) {
            mItemClickListener.onItemClick(null, v, position, 0);
        }
    }

    /**
     * 初始化View
     */
    private void initView() {
        setOrientation(LinearLayout.VERTICAL);
    }
}
