# ZooKeeper 介绍

Zookeeper是一个分布式的、开源的分布式应用协调服务

集群间通过Zab(Zookeeper Atomic Broadcast)协议保持数据的一致性。

该协议包含两个阶段：lead election, Atomic broadcas 阶段。

集群中选举出一个leader,其他的机器称为 follower,所有的写操作都传给leader,并通过broadcas将所有的更新告诉follower。

当leader崩溃或者leader失去大多数follower，需要重新选择一个leader.

Zookeeper的数据保持在内存中，这就意味着它可以实现高吞吐量和低延迟的数据。

zookeeper 集群的结构如下：

![image](http://osm01olbb.bkt.clouddn.com/github.com/Zookeeper/zkservice.jpg)

Zookeeper服务的组成部分必须相互知道，它们维持了一个内存状态影像，连同事务日志和快照在一个持久化的存储中。只要大多数的服务器是可用的，Zookeeper服务就是可用的。

客户端连接到一个单独的服务。客户端保持了一个TCP连接，通过这个TCP连接发送请求、获取响应、获取watch事件、和发送心跳。如果这个连接断了，会自动连接到其他不同的服务器。

序列。Zookeeper用数字标记每一个更新，用它来反射出所有的事务顺序。随后的操作可以使用这个顺序去实现更高级的抽象，例如同步原件。

快速。它在"Read-dominant"工作负载中特别快。Zookeeper应用运行在数以千计的机器上，并且它通常在读比写多的时候运行的最好，读写比例大概在10:1。

Zookeeper提供的命名空间非常像一个标准的文件系统。一个名字是一系列的以'/'隔开的路径元素。Zookeeper命名空间中所有的节点都是通过路径识别。

ZooKeeper 分层的命名空间
![image](http://osm01olbb.bkt.clouddn.com/github.com/Zookeeper/zookeepernamespaces.png)

#### 节点和临时节点
不像标准的文件系统，Zookeeper命名空间中的每个节点可以有数据也可以有子目录。它就像一个既可以是文件也可以是目录的文件系统。(Zookeeper的设计是保存协调数据：状态信息，配置，位置信息，等等，所以每个节点存储的数据通常较小，通常在1个字节到数千字节的范围之内)我们使用术语znode来表示Zookeeper的数据节点。

znode维持了一个stat结构，它包括数据变化的版本号、访问控制列表变化、和时间戳，允许缓存验证和协调更新。每当znode的数据有变化，版本号就会增加。例如，每当客户端检索数据时同时它也获取数据的版本信息。

命名空间中每个znode存储的数据都是原子性的读取和写入的，读取时获得znode所有关联的数据字节，写入时替换所有的数据。每个节点都有一个访问控制列表来制约谁可以做什么。

Zookeeper还有一个临时节点的概念。这些znode和session存活的一样长，session创建时存活，当session结束，也跟着删除。
#### 状态改变和监听
Zookeeper支持watches的概念。客户端可以在znode上设置一个watch。当znode发生变化时触发并移除watch。当watch被触发时，客户端会接收到一个包说明znode有变化了。并且如果客户端和其中一台server中间的连接坏掉了，客户端就会收到一个本地通知。

#### 保证
Zookeeper非常简单和高效。因为它的目标就是作为建设复杂服务的基础，比如同步。zookeeper提供了一套保证，他们包括：

顺序一致性 - 来自客户端的更新会按顺序应用。

原子性 - 更新成功或者失败，没有局部的结果产生。

唯一系统映像 - 客户端不管连接到哪个服务端都会看到同样的视图。

可靠性- 一旦一个更新被应用，它将从更新的时间开始一直保持到一个客户端重写更新。

时效性 - 系统中的客户端视图在特定的时间点保证是最新的。

#### 简单API
它支持这些操作：
- create - 在树形结构的位置中创建节点
- delete - 删除一个节点
- exists  测试节点在指定位置上是否存在
- get data - 从节点上读取数据
- set data - 往节点写入数据
- get chilren - 检索节点的子节点列表
-sync - 等待传输数据

#### 实现
Zookeeper Compnents 展示了Zookeeper服务的高级组件。除了请求处理器的异常之外，组成Zookeeper服务的服务器都会复制它们自己组件的副本。
***ZooKeeper Components***
![image](http://osm01olbb.bkt.clouddn.com/github.com/Zookeeper/struct.png)
Replicated database 是一个内存数据库，它包含全部的数据树。为了可恢复性，更新记录保存到磁盘上，并且写入操作在应用到内存数据库之前被序列化到磁盘上。

每个Zookeeper服务端服务客户端。客户端正确的连接到一个服务器提交请求。每个服务端数据库的本地副本为读取请求提供服务。服务的变化状态请求、写请求，被一个协议保护。

作为协议的一部分，所有的写操作从客户端转递到一台单独服务器，称为leader。其他的Zookeeper服务器叫做follows，它接收来自leader的消息建议并达成一致的消息建议。消息管理层负责在故障的时候更换Leader并同步Follows。

Zookeeper使用了一个自定义的原子消息协议。因为消息层是原子的，Zookeeper可以保证本地副本从不出现偏差。当leader接受到一个写请求，它计算写操作被应用时系统的状态，并将捕获到的新状态转化进入事务。

#### 性能
它在应用中读比写多的时候有特别高的性能，因为在写入的时候包含所有服务器状态同步。（读比写多是协调服务的典型案例）

Zookeeper吞吐量的读写比例变化：
![image](http://osm01olbb.bkt.clouddn.com/github.com/Zookeeper/writeread.png)
上图是Zookeeper3.2版本在dual 2Ghz Xeon and two SATA 15K RPM 驱动配置的服务器上的吞吐量图像。一个驱动作为专门的Zookeeper日志装置。快照写进操作系统驱动。1k的写请求和1K的读取请求。"Servers" 表明了Zookeeper全体的大小，组成Zookeeper服务的服务器数量。接近于30台机器模仿客户端。Zookeeper全体被配置为leaders不允许客户端连接。

注意：相对于3.1的发布版本，在3.2的版本中读写性能都改善了。

测试基准也表明它是可靠的。Reliability in the Presence of Errors 展示了怎样部署应对各种失败。事件标记出以下几点：

- follower的故障和恢复
- 不同follower的故障和恢复
- leader的故障
- 两个followers的故障和恢复
- 另一个leader的故障
#### 可靠性
展示我们启动了7台机器组成的Zookeeper服务注入超时故障的行为。我们之前运行了相同的饱和基准，但现在我们保持写的百分比在不变的30%，这是一个比较保守的工作负载比。

错误存在的可靠性
![image](http://osm01olbb.bkt.clouddn.com/github.com/Zookeeper/avali.png)
从这张图有几个重要的观察。
第一，如果follows失败并快速的恢复，Zookeeper能够维持一个高的吞吐量尽管有故障。但也许更重要的是，leader选举算法允许系统快速恢复，足以预防吞吐量大幅下降。
第二，在我们的观测中，Zookeeper用了不到200ms的时间就选出了一个新的leader。
第三，随着follower恢复，Zookeeper一旦开始处理它们的请求，它能够再次提高吞吐量。



