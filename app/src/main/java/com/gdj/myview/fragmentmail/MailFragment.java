package com.gdj.myview.fragmentmail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gdj.myview.R;

/**
 * Comment:
 *
 * @author : ${user} DJ鼎尔东/ 1757286697@qq.com
 * @version : 1.0
 * @date : 2017/8/2 18:36
 */
public class MailFragment extends Fragment {
    public final String TAG = "MailFragment";

    //定义funcctions
    Functions mFunctions;

    private FragmentMailActivity mActivity;

    public MailFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_mail, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof FragmentMailActivity){
            mActivity=(FragmentMailActivity)context;
            mActivity.setFunctionsForFragment(getTag());
        }
    }
}
