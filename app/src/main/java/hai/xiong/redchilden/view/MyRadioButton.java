package hai.xiong.redchilden.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.RadioButton;

import hai.xiong.redchilden.util.Convert;
import hai.xiong.redchilden.util.GlobalConfig;
import hai.xiong.redchilden.util.SpSkuUtils;

/**
 * Created by _Ms on 2016/8/11.
 */
public class MyRadioButton extends RadioButton {

    /**
     * Context
     */
    private Context mContext;

    public MyRadioButton(Context context) {
        super(context);
        mContext = context;
    }

    public MyRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!GlobalConfig.isLogin(mContext)) {
            return;
}

        int num = SpSkuUtils.getNum(mContext);
        if (num == 0) {
        return;
        }


        //启用抗锯齿和使用设备的文本字距
        Paint countPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DEV_KERN_TEXT_FLAG);
        countPaint.setColor(Color.RED);
        float width = canvas.getWidth();

        int r = 10;

        canvas.drawCircle(Convert.px2dp(mContext, width + 18 + r), Convert.px2dp(mContext, 10 + r),Convert.px2dp(mContext, 10 + r),countPaint);

        Paint textPaint=new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(Convert.px2dp(mContext, 19 + r));
        canvas.drawText(String.valueOf(num),Convert.px2dp(mContext, width + 10 + r),Convert.px2dp(mContext, 17 + r),textPaint);

        }
        }
