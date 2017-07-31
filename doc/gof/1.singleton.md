# 单例模式

## 懒汉式 线程不安全 
使用了懒汉方式，多个线程并行调用会出现问题。既在多线程下不能正常工作。
```java
public class Singleton{
   private static Singleton instance;
   public Singleton(){}
   public static Singleton getInstance(){
       if(instance==null){
           instance = new Singleton();
       }
       return instance;
   } 
}
```

## 懒汉式 线程安全 
为了线程安全，给getInstance方法设为了同步（synchronized）
缺点：效率不高
因为任何时候都只有一个线程调用该方法。
但同步操作只需要在第一次调用的时候才需要，即在第一次创建单例实例对象。（减小 synchroinzed 粒度）
```java
public static synchronized Singleton getInstance(){
       if(instance==null){
           instance = new Singleton();
       }
       return instance;
   } 
```
##  双重检验锁
是一种使用同步块加锁的方法，可称之为双重检查锁。<br>两次检查`instance==null`一次在同步块内，一次在同步块外
为什么在同步块内还要再检验一次？因为可能会有多个线程一起进入同步块外的 if，如果在同步块内不进行二次检验的话就会生成多个实例了。
```java
public static Singleton getInstance(){
   if(instance==null){
       synchronized(Singleton.class){
           if (instance == null) {                 
              instance = new Singleton();
           }
       }
   }
   return instance;
}
```
这段代码存在问题，问题是：`instance = new Singleton();`这不是一个院子操作。

***JVM所做的操作如下：***
- 给 instance 分配内存
- 调用 Singleton 的构造函数来初始化成员变量
- 将instance对象指向分配的内存空间 执行完这步 instance 就为非 null 了

JVM编译器存在指令重排的优化，上述第二步和第三步的顺序是没办法保证的。最终的执行顺序可能是 1-2-3 也可能是 1-3-2。如果是后者，则在 3 执行完毕、2 未执行之前，被线程二抢占了，这时 instance 已经是非 null 了（但却没有初始化），所以线程二会直接返回 instance，然后使用，然后就会报错。

解决办法：将instance声明成volatile
这种方式复杂又隐含问题
```java
public class Singleton {
    private volatile static Singleton instance;
    private Singleton (){}
    public static Singleton getSingleton() {
        if (instance == null) {                         
            synchronized (Singleton.class) {
                if (instance == null) {       
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
```
***volatile***可以使得字段具有可见性，也就是可以保证线程在本地不会存有 instance 的副本，每次都是去主内存中读取。
这里使用volatile的主要原因是其另一个特性：禁止指令重排序优化。<br>也就是说，在 volatile 变量的赋值操作后面会有一个内存屏障（生成的汇编代码上），读操作不会被重排序到内存屏障之前。比如上面的例子，取操作必须在执行完 1-2-3 之后或者 1-3-2 之后，不存在执行到 1-3 然后取到值的情况
即：***volatile 变量的写操作都先行发生于后面对这个变量的读操作***

注意：Java 5 以前的版本使用了 volatile 的双检锁还是有问题的。

## 饿汉式
这种方式是线程安全的，因为变量声明为了static,final。会在第一次加载类到内存中时就会初始化。
```java
public class Singleton{
    //类加载时就初始化
    private static final Singleton instance = new Singleton();
    
    private Singleton(){}

    public static Singleton getInstance(){
        return instance;
    }
}
```
这种方式的缺点：单例会在加载类后一开始就被初始化，即使客户端没有调用 getInstance()方法。饿汉式的创建方式在一些场景中将无法使用：譬如 Singleton 实例的创建是依赖参数或者配置文件的，在 getInstance() 之前必须调用某个方法设置参数给它，那样这种单例写法就无法使用了。

## 静态内部类
较为推荐的做法:
```java
public class Singleton { 
     private static class SingletonHolder {  
        private static final Singleton INSTANCE = new Singleton();  
    }  
    private Singleton (){}  
    public static final Singleton getInstance() {  
            return SingletonHolder.INSTANCE; 
    }  
}
```

这种方法使用了JVM 本身的机制保证了线程安全。
- 由于 SingletonHolder 是私有的，除了 getInstance() 之外没有办法访问它，因此它是懒汉式的.
- 同时读取实例的时候不会进行同步，没有性能缺陷(暂时还理解不是很透彻)
- 也不依赖 JDK 版本

## 枚举 Enum
```java
public enum EasySingleton{
    INSTANCE;
}
```
优点：防止反序列化导致重新创建新的对象

### 总结
一般情况下直接使用饿汉式就好了，如果明确要求要懒加载（lazy initialization）会倾向于使用静态内部类，如果涉及到反序列化创建对象时会试着使用枚举的方式来实现单例。