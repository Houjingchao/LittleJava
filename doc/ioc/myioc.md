# 自己的Ioc

ioc 容器需要可以存储对象，还需注解注入的功能
java允许对ClassSurround 进行操作，class文件由类加载装配后，在JVM 中形成了一份描述Class对象的元信息对象。
通过改元信息获知Class结构信息，如构造函数，属性和方法等。

**Java允许用户借助这个class相关的元信息间接调用class对象的功能，就为了程序化方式操作Class对象开辟了途径**

首先了解一下反射机制：
```java
public class XiaoHou {

	private String name;

	public XiaoHou() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void dosomething(){
	    System.out.println("LittleM is handsome~~");
	}
}
```

测试代码：
```java
public class Test {

	public static void main(String[] args) throws Exception {
		//1. 通过类装载器获取XiaoHou类对象  
		ClassLoader loader = Thread.currentThread().getContextClassLoader();   
		Class<?> clazz = loader.loadClass("com.littlem.ioc.XiaoHou");   
		  
		//2. 获取类的默认构造器对象并通过它实例化XiaoHou 
		Constructor<?> cons = clazz.getDeclaredConstructor((Class[])null);   
		XiaoHou xiaohou = (XiaoHou)cons.newInstance();  
		
		//3. 通过反射方法设置属性  
		Method setBrand = clazz.getMethod("setName", String.class);
		setBrand.invoke(xiaohou, "littleM");

		// 4. 运行方法
		xiaohou.dosomething();
	}
}

```
1:装载XiaoHou类对应的反射实例
2:通过构造函数对象的newInstrance()方法实例化XiaoHou对象
3:我们又通过XiaoHou的反射类对象的getMethod（String methodName,Class paramClass）获取属性的Setter方法对象
通过invoke（Object obj,Object param）方法调用目标类的方法

**首先设计接口：容器接口**
首先具有存储和移除一个对象的能力
其次包含更多获取和注册对象的方法

```java
/**
 * Created by LittleM on 2017/6/18.
 */
public interface ContainerInf {
    public <T> T getBean(Class<T> clazz);

    public <T> T getBeanByName(String name);

    public Object registerBean(Object bean);

    public Object registerBean(Class<?> clazz);

    public Object registerBean(String name, Object bean);

    public void remove(Class<?> clazz);

    public void removeByName(String name);

    public Set<String> getBeanNames();

    /**
     * 初始化装配
     */
    public void init();
}
```
实现代码：
```java
/**
 * 接口ContainerInf的实现
 * Created by LittleM on 2017/6/18.
 */
public class Container implements ContainerInf {

    /**
     * 保存所有的beans 格式为：
     * com.littlem.ioc...:balabala
     */
    private Map<String, Object> beans;
    /**
     * 存储bean 和name的关系：
     * name:com.little.ioc....
     */
    private Map<String, String> beanKeys;

    public Container() {
        this.beans = new ConcurrentHashMap<String, Object>();
        this.beanKeys = new ConcurrentHashMap<String, String>();
    }

    @Override
    public <T> T getBean(Class<T> clazz) {
        String name = clazz.getName();
        Object object = beans.get(name);
        if (null != object) {
            return (T) object;
        }
        return null;
    }

    @Override
    public <T> T getBeanByName(String name) {
        String className = beanKeys.get(name);
        Object object = beans.get(className);
        if (null != object) {
            return (T) object;
        }
        return null;
    }

    @Override
    public Object registerBean(Object bean) {
        String name = bean.getClass().getName();
        beanKeys.put(name, name);
        beans.put(name, bean);
        return bean;
    }

    @Override
    public Object registerBean(Class<?> clazz) {
        String name = clazz.getName();
        beanKeys.put(name, name);
        Object bean = ReflectUtil.newInstance(clazz);
        beans.put(name, bean);
        return bean;
    }

    @Override
    public Object registerBean(String name, Object bean) {
        String className = bean.getClass().getName();
        beanKeys.put(name, className);
        beans.put(className, bean);
        return bean;
    }

    @Override
    public void remove(Class<?> clazz) {
        String className = clazz.getName();
        if (null != className && !className.equals("")) {
            beanKeys.remove(className);
            beans.remove(className);
        }
    }

    @Override
    public void removeByName(String name) {
        String className = beanKeys.get(name);
        if (null != className && !className.equals("")) {
            beanKeys.remove(name);
            beans.remove(className);
        }
    }

    @Override
    public Set<String> getBeanNames() {
        return beanKeys.keySet();
    }

    /**
     * 初始化bean
     */
    @Override
    public void init() {
        beans.forEach((k, v) -> {
                    Object object = k;
                    inject(object);
                }
        );
    }

    /**
     * 注入对象
     */
    public void inject(Object object) {

        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

```

