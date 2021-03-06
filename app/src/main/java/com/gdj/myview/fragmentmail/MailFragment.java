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
 * @author : ${user} DJ鼎尔东/ 1757286697@qq.com
 * @version : 1.0
 * @date : 2017/8/2 18:36
 */
public class MailFragment extends BaseFragment {
    public static final String TAG = "MailFragment";
    //必须唯一
    public static final String FUNCTION_NO_PARAM_NO_RESULR = MailFragment.class.getSimpleName() + "npnr";

    public MailFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_mail, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btn = (Button) getView().findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    getFunctions().invokeFunction(FUNCTION_NO_PARAM_NO_RESULR);
                } catch (FunctionExecption functionExecption) {
                    functionExecption.printStackTrace();
                }
            }
        });
    }
}
