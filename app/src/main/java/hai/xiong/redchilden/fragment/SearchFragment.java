package hai.xiong.redchilden.fragment;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import hai.xiong.redchilden.R;
import hai.xiong.redchilden.activity.ProductListActivity;
import hai.xiong.redchilden.bean.SearchBean;
import hai.xiong.redchilden.util.Log;
import hai.xiong.redchilden.util.NetUrl;
import hai.xiong.redchilden.util.NetUtil;
import hai.xiong.redchilden.util.SPListUtils;
import hai.xiong.redchilden.view.ShowListView;

public class SearchFragment extends BaseFragment {

    public static final String SEARCH_RESULT = "search_result";
    @ViewInject(R.id.et_keyWord)
    private EditText et_keyWord;//搜索框-------
    @ViewInject(R.id.ib_search)
    private ImageButton ib_search;//搜索按钮
    @ViewInject(R.id.hot_search)
    private RelativeLayout hot_search;//热门搜索
    @ViewInject(R.id.history_search)
    private RelativeLayout history_search;//历史搜索
    @ViewInject(R.id.hot_search_item)
    private ShowListView hot_search_item;//热门搜索条目
    @ViewInject(R.id.history_search_item)
    private ShowListView history_search_item;//历史搜索条目
    @ViewInject(R.id.iv_hot_search)
    private ImageView iv_hot_search;//热门搜索箭头
    @ViewInject(R.id.iv_history_search)
    private ImageView iv_history_search;//历史搜索箭头
    @ViewInject(R.id.tv_clear_history)
    private TextView tv_clear_history;

    @ViewInject(R.id.title)
    private TextView titlie;
    @ViewInject(R.id.back)
    private TextView back;

    private ArrayAdapter<String> vHotadapter;

    private String vKeyword;//文本内容
    private ArrayAdapter<String> vHisadapter;
    private SPListUtils vSpListUtils;
    private SearchBean vSearchBean;

    /**
     * 热门搜索条目监听
     */
    private class OnHotItemClickListener implements OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
           // Log.lyh("热门嗖嗖嗖----------");
            TextView textView = (TextView) view.findViewById(android.R.id.text1);
            vKeyword = textView.getText().toString();
            tv_clear_history.setVisibility(View.VISIBLE);
            iv_history_search.setBackgroundResource(R.drawable.arrow19);
            history_search_item.setVisibility(View.VISIBLE);
            vSpListUtils.add(vKeyword);
            jump2product(vKeyword);
        }
    }

    /**
     * 历史搜索条目监听
     */
    private class OnHistoryItemClickListener implements OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
           // Log.lyh("历史嗖嗖嗖----------");
            TextView textView = (TextView) view.findViewById(android.R.id.text1);
            vKeyword = textView.getText().toString();
            vSpListUtils.add(vKeyword);
            jump2product(vKeyword);
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.search_fragment;
    }

    @Override
    protected void initCreate() {
        vSpListUtils = new SPListUtils(mContext, SEARCH_RESULT, 5);

    }

    @Override
    protected void initView(View rootView) {
        x.view().inject(this, rootView);
        titlie.setText("搜索");
        back.setVisibility(View.GONE);
    }

    @Override
    protected void initListener() {
        ib_search.setOnClickListener(this);
        hot_search.setOnClickListener(this);
        history_search.setOnClickListener(this);
        tv_clear_history.setOnClickListener(this);
        //热门搜索条目点击监听
        hot_search_item.setOnItemClickListener(new OnHotItemClickListener());
        //历史搜索条目点击监听
        history_search_item.setOnItemClickListener(new OnHistoryItemClickListener());

    }

    @Override
    protected void initData() {
        setAdapterChang();
        //判断历史记录是否有数据
        if (vSpListUtils.get().size() != 0) {
            iv_history_search.setBackgroundResource(R.drawable.arrow19);
        } else {
            tv_clear_history.setVisibility(View.GONE);
            iv_history_search.setBackgroundResource(R.drawable.arrow9);
        }

        //判断是否由网络
        //联网获取数据
        NetUtil.newGetRequest(NetUrl.BASE_URL + "/search/recommend", new NetUtil.NetworkCallBack(mContext,true,false,true) {
            @Override
            public void onCache(String cache) {

            }

            @Override
            public void onSucces(String result) {
                parseJson(result);
            }

            @Override
            public void onConnectError() {
                iv_hot_search.setBackgroundResource(R.drawable.arrow9);
            }
        });
//        NetUtil.getRequest(NetUrl.BASE_URL + "/search/recommend", new Callback.CacheCallback<String>() {
//            @Override
//            public boolean onCache(String result) {
//                return false;
//            }
//
//            @Override
//            public void onSuccess(String result) {
//                //Log.lyh("联网onSuccess" + result);
//
//                parseJson(result);
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                //Log.lyh("onError-------------");
//
//                    iv_hot_search.setBackgroundResource(R.drawable.arrow9);
//
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//            }
//
//            @Override
//            public void onFinished() {
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ib_search:
                Log.lyh("搜索搜索");
                vKeyword = et_keyWord.getText().toString().trim();
                if (TextUtils.isEmpty(vKeyword)) {
                    Toast.makeText(mContext, "请输入要搜索的内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                tv_clear_history.setVisibility(View.VISIBLE);
                iv_history_search.setBackgroundResource(R.drawable.arrow19);
                history_search_item.setVisibility(View.VISIBLE);

                vSpListUtils.add(vKeyword);

                jump2product(vKeyword);

                break;
            case R.id.hot_search://热门搜索状态
                //判断热门搜索
                hotorystate();

                break;

            case R.id.history_search://历史搜索状态

                //判断历史记录是否有数据
                historystate();

                break;
            case R.id.tv_clear_history:
                    vSpListUtils.clear();
                    iv_history_search.setBackgroundResource(R.drawable.arrow9);
                    tv_clear_history.setVisibility(View.GONE);
                    setAdapterChang();

                break;
        }
    }


    /**
     * 热门搜索状态
     */
    private void hotorystate() {
        if (hot_search_item.getVisibility() == View.GONE) {
            hot_search_item.setVisibility(View.VISIBLE);

            rotationdownitive(iv_hot_search);
            scal(hot_search_item);
            //animasets(iv_hot_search);//TODO
        } else {
            hot_search_item.setVisibility(View.GONE);
            rotationRightitive(iv_hot_search);

            scalsuo(hot_search_item);
        }
    }

    /**
     * 判断历史记录是否有数据
     */
    private void historystate() {
        if (vSpListUtils.get().size() != 0) {
            tv_clear_history.setVisibility(View.VISIBLE);
            scal(tv_clear_history);
            iv_history_search.setBackgroundResource(R.drawable.arrow19);
            if (history_search_item.getVisibility() == View.GONE) {
                history_search_item.setVisibility(View.VISIBLE);
                scal(history_search_item);
                rotationdownitive(iv_history_search);
            } else {
                tv_clear_history.setVisibility(View.GONE);
                scalsuo(tv_clear_history);
                history_search_item.setVisibility(View.GONE);
                scalsuo(history_search_item);
                rotationRightitive(iv_history_search);
            }
        } else {
            iv_history_search.setBackgroundResource(R.drawable.arrow9);
            if (history_search_item.getVisibility() == View.GONE) {
                history_search_item.setVisibility(View.VISIBLE);

                animasets(iv_history_search);//没有数据动画
            } else {
                animasets(iv_history_search);
                history_search_item.setVisibility(View.GONE);
            }

        }
    }

    /**
     * 搜索结果对接
     * @param keyword
     */
    public void jump2product(String keyword) {

        //适配器
        setAdapterChang();

        //搜索的商品url
        String searchuil = NetUrl.BASE_URL + "/search?keyword=" + keyword;
        //TODO 跳转搜索结果逻辑实现，共用
        Intent intent = new Intent(mContext, ProductListActivity.class);
        intent.putExtra("title", "搜索结果");
        intent.putExtra("URL", searchuil);
        startActivity(intent);
    }

    /**
     * 设置历史记录改变---adapter
     */
    private void setAdapterChang() {
        vHisadapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, vSpListUtils.get());
        history_search_item.setAdapter(vHisadapter);

    }

    /**
     * 解析数据
     *
     * @param result
     */
    private void parseJson(String result) {
        Log.lyh("解析------------");
        Gson gson = new Gson();
        vSearchBean = gson.fromJson(result, SearchBean.class);
        //将数据集合赋值个datas
        if (vHotadapter == null) {
            vHotadapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, vSearchBean.search_keywords);

        }
        hot_search_item.setAdapter(vHotadapter);
    }

    private void rotationRightitive(View v) {
        RotateAnimation ra = new RotateAnimation(0, -90,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        ra.setDuration(300);
        ra.setFillAfter(true);
        v.startAnimation(ra);

    }

    private void rotationdownitive(View v) {

        RotateAnimation ra = new RotateAnimation(-90, 0,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        ra.setDuration(300);
        ra.setFillAfter(true);
        v.startAnimation(ra);

    }

    private void animasets(View v) {

        ObjectAnimator oa = ObjectAnimator.ofFloat(v, "rotation", 0, 90, 0);
        oa.setDuration(800);
        oa.start();

    }

    private void scal(View v) {
        ScaleAnimation sa = new ScaleAnimation(1.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_PARENT, 0.5f, Animation.RELATIVE_TO_PARENT, 0.0f);
        sa.setDuration(300);
        v.startAnimation(sa);
    }

    private void scalsuo(final View v) {

        ScaleAnimation sa = new ScaleAnimation(1.0f, 1.0f, 1.0f, 0.0f, Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0f);
        sa.setDuration(190);
        v.startAnimation(sa);
        v.setVisibility(View.INVISIBLE);

        sa.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                v.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    @Override
    public void onResume() {
        setAdapterChang();
        super.onResume();
    }
}
