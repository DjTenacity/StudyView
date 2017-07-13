package com.gdj.myview.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gdj.myview.R;
import com.gdj.myview.utils.DisplayUtils;

/**
 * 作者：${LoveDjForever} on 2017/7/7 18:24
 *  * 邮箱： @qq.com
 */

public class JustMoveActivity extends AppCompatActivity {



    private int mLastY = 0;  //最后的点
    private static int mNeedDistance;   // 需要滑动的距离
    private static int mMinHight; //最小高度
    private static int mOrignHight; //原始的高度

    private int mCurrentDistance = 0;  //当前的距离
    private float mRate = 0;  //距离与目标距离的变化率 mRate=mCurrentDistance/mNeedDistance
    private int mPhotoOriginHeight; //图片的原始高度
    private int mPhotoOriginWidth; //图片的原始宽度
    private int mPhotoLeft;  //图片距左边的距离
    private int mPhotoTop;  //图片距离上边的距离
    private int mPhotoNeedMoveDistanceX;  // 图片需要移动的X距离
    private int mPhotoNeedMoveDistanceY;  // 图片需要移动的Y距离
    //需要移动的文字
    private int mTextLeft;  //文字距左边的距离
    private int mTextTop;  //文字距离上边的距离
    private int mTextNeedMoveDistanceX;  // 文字需要移动的X距离
    private int mTextNeedMoveDistanceY;  //文字需要移动的Y距离


    RelativeLayout rl_head;
    TextView tv_user_name;
    ImageView img_head_portrait;

    TextView tv_user_hosipital;
    TextView tv_user_hosipital_level;
    TextView tv_user_project;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_just_move);

        ListView lv= (ListView) findViewById(R.id.lv);
        lv.setAdapter(new MyAdapter(this));
        initDistance();
    }
    class  MyAdapter extends BaseAdapter{
        Context context;
        public MyAdapter(Context context){this.context=context;}

        @Override
        public int getCount() {
            return 18;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView= LayoutInflater.from(context).inflate(R.layout.item_main_list,null);
            return convertView;
        }
    }

    /**
     * 初始化需要滚动的距离
     */
    private void initDistance() {
        rl_head = (RelativeLayout) findViewById(R.id.rl_head);
        img_head_portrait = (ImageView) findViewById(R.id.img_head_portrait);

        tv_user_name = (TextView) findViewById(R.id.tv_user_name);
        tv_user_hosipital = (TextView) findViewById(R.id.tv_user_hosipital);
        tv_user_hosipital_level = (TextView) findViewById(R.id.tv_user_hosipital_level);
        tv_user_project = (TextView) findViewById(R.id.tv_user_project);

        mOrignHight = rl_head.getLayoutParams().height;
        mMinHight = DisplayUtils.dp2px(0 );  //设置最小的高度为这么多
        mNeedDistance = mOrignHight - mMinHight;
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) img_head_portrait.getLayoutParams();
        mPhotoOriginHeight = params.height;
        mPhotoOriginWidth = params.width;
        mPhotoLeft = params.leftMargin;
        mPhotoTop = params.topMargin;
        mPhotoNeedMoveDistanceX = this.getWindowManager().getDefaultDisplay().getWidth() / 2 - mPhotoLeft - mMinHight;
        mPhotoNeedMoveDistanceY = mPhotoTop - DisplayUtils.dp2px(10);
        /*******************移动的文字初始化***************************/
        RelativeLayout.LayoutParams textParams = (RelativeLayout.LayoutParams) tv_user_name.getLayoutParams();
        mTextLeft = textParams.leftMargin;
        mTextTop = textParams.topMargin;
        mTextNeedMoveDistanceX = this.getWindowManager().getDefaultDisplay().getWidth() / 2 - mTextLeft + 10;
        mTextNeedMoveDistanceY = mTextTop - mMinHight / 2 / 2;  //这里计算有点误差，正确的应该是剪去获取textview高度的一半
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = (int) ev.getY();
                android.util.Log.d("TAG", "ACTION_MOVE==mCurrentDistance" + mCurrentDistance);
                return super.dispatchTouchEvent(ev); //传递事件 例如可以用来子view的点击事件等
            case MotionEvent.ACTION_MOVE:
                int y = (int) ev.getY();
                int dy = mLastY - y;
                android.util.Log.d("TAG", "ACTION_MOVE==mCurrentDistance" + mCurrentDistance);
                if (mCurrentDistance >= mNeedDistance && dy > 0) {
                    return super.dispatchTouchEvent(ev);  //传递事件
                }
                if (mCurrentDistance <= 0 && dy < 0) {
                    return super.dispatchTouchEvent(ev); //把事件传递进去
                }
                //改变布局
                changeTheLayout(dy);
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
                checkTheHeight();
                android.util.Log.d("TAG", "ACTION_MOVE==mCurrentDistance" + mCurrentDistance);
                return super.dispatchTouchEvent(ev);
        }

        return false;
    }

    /**
     * 通过滑动的偏移量
     *
     * @param dy
     */
    private void changeTheLayout(int dy) {
        final ViewGroup.LayoutParams layoutParams = rl_head.getLayoutParams();
        layoutParams.height = layoutParams.height - dy;
        rl_head.setLayoutParams(layoutParams);
        checkTheHeight();
        rl_head.requestLayout();
        //计算当前移动了多少距离
        mCurrentDistance = mOrignHight - rl_head.getLayoutParams().height;
        mRate = (float) (mCurrentDistance * 1.0 / mNeedDistance);
        changeTheAlphaAndPostion(mRate);  //获取偏移率然后改变某些控件的透明度，和位置
        android.util.Log.d("TAG", "ACTION_MOVE==dy" + dy);
    }


    /**
     * 根据变化率来改变这些这些控件的变化率位置
     *
     * @param rate
     */
    private void changeTheAlphaAndPostion(float rate) {
        //先改变一些控件的透明度
        if (rate >= 1) {
            tv_user_hosipital.setVisibility(View.GONE);
            tv_user_hosipital_level.setVisibility(View.GONE);
            tv_user_project.setVisibility(View.GONE);
        } else {
            tv_user_hosipital.setVisibility(View.VISIBLE);
            tv_user_hosipital_level.setVisibility(View.VISIBLE);
            tv_user_project.setVisibility(View.VISIBLE);


            tv_user_hosipital.setAlpha(1 - rate);
            tv_user_hosipital.setScaleY(1 - rate);
            tv_user_hosipital.setScaleX(1 - rate);

            tv_user_hosipital_level.setAlpha(1 - rate);
            tv_user_hosipital_level.setScaleY(1 - rate);
            tv_user_hosipital_level.setScaleX(1 - rate);

            tv_user_project.setAlpha(1 - rate);
            tv_user_project.setScaleY(1 - rate);
            tv_user_project.setScaleX(1 - rate);
        }
        //接下来是改变控件的大小和位置了  （这就是关键了）
        final RelativeLayout.LayoutParams photoParams = (RelativeLayout.LayoutParams) img_head_portrait.getLayoutParams();
        photoParams.width = (int) (mPhotoOriginWidth - (rate * (mPhotoOriginWidth - mMinHight - DisplayUtils.dp2px(10))));
        photoParams.height = (int) (mPhotoOriginWidth - (rate * (mPhotoOriginWidth - mMinHight - DisplayUtils.dp2px(10))));
        photoParams.leftMargin = (int) (mPhotoLeft + mPhotoNeedMoveDistanceX * rate);
        photoParams.topMargin = (int) (mPhotoTop - mPhotoNeedMoveDistanceY * rate);
        Log.d("TAG", "photoParams.leftMargin" + photoParams.leftMargin);
        Log.d("TAG", " photoParams.topMargin" + photoParams.topMargin);
        img_head_portrait.setLayoutParams(photoParams);
        /*********************文字设置****************************/
        final RelativeLayout.LayoutParams textParams = (RelativeLayout.LayoutParams) tv_user_name.getLayoutParams();
        textParams.leftMargin = (int) (mTextLeft + mTextNeedMoveDistanceX * rate);
        textParams.topMargin = (int) (mTextTop - mTextNeedMoveDistanceY * rate);
        Log.d("TAG", "textParams.leftMargin" + textParams.leftMargin);
        Log.d("TAG", " textParams.topMargin" + textParams.topMargin);
        tv_user_name.setLayoutParams(textParams);
    }// Log.d("TAG",

    /**
     * 检查上边界和下边界
     */
    private void checkTheHeight() {
        final ViewGroup.LayoutParams layoutParams = rl_head.getLayoutParams();
        if (layoutParams.height < mMinHight) {
            layoutParams.height = mMinHight;
            rl_head.setLayoutParams(layoutParams);
            rl_head.requestLayout();
        }
        if (layoutParams.height > mOrignHight) {
            layoutParams.height = mOrignHight;
            rl_head.setLayoutParams(layoutParams);
            rl_head.requestLayout();
        }
    }

}
