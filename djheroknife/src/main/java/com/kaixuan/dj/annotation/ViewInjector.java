package com.kaixuan.dj.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Comment:  在class级别  获取属性
 *
 * 比如override：编写代码过程中在编译期，保证代码正确的重写，在运行期不需要使用到，
 * 这就是RetentionPolicy.SOURCE级别，源码级别，由源码得到class的时候不需要再出现，也就是class文件里面没有override
 *
 * RUNTIME级别就代表被加载到虚拟机的时候这个注解仍然加载的到
 *
 * CLASS级别就代表Class文件中有，但被加载到虚拟机的时候就没了，因为只需要在编译过程当中生成辅助文件就可以了，
 * 在运行过程当中，不需要去去读取，不需要去根据反射去读取注解的内容
 *
 * @author : dianjiegao
 * @version : 1.0
 * @date : 2017-07-25
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.FIELD)
public @interface ViewInjector {
    int value();
}
