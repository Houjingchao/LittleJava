# Enum(Java枚举类型简单介绍)

### 背景
long long ago,表示枚举类型是申明一组`int`常量。<br>
叫做int枚举模式
表示季节：
```java
public class Season {
    public static final int SPRING = 1;
    public static final int SUMMER = 2;
    public static final int AUTUMN = 3;
    public static final int WINTER = 4;
}
```

代码一般要求：
***安全性，易用性，可读性***
这种模式不是安全的：比如说我们设计一个函数，要求传入春夏秋冬的某个值。但是使用int类型，我们无法保证传入的值为合法。
```java
private String getSeason(int season){
        StringBuffer result = new StringBuffer();
        switch(season){
            case Season.SPRING :
                result.append("春天");
                break;
            case Season.SUMMER :
                result.append("夏天");
                break;
            case Season.AUTUMN :
                result.append("秋天");
                break;
            case Season.WINTER :
                result.append("冬天");
                break;
            default :
                result.append("地球没有的季节");
                break;
        }
        return result.toString();
    }

    public void doSomething(){
        System.out.println(this.getSeason(Season.SPRING));//正常用法
        System.out.println(this.getSeason(5));//导致了类型不安全问题
    }

```
接下来分析一下可读性：`int`常量不具有可读性。
换成`String`可能导致性能问题，因为他比较依赖字符串的比较操作。

### 定义
```java
public enum Season {
     SPRING, SUMMER, AUTUMN, WINER;
}
```