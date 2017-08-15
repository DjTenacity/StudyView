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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Comment:
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/8/4
 */
public class BrokenView extends View {

    private int bWidth, bHeight;     //左边的边距,,  图表出来后下边距的距离
    private int totalValue = 50;//总的
    private int jValue = 10;  // 每一个  y
    private String xStr = "x";
    private String yStr = "y";

    ArrayList<Double> dlk = new ArrayList<>();

    private int marginT, marginB=20;
    private HashMap<Double, Double> map  ;//数学系的所有坐标集合

    public BrokenView(Context context) {
        this(context, null);
    }

    public BrokenView(Context context, @Nullable AttributeSet attrs) {
        this(context, null, 0);
    }

    public BrokenView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setView(HashMap<Double, Double> map, int totalValue, int jValue,
                        int marginT, int marginB, String s, String b) {
        this.map = map;
        this.totalValue = totalValue;
        this.jValue = jValue;
        this.marginB = marginB;
        this.marginT = marginT;
        // this.xStr = s;
        //  this.yStr = b;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        dlk = getIntfromMap(map);
        int height = getHeight();
        int width = getWidth();

        bHeight = height - marginB;
        bWidth = 50;

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);//ANTI_ALIAS_FLAG 提高画质
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        int jSize = totalValue / jValue;

        //画横轴
        for (int i = 0; i < jSize + 1; i++) {
            canvas.drawLine(bWidth, bHeight - (bHeight - marginT) / jSize * i,
                    width, bHeight - (bHeight - marginT) / jSize * i, paint);

            //画y的刻度
            drawText(jValue * i + yStr, bWidth / 2,
                    bHeight - (bHeight - marginT) / jSize * i, canvas);
        }
        ArrayList<Integer> xList = new ArrayList<>();
        //画竖轴
        for (int j = 0; j < dlk.size(); j++) {
            //把所有的x坐标  转换为相对于  View的坐标
            float x = bWidth + (width - bWidth) / dlk.size() * j;
            xList.add((int) x);

            canvas.drawLine(x, marginT,
                    width, x, paint);

            //画x的刻度
            drawText(j + xStr, (int) x, bHeight - 20, canvas);
        }
        //换算所有点的集合
        Point[] points = getPoints(xList, map, dlk, bHeight, totalValue);

        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);

        //电鱼点之间连成线
        drawsPoiints(points, paint, canvas);
        //画点的正方形
        paint.setStrokeWidth(2);
        paint.setColor(Color.YELLOW);
        paint.setStyle(Paint.Style.FILL);
        //点会上正方形
        for (int i = 0; i < points.length - 1; i++) {
            canvas.drawRect(pointRect(points[i]), paint);
        }
    }

    private RectF pointRect(Point point) {
        return new RectF(point.x - 5, point.y - 5, point.x + 5, point.y + 5);
    }

    Point startPoint = new Point();
    Point endPoint = new Point();

    //绘制连线
    private void drawsPoiints(Point[] points, Paint paint, Canvas canvas) {

        for (int i = 0; i < points.length - 1; i++) {
            startPoint = points[i];
            endPoint = points[i];
            canvas.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y, paint);
        }
    }

    //得到所有点的集合
    private Point[] getPoints(ArrayList<Integer> xList, HashMap<Double, Double> map2, ArrayList<Double> dlk, int bHeight2, int totalValue) {

        Point[] mPoints = new Point[dlk.size()];

        for (int i = 0; i < mPoints.length; i++) {
            int y = bHeight2 - (int) ((bHeight2 - marginT) * (map2.get(dlk.get(i)) / totalValue));
            mPoints[i] = new Point(xList.get(i), y);
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

    //给map的值排个序------>  X
    ArrayList<Double> getIntfromMap(HashMap<Double, Double> map) {
        ArrayList<Double> dlk = new ArrayList<Double>();

        Set set = map.entrySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            Map.Entry mapentry = (Map.Entry) iterator.next();
            dlk.add((Double) mapentry.getKey());
        }
        Collections.sort(dlk);

        return dlk;
    }


}
