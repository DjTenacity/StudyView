package com.gdj.myview.fragmentmail;

import java.util.HashMap;

/**
 * 对于接口的抽象
 * 作者：${LoveDjForever} on 2017/8/2 11:46
 * 邮箱： @qq.com
 */
public class Functions {

    HashMap<String, FunctionNoparamAndResult> mFunctionNoparamAndResult;
    HashMap<String, FunctionNoparamWithResult> mFunctionNoparamWithResult;

    //对于接口方法的抽象
    public static abstract class Function {
        public String mFunctionName;

        public Function(String functionName) {
            this.mFunctionName = functionName;
        }
    }

    /**
     * 函数的共性有:返回值,参数合名字(修饰符是java共性)----> map根据名字取出接口
     */
    public void addFunction(FunctionNoparamAndResult function) {
        if (mFunctionNoparamAndResult == null) {
            mFunctionNoparamAndResult = new HashMap<String, FunctionNoparamAndResult>();
        } else {
            mFunctionNoparamAndResult.put(function.mFunctionName, function);
        }
    }

    /**调用函数**/
    public void invokeFunction(String functionName) throws FunctionExecption {

        FunctionNoparamAndResult f=null;
        if (mFunctionNoparamAndResult != null) {
            f=mFunctionNoparamAndResult.get(functionName);
            if(f!=null){
                f. function();
            }else{
                throw new FunctionExecption("has no Function" + functionName);
            }
        }
    }


    //没有参数,没有返回值的接口
    public static abstract class FunctionNoparamAndResult extends Function {
        public FunctionNoparamAndResult(String functionName) {
            super(functionName);
        }
        public abstract void function();
    }

    //没有参数,有返回值的接口
    public static abstract class FunctionNoparamWithResult<Result> extends Function {
        public FunctionNoparamWithResult(String functionName) {
            super(functionName);
        }

        public abstract Result function();
    }
    //有参数,没有返回值的接口Function
    //有参数,有返回值的接口

}
