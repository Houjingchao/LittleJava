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
- 将instance对象指向分配的内存空间（执行完这步 instance 就为非 null 了


