### Kotlin


### 协程

### 混合开发

### constraintlayout motionlayout

### 泛型

alibaba fastJson 可以将T转化为String

函数泛型，只要接收类型符合即可；

    private <T extends View> T get(){
        View view = new View();
        return (T)view;
    }
	
	EditText edt = get();

	
	//拷贝数组，并且赋予新长度
	 instances = Arrays.copyOf(instances, instances.length + 1);
     instances[instances.length - 1] = instance;
		
	// 拷贝数组
	System.arraycopy

详见代码中的注释；

继承了一个分型接口或者泛型类，如果不能确定泛型，那么照常写T；

	public interface RepairableShop<T> extends Shop<T> {
    	void repair(T item);
	}


泛型方法，常需要运行时强转
	public <V> V findViewById(int id){
        View view = findView(id);
        return (V) view;
    }



### 附加
Thread类中有一个join()方法，在一个线程中启动另外一个线程的join方法，当前线程将会挂起，而执行被启动的线程，直到被启动的线程执行完毕后，当前线程才开始执行。

android studio move to right，分屏功能；

private List<Shop<Apple>> appleShopList  = new ArrayList<Shop<Apple>>();

//加大括号，创建子类
private List<Shop<Apple>> appleShopList  = new ArrayList<Shop<Apple>>(){};

	
    /***
     * new ArrayList<Shop<Apple>>() 是个方法调用，在实际运行时才创建出来；运行时这个对象没有任何泛型信息了，反射也拿不到；
     * 需要创建子类，因为创建子类的话，在编译以后，就会拿到 [ArrayList<Shop<Apple>>] 这种类型信息;
     */
    private List<Shop<Apple>> appleShopList  = new ArrayList<Shop<Apple>>();

    private List<Shop<Apple>> appleShopList2  = new ArrayList<Shop<Apple>>(){};

    //以上就是创建了类，相当于
    class Test$1 extends ArrayList<Shop<Apple>>{

    }
