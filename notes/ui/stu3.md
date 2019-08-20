### 控件
*SnackBar，类似Toast，可以操作，Snackbar可以滑动退出，也可以处理用户交互（点击）事件

*DrawerLayout 抽屉

*NavigationView 一般和DrawerLayout结合使用

*BottomNavigationView ：底部导航，好像最多三个

*Recyclervew打造首页复杂界面

*CardView 继承Framelayout 可设置圆角、阴影，也可以包含其他控件、容器；

*CoordinaryLayout 继承ViewGroup； 

*AppBarLayout： 垂直线性布局，使用时，其他属性和线性布局是一样的；一般结合CoordinaryLayout一起使用

*CollaspsingToolbarLayout： 折叠toolbar，继承Framelayout，结合CoordinaryLayout 使用

*NestedScrollView 

*RecyclerView + CardView

###主题颜色

* colorPrimary: 标题栏颜色
* colorPrimaryDark： 状态栏颜色
* colorAccent ： 强调颜色 
* android:windowBackground :窗口背景
* android:navigationBarColor ：虚拟导航栏颜色
* android:itembackground: 菜单背景

###动画
* Fade 淡入
* Slide 滑动
* Explode 分解
* 共享元素

###自定义recyclerview
* tools:listitem 预览


###CoordinatorLayout
* 配合AppBarLayout layout_behavior

* 自定义behavior
总结：
事件从RecyclerView的滚动事件触发开始，找到NestedScrollingChildHelper,然找父类的CoordinatoryLayout，分析所有的子view的Behavior，最后交由Behavior来处理，执行相关的操作或动画；

###沉浸式
* android 4.4不能改变状态栏颜色；5.0以上可以改，可以改style，也可以代码设置；
* 所以4.4和5.0以上分开处理沉浸式；必要的话，模拟器测试；
* 安卓4.4沉浸式，状态栏阴影效果，特殊性

###弧度、角度
弧度：radian

角度：degress 

Math.atan2(y,x) 得到弧度；  

每个弧度有多少角度 = 180 / Math.PI(3.14);

角度 = 弧度 * (180 / Math.PI);

Matrix ：矩阵处理单位是角度

假设当前画布的中心点是x,y;  图片的大小是width和height； 要将图片移动到中心点，那么图片的坐标是
(x - width / 2, y - height / 2)

###PathMeasure和path实现动画


###SVG

android加载场景：

	  ...
	  defaultConfig {
	    ...
	    vectorDrawables.useSupportLibrary = true
	   }
	  ...

AppCompatImageView：app:srcCompat

TextView：CompoundDrawable

MenuItem：icon

一般表示简单图形

SVG解析，用java的dom解析工具类；解析完以后，响应事件；

SVG缺点，基于像素展开，所以需要缩放，如果是用画布来画的话，可以直接 canvas.scale(scale,scale);

面向对象的方式来做canvas绘制思想；

自定义解析svg数据：Thread当中；

PathParser 来解析pathData数据

path.computeBounds（rect,boolean), 算出path的最大矩阵，放在rect当中

计算极值

    left = left == -1 ? rect.left : Math.min(left, rect.left);
    right = right == -1 ? rect.right : Math.max(right, rect.right);
    top = top == -1 ? rect.top : Math.min(top, rect.top);
    bottom = bottom == -1 ? rect.bottom : Math.max(bottom, rect.bottom);         

根据极值，计算出svg的rect，然后触发onMeasure，在里头算出画布的scale

requestLayout: 和invalidate()相反，他只调用measure()和layout()过程，不会调用draw()。不会重新绘制任何视图包括该调用者本身。

invalide: View树的onDraw()绘制

postInvalidate()方法可以在UI线程执行，也可以在工作线程执行。而invalidate()只能在UI线程操作。但是从重绘速率讲：invalidate()效率高

android加载SVG：[https://www.jianshu.com/p/5c81970ddf33](https://www.jianshu.com/p/5c81970ddf33)

去矢量库找svg图标：https://www.iconfont.cn/

绘制画笔：
paint.setStyle(Paint.Style.STROKE); 设置绘制边框；

paint.setStyle(Paint.Style.FILL); 设置绘制内部

canvas.drawPath(path, paint);绘制path

paint.setShadowLayer:设置阴影


Region操作：
	
	public void setEmpty()  //清空
	public boolean set(Region region)   
	public boolean set(Rect r)   
	public boolean set(int left, int top, int right, int bottom)   
	public boolean setPath(Path path, Region clip)//将path和clip的两个区域取交集,把不规则的path存在region当中；

点击区域判断： region.contains(x,y)

特殊控件的事响应区域处理
https://www.gcssloop.com/customview/touch-matrix-region

###vlayout
用tools，inspector去分析咸鱼、淘宝的首页布局元素；

分析三方界面元素工具2： C:\Users\andy\AppData\Local\Android\Sdk\tools\bin/uiautomatorviewer.exe

另外，C:\Users\andy\AppData\Local\Android\Sdk\tools\bin下面有很多分析工具

**vlayout已经有了横向滚动控件和轮播图控件**

vlayout支持布局嵌套： 在for循环里头，生产、嵌套布局，最后交给delegateAdapter

图片叠加，把上面的透明度从0转换到1；LayerDrawable 对应的xml标签是<layer-list>，表示一种层次化的drawable集合；

	 //监听动画的执行的进度，不需要更改控件任何的属性，给number
        objectAnimator = ObjectAnimator.ofFloat(this,"number",0f,10f);

**以上为什么用number这种不存在的类型，就是为了应用androd系统的VSYNC信号，获取数值，自己做动画；**

大背景模糊实现方式<br/>
* 自定义viewgroup作为大背景，把其他的view嵌套在大背景当中；
* 在大背景当中用两张图片控件叠加做过度动画；
*     layerDrawable.setDrawableByLayerId() // 可以给图层的item打上id； 创建的时候打上id；
*     

layerDrawable 两张图片叠加，形成一张drawable

打碟界面：整个是viewPager



Constraintlayout 圆形动画

**createScaledBitmap：创建bitmap的时候，缩放bitmap**

	Bitmap bitmapDisc = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_disc_blackground), musicCircleWidth, musicCircleWidth, false);


RoundedBitmapDrawable

setPivotX 设置旋转中心

onFinishInflate 当View中所有的子控件均被映射成xml后触发
也就是会在Activity中调用setContentView之后就会调用onFinishInflate这个方法，这个方法就代表自定义控件中的子控件映射完成了，然后可以进行一些初始化控件的操作

当然在这个方法里面是得不到控件的高宽的，控件的高宽是必须在调用了onMeasure方法之后才能得到，而onFinishInflate方法是在setContentView之后、onMeasure之前

### 可自定义插值器
new Interpolator


###没有看的部分
* 手写RecyclerView
* 组合使用
* 3.1.1下拉刷新；到底加载更多；共享元素动画；沉浸式状态栏工具类；
