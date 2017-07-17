package com.gdj.myview.view;

public class ParallaxViewTag {
  /**视差动画播放时的参数的控制**/

  //绑定每一个view对应的是哪一个下标的
  protected int index;
  //x轴进入的速度
  protected float xIn;
  protected float xOut;
  protected float yIn;
  protected float yOut;
  protected float alphaIn;
  protected float alphaOut;

  @Override
  public String toString() {
    return "ParallaxViewTag{" +
            "index=" + index +
            ", xIn=" + xIn +
            ", xOut=" + xOut +
            ", yIn=" + yIn +
            ", yOut=" + yOut +
            ", alphaIn=" + alphaIn +
            ", alphaOut=" + alphaOut +
            '}';
  }
}
