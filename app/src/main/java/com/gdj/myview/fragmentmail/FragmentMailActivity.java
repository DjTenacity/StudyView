package com.gdj.myview.fragmentmail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.gdj.myview.R;

/**
 * Comment:
 *
 * @author : ${user} DJ鼎尔东/ 1757286697@qq.com
 * @version : 1.0
 * @date : 2017/8/2 18:33
 */
public class FragmentMailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_mail);
    }

    public void setFunctionsForFragment(String fragmentTag) {
        FragmentManager manager = getSupportFragmentManager();

        //activity  和fragment 绑定
        BaseFragment baseFragment = (BaseFragment) manager.findFragmentByTag(fragmentTag);
        baseFragment.setFunctions(Functions.getInstance());

        if (MailFragment.TAG.equals(fragmentTag)) {
            baseFragment.getFunctions().addFunction(new Functions.FunctionNoparamAndResult
                    (MailFragment.FUNCTION_NO_PARAM_NO_RESULR) {
                @Override
                public void function() {
                    Toast.makeText(FragmentMailActivity.this, "成功调用了没有参数没有返回值的接口", Toast.LENGTH_SHORT).show();
                }
            });
        } else if (MailFragment2.TAG.equals(fragmentTag)) {
            baseFragment.getFunctions().addFunction(new Functions.FunctionNoparamWithResult<String>
                    (MailFragment2.FUNCTION_NO_PARAM_WITH_RESULR) {
                @Override
                public String function() {
                    return "0000000我我我我";
                }
            });
        }
    }


}
