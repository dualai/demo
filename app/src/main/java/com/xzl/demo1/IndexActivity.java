package com.xzl.demo1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


/**
 * 测试结果：
 * Exoplayer: 有直接混合的模式(ConcatenatingMediaSource,setRepeatMode)，但是因为期间播放完成没有事件抛出来，所以，如果期间有播放图片的话，不能控制
 * 如果是SurfaceView重叠，那么有问题
 * TextureView重叠,可以
 * <p>
 * SurfaceView:尝试使用Visibile模式,但是visible会引发surfaceview的重建和销毁,其他模式在探索 https://blog.csdn.net/email_jade/article/details/82895335
 * https://www.jianshu.com/p/d558a4c9c868
 * <p>
 * https://blog.csdn.net/gfg156196/article/details/72899287
 * <p>
 * https://blog.csdn.net/xuedaqian123/article/details/77878781
 * <p>
 * https://blog.csdn.net/smileorcryps/article/details/52614631
 * <p>
 *
 * https://www.jianshu.com/p/d558a4c9c868
 *
 *
 *
 * getChildDrawIndex
 *
 * SurfaceView setVisible会重建
 *
 *  设置top也没法切换，只能设置一次
 * http://www.dewen.net.cn/q/17182/%E5%A4%9A%E4%B8%AASurfaceView%E9%87%8D%E5%8F%A0%E3%80%81%E5%B1%82%E7%BA%A7%E6%98%BE%E7%A4%BA%2C%E5%A6%82%E4%BD%95%E5%B8%83%E5%B1%80
 *
 * https://blog.csdn.net/u011803341/article/details/78717635
 *
 * https://blog.csdn.net/wenxiang423/article/details/82662113 原生循环播放视频不黑屏
 * https://blog.csdn.net/fkgjdkblxckvbxbgb/article/details/82812821
 *
 * Surfaceview原理 ：https://blog.csdn.net/Luoshengyang/article/details/8661317
 *
 * 大小切换：https://www.jianshu.com/p/5bbc7a563ef7
 *
 */


public class IndexActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
