package com.gdj.myview.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;


/**
 * Created by chc on 2015/4/29.
 */
public class CommonUtil {
    /**
     * 检查手机号码格式是否正确
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNum(String mobiles) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        System.out.println(m.matches() + "---");
        return m.matches();

    }

/*    public static void clearAccount(Context context) {
        ShareSDK.initSDK(context);
        Platform QQ = ShareSDK.getPlatform(context, cn.sharesdk.tencent.qq.QQ.NAME);
        if (QQ.isValid()) {
            QQ.removeAccount();
        }
        Platform Wechat = ShareSDK.getPlatform(context, cn.sharesdk.wechat.friends.Wechat.NAME);
        if (Wechat.isValid()) {
            Wechat.removeAccount();
        }
    }*/

    /**
     * 保留两位小数
     *
     * @param number 价格等
     * @return
     */
    public static String conversionNum(double number) {
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(number);
    }

    /**
     * 过滤特殊字符
     *
     * @param str
     * @return
     * @throws PatternSyntaxException
     */
    public static String stringFilter(String str) throws PatternSyntaxException {

        String regEx = "[/\\:*?<>|\"\n\t]";

        Pattern p = Pattern.compile(regEx);

        Matcher m = p.matcher(str);

        return m.replaceAll("");

    }

    /**
     * 组装请求URL
     *
     * @return
     */
//    public static String appendRequesturl(int... stringID) {
//        int[] ids = stringID;
//        StringBuffer sb = new StringBuffer();
//        sb.append(Constant.host);
//
//        for (int resource : ids) {
//            sb.append(MasterApplication.newInstance().getString(resource));
//        }
//
//        return sb.toString();
//    }

    //    public static double distance(double latitude,double longitude,double latitude2,double longitude2){
//        if(latitude == 0 || longitude == 0)
//            return 0.00;
//        LatLng p1 = new LatLng(latitude,longitude);
//        LatLng  p2  = new LatLng(latitude2, longitude2);
//        double m = DistanceUtil.getDistance(p1, p2);
//        return  format(m / 1000.0);
//    }
    public static double format(double f) {
        BigDecimal bg = new BigDecimal(f);
        return bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 根据listviewitem设置listview的高度
     *
     * @param listView
     */
    public static void setListViewHeight(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            listItem.invalidate();
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    /**
     * 计算Gridview的高度
     *
     * @param gridView
     * @return
     */
    public static int setGridViewHeightBasedOnChildren(GridView gridView) {
        // 获取GridView对应的Adapter
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null) {
            return 0;
        }
        int rows;
        int columns = 0;
        int horizontalBorderHeight = 0;
        Class<?> clazz = gridView.getClass();
        try {
            // 利用反射，取得每行显示的个数
            Field column = clazz.getDeclaredField("mRequestedNumColumns");
            column.setAccessible(true);
            columns = (Integer) column.get(gridView);
            // 利用反射，取得横向分割线高度
            Field horizontalSpacing = clazz
                    .getDeclaredField("mRequestedHorizontalSpacing");
            horizontalSpacing.setAccessible(true);
            horizontalBorderHeight = (Integer) horizontalSpacing.get(gridView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 判断数据总数除以每行个数是否整除。不能整除代表有多余，需要加一行
        if (listAdapter.getCount() % columns > 0) {
            rows = listAdapter.getCount() / columns + 1;
        } else {
            rows = listAdapter.getCount() / columns;
        }
        int totalHeight = 0;
        for (int i = 0; i < rows; i++) { // 只计算每项高度*行数
            View listItem = listAdapter.getView(i, null, gridView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }
        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight + horizontalBorderHeight * (rows - 1);// 最后加上分割线总高度
        gridView.setLayoutParams(params);
        return params.height;
    }

    /**
     * 判断两个日期的大小
     * 返回1是结束时间小于开始时间   返回2是结束时间大于当前时间
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int compare_date(String date1, String date2) {
        int rut = 0;
        DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
        LogUtils.i("开始时间" + date1 + "   结束时间" + date2);
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            LogUtils.i("开始时间" + dt1.getTime() + "   结束时间" + dt2.getTime());
            if (dt1.getTime() > dt2.getTime()) {
                rut = 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                rut = 2;
            } else if (dt1.getTime() == dt2.getTime()) {
                rut = 3;
            }
            return rut;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return rut;
    }

    public static int compare_date(String date1, long date2) {
        int rut = 0;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        LogUtils.i("开始时间" + date1 + "   结束时间" + date2);
        try {
            Date dt1 = df.parse(date1);
            // Date dt2 = df.parse(date2);
            LogUtils.i("开始时间" + dt1.getTime() + "   结束时间" + date2);
            if (dt1.getTime() > date2) {
                rut = 1;
            } else if (dt1.getTime() < date2) {
                rut = 2;
            } else if (dt1.getTime() == date2) {
                rut = 3;
            }
            return rut;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return rut;
    }

    /**
     * @param listView
     * @param mline    显示行数,0为全部显示
     */
    public static void setGridViewHeightBasedOnChildren(GridView listView, int mline) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }


        int totalHeight = 0;
        int colum = listView.getNumColumns();
        int line = listAdapter.getCount() / colum + (listAdapter.getCount() % 3 > 0 ? 1 : 0);
        if (mline > 0) {
            line = mline;
        }
//	    System.out.println(listAdapter.getCount()/3+";;;;;"+(listAdapter.getCount()%3>0?1:0)+";;;"+line);
        for (int i = 0; i < line; i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

//	    System.out.println("chc----height:"+totalHeight);
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
        ;
//	    ((MarginLayoutParams)params).setMargins(10, 10, 10, 10);
        listView.setLayoutParams(params);
    }

    public static boolean isMobileNO(String mobiles) {

        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");

        Matcher m = p.matcher(mobiles);

        return m.matches();

    }

    public static boolean isBankCard(String cardNo) {
        Pattern p = Pattern.compile("^\\d{16,19}$|^\\d{6}[- ]\\d{10,13}$|^\\d{4}[- ]\\d{4}[- ]\\d{4}[- ]\\d{4,7}$");
        Matcher m = p.matcher(cardNo);

        return m.matches();
    }

    public static boolean isMobileNOBy2015(String mobiles) {

        Pattern p = Pattern.compile("^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$");

        Matcher m = p.matcher(mobiles);

        return m.matches();

    }

    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 格式化要显示的字符串，做非空判断
     * 该方法主要做用在ui显示这一块，用于更好地显示字符，防止null字符出现和空指针
     *
     * @param str 要验证的字符串
     * @return 参数若为空或“”或null字符串，则返回空，反之直接返回原有值
     */
    public static String formatString(String str) {
        if (TextUtils.isEmpty(str))
            return null;
        if ("null".equalsIgnoreCase(str))
            return null;
        return str;
    }

    /**
     * 保留两位小数
     *
     * @param d
     * @return
     */
    public static String decimalToString(double d) {
        if (0 == d) {
            return String.format("%.2f", d);
        } else {
//            d = d / 100;//如果金额单位为分则需要处理
//            double b = d - 0.005;
//            BigDecimal bd = new BigDecimal(b);
            BigDecimal bd = new BigDecimal(d);
            bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
            return bd + "";
        }
    }

    public static String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;

        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else
            return String.format("%d B", size);
    }
}










