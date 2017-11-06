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
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Scroller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Comment:折线图
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/8/4
 */
public class BrokenScrollView extends View {

    private int bWidth, bHeight;     //左边的边距,,  图表出来后下边距的距离
    private int totalValue = 50;//总的
    private int jValue = 10;  // 每一个  y
    private String xStr = "x";
    private String yStr = "y";

    ArrayList<Double> dlk = new ArrayList<>();

    private int marginT, marginB = 20;
    private HashMap<Double, Double> map = new HashMap<>();//数学系的所有坐标集合
    private Paint paint;
    private Paint paint1;
    private Paint paint2;

    private int viewHeight;
    private int viewWidth;
    private int screenWidth;
    private int screenHeight;

    private VelocityTracker velocityTracker;
    private Scroller scroller;
    private ViewConfiguration viewConfiguration;

    ArrayList<Integer> xList = new ArrayList<>();

    public BrokenScrollView(Context context, @Nullable AttributeSet attrs) {
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

        scroller = new Scroller(context);
        viewConfiguration = ViewConfiguration.get(context);
    }

    public void setView(HashMap<Double, Double> map, int totalValue, int jValue,
                        int marginT, int marginB, String s, String b) {
        this.map = map;
        this.totalValue = totalValue;
        this.jValue = jValue;
        this.marginB = marginB;
        this.marginT = marginT;
        this.xStr = s;
        this.yStr = b;
        dlk = getIntfromMap(map);
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
        for (int j = 0; j < dlk.size(); j++) {
            //把所有的x坐标  转换为相对于  View的坐标
            float x = bWidth + 80 * j; //(width - bWidth) / dlk.size()
            xList.add((int) x);

            canvas.drawLine(x, marginT, x, bHeight, paint);
            //画x的刻度 j + 1 + xStr+
            drawText( dlk.get(j).toString()  , (int) x, bHeight + 20, canvas);
        }
        //换算所有点的集合
        Point[] points = getPoints(xList, map, dlk, bHeight, totalValue);

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

    //给map的键排个序------>  X   (map2.get(dlk.get(i))
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
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initSize(getContext());
    }

    /**
     * 初始化默认数据
     */
    private void initSize(Context c) {
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        viewHeight = heightSize;

        int totalWidth = 0;

        if (map.size() > 1) {
            totalWidth = 2 * bWidth + 80 * (map.size() - 1);
        }
        viewWidth = Math.max(screenWidth, totalWidth);  //默认控件最小宽度为屏幕宽度

        setMeasuredDimension(viewWidth, viewHeight);
        Log.d("ccy", "viewHeight = " + viewHeight + ";viewWidth = " + viewWidth);
    }

    private float lastX = 0;
    private float x = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }
        velocityTracker.addMovement(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!scroller.isFinished()) {  //fling还没结束
                    scroller.abortAnimation();
                }
                lastX = x = event.getX();
                return true;
            case MotionEvent.ACTION_MOVE:
                x = event.getX();
                int deltaX = (int) (lastX - x);
                if (getScrollX() + deltaX < 0) {    //越界恢复
                    scrollTo(0, 0);
                    return true;
                } else if (getScrollX() + deltaX > viewWidth - screenWidth) {
                    scrollTo(viewWidth - screenWidth, 0);
                    return true;
                }
                scrollBy(deltaX, 0);
                lastX = x;
                break;
            case MotionEvent.ACTION_UP:
                x = event.getX();
                velocityTracker.computeCurrentVelocity(1000);  //计算1秒内滑动过多少像素
                int xVelocity = (int) velocityTracker.getXVelocity();
                if (Math.abs(xVelocity) > viewConfiguration.getScaledMinimumFlingVelocity()) {  //滑动速度可被判定为抛动
                    scroller.fling(getScrollX(), 0, -xVelocity, 0, 0, viewWidth - screenWidth, 0, 0);
                    invalidate();
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}
