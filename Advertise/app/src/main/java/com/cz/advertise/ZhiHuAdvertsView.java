package com.cz.advertise;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * @author caozheng
 * Created time on 2017/12/5
 *
 * description: 仿知乎滑动切换图片广告
 */

public class ZhiHuAdvertsView extends AppCompatImageView {

    private RectF mBitmapRectF;
    private Bitmap mBitmap;

    private int mHeight;
    private int mDy;

    public ZhiHuAdvertsView(Context context){
        super(context);
    }

    public ZhiHuAdvertsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);

        mHeight = height;
        Drawable drawable = getDrawable();

        if (drawable == null) {
            return;
        }

        mBitmap = drawableToBitmap(drawable);
        mBitmapRectF = new RectF(0, 0, width,
                mBitmap.getHeight() * width / mBitmap.getWidth());

    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bd = (BitmapDrawable) drawable;
            return bd.getBitmap();
        }
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }

    public void setDy(int dy) {
        if (getDrawable() == null) {
            return;
        }

        mDy = dy - mHeight;
        if (mDy <= 0) {
            mDy = 0;
        }

        if (mDy > mBitmapRectF.height() - mHeight) {
            mDy = (int) (mBitmapRectF.height() - mHeight);
        }

        invalidate();
    }

    public void bindView(final ViewGroup parent){
        if(parent instanceof RecyclerView){
            ((RecyclerView) parent).addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    int[] location = getLocation();
                    int y = location[1];
                    //view距离屏幕顶部的高度 + view自身高度
                    int heightTotal = y + getHeight();

                    if(getVisibility() == View.VISIBLE){
                        LinearLayoutManager mLinearLayoutManager = (LinearLayoutManager) ((RecyclerView) parent).getLayoutManager();

                        //view完全可见时,开始滑动
                        if (y > 0 && getScreenHeight() >= heightTotal) {
                            setDy(mLinearLayoutManager.getHeight() - y);
                        }
                    }
                }
            });
        }else if(parent instanceof ListView){

        }else {
            Log.i("SwitchImageView", "不支持的ViewGroup类型");
        }
    }

    private int getScreenHeight(){
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();

        return dm.heightPixels;
    }

    private int[] getLocation(){
        int[] location = new int[2];
        //获取view坐标
        this.getLocationOnScreen(location);

        return location;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mBitmap == null) {
            return;
        }
        canvas.save();
        canvas.translate(0, -mDy);
        canvas.drawBitmap(mBitmap, null, mBitmapRectF, null);
        canvas.restore();
    }
}
