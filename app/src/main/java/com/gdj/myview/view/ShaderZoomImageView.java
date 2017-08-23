package com.gdj.myview.view;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.Shape;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.gdj.myview.R;

/**
 * Comment:学习渲染---放大镜
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/8/24
 */
public class ShaderZoomImageView extends View {

    private Bitmap bitmap;
    Paint textPaint;
    private LinearGradient linearGradient;
    private Matrix matrix;
    private ShapeDrawable drawable;

    //放大倍数
    final int FACTOR = 3;

    public ShaderZoomImageView(Context context) {
        super(context);
        inint();
    }

    public ShaderZoomImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inint();
    }

    private void inint() {
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon2);
        Bitmap bmp = bitmap;
        //放大后的整个图片
        bmp = Bitmap.createScaledBitmap(bmp, bmp.getWidth() * FACTOR, bmp.getHeight() * FACTOR, true);

        BitmapShader shader = new BitmapShader(bmp, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        //制作一个原型的图片,盖在canvas上面

        drawable = new ShapeDrawable(new OvalShape());
        drawable.getPaint().setShader(shader);
    }

    public ShaderZoomImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(bitmap, 0, 0, null);
        //画制作好的圆形图片
    }
}
