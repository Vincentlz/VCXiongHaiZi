package hai.xiong.redchilden.activity;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import hai.xiong.redchilden.R;

/**
 * User: Vincent on (985126792@qq.com)
 * Date: 2016-08-06 18:47
 */

public class SearchResultActivity extends BaseActivity {

    @ViewInject(R.id.price)
    private TextView price;
    @ViewInject(R.id.sales)
    private TextView sales;
    @ViewInject(R.id.highgood)
    private TextView highgood;
    @ViewInject(R.id.putawaytime)
    private TextView puawaytime;

    @Override
    protected int getLayoutId() {
        return R.layout.search_fragment_result;
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
        sales.setOnClickListener(this);
        price.setOnClickListener(this);
        highgood.setOnClickListener(this);
        puawaytime.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sales:
                Toast.makeText(this, "点击销量", Toast.LENGTH_SHORT).show();
                break;
            case R.id.price:
                Toast.makeText(this, "点击价格", Toast.LENGTH_SHORT).show();
                break;
            case R.id.highgood:
                Toast.makeText(this, "点击好评度", Toast.LENGTH_SHORT).show();
                break;
            case R.id.putawaytime:
                Toast.makeText(this, "点击上架时间", Toast.LENGTH_SHORT).show();
                break;
        }
    }


}
