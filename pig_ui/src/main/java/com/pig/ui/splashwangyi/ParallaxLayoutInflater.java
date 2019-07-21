package com.pig.ui.splashwangyi;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.pig.ui.R;
import com.pig.ui.util.LLog;

public class ParallaxLayoutInflater extends LayoutInflater {
    private static final String TAG = "happy";
    private ParallaxFragment fragment;

    protected ParallaxLayoutInflater(LayoutInflater original, Context newContext, ParallaxFragment fragment) {
        super(original, newContext);
        this.fragment = fragment;
        //当调用inflate的时候，回调factory2接口
        setFactory2(new ParallaxFactory(this));
    }

    protected ParallaxLayoutInflater(LayoutInflater original, Context newContext) {
        super(original, newContext);
    }

    @Override
    public LayoutInflater cloneInContext(Context context) {
        return new ParallaxLayoutInflater(this, context, fragment);
    }

    //    ------------------------------------------------
    class ParallaxFactory implements Factory2 {
        private final String[] sClassPrefix = {
                "android.widget.",
                "android.view."
        };
        int[] attrIds = {
                R.attr.a_in,
                R.attr.a_out,
                R.attr.x_in,
                R.attr.x_out,
                R.attr.y_in,
                R.attr.y_out};
        private LayoutInflater inflater;

        public ParallaxFactory(LayoutInflater inflater) {
            this.inflater = inflater;
        }
//    系统控件  TextView
//    自定义控件 com.wangyi.splashwangyi.MyView,给完整的名字

        /**
         * 每解析一个控件，就调用一次
         *
         * @param parent       顶级容器
         * @param name         控件的名称
         * @param context
         * @param attributeSet 属性名
         * @return
         */
        @Override
        public View onCreateView(View parent, String name, Context context, AttributeSet attributeSet) {
//            LLog.d("onCreateView....,"+name);
            View view = null;
            view = createMyView(name, context, attributeSet);

            if(view != null){
                TypedArray a = context.obtainStyledAttributes(attributeSet,attrIds);
                if(a != null && a.length() > 0){
                    //获取自定义属性的值
                    ParallaxViewTag tag = new ParallaxViewTag();
                    tag.alphaIn = a.getFloat(0, 0f);
                    tag.alphaOut = a.getFloat(1, 0f);
                    tag.xIn = a.getFloat(2, 0f);
                    tag.xOut = a.getFloat(3, 0f);
                    tag.yIn = a.getFloat(4, 0f);
                    tag.yOut = a.getFloat(5, 0f);
                    view.setTag(R.id.parallax_view_tag,tag); //tag值设置到view当中去，并且标注tag类型parallax_view_tag
                    a.recycle();
                }
                /**
                 * 这里要注意下，系统也加进去了，只不过以上的自定义属性都为默认值
                 */
                fragment.getParallaxViews().add(view);
            }

            return view;
        }

        private View createMyView(String name, Context context, AttributeSet attributeSet) {
            if (name.contains(",")) { //自定义控件
               return reflectView(name, null, context, attributeSet);
            } else {
//                reflectView(name,null,context,attributeSet);
                for (String prefix : sClassPrefix) { //直到找到对应包名,创建成功...
                    View view = reflectView(name, prefix, context, attributeSet);

                    //获取系统控件的自定义属性

                    if (view != null) {
                        return view;
                    }
                }
            }
            return null;
        }

        /**
         * 系统inflater是通过反射创建view
         *
         * @param name
         * @param prefix
         * @param context
         * @param attributeSet
         * @return
         */
        private View reflectView(String name, String prefix, Context context, AttributeSet attributeSet) {
            try {
                return inflater.createView(name, prefix, attributeSet);
            } catch (Exception ex) {
                return null;
            }
        }

        @Override
        public View onCreateView(String s, Context context, AttributeSet attributeSet) {

            return null;
        }
    }
}
