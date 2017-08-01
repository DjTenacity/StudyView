package com.kaixuan.dj.apt;


import com.kaixuan.dj.handle.ViewInjectHandle;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

/**
 * 指定APT工具访问规则
 */
@SupportedAnnotationTypes("com.kaixuan.dj.annotation.ViewInjector")
@SupportedSourceVersion(SourceVersion.RELEASE_6)
//调用AbstractProcessor的getSupportedAnnotationTypes 方法，返回字符串
//现在的特性要在jDK1.6之后才能支持
public class ViewInjectProcessor extends AbstractProcessor {

    //符合规则的类信息会传到process方法，在这里处理
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        //类的信息都在roundEnv一团乱麻————>梳理成有条理的 map结构  Map<String,List<View>>
        ViewInjectHandle viewInjectHandle=new ViewInjectHandle();


        return false;
    }
}
