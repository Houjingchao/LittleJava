# @interface 的用法 
参考：睡眼惺忪_ 的博客
## concept
java用
**@interface Annotation{}**
定义一个注解@Annotation，一个注解就是一个类
@Override，@Deprecated，@SuppressWarnings为常见的3个注解。
注解相当于一种标记，在程序中加上了注解，相当于为程序加上了某种标记。
**可以通过反射来了解你的类，以及各种元素上有无任何标记**（个人认为这点很重要）

#### @override
重写该方法的时候使用
```java
@override
public String ToString(){
    return "override";
}
```
#### @Deprecated
表示某个属性或者方法已经过时，不再想让人使用
```java
@Deprecated
public void play(){
   System.out.println("过时了");
}
```
#### @SuppressWarnings
用来压制程序中出来的警告，比如没有使用范行或者方法过时的时候
```java
publi void play(){
    System.out.println("");
}
```
**@Retention是注解的注解，用来修饰注解--称为原注解**
Retention注解有一个RetentionPolicy类型的属性，Enum RetentionPolicy是一个枚举类型
可理解为Rentention 搭配 RententionPolicy使用
RetentionPolicy有3个值：CLASS，RUNTIME，SOURCE
@Retention(RetentionPolicy.CLASS)，表示注解的信息被保留在class文件(字节码文件)中当程序编译时，但不会被虚拟机读取在运行的时候；
@Retention(RetentionPolicy.SOURCE ),表示注解的信息会被编译器抛弃，不会留在class文件中，注解的信息只会留在源文件中；
@Retention(RetentionPolicy.RUNTIME )，表示注解的信息被保留在class文件(字节码文件)中当程序编译时，会被虚拟机保留在运行时，
**所以他们可以用反射的方式读取**
**RetentionPolicy.RUNTIME**可以让你从JVM中读取Annotation注解的信息，以便在分析程序的时候使用.
例子如下：
定义个一注解@MyTarget，用RetentionPolicy.RUNTIME修饰；
```java
@Retention(RetentionPolicy.RUNTIME)  
public @interface MyTarget{}  
```
接下来使用该注解
```java
public class MyTargetTest  {  
 @MyTarget  
 public void doSomething()  {  
    System.out.println("do something");  
 }  
   
 public static void main(String[] args) throws Exception  {  
  Method method = MyTargetTest.class.getMethod("doSomething",null);  
  if(method.isAnnotationPresent(MyTarget.class))//如果doSomething方法上存在注解@MyTarget，则为true  {  
     System.out.println(method.getAnnotation(MyTarget.class));  
  }  
  }  
}  
```
输出结果：@com.self.MyTarget()，如果RetentionPolicy值不为RUNTIME,则不打印。
  

**注解这可以定义属性**
```java
@Retention(RetentionPolicy.RUNTIME)  
public @interface MyAnnotation{  
 String hello() default "gege";  
  String world();  
  int[] array() default { 2, 4, 5, 6 };  
  EnumTest.TrafficLamp lamp() ;  
  TestAnnotation lannotation() default @TestAnnotation(value = "ddd");  
  Class style() default String.class;  
} 
```
上面程序中，定义一个注解@MyAnnotation，定义了6个属性，他们的名字为：  
hello,world,array,lamp,lannotation,style.  
属性hello类型为String,默认值为gege  
属性world类型为String,没有默认值  
属性array类型为数组,默认值为2，4，5，6  
属性lamp类型为一个枚举,没有默认值  
属性lannotation类型为注解,默认值为@TestAnnotation，注解里的属性是注解  
属性style类型为Class,默认值为String类型的Class类型 
 
定义一个MyTest类
```java
public class MyTest{  
 @MyAnnotation(lannotation=@TestAnnotation(value="baby"), world = "shanghai",array={1,2,3},lamp=TrafficLamp.YELLOW)  
 @Deprecated  
 @SuppressWarnings("")  
 public void output(){  
  System.out.println("output something!");  
 }  
} 
```

通过反射读取信息
```java
public class MyReflection{  
public static void main(String[] args) throws Exception  {  
    MyTest myTest = new MyTest();  
    Class<MyTest> c = MyTest.class;  
    Method method = c.getMethod("output", new Class[] {});  
    //如果MyTest类名上有注解@MyAnnotation修饰，则为true  
    if(MyTest.class.isAnnotationPresent(MyAnnotation.class)){  
     System.out.println("have annotation");  
    }  
    if (method.isAnnotationPresent(MyAnnotation.class)){  
        method.invoke(myTest, null); //调用output方法  
     //获取方法上注解@MyAnnotation的信息  
    MyAnnotation myAnnotation = method.getAnnotation(MyAnnotation.class);  
    String hello = myAnnotation.hello();  
    String world = myAnnotation.world();  
    System.out.println(hello + ", " + world);//打印属性hello和world的值  
    System.out.println(myAnnotation.array().length);//打印属性array数组的长度  
    System.out.println(myAnnotation.lannotation().value()); //打印属性lannotation的值  
    System.out.println(myAnnotation.style());  
   }  
   //得到output方法上的所有注解，当然是被RetentionPolicy.RUNTIME修饰的  
   Annotation[] annotations = method.getAnnotations();  
      for (Annotation annotation : annotations)  {  
       System.out.println(annotation.annotationType().getName());  
   }  
   }  
}  

```
打印结果如下：
have annotation  
output something!  
gege, shanghai  
3  
baby  
class java.lang.String  
com.heima.annotation.MyAnnotation  
java.lang.Deprecated  


如果注解中有一个属性名字叫value,则在应用时可以省略属性名字不写。  
可见，@Retention(RetentionPolicy.RUNTIME )注解中
**RetentionPolicy.RUNTIME是注解属性值，属性名字是value**
属性的返回类型是RetentionPolicy，如下： 
```java
public @interface MyTarget{  
    String value();  
}  
```
可以这样用：  
```java
@MyTarget("aaa")  
public void doSomething(){  
  System.out.println("hello world");  
}
``` 
**注解@Target也是用来修饰注解的元注解，它有一个属性ElementType也是枚举类型，  
值为：ANNOTATION_TYPE CONSTRUCTOR  FIELD LOCAL_VARIABLE METHOD PACKAGE PARAMETER TYPE** 
如@Target(ElementType.METHOD) 修饰的注解表示该注解只能用来修饰在方法上。  
```java
@Target(ElementType.METHOD)  
@Retention(RetentionPolicy.RUNTIME)  
public @interface MyTarget{  
 String value() default "hahaha";  
}
```
如把@MyTarget修饰在类上，则程序报错，如：  
```java
@MyTarget  
public class MyTargetTest{}
```
