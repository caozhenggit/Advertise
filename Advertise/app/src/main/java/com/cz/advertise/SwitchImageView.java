package com.cz.advertise;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.v7.widget.RecyclerView;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author caozheng
 * Created time on 2017/12/5
 *
 * description: 仿QQ空间滑动切换图片广告
 */

public class SwitchImageView extends View {

    private Paint mPaint;
    private Canvas frontCanvas;
    private int height;
    private int width;
    private float radius = 0;
    private RectF rectF;
    private Bitmap frontBg;
    private float offsetX = 100;
    private float offsetY = 100;

    private int mBehindImage;
    private int mFrontImage;

    public SwitchImageView(Context context) {
        super(context);
        init();
    }

    public SwitchImageView(Context context, AttributeSet attrs){
        super(context, attrs);
        init();
    }

    private void init(){
        mPaint = new Paint();
        mPaint.setAlpha(0);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(0);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onDraw(Canvas behindCanvas) {
        super.onDraw(behindCanvas);
        //绘制前景图片至前景画布
        frontCanvas.drawBitmap(getBitmap(mFrontImage), null, rectF, null);
        //绘制圆遮挡前景图片
        frontCanvas.drawCircle(width - offsetX, height - offsetY, radius, mPaint);

        //绘制背景图片至背景画布
        behindCanvas.drawBitmap(getBitmap(mBehindImage), null, rectF, null);
        behindCanvas.drawBitmap(frontBg, null, rectF, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);

        rectF = new RectF(0, 0, width, height);
        //创建空bitmap
        frontBg = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        //创建前景画布
        frontCanvas = new Canvas(frontBg);
    }

    private void getLocation() {
        int[] location = new int[2];
        this.getLocationOnScreen(location);
        int y = location[1];
        int height = y + getHeight();

        if (y > 0 && getScreenHeight() >= height) {
            radius = (float) ((getScreenHeight() - height) * 1.5);
            frontCanvas.drawCircle(width - offsetX, this.height - offsetY, radius, mPaint);
        } else {
            if (radius < width) {
                radius = 0;
            }
        }

        invalidate();
    }

    private int getScreenHeight(){
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();

        return dm.heightPixels;
    }

    private Bitmap getBitmap(int resId){
        Bitmap mBitmap = ((BitmapDrawable) getResources().getDrawable(resId)).getBitmap();
        return mBitmap;
    }

    public void setBehindImage(int resId){
        mBehindImage = resId;
    }

    public void setFrontImage(int resId){
        mFrontImage = resId;
    }

    public void bindRecyclerView(ViewGroup parent){
        if (!(parent instanceof RecyclerView)) {
            throw new RuntimeException("必须是android.support.v7.widget.RecyclerView");
        }
        ((RecyclerView) parent).addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                getLocation();
            }
        });
    }

}
