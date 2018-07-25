# StudyView
用来学习kotlin，gradle，自定义控件以及部分效果实现

Drawable就是一个可画的对象，其可能是一张位图（BitmapDrawable），
也可能是一个图形（ShapeDrawable），还有可能是一个图层（LayerDrawable），
我们根据画图的需求，创建相应的可画对象，就可以将这个可画对象当作一块“画布（Canvas）”，
在其上面操作可画对象，并最终将这种可画对象显示在画布上，有点类似于“内存画布“。


![动画效果](https://github.com/DjTenacity/StudyView/blob/master/app/src/main/img/BO3N5.gif)

![动画效果](https://github.com/DjTenacity/StudyView/blob/master/app/src/main/img/animation.gif)

![动画效果](https://github.com/DjTenacity/StudyView/blob/master/app/src/main/img/myView.gif)




1 渲染   ShaderBitmap --->
    LinearGradient  线性渐变 ,
      RadialGradient   环形渲染   ,水波纹,,调色板
        SweepGradient   梯度渲染    ,雷达  微信等雷达扫描效果。手机卫士垃圾扫描

2 颜色估值器 WelcompagerTransformer --根据百分比变色--> ArgbEvaluator
            //颜色估值器,,,,Math.abs(position)百分比--->-1~0~1
    		ArgbEvaluator evaluator = new ArgbEvaluator();
    		int color = bg1_green;
    		if(tag.intValue()==pageIndex){
    			switch (pageIndex) {
    				case 0:
    					color = (int) evaluator.evaluate(Math.abs(position), bg1_green, bg2_blue);
    					break;
    				case 1://存在两种情况,上一页下一页
    					color = (int) evaluator.evaluate(Math.abs(position), bg2_blue, bg1_green);
    					break;
    				case 2:
    					color = (int) evaluator.evaluate(Math.abs(position), bg1_green, bg2_blue);
    					break;
    				default:
    					break;
    			}
    			//设置整个viewpager的背景颜色
    			parent.setBackgroundColor(color);

 3 ClipDrawable是Drawable中的一种，和我们常见的BitmapDrawable是同类。主要功能是能针对自身进行裁剪复制显示 ClipDrawable


 4  ListView 监听滑动过度    ParallaxListView ----> overScrollBy


 5  Canvas的学习    StudyCanvasView
 
   canvas.drawArc(rectF, -90, -180 * percent, false, paint2);  
    第二个参数是开始的角度 ,第三个参数是 要绘制的角度,切记,不是重点的角度!!!
    第四个参数是  是否让起始点存在链接 圆心的线
            
            
 6  动画  AnimationActivity
        
            mMonthPager.animate()
                .scaleX(0)
                .scaleY(0)
                .setDuration(260)
                .setInterpolator(new LinearInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                    }
                });