# java8 foreach
主要介绍java8 foreach Map,List

### 1.Foreach map

1.1 normal
```java
Map<String, Integer> maps = new HashMap<>();
maps.put("a", 1);
maps.put("b", 2);

for (Map.Entry<String, Integer> entry : maps.entrySet()) {
	System.out.println(entry.getKey() + "--" + entry.getValue());
}
```
1.2 pro

```java
items.forEach((k,v)->System.out.println("k + "- " + v));

items.forEach((k,v)->{
	__System.out.println( k__ + "-" + v);
});
```

### 2. Foreach操作List

2.1 normal

```java
List<String> lists = new ArrayList<>();
lists.add("1");
lists.add("2");
for(String item : lists){
	System.out.println(item);
}
```

2.2 pro
```java
lists.forEach(item->System.out.println(item));

lists.forEach(item->{
		System.out.println(item);
});
```

