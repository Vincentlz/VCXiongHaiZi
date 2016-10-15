package hai.xiong.redchilden.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import hai.xiong.redchilden.R;

/**
 * Created by dell on 2016/8/9.
 */
public class FilterListAcitivity extends BaseActivity {
    @ViewInject(R.id.back)
    private TextView back;
    @ViewInject(R.id.title)
    private TextView title;
    @ViewInject(R.id.listview)
    private ListView listview;
    private int index;
    private String[] brands={"蒙牛","伊利","完达山","雅培","三鹿","贝因美","雀巢","美赞臣","惠氏"};
    private String[] prices={"20以下","20-50","50-100","100-200","200-300","300以上",};
    private String[] gongneng={"初生儿","1岁以下","1-2岁","3-5岁","少年","青少年","妇女","老年"};
    private String[] kucun={"无货","1000件下","2000-5000","5000以上","爆仓"};
    private String [] indexItems;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_level1;
    }

    @Override
    protected void initCreate() {
        x.view().inject(this);
        index = getIntent().getIntExtra("index", 0);
        switch (index) {
            case 1:
                title.setText("品牌");
                indexItems=brands;
                break;
            case 2:
                title.setText("价格");
                indexItems=prices;
                break;
            case 3:
                title.setText("功能");
                indexItems=gongneng;
                break;
            case 4:
                title.setText("库存");
                indexItems=kucun;
                break;
        }
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {
        back.setOnClickListener(this);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int position=0;
                switch (index) {
                    case 1:
                        position=100;
                        break;
                    case 2:
                        position=200;
                        break;
                    case 3:
                        position=300;
                        break;
                    case 4:
                        position=400;
                        break;
                }
                Intent intent = new Intent(mContext,FilterActivity.class);
                intent.putExtra("content",indexItems[i]);
                setResult(position,intent);
                finish();
            }
        });
    }



    @Override
    protected void initData() {
        listview.setAdapter(new ArrayAdapter<String>(mContext,android.R.layout.simple_list_item_1,indexItems));
    }

}
