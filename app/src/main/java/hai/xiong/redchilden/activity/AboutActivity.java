package hai.xiong.redchilden.activity;

import android.widget.Button;
import android.widget.ImageView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import hai.xiong.redchilden.R;

/**关于
 * Created by LvYou on 2016/8/6.
 */
public class AboutActivity extends BaseActivity{

    // 更多
    @ViewInject(R.id.back)
    private ImageView back;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
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
    }

    @Override
    protected void initData() {

    }
}
