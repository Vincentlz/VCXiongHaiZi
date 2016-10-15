package hai.xiong.redchilden.fragment;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import hai.xiong.redchilden.R;
import hai.xiong.redchilden.activity.AboutActivity;
import hai.xiong.redchilden.activity.AccountCenterActivity;
import hai.xiong.redchilden.activity.FadeBackActivity;
import hai.xiong.redchilden.activity.HelpCenterActivity;
import hai.xiong.redchilden.activity.ScanHistoryActivity;

/**
 * 更多
 * Created by LvYou on 2016/8/5.
 */
public class MoreFragment extends BaseFragment implements View.OnClickListener {
    //账户中心
    @ViewInject(R.id.rl_account_center)
    private RelativeLayout rl_account_center;

    //浏览记录
    @ViewInject(R.id.rl_scan_record)
    private RelativeLayout rl_scan_record;

    //帮助中心
    @ViewInject(R.id.rl_help_center)
    private RelativeLayout rl_help_center;

    //用户反馈
    @ViewInject(R.id.rl_fadeback)
    private RelativeLayout rl_fadeback;

    //关于
    @ViewInject(R.id.rl_about)
    private RelativeLayout rl_about;

    //客服电话
    @ViewInject(R.id.bt_cus_tel)
    private Button bt_cus_tel;

    //统一标题
    @ViewInject(R.id.title)
    private TextView titlie;
    @ViewInject(R.id.back)
    private TextView back;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_more;
    }


    @Override
    protected void initCreate() {

    }

    @Override
    protected void initView(View rootView) {
        x.view().inject(this, rootView);
        titlie.setText("更多");
        back.setVisibility(View.GONE);
    }

    @Override
    protected void initListener() {
        rl_account_center.setOnClickListener(this);
        rl_scan_record.setOnClickListener(this);
        rl_help_center.setOnClickListener(this);
        rl_fadeback.setOnClickListener(this);
        rl_about.setOnClickListener(this);
        bt_cus_tel.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //账户中心
            case R.id.rl_account_center:
                startActivity(new Intent(getActivity(), AccountCenterActivity.class));
                break;

            case R.id.rl_scan_record:
                // 浏览记录
                startActivity(new Intent(getActivity(), ScanHistoryActivity.class));
                break;
            //帮助中心
            case R.id.rl_help_center:
                startActivity(new Intent(getActivity(), HelpCenterActivity.class));

                break;
            //用户反馈
            case R.id.rl_fadeback:
                startActivity(new Intent(getActivity(), FadeBackActivity.class));
                break;
            //关于
            case R.id.rl_about:
                startActivity(new Intent(getActivity(), AboutActivity.class));
                break;
            //客服电话
            case R.id.bt_cus_tel:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+"037166666666"));
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
