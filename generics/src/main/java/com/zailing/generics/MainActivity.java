package com.zailing.generics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zailing.LogUtil;
import com.zailing.generics.food.Animal;
import com.zailing.generics.food.Eater;
import com.zailing.generics.food.Human;
import com.zailing.generics.fruit.Apple;
import com.zailing.generics.fruit.Banana;
import com.zailing.generics.fruit.Fruit;
import com.zailing.generics.shop.AppleShop;
import com.zailing.generics.shop.Shop;
import com.zailing.generics.shop.SimShop;
import com.zailing.generics.shop.UnicomSimShop;
import com.zailing.generics.sim.ChinaUnicomSim;
import com.zailing.generics.sim.Sim;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    /**
     * 泛型三步
     * 创建，声明，实例化；
     */
    /**
     * 创建
     * 类创建
     * 方法创建
     */
    /**
     * 类创建
     * class 创建
     * interface 创建
     */
    /**
     * 泛型的目的，什么时候创建泛型；
     * 当类中的参数类型、成员变量类型、返回值类型不定的时候
     *
     * 泛型定义 ClassName<T>
     *
     * 确定时机： implements Shop<Apple>，也就是Class<具体类型>,也叫类型参数实例化；
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Wrapper<String> stringWrapper = new Wrapper<>();
//        stringWrapper.setInstance("哈哈哈哈哈");
//        String name = stringWrapper.getInstance();

        /**
         * 泛型
         * 通过编译之前的检查
         * 会自动帮忙转型、自动判断类型；
         * 省去了一些强制转型的代码；
         *
         * 使用泛型，写代码更方便；扩展代码
         */
        HenCoderList<String> list = new HenCoderList<>();
        list.add("hehehe");
        String string = list.get(0);

        Shop<Apple> appleShop = new AppleShop();
//        Shop<Apple> appleShop = null;
        Apple apple = appleShop.buy();
        appleShop.refund(apple);

        /**
         *静态变量是被泛型类所有实例所共享的。对于声明为MyClass<T>的类，访问其中的静态变量的方法仍然是MyClass.myStaticVar。
         * 不管是通过new MyClass<String>还是new MyClass<Integer>创建的对象，都是共享一个静态变量，所以静态变量用泛型没意义；
         */

//        SimShop<Apple, ChinaUnicomSim> unicomSimSimShop = new UnicomSimShop<Apple, ChinaUnicomSim>();

//        UnicomSimShop unicomSimSimShop2 = new UnicomSimShop();

        /**
         * SimShop<Apple, ChinaUnicomSim> 泛型参数实例化；
         */

        SimShop<Apple, ChinaUnicomSim> unicomSimSimShop1 = new UnicomSimShop();

//        SimShop<Apple, Banana> bananaSimShop = null;

        SimShop<Apple, Sim> simShop = null;


        /***
         * 泛型变量的声明和实例化
         * 左边：泛型变量的声明 右边：泛型变量的实例化,右边运行时，
         */
        List<String> list2 = new ArrayList<String>();


        /**
         *  new ArrayList<Apple> :泛型实例化后，代表ArrayList中只能放苹果，不能放其他水果；以下有错误；
         */
//        ArrayList<Apple> apples = new ArrayList<Apple>();

        /**
         * fruits实际上是个苹果的list，如果拿去放香蕉会出错；
         */
//        ArrayList<Fruit> fruits = apples;
////        ArrayList<Apple> apples = new ArrayList<>();
////        Fruit banana = new Banana();
////        apples.add(banana);
//
//        Fruit banana = new Banana();
//        /**
//         * 假设上面不跑错，fruits 可以添加香蕉、苹果；导致list出错，因为fruits只能添加苹果;
//         */
//        fruits.add(banana);
//
//        /**
//         * list 4 可以添加水果
//         */
//        ArrayList<Fruit> list4 = new ArrayList<Fruit>();
//        list4.add(banana);

        /**
         * 错误
         */
//        ArrayList<? extends Fruit> fruits = new ArrayList<Apple>();
//        Fruit banana = new Banana();
//        fruits.add(banana);

        /**
         * 错误
         */
//        ArrayList<? extends Fruit> fruits = new ArrayList<Apple>();
//        Banana banana = new Banana();
//        fruits.add(banana);

        /**
         * 错误
         */
//        /**
//         *  < ? extends Fruit> :
//         */
//        ArrayList<? extends Fruit> fruits = new ArrayList<Apple>();
//        fruits.add(new Apple());


        /**
         *  < ? extends Fruit> : 用来声明，表示右边过来的赋值参数需要符合 extends Fruit的条件；Apple Banana Fruit都可以使第一句编译通过；
         *
         *
         *
         */
//        ArrayList<? extends Fruit> fruits = new ArrayList<Apple>();
//        //有可能fruits后来又被重新赋值，于是无法预知fruits到底会放什么
//        fruits = new ArrayList<>();
//
//        fruits.add(new Apple());

//        ArrayList<? extends Fruit> fruits = new ArrayList<Apple>();
//        //有可能fruits后来又被重新赋值，变成一个放香蕉的list，于是无法预知fruits到底会放什么，因为右边是运行时候才确定；
//             这种情况下，加什么都不行；
//        fruits = new ArrayList<Banana>();
//        fruits.add(new Apple());

//        /**
//         * 正确
//         */
//        ArrayList<Fruit> fruits = new ArrayList<>();
//        fruits.add(new Apple());


//        Fruit fruit = new Apple();
//
//        /**
//         * 可以存放任何水果；
//         */
//        ArrayList<Fruit> fruits = new ArrayList<>();
//        fruits.add(new Apple());
//
//        /**
//         * 想要水果的fruit集合，却给了一个只能存放苹果的list，有错，万一后面要放香蕉？
//         */
//        ArrayList<Fruit> fruits1 = new ArrayList<Apple>();
//        fruits1.add(new Apple());
//
//        /**
//         * 想要盛放【某种水果！！！，只能是某种】的list，右边给啥都行，只要是fruit，但是add的时候，可能是任何一个水果，所以add通不过；
//         * 是某一个，但是又不能确定是某哪个?
//         */
//        ArrayList<? extends Fruit> fruits2 = new ArrayList<Apple>();
//        fruits2.add(new Apple());
//        fruits2.add(new Banana());


//        ArrayList<? extends Fruit> fruits = new ArrayList<Apple>();
        // 只有Get方法可用；因为get是获取;
//        for(int i = 0;i<fruits.size();i++){
//            Fruit fruit = fruits.get(i);
//        }
//        fruits.add(new Apple());

        List<Apple> apples = new ArrayList<>();
        List<Banana> bananas = new ArrayList<>();

        /**
         * 用？ extend Fruit的场景; 每种水果称重量;
         */
        float appleWeight = totalWeight(apples);
        float bananaWeight = totalWeight(bananas);

        /**
         * 泛型，类型擦除；所以编译时候严格？
         */

        ////数组
        /***
         * 错误,运行时错误，编译查不出来；
         */
//        Fruit[] fruitsArr = new Apple[10];
//        fruitsArr[0] = new Banana();
//        LogUtil.d("fruits 数组成功了");

        /**
         * 强制转，编译通过； 泛型的类型擦除导致以下运行通过；
         */
//        List<Fruit> fruits = (ArrayList)new ArrayList<Apple>();
//        LogUtil.d("泛型开始");
//        fruits.add(new Banana());
//        LogUtil.d("泛型成功");



//        List<Fruit> fruits = (ArrayList)new ArrayList<Apple>();
//        LogUtil.d("泛型开始");
//        fruits.add(new Banana());
//        LogUtil.d("泛型成功");

        /**
         * 举个例子
         */
//        Eater<Human> monster = new Eater<Human>();
//        monster.eat(new Human());
//        Eater<Animal> beast = new Eater<Animal>();
//        beast.eat(new Animal());
//        beast.eat(new Human());
//        //编译时要求左右一样，因为类型擦除，编译后，两边的尖括号没了，可能会导致运行时出错，所以编译器加了限制
//        Eater<Animal> animalEater = new Eater<Human>();
//
//        Eater<? extends Animal> someAimalEater = new Eater<Human>();
//        someAimalEater.eat(new Human());

    }

    /**
     * 错误
     */
//    float totalWeight(List<Fruit> fruits){
//        float weight = 0;
//        for(Fruit fruit : fruits){
//            weight += fruit.getWeight();
//        }
//        return weight;
//    }

    /**
     * 以下必须用 List<? extends Fruit>，迭代可用
     * @param fruits
     * @return
     */
    float totalWeight(List<? extends Fruit> fruits){
        float weight = 0;
        for(Fruit fruit : fruits){
            weight += fruit.getWeight();
        }
//        for(int i = 0;i<fruits.size();i++){
//            Fruit fruit = fruits.get(i);
//        }
        return weight;
    }


}
