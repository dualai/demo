###
避免内存泄漏写法
context.getApplicationContext();

**android相同分辨率的设备，有可能dpi值也可能不一样**

dpi：像素密度   每英寸的像素点，一英寸= 2.54cm，160dpi刚好是1px = 1dp； 

屏幕缩放适配法则：

1、自定义viewgroup适配</br>
优化方案：把自定义view onMeasure中的适配方法抽出来到工具方法中；

**2、百分比适配**（google已经不推荐，但是根据项目需要可以）

com.android.support.percent: 加入百分比布局gradle，并且将容易改成PercentViewGroup，是已有容器的扩展

自定义百分比容器

先自定义attrs
子控件的layoutparams都是通过父类的generateLayoutParams——>new LayoutParams(context,attrs)来创建的； 在layoutParams的构造方法中，解析出属性值； 一般流程，会在自定义的ViewGroup当中做一个静态的LayoutParams类，容器的generateLayoutParams()就是返回该LayoutParams

3、**修改系统的density，densityDpi适配，android最终都是转成了px，
设值的时候用dp**

存在问题：去设置中字体首选项设置字体的显示大小，对字体大小无变化
，需要 添加字体设置变化监听的回调，或者不处理
监听字体，application.registerComponentCallback？？
看一看

优点：简单高效

缺点：暂无；

在BaseActivity的onCreate方法调用； 或者在application.registerActivityLifecycleCallbacks当中做


###density（dpi/160）


###scaleDensity：字体缩放比例，默认情况scaleDensity = density

###densityDpi(160dpi、320dpi)


###沉浸式布局
* root.setFitsSystemWindow(true)（不推荐这么做） 设置后，默认会给个padding值，padding高度刚好是状态栏高度
* 将状态栏变成透明，一般都是用代码来设置为主；
	> 代码设置



### 没有看的东西
* 刘海屏适配
* NestedScrollView
* CoordinatorLayout
* CollapsingToolbarLayout
* AppBarLayout

### Glide 高斯模糊处理转换类
BlurTransformation