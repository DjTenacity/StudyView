package com.gdj.myview.fragmentmail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.gdj.myview.R;

/**
 * Comment:
 *
 * @author : DJ鼎尔东/ 1757286697@qq.com
 * @version : 1.0
 * @date : 2017/8/2 18:36
 */
public class MailFragment2 extends BaseFragment {
    public static final String TAG = "MailFragment2";
    //必须唯一
    public static final String FUNCTION_NO_PARAM_WITH_RESULR = MailFragment2.class.getSimpleName() + "npwr";


    public MailFragment2() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_mail2, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btn = (Button) getView().findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String cc = getFunctions().invokeFunction(FUNCTION_NO_PARAM_WITH_RESULR, String.class);
                    Toast.makeText(getContext(), cc, Toast.LENGTH_SHORT).show();

                } catch (FunctionExecption functionExecption) {
                    functionExecption.printStackTrace();
                }
            }
        });
    }

}
