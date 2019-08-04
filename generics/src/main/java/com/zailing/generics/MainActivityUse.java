package com.zailing.generics;

import android.app.Activity;
import android.os.Bundle;

import com.google.gson.reflect.TypeToken;
import com.zailing.generics.fruit.Apple;
import com.zailing.generics.fruit.Fruit;
import com.zailing.generics.fruit.Watermelon;
import com.zailing.generics.shop.NormalShop;
import com.zailing.generics.shop.Shop;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


/***
 * 什么时候用泛型
 *
 * 1.泛型的创建者为了后面的使用者可以拿到T类型的子类，调用子类的api，也就是说，类中至少应该有一个方法的返回值用T修饰，（属性用T修饰？）
 *  如果直接用Object修饰，不方便，还要转类型；
 *   如果T都是位于方法形参中，一般就没必要定义泛型； 也有几种特殊情况，以下是特殊情况
 *   2.Comparable接口 T 传入自身的类作为用于比较的对象，其实也是方便使用者直接在int compareTo(T o) 中使用o的api来对比大小；
 *
 *   3.见 Shop的方法 用泛型作为方法限制，跟泛型类无关；
 */

public class MainActivityUse extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //List<? super Apple> 泛型声明
        List<? super Apple> lists = new ArrayList<Fruit>();
        Shop<Watermelon> watermelonShop = new Shop<Watermelon>() {
            @Override
            public Watermelon buy() {
                return null;
            }

            @Override
            public float refund(Watermelon item) {
                return 0;
            }
        };

        /**
         * 不需要转类型
         */
        watermelonShop.buy().hasSeed();

        NormalShop normalShop = new NormalShop() {
            @Override
            public Object buy() {
                return null;
            }

            @Override
            public float refund(Object item) {
                return 0;
            }
        };

        /***
         * 需要转类型;
         */
        ((Watermelon)normalShop.buy()).hasSeed();

        /**
         * 泛型的远见，创造者提供使用者的便利；
         *
         *
         *
         */

        List<Fruit> fruits = new ArrayList<>();
        Apple apple = new Apple();
//        fruits.add(apple);



        Shop<?> shop = null;

        //? 既限制顶，也限制底，一般不这么做
//        List<?> list = null;
//        list.add("abc");

        /**
         * 确定了是一种，这种是fruit的子类，所以不能加apple
         */
        List<? extends Fruit> list0 = null;
//        list0.add(new Apple());//

        List<? super Apple> list = null;
        list.get(9); //拿到的既可能是apple，也可能是其他的


        /***
         * 泛型类型擦除，在运行时，所有的类型参数，和尖括号里头的东西，都会被擦掉，变成Object
         * 调用返回的T对象的api的时候，java编译器会帮忙强制把Object转型成传入给T的具体实参
         * 还加了个bridge method 来调用具有T作为方法参数的方法（参数被擦除为Object）；
         *
         */

        // 类型参数，List<String> 最后都被擦除为List,不存在以下类型
//    List<String>.class;

        Object[] objects1 = new String[10];
//        List<Object> objects2 = new ArrayList<String>();

//        Shop<Apple>[] objects3  = new Shop<Apple>[10];
//        Shop<?>[] objects4  = new Shop<?>[10];

        /**
         * 尽量不要用泛型和数组来配合使用，而是用集合；
         */

        //但是Gson可以获取到最终的。。。，因为有TypeToken,
        TypeToken<List<String>> listTypeToken = new TypeToken<List<String>>(){};

        /**
         * Method或者Field 可以获取到泛型的整个类型；
         */
        Method myMethod = null;
        myMethod.getGenericParameterTypes();

        Field field = null;
        field.getGenericType();
    }

    /**
     * 带问好的泛型声明也可以写在返回类型里头
     * @return
     */
    private List<? extends Fruit> getAllFruits(){
        return null;
    }

    /***
     * new ArrayList<Shop<Apple>>() 是个方法调用，在实际运行时才创建出来；运行时这个对象没有任何泛型信息了，反射也拿不到；
     * 需要创建子类，因为创建子类的话，在编译以后，就会拿到 [ArrayList<Shop<Apple>>] 这种类型信息;
     */
    private List<Shop<Apple>> appleShopList  = new ArrayList<Shop<Apple>>();

    private List<Shop<Apple>> appleShopList2  = new ArrayList<Shop<Apple>>(){};

    //以上就是创建了类，相当于
    class Test$1 extends ArrayList<Shop<Apple>>{

    }




}


