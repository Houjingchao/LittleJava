# IOC的概念

## IOC 定义

Ioc(Inversion of Control),意思为控制反转，是一种设计思想
**将你设计好的对象教给容器控制，而不是传统的在你的对象内部直接控制**

**由谁控制，控制内容**
 
由谁控制：由Ioc容器控制对象的创建
控制内容：控制外部资源获取

**为何是反转**
因为由容器帮我们查找及注入依赖对象，对象只是被动的接受依赖对象，所以是反转；
哪些方面反转了？依赖对象的获取被反转了。

## demo 

1.**自己获取**
```java
public class A{
    void get(){
        B b = new B();
    }
}
```

2.**工厂方法**
```java
public class A{
    void get(){
        B b = BFactory.createB();
    }
}
```

3.**直接传入**
```java
public class A{
    void get(B b){
      //ba la ba la 
    }
}
```

## Ioc特点
设计出松耦合的程序，使得整个体系变得非常灵活
便于测试
体现了面向对象设计法则之一：即由IoC容器帮对象找相应的依赖对象并注入，而不是由对象主动去找。

## DI
DI(Dependency Injection)，既"依赖注入"
为了提升组件的重用的概率

ioc 和di 的关系：
相对IoC 而言，“依赖注入”明确描述了“被注入对象依赖IoC容器配置依赖对象”。











