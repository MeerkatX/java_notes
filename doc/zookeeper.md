# Zookeeper

针对大型分布式系统的可靠协调系统，提供的功能包括：配置维护、名字服务、分布式同步、组服务

## 配置

`conf/zoo.cfg`

```bash
# 服务器与客户端之间维持的心跳时间
tickTime=2000
# 集群中Follewer服务器与Leader服务器之间最大的初始化连接数
initLimit=10
# 通信时间限制
syncLimit=5
# 元数据储存路径
dataDir=/tmp/zookeeper
# 客户端连接端口号
clientPort=2181
# 配置集群节点信息，序号要保证唯一
server.1=127.0.0.1:2888:3888
server.2=127.0.0.2:2888:3888
# 设置客户端最大连接数
maxClientCnxns=60
# 需要保留的文件数目
autopurge.snapRetainCount=3
# 日志清理频率，单位是小时，如果填写 0 ，表示不开启自动清理
autopurge.purgeInterval=1
```

### 参数解释

```bash
server.id=host:port:port
```

- 不能有相同id的节点，需要确保每个节点的`myid`文件中的id值不同；
- 每一行`server.id=host:port:port`中的id值，需要与所对应节点的数据目录下的`myid`文件中的id值保持一致；
- 每一个配置文件都需要配置全部的节点信息。不仅仅是配置自己的那份，还需要配置所有节点的`id`、`ip`、端口。
- 在每一行`server.id=host:port:port`中，需要配置两个端口。前一个端口（如示例中的2888）用于节点之间的通信，后一个端口（如示例中的3888）用于选举主节点。

`tickTime、initLimit、syncLimit`

- `tickTime`：配置单元时间。单元时间是`ZooKeeper`的时间计算单元，其他的时间间隔都是使用`tickTime`的倍数来表示的。如果不配置，单元时间默认值为3000，单位是毫秒（`ms`）。
- `initLimit`：节点的初始化时间。该参数用于Follower（从节点）的启动，并完成与Leader（主节点）进行数据同步的时间。Follower节点在启动过程中，会与Leader节点建立连接并完成对数据的同步，从而确定自己的起始状态。Leader节点允许Follower节点在`initLimit`时间内完成这项工作。该参数默认值为10，表示是参数`tickTime`值的10倍，必须配置且为正整数。
- `syncLimit`：心跳最大延迟周期。该参数用于配置Leader节点和Follower节点之间进行心跳检测的最大延时时间。在ZK集群运行的过程中，Leader节点会通过心跳检测来确定Follower节点是否存活。如果Leader节点在`syncLimit`时间内无法获取到Follower节点的心跳检测响应，那么Leader节点就会认为该Follower节点已经脱离了和自己的同步。该参数默认值为5，表示是参数`tickTime`值的5倍。此参数必须配置且为正整数

### 伪分布式

`zoo-1.cfg`

```bash
tickTime=2000
initLimit=10
syncLimit=5
dataDir=C:/zookeeper/data/zoo-1/
clientPort=2181
dataLogDir=C:/zookeeper/log/zoo-1/
server.1=127.0.0.1:2888:3888
server.2=127.0.0.1:2889:3889
server.3=127.0.0.1:2890:3890
```

`zoo-2.cfg`

```bash
tickTime=2000
initLimit=10
syncLimit=5
dataDir=C:/zookeeper/data/zoo-2/
clientPort=2182
dataLogDir=C:/zookeeper/log/zoo-2/
server.1=127.0.0.1:2888:3888
server.2=127.0.0.1:2889:3889
server.3=127.0.0.1:2890:3890
```

`zoo-3.cfg`

```bash
tickTime=2000
initLimit=10
syncLimit=5
dataDir=C:/zookeeper/data/zoo-3/
clientPort=2183
dataLogDir=C:/zookeeper/log/zoo-3/
server.1=127.0.0.1:2888:3888
server.2=127.0.0.1:2889:3889
server.3=127.0.0.1:2890:3890
```

`zkServer-1.cmd`

```bash
setlocal
call "%~dp0zkEnv.cmd"

set ZOOCFG=C:\zookeeper\conf\zoo-1.cfg # 添加修改到这里
set ZOOMAIN=org.apache.zookeeper.server.quorum.QuorumPeerMain

echo on
call %JAVA% "-Dzookeeper.log.dir=%ZOO_LOG_DIR%" "-Dzookeeper.root.logger=%ZOO_LOG4J_PROP%" "-Dzookeeper.log.file=%ZOO_LOG_FILE%" "-XX:+HeapDumpOnOutOfMemoryError" "-XX:OnOutOfMemoryError=cmd /c taskkill /pid %%%%p /t /f" -cp "%CLASSPATH%" %ZOOMAIN% "%ZOOCFG%" %*

endlocal
```

## 命令

### `get`

`get [-s] [-w] path`

```bash
[zk: 127.0.0.1:2181(CONNECTED) 6] get -s /

cZxid = 0x0 # ZNODE节点创建时的Zxid事务id
ctime = Thu Jan 01 08:00:00 CST 1970 # ZNODE创建时的时间戳
mZxid = 0x0 # 节点修改时的事务id，与子节点无关
mtime = Thu Jan 01 08:00:00 CST 1970 # ZNODE最新一次更新时的时间戳
pZxid = 0x100000004 # ZNODE节点的子节点的最后一次修改的事务id，与孙节点无关（添加/删除子节点，修改子节点数据内容不影响）
cversion = 0 #子节点版本号
dataVersion = 0 # 数据版本号
aclVersion = 0 # 节点的ACL权限修改版本号
ephemeralOwner = 0x0 # 
dataLength = 0 # 该节点的数据长度
numChildren = 2 # 该节点拥有子节点的数量
```

### `create`

`create [-s] [-e] [-c] [-t ttl] path [data] [acl]`

```bash
[zk: 127.0.0.1:2181(CONNECTED) 3] create /zk data
Created /zk
```

### `delete`

`delete [-v version] path`

```bash
[zk: 127.0.0.1:2181(CONNECTED) 11] delete /zk
[zk: 127.0.0.1:2181(CONNECTED) 12] ls /
[zookeeper]
```

### `set`

`set [-s] [-v version] path data`

```bash
[zk: 127.0.0.1:2181(CONNECTED) 15] set -s /zk data2
cZxid = 0x100000006
ctime = Tue Oct 20 21:37:46 CST 2020
mZxid = 0x100000007
mtime = Tue Oct 20 21:38:18 CST 2020
pZxid = 0x100000006
cversion = 0
dataVersion = 1
aclVersion = 0
ephemeralOwner = 0x0
dataLength = 5
numChildren = 0
```

## 节点

### PERSISTENT

持久化节点

所谓持久节点是指在节点创建后就一直存在，直到有删除操作来主动清除这个节点。持久化节点的生命周期是永久有效的，不会因为创建该节点的客户端会话失效而消失。

### PERSISTENT_SEQUENTIAL

持久化顺序节点

这类节点的生命周期和持久节点是一致的。额外的特性是，在`ZooKeeper`中，每个父节点会它的第一级子节点维护一份顺序编码，会记录每个子节点创建的先后顺序。如果在创建子节点的时候，可以设置这个属性，那么在创建节点过程中，`ZooKeeper`会自动为给定节点名加上一个表示顺序的数字后缀来作为新的节点名。这个顺序后缀数字的数值上限是整型的最大值。
例如，在创建节点时只需要传入节点`/test_`, `ZooKeeper`自动会在`test_`后面补充数字顺序。

### PHEMERAL

临时节点

和持久节点不同的是，临时节点的生命周期和客户端会话绑定。也就是说，如果客户端会话失效了，那么这个节点就会自动被清除掉。注意，这里提到的是会话失效，而非连接断开。还要注意一件事，就是当客户端会话失效后，所产生的节点也不是一下子就消失了，也要过一段时间，大概是10秒以内。大家可以试一下，本机操作生成节点，在服务器端用命令来查看当前的节点数目，我们会发现有些客户端的状态已经是stop（中止），但是产生的节点还在。
另外，在临时节点下面不能创建子节点。

### EPHEMERAL_SEQUENTIAL

临时顺序节点

此节点是属于临时节点，不过带有顺序编号，客户端会话结束，所产生的节点就会消失。

## 参考文献

《Netty、Redis、Zookeeper高并发实践》

