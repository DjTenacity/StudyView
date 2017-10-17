package com.gdj.myview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.gdj.myview.entry.DataBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Comment:年度折线图
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/8/4
 */
public class BrokenYearView extends View {

    private int bWidth, bHeight;     //左边的边距,,  图表出来后下边距的距离
    private int totalValue = 50;//总的
    private int jValue = 10;  // 每一个  y
    private String yStr = "y";

    private int marginT, marginB = 20;
    List<DataBean> dataBeanList = new ArrayList<>();
    private Paint paint;
    private Paint paint1;
    private Paint paint2;


    ArrayList<Integer> xList = new ArrayList<>();

    public BrokenYearView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //ANTI_ALIAS_FLAG 提高画质
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);

        paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint1.setColor(Color.BLUE);
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setStrokeWidth(2);

        paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint2.setColor(Color.GREEN);
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setStrokeWidth(2);
    }

    public void setView(List<DataBean> dataBeanList, int totalValue, int jValue,
                        int marginT, int marginB, String yStr) {
        this.dataBeanList = dataBeanList;
        this.totalValue = totalValue;
        this.jValue = jValue;
        this.marginB = marginB;
        this.marginT = marginT;
        this.yStr = yStr;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int height = getHeight();
        int width = getWidth();

        bHeight = height - marginB;
        bWidth = 50;
        int jSize = totalValue / jValue;

        //画横轴
        for (int i = 0; i < jSize + 1; i++) {
            canvas.drawLine(bWidth, bHeight - (bHeight - marginT) / jSize * i,
                    width, bHeight - (bHeight - marginT) / jSize * i, paint);

            //画y的刻度
            drawText(jValue * i + yStr, bWidth / 2,
                    bHeight - (bHeight - marginT) / jSize * i, canvas);
        }

        //画竖轴
        for (int j = 0; j < 12; j++) {
            //把所有的x坐标  转换为相对于  View的坐标
            float x = bWidth + (width - bWidth) / 12 * j;
            xList.add((int) x);

            canvas.drawLine(x, marginT, x, bHeight, paint);

            //画x的刻度
            drawText(j + 1 + "月", (int) x, bHeight + 20, canvas);
        }
        //换算所有点的集合
        Point[] points = getPoints(xList, dataBeanList, bHeight, totalValue);

        //电鱼点之间连成线
        drawsPoints(points, paint1, canvas);

        //画点的正方形
        paint2.setStyle(Paint.Style.FILL);
        //点会上正方形
        for (int i = 0; i < points.length; i++) {
            canvas.drawRect(pointRect(points[i]), paint2);
        }
    }

    private RectF pointRect(Point point) {
        return new RectF(point.x - 5, point.y - 5, point.x + 5, point.y + 5);
    }

    Point startPoint = new Point();
    Point endPoint = new Point();

    //绘制连线
    private void drawsPoints(Point[] points, Paint paint, Canvas canvas) {

        for (int i = 0; i < points.length - 1; i++) {
            startPoint = points[i];
            endPoint = points[i + 1];
            canvas.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y, paint);
        }
    }

    //得到所有点的集合  ,xList-->横坐标
    private Point[] getPoints(ArrayList<Integer> xList, List<DataBean> dataBeanList, int bHeight , int totalValue) {

        Point[] mPoints = new Point[dataBeanList.size()];

        for (int i = 0; i < mPoints.length; i++) { //y坐标

            DataBean bean = dataBeanList.get(i);

            String date = bean.date.substring(5, 7);//截取“年月日”  08

            int y = bHeight  - (int) ((bHeight  - marginT) * (bean.value / totalValue));

            mPoints[i] = new Point(xList.get(Integer.parseInt(date) - 1), y);
        }

        return mPoints;
    }

    private void drawText(String s, int x, int y, Canvas canvas) {
        Paint paint = new Paint();
        paint.setAlpha(0x0000ff);
        paint.setTextSize(20);
        Typeface typeFace = Typeface.create("宋体", Typeface.ITALIC);
        paint.setTypeface(typeFace);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(s, x, y, paint);
    }
}
