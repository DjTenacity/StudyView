package com.gdj.myview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Comment:Canvas的学习
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/8/26
 */
public class StudyCanvasView extends View {

    private Paint paint;


    public StudyCanvasView(Context context) {
        super(context);
    }

    public StudyCanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public StudyCanvasView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    float[] pts = {0, 0, 100, 100, 200, 200, 300, 300};

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStyle(Paint.Style.STROKE);

        canvas.drawLines(pts, paint);
        canvas.drawLines(pts, 10, 2, paint);//通过offset设置线的间隔距离，可以实现虚线效果

        //		canvas.drawPoint(500, 500, paint);
//		canvas.drawPoints(pts, paint);  //点

        //Rect int和RectF float  区别只是精度问题
        RectF r = new RectF(100, 100, 400, 500);//相对父容器
//		canvas.drawRect(r, paint);
//		canvas.drawRect(left, top, right, bottom, paint);//同上

        //画圆角矩形
        //x-radius ,y-radius圆角的半径
//		canvas.drawRoundRect(r, 30, 30, paint);

//		canvas.drawCircle(300, 300, 200, paint);
        paint.setColor(Color.GREEN);

        //椭圆
//		canvas.drawOval(r, paint);

        //		canvas.drawArc(
//				r,
//				startAngle, //起始角度，相对X轴正方向
//				sweepAngle, //画多少角度的弧度
//				useCenter, //boolean, false：只有一个纯弧线；true：闭合的边
//				paint)；
        //扇形
        canvas.drawArc(r, 0, 90, true, paint);//顺时针旋转90度
//------------------------------画笔----------------------------------------
        //		Path path = new Path();
//		path.moveTo(100, 100);//画笔落笔的位置
//		//移动
//		path.lineTo(200, 100);
////		path.lineTo(200, 200);
//		path.cubicTo(250, 200, 350, 300, 450, 400); //贝瑟尔曲线
//		path.close(); //闭合,就会连接起点
//
//		canvas.drawPath(path, paint);

        //圆角矩形路径,  四个角 的横轴半径与竖轴半径,,,,可以修改每一个角的弧度
//		float radii[] = {10,10,10,10,10,10,50,60};
//		path.addRoundRect(r, radii, Direction.CCW);
//		path.addArc(oval, startAngle, sweepAngle)
//		canvas.drawPath(path, paint);


/**Region区域*/

//		//创建一块矩形的区域
//		Region region = new Region(100, 100, 400, 500);
//		Region region1 = new Region();
//		region1.setPath(path, region);//path的椭圆区域和矩形区域进行交集
//
//		//结合区域迭代器使用（得到图形里面的所有的矩形区域）
//		RegionIterator iterator = new RegionIterator(region1);
//
//		Rect rect = new Rect();
//		while (iterator.next(rect)) {
//			canvas.drawRect(rect, paint);//就像是微积分画了无数的矩形
//		}
//		//合并
//		region.union(r);
//		region.op(r, Op.INTERSECT);//交集部分


        //--------------------Canvas变换技巧--------------------------
        //1.平移（Translate）
//		RectF r = new RectF(0, 0, 400, 500);
//		canvas.drawRect(r, paint);
//		paint.setColor(Color.BLUE);
//		//将画布平移
//		canvas.translate(50, 50);
//		//当canvas执行drawXXX的时候就会新建一个新的画布(图层)
//		canvas.drawRect(r, paint);
//		RectF r2 = new RectF(0, 0, 400, 500);
//		paint.setColor(Color.RED);
//		//虽然新建了一个画布图层，但是还是会沿用之前设置的平移变换。不可逆的。（save和restore来解决）
//		canvas.drawRect(r2, paint);
        //2.缩放Scale
//		RectF r = new RectF(0, 0, 400, 500);
//		canvas.drawRect(r, paint);
//		paint.setColor(Color.BLUE);
        //sx,sy：分别对x/y方向的一个缩放系数,画布的缩放会导致里面所有的绘制的东西都会有一个缩放效果
//		canvas.scale(1.5f, 0.5f);
//		canvas.drawRect(r, paint);

        //3.旋转Rotate  绕着坐标原点
//		RectF r = new RectF(200, 200, 400, 500);
//		canvas.drawRect(r, paint);
//		paint.setColor(Color.BLUE);
//		canvas.rotate(45);绕着坐标原点
//		canvas.rotate(45, 200, 200);//角度与旋转的中心点
//		canvas.drawRect(r, paint);

        //4.斜拉画布Skew
//		RectF r = new RectF(200, 200, 400, 500);
//		canvas.drawRect(r, paint);
//		paint.setColor(Color.BLUE);
//		//sx,sy倾斜度：X轴方向上倾斜60度，tan60=根号3
//		canvas.skew(1.73f, 0);
//		canvas.drawRect(r, paint);
        //5.裁剪画布clip
       // RectF r = new RectF(200, 200, 400, 500);
        canvas.drawRect(r, paint);
        paint.setColor(Color.BLUE);
        canvas.clipRect(new Rect(250, 250, 300, 400));
        canvas.drawColor(Color.YELLOW);


    }
}
/**
 * 1）当canvas执行drawXXX的时候就会新建一个新的画布图层
 canvas.drawRect(r, paint);
 2）虽然后面新建了一个画布图层，但是还是会沿用之前设置的平移变换。不可逆的。（save和restore来解决）
 canvas.translate(50, 50);
 //当canvas执行drawXXX的时候就会新建一个新的画布图层
 canvas.drawRect(r, paint);
 RectF r2 = new RectF(0, 0, 400, 500);
 paint.setColor(Color.RED);
 //虽然新建了一个画布图层，但是还是会沿用之前设置的平移变换。不可逆的。（save和restore来解决）
 canvas.drawRect(r2, paint);
 * */