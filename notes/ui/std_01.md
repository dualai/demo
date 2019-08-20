#Paint#

Shader，着色器
BitmapShader，ComposeShader，LinearGradient，RadialGradient，SweepGradient。Shader中有一个TileMode，共有3种模式，

CLAMP：当图片小于绘制尺寸时要进行边界拉伸来填充

REPEAT：当图片小于绘制尺寸时重复平铺

MIRROR：当图片小于绘制尺寸时镜像平铺

xfermode： 图形混合

setColorFilter，ImageView和Drawable上也有这个方法

#Canvas#
translate：平移
skew：倾斜
scale：缩放
rotate：旋转
matrix：矩阵

save
restore
saveCount
restoreToCount：回到指定点

saveLayer：

savelayeralpha：可以指定图层的透明度

restore函数：清除当前画布的matrix/clip状态信息，然后从栈顶取出保存的状态信息应用到画布，调用restore的次数不能超过save的次数。

Save（）： 会把当前的画布的状态进行保存，然后放入Canvas状态栈中；
saveLayer()或者saveLayerAlpha: 会把当前画布状态保存放入栈中，会新建一个bitmap（新的一层），后续的操作都会作用在这个bitmap上，同      时可以指定新建bitmap的大小和透明度等。restore（）或restoreToCount： 就会把栈中最顶层的画布状态取出来，并按照这个状态恢复当前的画        布，并在这个画布上做画

save，saveLayer，savelayeralpha保存画布信息共用一个栈，所以每次调用save函数getSaveCount函数都会加一，每次调用restore函数  getSaveCount函数都会减一。调用restoreToCount（id）则会直接退栈到id标识的canvas状态，此时在其顶部保存的状态信息都已经被弹栈了。
 

非必要情况下用save，因为saveLayer会新建一层，如果用saveLayer，可以指明大小；

[https://blog.csdn.net/u010126792/article/details/85164946](https://blog.csdn.net/u010126792/article/details/85164946)

### 绘制画drawCircle的时候要注意了，绘制stroke的时候是向外扩散？详见以下章节的代码
1.2.4-Canvas操作案例
---
##贝塞尔曲线：
###Path：
moveTo

addPath
arcT
addArc
addRect
addCircle
####lineTo：一阶贝塞尔曲线，就是表示一条直线

####quadTo //二阶贝塞尔 一个控制点 

####cubicTo //三阶贝塞尔 两个控制点


##属性动画
解析成关键帧
关键帧：决定起始点和终止点的位置
vsync信号来触发，为什么不用子线程thread休眠间隔执行？
自定义控件很多时候，可以用thread休眠方式来进行
vsync信号：1、统一进行绘制 2、没有绘制完不会卡死 3、只有一个
但是第三方应用没法监听sync

##平行动画
系统控件，比如ImageView里头也可以添加自定义属性，通过自定义layoutInflater<br/>

**自定义layoutInflater**
加载view的时候做个过滤？监听布局的添加过程<br/>

Constructor:反射类，描述类的构造器函数的信息，可通过相关构造函数创建类

##屏幕适配
1、限定符适配values-分辨率，values-sw等，orientation限定符，尺寸large等限定符
2、百分比适配 percentViewgroup，google 不太好
3、代码动态适配，java代码中实现
4、ConstraintLayout
5、头条开源等比缩放控件

dimens适配缺点：

**等比缩放，主流**

svg格式图片，xml文件格式描述

系统的DisplayMetrics 宽高不随着方向改变而改变

网易云音乐适配，
<2017: 代码动态适配，在各种viewgroup的onMeasure中获取width和height的px像素值之后，乘以缩放系数，进行等比缩放； 但是不知道字体等？<br/>
优点：和美工无缝对接、对代码侵入性较大
缺点：自定义控件比较麻烦，并且每种viewgroup类型都要做自定义，无法预览

2017后: 两个都采取了，第二个方案，工具类，每个UI元素，动态设置；
优点：代码侵入性小一些；但是麻烦，对每一个界面元素进行赋值，对开发者来说麻烦；



##看不懂的部分和没看的部分
* 手写事件分发
* 贝塞尔曲线
* PathMeasure
* vsync
* 

##可以做到github或者写文章的
* 小红书平行动画的自定义layoutmanager

##精妙的章节


##网址
[http://androidblog.cn/](http://androidblog.cn/)



