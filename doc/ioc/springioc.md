# 在spring 中怎么使用

spring中获取对象的方式
```java
public static void main(String[] args){
   ApplicationContext context = new FileSystemXmlApplicationContext("applicationContext.xml");
   XiaoHou xiaohou = (XiaoHou) context.getBean("xiaohou");   
}
```
为了获取对象，首先需要在`applicationContext.xml`中配置
```xml
<bean>
	<property name="name" value="LittlM" />   
</bean>  
```

`XiaoHou`类是这样的
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
}
```
基于Annotation的实现

首先还是要在 `xml` 中配置启用注解方式

```xml
<context:annotation-config/>  
```
下面是一个使用场景：

```java
public class XiaoHou {

	@Autowired
	private Service service ;
	
	public void getName() {
		service.getName();
	}
}
```

```java
@Service("service")
public class Service {
		
	public void getName() {
		System.out.println("LittleM");
	}

}
```
## LINKS
   * [目录](<index.md>)
   * 上一节: [IOC的概念](<iocConcept.md>)
   * 下一节: [自己的IOC](<myioc.md>)







