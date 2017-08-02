package com.gdj.myview.fragmentmail;

import java.util.HashMap;

/**
 * 对于接口的抽象
 * 作者：${LoveDjForever} on 2017/8/2 11:46
 * 邮箱： @qq.com
 */
public class Functions {

    private HashMap<String, FunctionNoparamAndResult> mFunctionNoparamAndResult;
    private HashMap<String, FunctionNoparamWithResult> mFunctionNoparamWithResult;

    private static Functions mFunctions;

    private Functions() {
    }

    public static synchronized Functions getInstance() {
        if (mFunctions == null) {
            synchronized (Functions.class) {
                if (mFunctions == null) {
                    mFunctions = new Functions();
                }
            }
        }
        return mFunctions;
    }

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

    /**
     * 调用函数
     **/
    public void invokeFunction(String functionName) throws FunctionExecption {

        FunctionNoparamAndResult f = null;
        if (mFunctionNoparamAndResult != null) {
            f = mFunctionNoparamAndResult.get(functionName);
            if (f != null) {
                f.function();
            } else {
                throw new FunctionExecption("has no Function" + functionName);
            }
        }
    }

    public void addFunction(FunctionNoparamWithResult function) {
        if (mFunctionNoparamWithResult == null) {
            mFunctionNoparamWithResult = new HashMap<String, FunctionNoparamWithResult>();
        } else {
            mFunctionNoparamWithResult.put(function.mFunctionName, function);
        }
    }

    /**
     * 调用函数
     **/
    public <Result> Result invokeFunction(String functionName, Class<Result> c) throws FunctionExecption {

        FunctionNoparamWithResult f = null;
        if (mFunctionNoparamWithResult != null) {
            f = mFunctionNoparamWithResult.get(functionName);
            if (f != null) {
                if (c != null) {
                    return c.cast(f.function());
                }
                return (Result) f.function();
            } else {
                throw new FunctionExecption("has no Function" + functionName);
            }
        }
        return null;
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
