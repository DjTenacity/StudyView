package com.gdj.myview.entry

/**
 *  核心的只是设计思想，架构思想，编码水平
 */
class B {
    fun test() {

    }

}

//代理模式
class PoxyA() {// : xxxInterface

    val b = B()
    //代理方法
    fun poxyS() {
        b.test()
    }
//UML 代理类A直接依赖于代理类B
}

//  装饰者设计模式
//依赖注入（耦合度会很低），而注解就是耦合性最低的依赖注入
//并不直接依赖于b，间接依赖于b，通过构造器，讲b的实例,实例化好了之后，传进来
class PoxyC(var b: B) {// : xxxInterface
    fun xx() {
        b.test()
    }
}

//mvc   m---->业务逻辑和实体模型     c ---》页面布局
//mvp                               activity 只是v层，加了present层，v层是不能直接操作依赖于 moudle层
//dagger2

