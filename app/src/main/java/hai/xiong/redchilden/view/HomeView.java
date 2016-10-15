package hai.xiong.redchilden.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import hai.xiong.redchilden.R;
import hai.xiong.redchilden.bean.HomeRoll;
import hai.xiong.redchilden.util.Log;
import hai.xiong.redchilden.util.NetUrl;

/**
 * Created by Administrator on 2016/8/6 0006.
 */
public class HomeView extends ViewPager{
    private static final int ROLL_ACTION =27 ;
    //获取传递的list
    public List<HomeRoll.HomeBannerBean> list;

    public HomeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initview();
    }
    public LinearLayout liear;
    public HomeView(Context context, List<HomeRoll.HomeBannerBean> list,LinearLayout linear) {
        super(context);
        this.liear=linear;
        this.list=list;
        initview();

    }
    //注册控件
//    @ViewInject(R.id.vp_home)
//    private ViewPager viewpager;
//    @ViewInject(R.id.ll_dots)
//    private LinearLayout linear;
    public  MyPagerAdapter adapter;
    private void initview() {
        //viewPager设置适配器
        adapter=new MyPagerAdapter();
       setAdapter(adapter);
        setCurrentItem(1);
//        //添加ViewPager的条目改变监听
       setOnPageChangeListener(new MyViewPAgerOnPageChangeListener());
//        //添加动态轮播图
        ActionRoll();
    }

private float downX = 0;
    private int downTime = 0;//按下时间
    private float downY = 0;//按下y
@Override
public boolean onTouchEvent(MotionEvent ev) {
    //能够检测到down事件和一部分move事件,cancle事件
//        Log.i("RollViewPager", "onTouchEvent:"+ev.getAction());
    switch (ev.getAction()) {
        case MotionEvent.ACTION_DOWN:
            downX = ev.getX();
            downY =  ev.getY();
            downTime = (int) System.currentTimeMillis();
            //大喊一声,爹,不要抢事件(请求父亲不允许中断事件)
            getParent().requestDisallowInterceptTouchEvent(true);
            //按下--停止(handler移除消息)
            handler.removeMessages(ROLL_ACTION);
            break;
        case MotionEvent.ACTION_MOVE:
            float moveX = ev.getX();
            float moveY = ev.getY();
            float disX = Math.abs(moveX-downX);
            float disY = Math.abs(moveY-downY);
            //如果是左右滑动,执行以下逻辑
            if(disX>disY){
                if(getCurrentItem() ==adapter.getCount()-1 && downX>moveX){
                    getParent().requestDisallowInterceptTouchEvent(false);
                }else if(getCurrentItem() == 0 && downX<moveX){
                    getParent().requestDisallowInterceptTouchEvent(false);
                }else{
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
            }else{
                //如果是上下滑动,交由父亲处理
                getParent().requestDisallowInterceptTouchEvent(false);
            }


            break;
        case MotionEvent.ACTION_UP:
            //1.区分 长按事件  & 单击
            int upTime = (int) System.currentTimeMillis();
            if(upTime-downTime<500){
                //2.区分 移动事件 &单击
                float upX = ev.getX();
                float upY = ev.getY();
                disX = Math.abs(upX - downX);
                disY = Math.abs(upY-downY);
                if(disX<5&&disY<5){
                    //单击
                    if(onItemClickListener!=null)
                        onItemClickListener.onItemClick(getCurrentItem());//当前显示的界面
                }
            }
            //抬起--继续轮播
            ActionRoll();
            break;
        case MotionEvent.ACTION_CANCEL:
            //抬起--继续轮播
            ActionRoll();
            break;

        default:
            break;
    }
    return super.onTouchEvent(ev);
}
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        /**
         * 条目单击
         * @param position :当前点击viewpager界面的索引
         */
        public void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
    /**
     * 当前控件显示到屏幕上(显示)
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }
    /**
     * 当前控件从屏幕移除(不显示)
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        handler.removeCallbacksAndMessages(null);
    }

    public OnRollClicklister onRollClicklister;
    public interface  OnRollClicklister{
        public void onClicklister(int position);
    }
    public void setOnRollClickListener(OnRollClicklister onRollClicklister){
        this.onRollClicklister=onRollClicklister;
    }
    private void startScroll() {
        handler.sendEmptyMessageDelayed(ROLL_ACTION,2000);
    }

    private void stopScroll() {
        handler.removeMessages(ROLL_ACTION);
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==ROLL_ACTION){
                int currentItem = getCurrentItem();
                currentItem ++;
                setCurrentItem(currentItem);
            }
            handler.sendEmptyMessageDelayed(ROLL_ACTION,2000);
        }
    };


    private void ActionRoll() {
        handler.sendEmptyMessageDelayed(ROLL_ACTION,2000);
    }

    class MyPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return  list.size()+2;
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view==o;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView=new ImageView(getContext());
            if (position == 0) { //新增的D
                position =list.size() - 1;// 最后一个元素
            } else if (position == getCount() - 1) {  //最后一个元素 A
                position = 0;
            } else {
                position -= 1; //计算新的索引
            }
            String PhotoUrl=list.get(position).pic;
            x.image().bind(imageView, NetUrl.BASE_URL+PhotoUrl);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }
    }

    private class MyViewPAgerOnPageChangeListener implements ViewPager.OnPageChangeListener {
        public int position;
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            this.position=i;
//            for (int x = 0; x < viewpager.getAdapter().getCount()-2; x++) {
//                ((ImageView)linear.getChildAt(x)).setImageResource(x == i ? R.drawable.slide_adv_selected : R.drawable.slide_adv_normal);
//            }
            int childCount = liear.getChildCount();
            for (int x = 0; x <  liear.getChildCount(); x++){
                if (i==0){
                    ((ImageView)liear.getChildAt(x)).setImageResource(x == liear.getChildCount()-1 ? R.drawable.slide_adv_selected : R.drawable.slide_adv_normal);
                }if (i== getAdapter().getCount()-1){
                    ((ImageView)liear.getChildAt(x)).setImageResource(x == 0 ? R.drawable.slide_adv_selected : R.drawable.slide_adv_normal);
                }
                ((ImageView)liear.getChildAt(x)).setImageResource(x == i-1 ? R.drawable.slide_adv_selected : R.drawable.slide_adv_normal);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {
            //状态改变的时候 调用    手指抬起的时候切换
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                //空闲切换
                // 页面切换   自动的切换到对应的界面    最后一个A----->第一个A
                if (position == new MyPagerAdapter().getCount() - 1) {
                    //最后一个元素  是否平滑切换
                    setCurrentItem(1, false);
                } else if (position == 0) {
                    //是第一个元素{D] ----> 倒数第二个元素[D]
                  setCurrentItem(new MyPagerAdapter().getCount() - 2, false);
                }
            }
        }
    }
}
