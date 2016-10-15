package hai.xiong.redchilden.activity;

import android.support.v4.app.Fragment;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import hai.xiong.redchilden.R;
import hai.xiong.redchilden.fragment.BrandFragment;
import hai.xiong.redchilden.fragment.HomeFragment;
import hai.xiong.redchilden.fragment.MoreFragment;
import hai.xiong.redchilden.fragment.SearchFragment;
import hai.xiong.redchilden.fragment.ShoppingCarFragment;
import hai.xiong.redchilden.util.GlobalConfig;

public class MainActivity extends BaseActivity {

    private class MyRadioGroupOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            //根据ID切换Fragment
            switch (i) {
                case R.id.rb_menu_home:
                    FragmentChange(0);
                    break;
                case R.id.rb_menu_search:
                    FragmentChange(1);
                    break;
                case R.id.rb_menu_brand:
                    FragmentChange(2);
                    break;
                case R.id.rb_menu_shopping:
                    FragmentChange(3);
                    break;
                case R.id.rb_menu_more:
                    FragmentChange(4);
                    break;
            }
        }
    }

    /**
     * 添加子Fragment
     *
     * @return
     */
    private List<Fragment> fragments;

    /**
     * 双返回键退出
     */
    private long mTwoBackTimeExit = -1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initCreate() {
        fragments = new ArrayList<Fragment>();
    }

    /**
     * 初始化控件
     */
    @ViewInject(R.id.container)
    public FrameLayout frameLayout;
    @ViewInject(R.id.rg_menu)
    public RadioGroup rg_menu;
    @ViewInject(R.id.rb_menu_home)
    public RadioButton rb_menu_home;
    @ViewInject(R.id.rb_menu_shopping)
    public RadioButton rb_menu_shopping;

    @Override
    protected void initView() {
        //注册控件
        x.view().inject(this);
        //默认选择首页
        rb_menu_home.setChecked(true);
    }

    @Override
    protected void initListener() {
        //添加RadioGroup的Button的点击事件
        rg_menu.setOnCheckedChangeListener(new MyRadioGroupOnCheckedChangeListener());
    }

    @Override
    protected void initData() {
        fragments.add(new HomeFragment());
        fragments.add(new SearchFragment());
        fragments.add(new BrandFragment());
        fragments.add(new ShoppingCarFragment());
        fragments.add(new MoreFragment());
        FragmentChange(0);
    }

    /**
     * FragMent的切换
     */
    public void FragmentChange(int position) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragments.get(position)).commit();
        if (position == 0) {
            rg_menu.check(R.id.rb_menu_home);
        }
    }

    @Override
    public void onBackPressed() {

        //双返回键退出
        if (mTwoBackTimeExit == -1 || System.currentTimeMillis() - mTwoBackTimeExit > 2000) {
            Toast toast = Toast.makeText(this, "再次按键退出", Toast.LENGTH_SHORT);
            toast.setDuration(2000);
            toast.show();
            mTwoBackTimeExit = System.currentTimeMillis();
        } else {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        if (!GlobalConfig.isAutoLogin(mContext)) {
            GlobalConfig.loginOutUser(mContext);
        }
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();

        rb_menu_shopping.invalidate();

    }
}
