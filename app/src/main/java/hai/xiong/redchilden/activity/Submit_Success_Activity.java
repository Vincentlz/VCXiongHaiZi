package hai.xiong.redchilden.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import hai.xiong.redchilden.R;

/**
 * Created by 徐心意 on 2016/8/8.
 */
public class Submit_Success_Activity extends BaseActivity {
    @ViewInject(R.id.tv_number)
    private TextView mTv_number;
    @ViewInject(R.id.tv_pay_money)
    private TextView mtv_pay_money;
    @ViewInject(R.id.tv_pay_method)
    private TextView tv_pay_method;
    @ViewInject(R.id.bt_finish)
    private Button mbt_finish;
    @ViewInject(R.id.tv_see)
    private Button mTv_see;
    @ViewInject(R.id.back)
    private TextView back;


    @Override
    protected int getLayoutId() {
        return R.layout.pay_submit_success;
    }

    @Override
    protected void initCreate() {
        Intent intent = getIntent();


    }

    @Override
    protected void initView() {
        x.view().inject(this);
    }

    @Override
    protected void initListener() {
        mbt_finish.setOnClickListener(this);
        mTv_see.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        String paymentType = intent.getStringExtra("paymentType");
        tv_pay_method.setText("支付方式：" + paymentType);
        String orderId = intent.getStringExtra("orderId");
        mTv_number.setText("订单编号：" + orderId);
        String price = intent.getStringExtra("price");
        mtv_pay_money.setText("应付金额：" + price + "RMB");

    }

    @Override
    protected void onInnerClick(View view) {
        switch (view.getId()) {
            case R.id.bt_finish:
                finish();

                break;
            case R.id.tv_see:
                startActivity(new Intent(mContext, MyOrderActivity.class));

        }
    }
}
