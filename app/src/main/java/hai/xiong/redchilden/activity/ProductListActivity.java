package hai.xiong.redchilden.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import hai.xiong.redchilden.R;
import hai.xiong.redchilden.adapter.BaseListViewAdapter;
import hai.xiong.redchilden.bean.ProductLists;
import hai.xiong.redchilden.util.NetUrl;
import hai.xiong.redchilden.util.NetUtil;

/**
 * Created by dell on 2016/8/6.
 */
public class ProductListActivity extends BaseActivity {

    //排序方式
    private final String ORDERBY_SALES ="sales";
    private final String ORDERBY_PRICE ="price";
    private final String ORDERBY_GOOD ="good";
    private final String ORDERBY_TIME ="time";
    //商品条目
    @ViewInject(R.id.productLv)
    private ListView productLv;
    @ViewInject(R.id.back)
    private TextView back;
    //价格排序
    @ViewInject(R.id.textRankPrice)
    private TextView textRankPrice;
    //销量排序
    @ViewInject(R.id.textRankSale)
    private TextView textRankSale;
    //好评排序
    @ViewInject(R.id.textRankGood)
    private TextView textRankGood;
    //上架时间排序
    @ViewInject(R.id.textRankTime)
    private TextView textRankTime;
    //标题
    @ViewInject(R.id.textTitle)
    private TextView textTitle;
    //筛选
    @ViewInject(R.id.filtTv)
    private TextView filtTv;
    @ViewInject(R.id.linRank)
    private LinearLayout linRank;
    @ViewInject(R.id.search_failed)
    private RelativeLayout search_failed;
    @ViewInject(R.id.tv_back)
    private TextView tv_back;
    @ViewInject(R.id.gridview)
    private GridView gridview;
    //切换
    @ViewInject(R.id.qiehuan)
    private ImageView qiehuan;
    private ArrayList<TextView> rankTitle;
    private ArrayList<ProductLists.ProductlistBean> datas;
    //点击排序的集合索引
    private int index=0;
    //选择的排序
    private String orderby=ORDERBY_SALES;

    private boolean isList ;
    private AdapterView.OnItemClickListener onItemClickListener;
    private String requestUrl;
    private String title;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_product_list;
    }

    @Override
    protected void initCreate() {
        x.view().inject(this);
        //获得搜索页面的结果
        String url = getIntent().getStringExtra("URL");
        title = getIntent().getStringExtra("title");
        textTitle.setText(title);
        requestUrl = url+"&orderby=";
        if("搜索结果".equals(title)){
                //搜索页面
            filtTv.setVisibility(View.GONE);
        }
        if("专题商品".equals(title)){
            requestUrl=NetUrl.BASE_URL +"/productlist?page=1&pageNum=10&cid=1&orderby=";
        }
    }

    @Override
    protected void initView() {

        isList=true;
        gridview.setVisibility(View.GONE);
        rankTitle=new ArrayList<>();
        rankTitle.add(textRankSale);
        rankTitle.add(textRankPrice);
        rankTitle.add(textRankGood);
        rankTitle.add(textRankTime);
    }

    @Override
    protected void initListener() {
        back.setOnClickListener(this);
        textRankPrice.setOnClickListener(this);
        textRankSale.setOnClickListener(this);
        textRankGood.setOnClickListener(this);
        textRankTime.setOnClickListener(this);
        qiehuan.setOnClickListener(this);
        tv_back.setOnClickListener(this);
        //筛选按钮
        filtTv.setOnClickListener(this);
        //设置条目点击事件
        onItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // TODO 移除ListView待测试
                if(isList){
                    i = i-productLv.getHeaderViewsCount();
                }
                ProductLists.ProductlistBean productlistBean = datas.get(i);
                Intent intent = new Intent(mContext,ProductDetailsActivity.class);
                intent.putExtra("id", productlistBean.id);
                startActivity(intent);
            }
        };
        productLv.setOnItemClickListener(onItemClickListener);
        gridview.setOnItemClickListener(onItemClickListener);
    }

    @Override
    protected void onInnerClick(View view) {
        super.onInnerClick(view);
        if(view.getId()==R.id.tv_back){
                finish();
              return;
        }
        switch (view.getId()) {

            case R.id.textRankSale:
                //销量排序
                orderby=ORDERBY_SALES;
                index=0;
                break;
            case R.id.textRankPrice:
                //价格排序
                orderby=ORDERBY_PRICE;
                index=1;
                break;
            case R.id.textRankGood:
                //好评排序
                orderby=ORDERBY_GOOD;
                index=2;
                break;
            case R.id.textRankTime:
                //上架时间排序
                orderby=ORDERBY_TIME;
                index=3;
                break;
            case R.id.filtTv:
               // Toast.makeText(mContext,"筛选君请假了。请下次再尝试",Toast.LENGTH_SHORT).show();
                startActivityForResult(new Intent(mContext,FilterActivity.class),100);
                break;
            case R.id.qiehuan:
                if(isList){
                    productLv.setVisibility(View.GONE);
                    gridview.setVisibility(View.VISIBLE);
                    qiehuan.setImageResource(R.drawable.icon_pic_grid_type);
                    isList=false;
                }else {
                    productLv.setVisibility(View.VISIBLE);
                    gridview.setVisibility(View.GONE);
                    qiehuan.setImageResource(R.drawable.icon_pic_list_type);
                    isList=true;
                }
                break;
        }
            getData(requestUrl,orderby);

        updateBackground(index);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void updateBackground(int index) {
        for (int i = 0; i < rankTitle.size(); i++) {
            if(i==index){
                rankTitle.get(i).setBackgroundResource(R.drawable.segment_selected_1_bg);
                rankTitle.get(i).setTextColor(Color.WHITE);
            }else{
                rankTitle.get(i).setBackgroundResource(R.drawable.segment_normal_2_bg);
                rankTitle.get(i).setTextColor(Color.BLACK);
            }
        }
    }

    @Override
    protected void initData() {

        //联网获取数据,默认按销量排行
            getData(requestUrl,ORDERBY_SALES);
    }



    private MyListAdapter listAdapter;

    private class MyListAdapter extends BaseListViewAdapter<ProductLists.ProductlistBean> {

        /**
         * ListViewAdapter
         *0
         * @param dataList 数据列表
         */
        public MyListAdapter(List<ProductLists.ProductlistBean> dataList) {
            super(dataList);
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder vh = null;
            if (view == null) {
                vh = new ViewHolder();
                view = View.inflate(mContext, R.layout.listview_item_product, null);
                //商品图片
                vh.iv_icon = (ImageView) view.findViewById(R.id.goodsIconIv);
                //商品名
                vh.tv_name = (TextView) view.findViewById(R.id.textClothesName);
                //商品价格
                vh.tv_price = (TextView) view.findViewById(R.id.textClothesPrice);
                //打折后价格
                vh.tv_price2 = (TextView) view.findViewById(R.id.textMarketPrice);
                //评论数量
                vh.textProductCommentNum = (TextView) view.findViewById(R.id.textProductCommentNum);
                view.setTag(vh);
            } else {
                vh = (ViewHolder) view.getTag();
            }
            ProductLists.ProductlistBean plb = datas.get(i);
            ImageOptions options = new ImageOptions.Builder().setImageScaleType(ImageView.ScaleType.FIT_XY).build();
            x.image().bind(vh.iv_icon,NetUrl.BASE_URL+plb.pic, options);
            vh.tv_name.setText(plb.name);
            vh.tv_price.setText("￥"+plb.price);
            vh.tv_price2.setText("￥"+plb.marketprice);
            //添加删除线
            vh.tv_price2.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            vh.textProductCommentNum.setText(plb.commentcount+"");
            return view;
        }
    }
    private MyGridAdapter myGridAdapter;
    private class MyGridAdapter extends BaseListViewAdapter<ProductLists.ProductlistBean>{

        /**
         * ListViewAdapter
         *
         * @param dataList 数据列表
         */
        public MyGridAdapter(List<ProductLists.ProductlistBean> dataList) {
            super(dataList);
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder vh=null;
            if(view==null){
                vh = new ViewHolder();

                view = View.inflate(mContext,R.layout.gridview_items_products,null);
                vh.iv_icon = (ImageView) view.findViewById(R.id.imgIcon);
                vh.tv_name = (TextView) view.findViewById(R.id.tv_name);
                vh.tv_price2 = (TextView) view.findViewById(R.id.tv_price2);
                view.setTag(vh);
            }else{
               vh = (ViewHolder) view.getTag();
            }
            ProductLists.ProductlistBean plb = datas.get(i);
            x.image().bind(vh.iv_icon,NetUrl.BASE_URL+plb.pic);
            vh.tv_name.setText(plb.name);
            vh.tv_price2.setText("￥"+plb.price);
            return view;
        }
    }
    private class ViewHolder {
        public ImageView iv_icon;
        public TextView tv_name;
        public TextView tv_price;
        public TextView tv_price2;
        public TextView textProductCommentNum;
    }

    /**
     * 获取普通商品列表的URL
     * */
    private void getData(String url,String orderby) {

        NetUtil.newGetRequest(url + orderby, new NetUtil.NetworkCallBack(mContext,true,true,true) {
            @Override
            public void onCache(String cache) {
                parseJson(cache);
            }

            @Override
            public void onSucces(String result) {
                parseJson(result);
            }

            @Override
            public void onConnectError() {

            }
        });
    }
    private void parseJson(String jsonString) {
        Gson gson = new Gson();
        ProductLists productLists = gson.fromJson(jsonString,ProductLists.class);
        if (productLists.listCount==0) {
            //没有数据
            productLv.setVisibility(View.GONE);
            productLv.setEnabled(false);
            linRank.setVisibility(View.GONE);
            productLv.setVisibility(View.GONE);
            search_failed.setVisibility(View.VISIBLE);
        }else{
            //将数据集合赋值个datas
            datas = productLists.productlist;
            if (listAdapter == null) {
                listAdapter = new MyListAdapter(datas);
                productLv.setAdapter(listAdapter);
            } else {
                listAdapter.notifyDataSetChanged();
            }
            if (myGridAdapter == null) {
                myGridAdapter = new MyGridAdapter(datas);
                gridview.setAdapter(myGridAdapter);
            } else {
                myGridAdapter.notifyDataSetChanged();
            }
        }

    }
}
