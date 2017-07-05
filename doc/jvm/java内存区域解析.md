# java 内存区域解析

java 内存区域主要分为五部分：
- 程序计数器（PC）
- 虚拟机栈(JVM Stack)
- 本地方法栈(Native Method Stack)
- Java 堆内存(Java Heap)
- 方法区(Method Area)
![image](http://osm01olbb.bkt.clouddn.com/github.com/jvmjvm-memory.png)
<img src="http://osm01olbb.bkt.clouddn.com/github.com/jvmjvm-memory.png" width="164"/>
### 程序计数器
CPU内部的寄存器中就包含一个程序计数器，存放程序执行的下一条指令地址。在程序开始执行前，将程序指令序列的起始地址，即程序的第一条指令所在的内存单元地址送入PC.
CPU按照PC的地址从内存中读取第一条指令。每一条指令执行时，CPU会自动修改PC的量至下一条指令的地址，指令之间的跳转离不开PC。JVM内存中的程序计数器也是这样的作用，它储存JVM当前执行bytecode的地址。
