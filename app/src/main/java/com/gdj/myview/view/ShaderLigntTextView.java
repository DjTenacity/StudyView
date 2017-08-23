package com.gdj.myview.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Comment:学习LinearGradient线性渲染
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/8/24
 */
public class ShaderLigntTextView extends TextView {

    Paint textPaint;
    private LinearGradient linearGradient;
    private Matrix matrix;

    private float translateX;
    private float deltaX = 20;

    public ShaderLigntTextView(Context context) {
        super(context);
    }

    public ShaderLigntTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ShaderLigntTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        textPaint = getPaint();
        //GradientSize=两个文字的大小
        String text = getText().toString();
        float textWidth = textPaint.measureText(text);
        //相对文字的大小,设置大小
        int GradientSize = (int) (3 * textWidth / text.length());
        //    ..................x,y,x1,y1, colors,颜色区域,CLAMP 边缘融合
        linearGradient = new LinearGradient(-GradientSize, 0, 0, 0, new int[]{0x22ffffff, 0xffffffff, 0x22ffffff}, new float[]{0, 0.5f, 1}, Shader.TileMode.CLAMP);
        textPaint.setShader(linearGradient);
        matrix = new Matrix();
    }

    //onDraw会不停的调用,所以为了性能抽取到上面
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float textWidth = getPaint().measureText(getText().toString());
        translateX += deltaX;//过去,同时要考虑回来
        if (translateX > textWidth + 1 || translateX < 1) {
            deltaX = -deltaX;
        }
//		matrix.setScale(sx, sy)
        matrix.setTranslate(translateX, 0);
        linearGradient.setLocalMatrix(matrix);

        postInvalidateDelayed(50);
    }
}
