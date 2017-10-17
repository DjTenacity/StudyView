package com.gdj.myview.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.gdj.myview.R;

@SuppressLint("AppCompatCustomView")
public class CustomImageView extends ImageView {

    private int gravity = 1;
    private int radius;
    private Paint paint;

    public CustomImageView(Context context) {
        this(context, null);
    }

    public CustomImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomImageView, defStyleAttr, 0);
       // gravity = a.getString(R.styleable.CustomImageView_horn_gravity) == null ? "" : a.getString(R.styleable
        //        .CustomImageView_horn_gravity);
        gravity=a.getInteger(R.styleable.CustomImageView_horn_gravity,1);
        radius = a.getInteger(R.styleable.CustomImageView_radius, 20);
        a.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 画笔
        paint = new Paint();
        // 颜色设置
        paint.setColor(0xff424242);
        // 抗锯齿
        paint.setAntiAlias(true);

        Drawable drawable = getDrawable();
        if (drawable == null) {
            return;
        }
        // 这里 get 回来的宽度和高度是当前控件相对应的宽度和高度（在 ml 设置）
        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }

        // 获取 bitmap，即传入 imageview 的 bitmap
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        // 标志
        int saveFlags = Canvas.MATRIX_SAVE_FLAG | Canvas.CLIP_SAVE_FLAG | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG
                | Canvas.FULL_COLOR_LAYER_SAVE_FLAG | Canvas.CLIP_TO_LAYER_SAVE_FLAG;
        canvas.saveLayer(0, 0, getWidth(), getHeight(), null, saveFlags);

        switch (gravity) {
            case 1://左上
                drawLeftTop(canvas, bitmap);
                break;
            case 2://右上
                drawRightTop(canvas, bitmap);
                break;
            case 3://左下
                drawLeftBottom(canvas, bitmap);
                break;
            case 4://右下
                drawRightBottom(canvas, bitmap);
                break;
            default://默认左上
                drawRightTop(canvas, bitmap);
                break;
        }
        canvas.restore();
    }

    private void drawRightBottom(Canvas canvas, Bitmap bitmap) {
        float scaleWidth = (float) getWidth() / bitmap.getWidth();
        float scaleHeight = (float) getHeight() / bitmap.getHeight();
        float scale = scaleWidth < scaleHeight ? scaleWidth : scaleHeight;
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        // bitmap 缩放
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        Path path = new Path();
        path.reset();

        float left = getWidth() - bitmap.getWidth();
        float top = getHeight() - bitmap.getHeight();
        float right = getWidth();
        float bottom = getHeight();

        path.moveTo(left + radius, top);
        path.lineTo(right - radius, top);
        RectF reacF;
        reacF = new RectF(right - 3 * radius, top, right - radius, top + 2 * radius);
        path.arcTo(reacF, -90, 90);
        path.lineTo(right - radius, bottom - radius);
        reacF = new RectF(right - radius, bottom - 2 * radius, right + radius, bottom);
        path.arcTo(reacF, 180, -90);
        path.lineTo(radius, bottom);
        reacF = new RectF(left, bottom - 2 * radius, left + 2 * radius, bottom);
        path.arcTo(reacF, 90, 90);
        path.lineTo(left, radius);
        reacF = new RectF(left, top, left + 2 * radius, top + 2 * radius);
        path.arcTo(reacF, 180, 90);
        path.close();
        canvas.drawPath(path, paint);
        // Paint 的 Xfermode，PorterDuff.Mode.SRC_IN 取两层图像的交集部门, 只显示上层图像。
        PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        paint.setXfermode(xfermode);
        // draw 上去
        canvas.drawBitmap(bitmap, left, top, paint);
    }

    private void drawLeftBottom(Canvas canvas, Bitmap bitmap) {
        float scaleWidth = (float) getWidth() / bitmap.getWidth();
        float scaleHeight = (float) getHeight() / bitmap.getHeight();
        float scale = scaleWidth < scaleHeight ? scaleWidth : scaleHeight;
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        // bitmap 缩放
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        Path path = new Path();
        path.reset();

        float left = 0;
        float top = 0;
        float right = bitmap.getWidth();
        float bottom = bitmap.getHeight();

        path.moveTo(left, top);
        path.lineTo(right - radius, top);
        RectF reacF;
        reacF = new RectF(right - 2 * radius, top, right, top + 2 * radius);
        path.arcTo(reacF, -90, 90);
        path.lineTo(right, bottom - radius);
        reacF = new RectF(right - 2 * radius, bottom - 2 * radius, right, bottom);
        path.arcTo(reacF, 0, 90);
        path.lineTo(left, bottom);
        reacF = new RectF(left - radius, bottom - 2 * radius, left + radius, bottom);
        path.arcTo(reacF, 90, -90);
        path.lineTo(left + radius, top + radius);
        reacF = new RectF(left + radius, top, left + 3 * radius, top + 2 * radius);
        path.arcTo(reacF, 180, 90);
        path.close();
        canvas.drawPath(path, paint);

        // Paint 的 Xfermode，PorterDuff.Mode.SRC_IN 取两层图像的交集部门, 只显示上层图像。
        PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        paint.setXfermode(xfermode);
        // draw 上去
        canvas.drawBitmap(bitmap, left, top, paint);
    }

    private void drawRightTop(Canvas canvas, Bitmap bitmap) {
        float scaleWidth = (float) getWidth() / bitmap.getWidth();
        float scaleHeight = (float) getHeight() / bitmap.getHeight();
        float scale = scaleWidth < scaleHeight ? scaleWidth : scaleHeight;
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        // bitmap 缩放
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        Path path = new Path();
        path.reset();

        float left = getWidth() - bitmap.getWidth();
        float top = 0;
        float right = getWidth();
        float bottom = bitmap.getHeight();

        path.moveTo(left + radius, top);
        path.lineTo(right, top);
        RectF reacF;
        reacF = new RectF(right - radius, top, right + radius, top + 2 * radius);
        path.arcTo(reacF, 270, -90);
        path.lineTo(right - radius, bottom - radius);
        reacF = new RectF(right - 3 * radius, bottom - 2 * radius, right - radius, bottom);
        path.arcTo(reacF, 0, 90);
        path.lineTo(left + radius, bottom);
        reacF = new RectF(left, bottom - 2 * radius, left + 2 * radius, bottom);
        path.arcTo(reacF, 90, 90);
        path.lineTo(left, radius);
        reacF = new RectF(left, top, left + 2 * radius, top + 2 * radius);
        path.arcTo(reacF, 180, 90);
        path.close();
        canvas.drawPath(path, paint);

        // Paint 的 Xfermode，PorterDuff.Mode.SRC_IN 取两层图像的交集部门, 只显示上层图像。
        PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        paint.setXfermode(xfermode);
        // draw 上去
        canvas.drawBitmap(bitmap, left, top, paint);
    }

    private void drawLeftTop(Canvas canvas, Bitmap bitmap) {
        float scaleWidth = (float) getWidth() / bitmap.getWidth();
        float scaleHeight = (float) getHeight() / bitmap.getHeight();
        float scale = scaleWidth < scaleHeight ? scaleWidth : scaleHeight;
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        // bitmap 缩放
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        Path path = new Path();
        path.reset();

        float left = 0;
        float top = 0;
        float right = bitmap.getWidth();
        float bottom = bitmap.getHeight();

        path.moveTo(left, top);
        path.lineTo(right - radius, top);
        RectF reacF;
        reacF = new RectF(right - 2 * radius, top, right, top + 2 * radius);
        path.arcTo(reacF, -90, 90);
        path.lineTo(right, bottom - radius);
        reacF = new RectF(right - 2 * radius, bottom - 2 * radius, right, bottom);
        path.arcTo(reacF, 0, 90);
        path.lineTo(radius, bottom);
        reacF = new RectF(left + radius, bottom - 2 * radius, left + 3 * radius, bottom);
        path.arcTo(reacF, 90, 90);
        path.lineTo(left + radius, top + radius);
        reacF = new RectF(left - radius, top, left + radius, top + 2 * radius);
        path.arcTo(reacF, 0, -90);
        path.close();
        canvas.drawPath(path, paint);

        // Paint 的 Xfermode，PorterDuff.Mode.SRC_IN 取两层图像的交集部门, 只显示上层图像。
        PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        paint.setXfermode(xfermode);
        // draw 上去
        canvas.drawBitmap(bitmap, left, top, paint);
    }
}
