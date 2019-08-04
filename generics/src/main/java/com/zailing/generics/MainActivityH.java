package com.zailing.generics;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zailing.generics.fruit.Apple;
import com.zailing.generics.fruit.Fruit;
import com.zailing.generics.shop.Shop;

import java.util.ArrayList;
import java.util.List;

public class MainActivityH extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * 可以放苹果的list
         * 能放apple的list List<? super Apple>
         */
        List<? super Apple> lists = new ArrayList<Fruit>();


        //概念上来说，泛型实例化的两种形式，1.声明的时候传入  2.调用具体方法的时候，传入对象，无论是构造方法传入类型，还是调用具体方法传入具体new出来的对象，
        //都是对泛型方法的实例化；

        List<Fruit> fruits = new ArrayList<>();
        Apple apple = new Apple();
//        fruits.add(apple);


        apple.addMetoList(fruits);


        // Shop<Apple> 泛型声明
        Shop<Apple> shop = null;

        shop.refund(new Apple());

        List<Shop<? extends Apple>> shops;

        Object object = new ArrayList<Shop<? extends Fruit>>();

        /**
         * 只用 ？号，上面也限制，下面也限制，一般不用；
         */

        /***
         *  ? extends View , ? super EditText, ?  方法调用的时候，不能用这些；
         *
         *  extends：可以写在定义泛型的时候，也就是形参的尖括号右边，作为范围的缩窄； 也可以写在 ？ 号的右边，作为限定；
         *  super: 同上
         *  ? : 只能写在声明东西的时候，作为限定符，重要
         */


        /**
         * 这种写法，声明的括号和调用创建的尖括号必须一致；不能大不能小；
         * 如果要改变[右边创建时候的范围]，需要在左边声明的时候引入？ extends 。。或者 ？ super 。。。
         */
        Shop<Fruit> fruitShop = new Shop<Fruit>() {
            @Override
            public Fruit buy() {
                return null;
            }

            @Override
            public float refund(Fruit item) {
                return 0;
            }
        };



        Shop<? extends Fruit> fruitShop2 = new Shop<Fruit>() {
            @Override
            public Fruit buy() {
                return null;
            }

            @Override
            public float refund(Fruit item) {
                return 0;
            }
        };


        /**
         * 尖括号写在哪里
         *
         * 写在类名、接口名的右边：表示对要定义的类型参数的包裹
         * 泛型实例化的声明处；
         * 实例化泛型类的时候，构造方法的右边； 一般方法的话，是写在左边，如下；
         */

        /**
         * 错误
         */
//        EditText viewById = this.<TextView>findViewById(R.id.spread_inside);

        /**
         * 正确
         */
        TextView viewById = this.<TextView>findViewById(R.id.spread_inside);

        /***
         *
         */



        /**
         * 重复功能：对父类或者父接口进行扩展；
         * 继承的时候，对父接口进行扩展；需要子类和父类都写上T
         * Comparable接口，相当于把Comparable里头的东西搬过来；右边的尖括号写的是自己的类名； 实现接口的方法，里头传了个自己类型的参数，
         * 以此来扩展父接口；
         *
         */
    }


    public final class String implements Comparable<String> {
        @Override
        public int compareTo(String anotherString) {
            return 0;
        }
    }

}
