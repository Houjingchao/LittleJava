# java8简明指南

## Lambda表达式
Lambda表达式(也称为闭包),它允许我们将函数当成参数传递给某个方法，或者把代码本身当作数据处理
最简单的Lambda表达式可由都好分隔的参数列表,->符号，语句块组成。例子如下：
```java
Arrays.asList("a","b","c").forEach(e->System.out.println(e));
```
上面例子中的参数 e的类型是由编译器得出的，也可以显示的指定。例子如下：
```java
Arrays.asList("a","b","c").forEach((String e)->System.out.println(e));
```
如果Lambda需要更复杂的语句块，可以用花括号将该语句扩起来。例子如下：
```java
Arrays.asList("a","b","c").forEach(e->{
    System.out.println(e)
    System.out.println("再次输出："+e)
});
```
Lambda表达式，可以引用类成员和局部变量（会将这些变量隐式的转换成final的）。下面两个代码块的效果完全相同：
```java
String separator = ",";
Arrays.asList("a","b","c").forEach(e->System.out.println(e+separator));
```

```java
final String separator = ",";
Arrays.asList("a","b","c").forEach(e->System.out.println(e+separator));
```
Lambda表达式有返回值，返回值的类型也由编译器推理得出。
如果Lambda的语句块只有一行，则可以不使用return 语句。
下面两个方法效果相同：
```java
Arrays.asList( "a", "b", "d" ).sort( ( e1, e2 ) -> e1.compareTo( e2 ) );
```
```java
Arrays.asList( "a", "b", "d" ).sort( ( e1, e2 ) -> {
    int result = e1.compareTo( e2 );
    return result;
} );

```





