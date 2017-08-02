package com.gdj.myview.fragmentmail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.gdj.myview.R;

/**
 * Comment:
 *
 * @author : DJ鼎尔东/ 1757286697@qq.com
 * @version : 1.0
 * @date : 2017/8/2 18:36
 */
public class BaseFragment extends Fragment {

    //定义funcctions
    private Functions mFunctions;

    private FragmentMailActivity mActivity;

    public BaseFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentMailActivity) {
            mActivity = (FragmentMailActivity) context;
            mActivity.setFunctionsForFragment(getTag());
        }
    }


    public Functions getFunctions() {
        return mFunctions;
    }

    public void setFunctions(Functions mFunctions) {
        this.mFunctions = mFunctions;
    }
}
