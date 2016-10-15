package hai.xiong.redchilden.activity;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import hai.xiong.redchilden.R;
import hai.xiong.redchilden.util.NetUrl;


/**
 * Created by dell on 2016/8/9.
 */
public class FilterActivity extends BaseActivity {
    @ViewInject(R.id.back)
    private TextView back;
    @ViewInject(R.id.title)
    private TextView title;
    @ViewInject(R.id.rl1)
    private RelativeLayout rl1;
    @ViewInject(R.id.rl2)
    private RelativeLayout rl2;
    @ViewInject(R.id.rl3)
    private RelativeLayout rl3;
    @ViewInject(R.id.rl4)
    private RelativeLayout rl4;
    private int index;
    @ViewInject(R.id.brand)
    private TextView brand;
    @ViewInject(R.id.price)
    private TextView price;
    @ViewInject(R.id.gongneng)
    private TextView gongneng;
    @ViewInject(R.id.kucun)
    private TextView kucun;
    private TextView indexTv;
    @ViewInject(R.id.ok)
    private TextView ok;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_filter;
    }

    @Override
    protected void initCreate() {
        x.view().inject(this);
        title.setText("筛选");
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initListener() {
        back.setOnClickListener(this);
        rl1.setOnClickListener(this);
        rl2.setOnClickListener(this);
        rl3.setOnClickListener(this);
        rl4.setOnClickListener(this);
        ok.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onInnerClick(View view) {
        super.onInnerClick(view);
        if (view.getId() == R.id.ok) {
            Intent intent = new Intent(mContext, ProductListActivity.class);
            setResult(200, intent);
            finish();
            return;
        }
        switch (view.getId()) {
            case R.id.rl1:
                //品牌
                index = 1;
                break;
            case R.id.rl2:
                //价格
                index = 2;
                break;
            case R.id.rl3:
                //功能
                index = 3;
                break;
            case R.id.rl4:
                //库存
                index = 4;
                break;
        }
        Intent i = new Intent(mContext, FilterListAcitivity.class);
        i.putExtra("index", index);
        startActivityForResult(i, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {

            String content = data.getStringExtra("content");
            if (content != null) {
                switch (resultCode) {
                    case 100:
                        indexTv = brand;
                        break;
                    case 200:
                        indexTv = price;
                        break;
                    case 300:
                        indexTv = gongneng;
                        break;
                    case 400:
                        indexTv = kucun;
                        break;
                }
                indexTv.setTextColor(Color.RED);
                indexTv.setText(content);
            }

        }
    }
}
