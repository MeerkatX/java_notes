# Netty学习代码

## Netty优点：
- API使用简单，开发门槛低。
- 功能强大，预置了多种编解码功能，支持多种主流协议。
- 定制能力强，可以通过ChannelHandler对通信框架进行灵活扩展。
- 性能高，与其他业界主流的NIO框架对比，Netty的综合性能最优。
- 成熟、稳定，Netty修复了已经发现的所有JDK NIO中的BUG，业务开发人员不需要再为NIO的BUG而烦恼。
- 社区活跃，版本迭代周期短，发现的BUG可以被及时修复。

![netty架构](doc/netty%E6%A1%86%E6%9E%B6.png)

----

Netty样板代码：`firstexample`包下

## Channel

> 生命周期回调

- handlerAdded() ：当业务处理器被加入到流水线后，此方法被回调。也就是在完成ch.pipeline().addLast(handler)语句之后，会回调handlerAdded()。可以在后面源码分析中看到
- channelRegistered()：当通道成功绑定一个NioEventLoop线程后，会通过流水线回调所有业务处理器的channelRegistered()方法。
- channelActive()：当通道激活成功后，会通过流水线回调所有业务处理器的channelActive()方法。通道激活成功指的是，所有的业务处理器添加、注册的异步任务完成，并且NioEventLoop线程绑定的异步任务完成。
- channelInactive()：当通道的底层连接已经不是ESTABLISH状态，或者底层连接已经关闭时，会首先回调所有业务处理器的channelInactive()方法。
- channelUnregistered()：通道和NioEventLoop线程解除绑定，移除掉对这条通道的事件处理之后，回调所有业务处理器的channelUnregistered ()方法。
- handlerRemoved()：最后，Netty会移除掉通道上所有的业务处理器，并且回调所有的业务处理器的handlerRemoved()方法。

> 入站、出站处理回调

- channelRead()：有数据包入站，通道可读。流水线会启动入站处理流程，从前向后，入站处理器的channelRead()方法会被依次回调到。
- channelReadComplete()：流水线完成入站处理后，会从前向后，依次回调每个入站处理器的channelReadComplete()方法，表示数据读取完毕。

> ChannelHandler

入站、出站

>  AbstrachChannel

```java
protected AbstractChannel(Channel parent) {
    this.parent = parent;
    id = newId();
    unsafe = newUnsafe();//每个channel需要一个unsafe实例
    pipeline = newChannelPipeline();//每个channel内部都会创建一个pipline
}
```

Netty 中的 Unsafe 的意思，它封装了 Netty 中会使用到的 JDK 提供的 NIO 接口，比如将 channel 注册到 selector 上，比如 bind 操作，比如 connect 操作等，这些操作都是稍微偏底层一些。Netty 同样也是不希望我们的业务代码使用 Unsafe 的实例，它是提供给 Netty 中的源码使用的。

> DefaultChannelPipeline

pipeline = newChannelPipeline();

```java
protected DefaultChannelPipeline(Channel channel) {
    this.channel = ObjectUtil.checkNotNull(channel, "channel");
    succeededFuture = new SucceededChannelFuture(channel, null);
    voidPromise =  new VoidChannelPromise(channel, true);
    //实例化了两个handler tail实现了ChannelInboundHandler 
    tail = new TailContext(this);
    //head实现了 ChannelOutboundHandler 和 ChannelInboundHandler 
    head = new HeadContext(this);

    head.next = tail;
    tail.prev = head;
}
```

[Netty 源码解析（三）: Netty 的 Future 和 Promise](https://www.cnblogs.com/yuandengta/p/12800131.html)

### Future 和 Promise

**有两种编程方式，一种是用 await()，等 await() 方法返回后，得到 promise 的执行结果，然后处理它；另一种就是提供 Listener 实例，我们不太关心任务什么时候会执行完，只要它执行完了以后会去执行 listener 中的处理方法就行。**

这两种方式真正完成了异步，Guava异步也是有两种方式来完成，而 Java 的 FutureTask 只完成了一半（即阻塞get）

Promise、Guava和 FutureTask 不同点在于：

- Promise和Guava是非阻塞的异步回调，调用线程是不阻塞的，可以继续执行自己的业务逻辑。也可以调用阻塞方法来获取结果
- FutureTask是阻塞的异步回调，调用线程是阻塞的，在获取异步结果的过程中，一直阻塞等待异步线程返回结果。后来添加了CompletableFuture来实现真正的非阻塞异步回调。

Promise的await方法通过sync + this.wait() + waiters(仅计数用)

```java
public interface ChannelPromise extends ChannelFuture, Promise<Void> {

    @Override
    Channel channel();

    @Override
    ChannelPromise setSuccess(Void result);

    ChannelPromise setSuccess();

    boolean trySuccess();

    @Override
    ChannelPromise setFailure(Throwable cause);

    @Override
    ChannelPromise addListener(GenericFutureListener<? extends Future<? super Void>> listener);

    @Override
    ChannelPromise addListeners(GenericFutureListener<? extends Future<? super Void>>... listeners);

    @Override
    ChannelPromise removeListener(GenericFutureListener<? extends Future<? super Void>> listener);

    @Override
    ChannelPromise removeListeners(GenericFutureListener<? extends Future<? super Void>>... listeners);

    @Override
    ChannelPromise sync() throws InterruptedException;

    @Override
    ChannelPromise syncUninterruptibly();

    @Override
    ChannelPromise await() throws InterruptedException;

    @Override
    ChannelPromise awaitUninterruptibly();

    /**
     * Returns a new {@link ChannelPromise} if {@link #isVoid()} returns {@code true} otherwise itself.
     */
    ChannelPromise unvoid();
}
```

### DefaultPromise

```java
public class DefaultPromise<V> extends AbstractFuture<V> implements Promise<V> {
    // 保存执行结果
    private volatile Object result;
    
    // 执行任务的线程池，promise 持有 executor 的引用
    private final EventExecutor executor;
    
      // 监听者，回调函数，任务结束后（正常或异常结束）执行，这个底层用类封装，采用数组保存listeners
    private Object listeners;
    //    listeners = new DefaultFutureListeners((GenericFutureListener<?>) listeners, listener);
    //    private GenericFutureListener<? extends Future<?>>[] listeners;
    //    当达到监听者数组长度最大值，扩容 this.listeners = listeners = Arrays.copyOf(listeners, size << 1); 两倍
    //    listeners[size] = l;  "l" 即传入的listeners

    // 等待这个 promise 的线程数(调用sync()/await()进行等待的线程数量)
    private short waiters;

    // 是否正在唤醒等待线程，用于防止重复执行唤醒，不然会重复执行 listeners 的回调方法
    private boolean notifyingListeners;
    ......
}
```

setSuccess和trySuccess区别：

```java
@Override
public Promise<V> setSuccess(V result) {
    if (setSuccess0(result)) {//setSuccess0会调用 notifyListeners(); 主题通知所有监听者
        return this;
    }
    throw new IllegalStateException("complete already: " + this);
}

@Override
public boolean trySuccess(V result) {
    return setSuccess0(result);
}

@Override
public Promise<V> setFailure(Throwable cause) {
    if (setFailure0(cause)) {
        return this;
    }
    throw new IllegalStateException("complete already: " + this, cause);
}

@Override
public boolean tryFailure(Throwable cause) {
    return setFailure0(cause);
}
```

上面几个方法都非常简单，先设置好值，然后执行监听者们的回调方法。notifyListeners() 方法感兴趣的读者也可以看一看，不过它还涉及到 Netty 线程池的一些内容，我们还没有介绍到线程池，这里就不展开了。上面的代码，在 setSuccess0 或 setFailure0 方法中都会唤醒阻塞在 sync() 或 await() 的线程另外，就是可以看下 sync() 和 await() 的区别，其他的我觉得随便看看就好了。

```java
@Override
public Promise<V> sync() throws InterruptedException {
    await();
    rethrowIfFailed();//如果任务失败重新抛出相关异常
    return this;
}
```

```java
@Override
public Promise<V> await() throws InterruptedException {
    if (isDone()) {
        return this;
    }

    if (Thread.interrupted()) {
        throw new InterruptedException(toString());
    }

    checkDeadLock();

    synchronized (this) {//获取锁
        while (!isDone()) {//如果没有完成
            incWaiters();
            try {
                wait();//这里是阻塞了调用类 this.wait()，与java future不同
            } finally {
                decWaiters();
            }
        }
    }
    return this;
}
```

## bind/ connect 

一切的开始：

```java
//AbstractBootstrap 类

private ChannelFuture doBind(final SocketAddress localAddress) {
    final ChannelFuture regFuture = initAndRegister();//新建channel和注册到selector
    final Channel channel = regFuture.channel();
    if (regFuture.cause() != null) {
        return regFuture;
    }

    if (regFuture.isDone()) {
        // At this point we know that the registration was complete and successful.
        ChannelPromise promise = channel.newPromise();
        //前面的处理都完成了，即新建了NioServerSocketChannel和注册到Selector中后
        //绑定监听地址 通过pipline （AbstractChannel中源码）来完成 这是一个出站事件从管道tail到head 其中最后在head
        //即 final class HeadContext extends AbstractChannelHandlerContext
        //调用了connect方法 层层传递到了java 原生方法
        //javaChannel().bind(localAddress, config.getBacklog());
        doBind0(regFuture, channel, localAddress, promise);
        
        return promise;
    } else {
        // Registration future is almost always fulfilled already, but just in case it's not.
        final PendingRegistrationPromise promise = new PendingRegistrationPromise(channel);
        regFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                Throwable cause = future.cause();
                if (cause != null) {
                    // Registration on the EventLoop failed so fail the ChannelPromise directly to not cause an
                    // IllegalStateException once we try to access the EventLoop of the Channel.
                    promise.setFailure(cause);
                } else {
                    // Registration was successful, so set the correct executor to use.
                    // See https://github.com/netty/netty/issues/2586
                    promise.registered();

                    doBind0(regFuture, channel, localAddress, promise);
                }
            }
        });
        return promise;
    }
}
```

在上面的 doConnect/Bind 方法中，我们看到它在调用底层的 connect/bind 方法后，会设置 interestOps 为 SelectionKey.OP_CONNECT/OP_ACCEPT。剩下的就是 NioEventLoop 的事情了，还记得 NioEventLoop 的 run() 方法吗？也就是说这里的 connect 成功以后，这个 TCP 连接就建立起来了，后续的操作会在 NioEventLoop.run() 方法中被 processSelectedKeys() 方法处理掉。

## 线程和Channel

eventloopgroup相当于是线程池，eventloop相当于线程

Reactor模式中 Eventloop相当于Reactor类 事件轮询在 run方法中

Eventloop与Channel是一对多的关系、Selector是一对一关系、线程是一对一关系，一个反应器`reactor`可以注册多个通道`channel` 

#### MultithreadEventExecutorGroup

创建事件循环组，调用父类构造方法 MultithreadEventExecutorGroup：

```java
protected MultithreadEventExecutorGroup(int nThreads, Executor executor,
                                            EventExecutorChooserFactory chooserFactory, Object... args) {
        if (nThreads <= 0) {
            throw new IllegalArgumentException(String.format("nThreads: %d (expected: > 0)", nThreads));
        }

        if (executor == null) {
            // 为每个任务新建一个线程 Executor接口 Runnable 命令模式
            executor = new ThreadPerTaskExecutor(newDefaultThreadFactory());
            //实现类 ThreadPerTaskExecutor 的逻辑就是每来一个任务，新建一个线程。
        }

        children = new EventExecutor[nThreads];//相当于线程数组

        for (int i = 0; i < nThreads; i ++) {
            boolean success = false;
            try {
                children[i] = newChild(executor, args);//对每个执行器进行实例化 即开线程
                success = true;
            } catch (Exception e) {
                // TODO: Think about if this is a good exception type
                throw new IllegalStateException("failed to create a child event loop", e);
            } finally {
                //如果创建线程失败的处理
                if (!success) {
                    for (int j = 0; j < i; j ++) {
                        children[j].shutdownGracefully();
                    }

                    for (int j = 0; j < i; j ++) {
                        EventExecutor e = children[j];
                        try {
                            while (!e.isTerminated()) {
                                e.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);
                            }
                        } catch (InterruptedException interrupted) {
                            // Let the caller handle the interruption.
                            Thread.currentThread().interrupt();
                            break;
                        }
                    }
                }
            }
        }

    	//创建成功后 从线程池中选择一个线程的选择策略
        chooser = chooserFactory.newChooser(children); // DefaultEventExecutorChooserFactory

    	//设置一个Listener用来监听该线程池的termination事件
		//下面的代码逻辑是：给池中每一个线程都设置这个 listener，当监听到所有线程都 terminate 以后，
    	//这个线程池就算真正的 terminate 了。
        final FutureListener<Object> terminationListener = new FutureListener<Object>() {
            @Override
            public void operationComplete(Future<Object> future) throws Exception {
                if (terminatedChildren.incrementAndGet() == children.length) {
                    terminationFuture.setSuccess(null);
                }
            }
        };

        for (EventExecutor e: children) {
            e.terminationFuture().addListener(terminationListener);
        }

        Set<EventExecutor> childrenSet = new LinkedHashSet<EventExecutor>(children.length);
        Collections.addAll(childrenSet, children);
        readonlyChildren = Collections.unmodifiableSet(childrenSet);
    }
```

关键的 newChild 方法：

NioEventLoopGroup类中重写

```java
@Override
protected EventLoop newChild(Executor executor, Object... args) throws Exception {
    EventLoopTaskQueueFactory queueFactory = args.length == 4 ? (EventLoopTaskQueueFactory) args[3] : null;
    //调用NioEventLoop构造方法
    return new NioEventLoop(this, executor, (SelectorProvider) args[0],
        ((SelectStrategyFactory) args[1]).newSelectStrategy(), (RejectedExecutionHandler) args[2], queueFactory);
}
```

NioEventLoop 其实就是一个线程

```java
//线程池 NioEventLoopGroup 是池中的线程 NioEventLoop 的 parent

NioEventLoop(NioEventLoopGroup parent, Executor executor, SelectorProvider selectorProvider,
             SelectStrategy strategy, RejectedExecutionHandler rejectedExecutionHandler,
             EventLoopTaskQueueFactory queueFactory) {
    //父类构造器
    super(parent, executor, false, newTaskQueue(queueFactory), newTaskQueue(queueFactory),
            rejectedExecutionHandler);
    this.provider = ObjectUtil.checkNotNull(selectorProvider, "selectorProvider");
    this.selectStrategy = ObjectUtil.checkNotNull(strategy, "selectStrategy");
    final SelectorTuple selectorTuple = openSelector();// 开启 NIO 中最重要的组件：Selector 
    // 每个 NioEventLoop 都有自己的 Selector
    this.selector = selectorTuple.selector;
    this.unwrappedSelector = selectorTuple.unwrappedSelector;
}
```

调用的父类的父类构造器 SingleThreadEventExecutor ：

```java
protected SingleThreadEventExecutor(EventExecutorGroup parent, Executor executor,
                                    boolean addTaskWakesUp, Queue<Runnable> taskQueue,
                                    RejectedExecutionHandler rejectedHandler) {
    super(parent);
    this.addTaskWakesUp = addTaskWakesUp;
    this.maxPendingTasks = DEFAULT_MAX_PENDING_EXECUTOR_TASKS;
    this.executor = ThreadExecutorMap.apply(executor, this); //之前实例化的 ThreadPerTaskExecutor
    
    //taskQueue，这个东西很重要，提交给NioEventLoop的任务都会进入到这个taskQueue中等待被执行
	//这个queue的默认容量是16
    this.taskQueue = ObjectUtil.checkNotNull(taskQueue, "taskQueue");
    this.rejectedExecutionHandler = ObjectUtil.checkNotNull(rejectedHandler, "rejectedHandler");
/*
taskQueue：这算是该构造方法中新的东西，它是任务队列。我们前面说过，NioEventLoop 需要负责 IO 事件和非 IO 事件，通常它都在执行 selector 的 select 方法或者正在处理 selectedKeys，如果我们要 submit 一个任务给它，任务就会被放到 taskQueue 中，等它来轮询。该队列是线程安全的 LinkedBlockingQueue，默认容量为 16。

rejectedExecutionHandler：taskQueue 的默认容量是 16，所以，如果 submit 的任务堆积了到了 16，再往里面提交任务会触发 rejectedExecutionHandler 的执行策略。
*/

}
```

上面并没有真正创建 NioEventLoop 中的线程（没有创建 Thread 实例）创建线程的时机在第一个任务提交过来的时候 即Channel 的register操作时创建。

#### initAndRegister

从bind方法出发可以找到initAndRegister

这是梦开始的地方

```java
final ChannelFuture initAndRegister() {
    Channel channel = null;
    try {   
        
        channel = channelFactory.newChannel();//channel实例化 拥有pipline 和 unsafe 以及 pipline有head和tail
        
        
        init(channel);//往pipline中添加handler 此时pipline是 head + channelInitializer + tail
        /*
        当前 channel 中的一些情况：实例化了 JDK 底层的 Channel，设置了非阻塞，实例化了 Unsafe，
        实例化了 Pipeline，同时往 pipeline 中添加了 head、tail 以及一个 ChannelInitializer 实例。
        */
   
    } catch (Throwable t) {
        //失败策略
		//...
    }

    
    ChannelFuture regFuture = config().group().register(channel);
    //config().group() 方法会返回前面实例化的 NioEventLoopGroup 的实例 调用register方法
    
    
    if (regFuture.cause() != null) {
        if (channel.isRegistered()) {
            channel.close();
        } else {
            channel.unsafe().closeForcibly();
        }
    }

    // If we are here and the promise is not failed, it's one of the following cases:
    // 1) If we attempted registration from the event loop, the registration has been completed at this point.
    //    i.e. It's safe to attempt bind() or connect() now because the channel has been registered.
    // 2) If we attempted registration from the other thread, the registration request has been successfully
    //    added to the event loop's task queue for later execution.
    //    i.e. It's safe to attempt bind() or connect() now:
    //         because bind() or connect() will be executed *after* the scheduled registration task is executed
    //         because register(), bind(), and connect() are all bound to the same thread.
// 源码中说得很清楚，如果到这里，说明后续可以进行 connect() 或 bind() 了，因为两种情况：
// 1. 如果 register 动作是在 eventLoop 中发起的，那么到这里的时候，register 一定已经完成
// 2. 如果 register 任务已经提交到 eventLoop 中，也就是进到了 eventLoop 中的 taskQueue 中，
//    由于后续的 connect 或 bind 也会进入到同一个 eventLoop 的 queue 中，所以一定是会先 register 成功，才会执行 connect 或 bind
    return regFuture;
}
```

上面的**init**方法调用：ServerBootstrap下：


```java
@Override
void init(Channel channel) {
    setChannelOptions(channel, newOptionsArray(), logger);
    setAttributes(channel, attrs0().entrySet().toArray(EMPTY_ATTRIBUTE_ARRAY));

    ChannelPipeline p = channel.pipeline();
    
    final EventLoopGroup currentChildGroup = childGroup;
    final ChannelHandler currentChildHandler = childHandler;
    final Entry<ChannelOption<?>, Object>[] currentChildOptions;
    synchronized (childOptions) {
        currentChildOptions = childOptions.entrySet().toArray(EMPTY_OPTION_ARRAY);
    }
    final Entry<AttributeKey<?>, Object>[] currentChildAttrs = childAttrs.entrySet().toArray(EMPTY_ATTRIBUTE_ARRAY);
    
    p.addLast(new ChannelInitializer<Channel>() {
        @Override
        public void initChannel(final Channel ch) {
            final ChannelPipeline pipeline = ch.pipeline();
            ChannelHandler handler = config.handler();
            if (handler != null) {
                pipeline.addLast(handler);
            }
    
            ch.eventLoop().execute(new Runnable() {
                @Override
                public void run() {
                    // 添加一个 handler 到 pipeline 中：ServerBootstrapAcceptor
                    // 从名字可以看到，这个 handler 的目的是用于接收客户端请求
                    pipeline.addLast(new ServerBootstrapAcceptor(
                            ch, currentChildGroup, currentChildHandler, currentChildOptions, currentChildAttrs));
                }
            });
        }
    });
}
```

pipeline 中的辅助类 ChannelInitializer，我们看到，它本身是一个 handler（Inbound 类型），但是它的作用和普通 handler 有点不一样，它纯碎是用来辅助将其他的 handler 加入到 pipeline 中的。之后完成pipline增加handler之后会移除该ChannelInitializer



> MultithreadEventLoopGroup中的**register**方法（NioEventLoopGroup父类）


```java
@Override
public ChannelFuture register(Channel channel) {
    return next().register(channel);
}
```

**next()** 方法很简单，就是选择线程池中的一个线程（还记得 chooserFactory 吗），也就是选择一个 NioEventLoop 实例，这个时候我们就进入到 NioEventLoop 了。NioEventLoop 的 register(channel) 方法实现在它的父类 SingleThreadEventLoop 中

```java
@Override
public ChannelFuture register(final ChannelPromise promise) {
    ObjectUtil.checkNotNull(promise, "promise");
    promise.channel().unsafe().register(this, promise);
    // promise 关联了 channel，channel 持有 Unsafe 实例，register 操作就封装在 Unsafe 中
    return promise;

}
```

这几步涉及到了好几个类：NioEventLoop、Promise、Channel、Unsafe 等，大家要仔细理清楚它们的关系。对于我们前面过来的 register 操作，其实提交到 eventLoop 以后，就直接返回 promise 实例了，剩下的register0 是异步操作，它由 NioEventLoop 实例来完成。

#### AbstractChannel


```java
@Override
public final void register(EventLoop eventLoop, final ChannelPromise promise) {
    ObjectUtil.checkNotNull(eventLoop, "eventLoop");
    if (isRegistered()) {
        promise.setFailure(new IllegalStateException("registered to an event loop already"));
        return;
    }
    if (!isCompatible(eventLoop)) {
        promise.setFailure(
                new IllegalStateException("incompatible event loop type: " + eventLoop.getClass().getName()));
        return;
    }



	// 将这个 eventLoop 实例设置给这个 channel，从此这个 channel 就是有 eventLoop 的了
    // 我觉得这一步其实挺关键的，因为后续该 channel 中的所有异步操作，都要提交给这个 eventLoop 来执行
    AbstractChannel.this.eventLoop = eventLoop;
	
    if (eventLoop.inEventLoop()) {
        // 如果发起 register 动作的线程就是 eventLoop 实例中的线程，那么直接调用 register0(promise)
     	// 对于我们来说，它不会进入到这个分支，
    	// 之所以有这个分支，是因为我们是可以 unregister，然后再 register 的，后面再仔细看
        register0(promise);
    } else {
        try {
            
            
            // 否则，提交任务给 eventLoop，eventLoop 中的线程会负责调用 register0(promise)
            // execute负责开线程，这里才正式打开了一个线程
            eventLoop.execute(new Runnable() {
                @Override
                public void run() {
                    register0(promise);
                }
            });
        } catch (Throwable t) {
			//异常处理 ...
        }
    }
}
```

```java
private void execute(Runnable task, boolean immediate) {
    boolean inEventLoop = inEventLoop();
    addTask(task);//添加任务到任务队列中（register归入非IO操作）
    if (!inEventLoop) {//判断添加任务的线程是不是当前eventloop中的线程
        
        startThread();//如果不是NioEventLoop内部线程提交的task，判断是否启动，没有启动线程
        
        if (isShutdown()) {
            boolean reject = false;
            try {
                if (removeTask(task)) {
                    reject = true;
                }
            } catch (UnsupportedOperationException e) {
                // The task queue does not support removal so the best thing we can do is to just move on and
                // hope we will be able to pick-up the task before its completely terminated.
                // In worst case we will log on termination.
            }
            if (reject) {
                reject();
            }
        }
    }

    if (!addTaskWakesUp && immediate) {
        wakeup(inEventLoop);
    }
}
```

```java
private void startThread() {
    if (state == ST_NOT_STARTED) {
        //判断线程是否启动
        if (STATE_UPDATER.compareAndSet(this, ST_NOT_STARTED, ST_STARTED)) {
            //设置线程启动状态
            boolean success = false;
            try {
                doStartThread();//这一段开始调用executor开启线程 见下文
                success = true;
            } finally {
                if (!success) {
                    STATE_UPDATER.compareAndSet(this, ST_STARTED, ST_NOT_STARTED);
                }
            }
        }
    }
}
```

```java
private void doStartThread() {
    assert thread == null;
    
    //即之前传入的 ThreadPerTaskExecutor 一旦调用execute就会创建一个新线程
    /*
    ThreadPerTaskExecutor 下：
    @Override
    public void execute(Runnable command) {
        threadFactory.newThread(command).start();
    }
    */
    executor.execute(new Runnable() {
        @Override
        public void run() {
            
            // 将 “executor” 中创建的这个线程设置为 NioEventLoop 的线程！！！
            thread = Thread.currentThread();
            if (interrupted) {
                thread.interrupt();
            }

            boolean success = false;
            updateLastExecutionTime();
            try {
                // 执行 SingleThreadEventExecutor 的 run() 方法，它在 NioEventLoop 中实现了
                SingleThreadEventExecutor.this.run();
                success = true;
                
            } catch (Throwable t) {
                logger.warn("Unexpected exception from an event executor: ", t);
            } finally {
            	//...
            }
        }
    });
}
```

#### NioEventLoop.run()

关键中的关键

```java

//类似java线程池中worker，循环获取新任务，不断做select操作和轮询taskQueue队列
@Override
protected void run() {
    int selectCnt = 0;
    for (;;) {
        //死循环 即事件循环
        try {
            int strategy;
            try {
              /*
                selectStrategy 终于要派上用场了
                它有两个值，一个是 CONTINUE 一个是 SELECT
                针对这块代码，我们分析一下。
                1. 如果 taskQueue 不为空，也就是 hasTasks() 返回 true，
                那么执行一次 selectNow()，该方法不会阻塞
                2. 如果 hasTasks() 返回 false，那么执行 SelectStrategy.SELECT 分支，
                进行 select(...)，这块是带阻塞的
                这个很好理解，就是按照是否有任务在排队来决定是否可以进行阻塞
            */
                strategy = selectStrategy.calculateStrategy(selectNowSupplier, hasTasks());
                switch (strategy) {
                case SelectStrategy.CONTINUE:
                    continue;

                case SelectStrategy.BUSY_WAIT:
                    // fall-through to SELECT since the busy-wait is not supported with NIO

                case SelectStrategy.SELECT:
                    long curDeadlineNanos = nextScheduledTaskDeadlineNanos();
                    if (curDeadlineNanos == -1L) {
                        curDeadlineNanos = NONE; // nothing on the calendar
                    }
                    nextWakeupNanos.set(curDeadlineNanos);
                    try {
                        if (!hasTasks()) {
                            // 如果 !hasTasks()，那么进到这个 select 分支，这里 select 带阻塞的
                            strategy = select(curDeadlineNanos);
                            
                            
/*
    private int select(long deadlineNanos) throws IOException {
        if (deadlineNanos == NONE) {
            return selector.select(); //selector进行选择，阻塞
        }
        //如果有定时，就走下面这个分支
        // Timeout will only be 0 if deadline is within 5 microsecs
        long timeoutMillis = deadlineToDelayNanos(deadlineNanos + 995000L) / 1000000L;
        return timeoutMillis <= 0 ? selector.selectNow() : selector.select(timeoutMillis);
    }
                            
*/
                        }
                    } finally {
                        // This update is just to help block unnecessary selector wakeups
                        // so use of lazySet is ok (no race condition)
                        nextWakeupNanos.lazySet(AWAKE);
                    }
                    // fall through
                default:
                }
            } catch (IOException e) {
                // If we receive an IOException here its because the Selector is messed up. Let's rebuild
                // the selector and retry. https://github.com/netty/netty/issues/8566
                rebuildSelector0();
                selectCnt = 0;
                handleLoopException(e);
                continue;
            }

            selectCnt++;
            cancelledKeys = 0;
            needsToSelectAgain = false;
            final int ioRatio = this.ioRatio; // 默认地，ioRatio 的值是 50
            boolean ranTasks;
            if (ioRatio == 100) {
                // 如果 ioRatio 设置为 100，那么先执行 IO 操作，然后在 finally 块中执行 taskQueue 中的任务
                try {
                    // 1. 执行 IO 操作。因为前面 select 以后，可能有些 channel 是需要处理的。
                    if (strategy > 0) {
                        processSelectedKeys();
                    }
                } finally {
                    // Ensure we always run tasks. // 2. 执行非 IO 任务，也就是 taskQueue 中的任务
                    ranTasks = runAllTasks();
                }
            } else if (strategy > 0) {
                // 如果 ioRatio 不是 100，那么根据 IO 操作耗时，限制非 IO 操作耗时
                final long ioStartTime = System.nanoTime();
                try {
                    processSelectedKeys(); // 执行 IO 操作
                } finally {
                    // Ensure we always run tasks.
                    // 根据 IO 操作消耗的时间，计算执行非 IO 操作（runAllTasks）可以用多少时间.
                    final long ioTime = System.nanoTime() - ioStartTime;
                    ranTasks = runAllTasks(ioTime * (100 - ioRatio) / ioRatio);
                }
            } else {
                ranTasks = runAllTasks(0); // This will run the minimum number of tasks
            }

            if (ranTasks || strategy > 0) {
                if (selectCnt > MIN_PREMATURE_SELECTOR_RETURNS && logger.isDebugEnabled()) {
                    logger.debug("Selector.select() returned prematurely {} times in a row for Selector {}.",
                            selectCnt - 1, selector);
                }
                selectCnt = 0;
            } else if (unexpectedSelectorWakeup(selectCnt)) { // Unexpected wakeup (unusual case)
                selectCnt = 0;
            }
        } catch (CancelledKeyException e) {
            // Harmless exception - log anyway
            if (logger.isDebugEnabled()) {
                logger.debug(CancelledKeyException.class.getSimpleName() + " raised by a Selector {} - JDK bug?",
                        selector, e);
            }
        } catch (Throwable t) {
            handleLoopException(t);
        }
        // Always handle shutdown even if the loop processing threw an exception.
        try {
            if (isShuttingDown()) {
                closeAll();
                if (confirmShutdown()) {
                    return;
                }
            }
        } catch (Throwable t) {
            handleLoopException(t);
        }
    }
}
```

1. 首先，会根据 hasTasks() 的结果来决定是执行 selectNow() 还是 select(oldWakenUp)，这个应该好理解。如果有任务正在等待，那么应该使用无阻塞的 selectNow()，如果没有任务在等待，那么就可以使用带阻塞的 select 操作。
2. ioRatio 控制 IO 操作所占的时间比重：

- 如果设置为 100%，那么先执行 IO 操作，然后再执行任务队列中的任务。
- 如果不是 100%，那么先执行 IO 操作，然后执行 taskQueue 中的任务，但是需要控制执行任务的总时间。也就是说，非 IO 操作可以占用的时间，通过 ioRatio 以及这次 IO 操作耗时计算得出。

#### register0

```java
//这一切是在一个eventloop的task中执行的 register0即一个task
private void register0(ChannelPromise promise) {
    try {
        // check if the channel is still open as it could be closed in the mean time when the register
        // call was outside of the eventLoop
        if (!promise.setUncancellable() || !ensureOpen(promise)) {
            return;
        }
        boolean firstRegistration = neverRegistered;
        
        
        doRegister(); //    将channel注册到selector上 
        //监听集合设置为0说明什么都不监听
        //selectionKey = javaChannel().register(eventLoop().unwrappedSelector(), 0, this);
        
        // 附 JDK 中 Channel 的 register 方法： channel.register(selector,SelectionKey.READ)
        // public final SelectionKey register(Selector sel, int ops, Object att) {...}
        
        
        neverRegistered = false;
        registered = true;
        
        // 到这里，就算是 registered 了

        // 这一步也很关键，因为这涉及到了 ChannelInitializer 的 init(channel)
        // 我们之前说过，init 方法会将 ChannelInitializer 内部添加的 handlers 添加到 pipeline 中

        // Ensure we call handlerAdded(...) before we actually notify the promise. This is needed as the
        // user may already fire events through the pipeline in the ChannelFutureListener.
        pipeline.invokeHandlerAddedIfNeeded();
    
         // 设置当前 promise 的状态为 success
        //  因为当前 register 方法是在 eventLoop 中的线程中执行的，需要通知提交 register 操作的线程
        safeSetSuccess(promise);
        
        // 当前的 register 操作已经成功，该事件应该被 pipeline 上
        // 所有关心 register 事件的 handler 感知到，往 pipeline 中扔一个事件
        pipeline.fireChannelRegistered();
        
        
        // 打开channel
        // Only fire a channelActive if the channel has never been registered. This prevents firing
        // multiple channel actives if the channel is deregistered and re-registered.
        if (isActive()) {
            if (firstRegistration) {
                //如果channel第一次执行register，fire channelactive事件（监听器模式）
                pipeline.fireChannelActive();
            } else if (config().isAutoRead()) {
                
                // This channel was registered before and autoRead() is set. This means we need to begin read
                // again so that we process inbound data.
                //
                // See https://github.com/netty/netty/issues/4805
                
                // 该 channel 之前已经 register 过了，
                // 这里让该 channel 立马去监听通道中的 OP_READ 事件 绑定read事件
                beginRead();
                
/*
AbstractNioChannel下：
	@Override
    protected void doBeginRead() throws Exception {
        // Channel.read() or ChannelHandlerContext.read() was called
        final SelectionKey selectionKey = this.selectionKey;
        if (!selectionKey.isValid()) {
            return;
        }

        readPending = true;

        final int interestOps = selectionKey.interestOps();
        if ((interestOps & readInterestOp) == 0) {
            selectionKey.interestOps(interestOps | readInterestOp);//绑定监听read事件
        }
    }
*/
      
            }
        }
    } catch (Throwable t) {
        // Close the channel directly to avoid FD leak.
        closeForcibly();
        closeFuture.setClosed();
        safeSetFailure(promise, t);
    }
}
```
##### pipeline.fireChannelRegistered()

```java
//DefaultChannelPipeline
	@Override
    public final ChannelPipeline fireChannelRegistered() {
        AbstractChannelHandlerContext.invokeChannelRegistered(head);//传入的是pipline的头
        return this;
    }
```

我们往 pipeline 中扔了一个 channelRegistered 事件，这里的 register 属于 Inbound 事件，pipeline 接下来要做的就是执行 pipeline 中的 Inbound 类型的 handlers 中的 channelRegistered() 方法。

从上面的代码，我们可以看出，往 pipeline 中扔出 channelRegistered 事件以后，第一个处理的 handler 是 head。

```java
//AbstractChannelHandlerContext

    static void invokeChannelRegistered(final AbstractChannelHandlerContext next) {
        EventExecutor executor = next.executor();
        if (executor.inEventLoop()) { 
            next.invokeChannelRegistered();
        } else {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    //调用head的注册方法 放到 NioEventLoop 中的 taskQueue 中执行的
                    next.invokeChannelRegistered();
                }
            });
        }
    }

    private void invokeChannelRegistered() {
        if (invokeHandler()) {
            try {
                ((ChannelInboundHandler) handler()).channelRegistered(this);
            } catch (Throwable t) {
                invokeExceptionCaught(t);
            }
        } else {
            fireChannelRegistered();
        }
    }

    // HeadContext
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        // 1. 这一步是 head 对于 channelRegistered 事件的处理。没有我们要关心的
        invokeHandlerAddedIfNeeded();
        // 2. 向后传播 Inbound 事件
        ctx.fireChannelRegistered();
    }

    //然后 head 会执行 fireChannelRegister() 方法：
    // AbstractChannelHandlerContext
    @Override
    public ChannelHandlerContext fireChannelRegistered() {
        // 这里很关键
        // findContextInbound() 方法会沿着 pipeline 找到下一个 Inbound 类型的 handler
        invokeChannelRegistered(findContextInbound(MASK_CHANNEL_REGISTERED));
        return this;
    }

    private AbstractChannelHandlerContext findContextInbound(int mask) {
        AbstractChannelHandlerContext ctx = this;
        EventExecutor currentExecutor = executor();
        do {
            ctx = ctx.next;//这里循环寻找下一个inbound handler
        } while (skipContext(ctx, currentExecutor, mask, MASK_ONLY_INBOUND));
        return ctx;
    }
```

注意：pipeline.fireChannelRegistered() 是将 channelRegistered 事件抛到 pipeline 中，pipeline 中的 handlers 准备处理该事件。而 context.fireChannelRegistered() 是一个 handler 处理完了以后，向后传播给下一个 handler。 它们两个的方法名字是一样的，但是来自于不同的类。

##### pipeline.invokeHandlerAddedIfNeeded() 

```java
//pipeline.invokeHandlerAddedIfNeeded(); 按顺序调用，开始初始化管道信息。

public class DefaultChannelPipeline{
    
    	//1
    	final void invokeHandlerAddedIfNeeded() {callHandlerAddedForAllHandlers();}
    
        //2
    	private void callHandlerAddedForAllHandlers() {
            //...
            PendingHandlerCallback task = pendingHandlerCallbackHead;
            while (task != null) {
            task.execute();//到这里
            task = task.next;}
        }
    
    	private abstract static class PendingHandlerCallback implements Runnable {
            abstract void execute();
        }
    	private final class PendingHandlerAddedTask extends PendingHandlerCallback {
            @Override
            public void run() {callHandlerAdded0(ctx);}
            
            //3
            @Override
        	void execute() {if(inEventLoop())callHandlerAdded0(ctx);
                            else executor.execute(this);//就是那个run方法，其实也是callHandleradd0}
  		}
    
    	//4
        callHandlerAdded0(final AbstractChannelHandlerContext ctx){ctx.callHandlerAdded();}
}
    
abstract class AbstractChannelHandlerContext{
    	
    	//5
    	callHandlerAdded() {handler().handlerAdded(this);}
    
}
public abstract class ChannelInitializer{
    	
        //6
    	handlerAdded(ChannelHandlerContext ctx){initChannel(ctx);}
    
}
```
之后就是调用channelInitializer抽象类下的handlerAdded 这里应该是模板模式（其实只是简单回调子类重写方法）

#### handlerAdded

（由上面调用下来）

```java
@Override
public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
    if (ctx.channel().isRegistered()) {
        // This should always be true with our current DefaultChannelPipeline implementation.
        // The good thing about calling initChannel(...) in handlerAdded(...) is that there will be no ordering
        // surprises if a ChannelInitializer will add another ChannelInitializer. This is as all handlers
        // will be added in the expected order.
        

        if (initChannel(ctx)) {//跟踪
    
            // We are done with init the Channel, removing the initializer now.
            removeState(ctx);
        }
    }
}
//上面的initchannel
private boolean initChannel(ChannelHandlerContext ctx) throws Exception {
        if (initMap.add(ctx)) { // Guard against re-entrance.
            try {
                
                //重载方法 ChannelHandlerContext ctx 和 SocketChannel socketChannel
                initChannel((C) ctx.channel());
                //这个调用的就是我们自己重写的方法
                //将把我们自定义的 handlers 添加到 pipeline 中
    
            /*
            protected void initChannel(SocketChannel socketChannel) throws Exception {
        		ChannelPipeline pipeline = socketChannel.pipeline();
        		pipeline.addLast("httpServerCodec", new HttpServerCodec());
        		pipeline.addLast("HttpServerHandler", new HttpServerHandler());//一般要把自己写的实现加入进来
    		}
            */
                
                
            } catch (Throwable cause) {
                // Explicitly call exceptionCaught(...) as we removed the handler before calling initChannel(...).
                // We do so to prevent multiple calls to initChannel(...).
                exceptionCaught(ctx, cause);
            } finally {
                
                // 2. 将 ChannelInitializer 实例从 pipeline 中删除
                ChannelPipeline pipeline = ctx.pipeline();
                if (pipeline.context(this) != null) {
                    pipeline.remove(this);
                }
            }
            return true;
        }
        return false;
    }
```

#### 绑定连接事件监听

```java
@Override
protected boolean doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
    if (localAddress != null) {
        doBind0(localAddress);
    }

    boolean success = false;
    try {
        boolean connected = SocketUtils.connect(javaChannel(), remoteAddress);
        if (!connected) {
            selectionKey().interestOps(SelectionKey.OP_CONNECT);
        }
        success = true;
        return connected;
    } finally {
        if (!success) {
            doClose();
        }
    }
}
```

#### boss worker间关联

**ServerBootstrapAcceptor**是关键

```java
//这个就是前面的init
@Override
void init(Channel channel) {
    setChannelOptions(channel, newOptionsArray(), logger);
    setAttributes(channel, attrs0().entrySet().toArray(EMPTY_ATTRIBUTE_ARRAY));

    ChannelPipeline p = channel.pipeline();

    final EventLoopGroup currentChildGroup = childGroup;
    final ChannelHandler currentChildHandler = childHandler;
    final Entry<ChannelOption<?>, Object>[] currentChildOptions;
    synchronized (childOptions) {
        currentChildOptions = childOptions.entrySet().toArray(EMPTY_OPTION_ARRAY);
    }
    final Entry<AttributeKey<?>, Object>[] currentChildAttrs = childAttrs.entrySet().toArray(EMPTY_ATTRIBUTE_ARRAY);

    p.addLast(new ChannelInitializer<Channel>() {
        @Override
        public void initChannel(final Channel ch) {
            final ChannelPipeline pipeline = ch.pipeline();
            ChannelHandler handler = config.handler();
            if (handler != null) {
                pipeline.addLast(handler);
            }

            ch.eventLoop().execute(new Runnable() {
                @Override
                public void run() {
                 /*
                 serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                .childHandler(new ServerInitializer());//定义子处理器 */
                    //传入了worker线程组，childer的 handler (childHandler)
                    pipeline.addLast(new ServerBootstrapAcceptor(
                            ch, currentChildGroup, currentChildHandler, currentChildOptions, currentChildAttrs));
                }
            });
        }
    });
}
```

[boss worker 之间是如何关联的](https://segmentfault.com/a/1190000007283053)

ServerBootstrapAcceptor也是一个入站

```java
@Override
@SuppressWarnings("unchecked")
public void channelRead(ChannelHandlerContext ctx, Object msg) {
    final Channel child = (Channel) msg;

    child.pipeline().addLast(childHandler);

    setChannelOptions(child, childOptions, logger);
    setAttributes(child, childAttrs);

    try {
        //childGroup就是worker 注册方法就是绑定 channel 和 worker中的线程
        childGroup.register(child).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (!future.isSuccess()) {
                    forceClose(child, future.cause());
                }
            }
        });
    } catch (Throwable t) {
        forceClose(child, t);
    }
}
```

当一个 client 连接到 server 时, Java 底层的 NIO ServerSocketChannel 会有一个 **SelectionKey.OP_ACCEPT** 就绪事件, 接着就会调用到 NioServerSocketChannel.doReadMessages:

```java

@Override
protected int doReadMessages(List<Object> buf) throws Exception {
    SocketChannel ch = javaChannel().accept();
    ... 省略异常处理
    buf.add(new NioSocketChannel(this, ch));
    return 1;
}

```

在 doReadMessages 中, 通过 javaChannel().accept() 获取到客户端新连接的 SocketChannel, 接着就实例化一个 **NioSocketChannel**, 并且传入 NioServerSocketChannel 对象(即 this), 由此可知, 我们创建的这个 NioSocketChannel 的父 Channel 就是 NioServerSocketChannel 实例 .
接下来就经由 Netty 的 ChannelPipeline 机制, 将读取事件逐级发送到各个 handler 中, 于是就会触发前面我们提到的 ServerBootstrapAcceptor.channelRead 方法啦.

## 解码器

### 解码器父类 ByteToMessageDecoder

模板模式，需要子类实现 decode 方法。实现自己的解码器：

- 首先继承ByteToMessageDecoder抽象类。
- 然后实现其基类的decode抽象方法。将ByteBuf到POJO解码的逻辑写入此方法。将Bytebuf二进制数据，解码成一个一个的Java POJO对象。
- 在子类的decode方法中，需要将解码后的Java POJO对象，放入decode的List<Object>实参中。这个实参是ByteToMessageDecoder父类传入的，也就是父类的结果收集列表。

```java
//例如：
public class DelimiterBasedFrameDecoder extends ByteToMessageDecoder {
    //实现了ByteToMessageDecoder抽象方法
    @Override
    protected final void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        Object decoded = decode(ctx, in);//解码为object对象，（对于多个对象的情况，一般用循环来做。）
        if (decoded != null) {
            out.add(decoded);//将解码后的对象放入实参中
        }
    }
}
```

## 粘包和拆包

- 每次读取底层缓冲的数据容量是有限制的，当TCP底层缓冲的数据包比较大时，会将一个底层包分成多次ByteBuf进行复制，进而造成进程缓冲区读到的是半包。
- 当TCP底层缓冲的数据包比较小时，一次复制的却不止一个内核缓冲包，进而造成进程缓冲区读到的是粘包。

解决：

在接收端，Netty程序需要根据自定义协议，将读取到的进程缓冲区ByteBuf，在应用层进行二次拼装，重新组装应用层数据包，即分包，拆包。

## pipline的责任链模式分析

netty责任链通过 链表 来完成，其中 ChannelHandlerContext 接口下的抽象类 abstract class AbstractChannelHandlerContext 为链表节点。

```java
//接口
public interface ChannelPipeline
        extends ChannelInboundInvoker, ChannelOutboundInvoker, Iterable<Entry<String, ChannelHandler>> {
     //增删改查责任链节点   
}
//实现类
public class DefaultChannelPipeline implements ChannelPipeline {
    final AbstractChannelHandlerContext head;//保存了头尾节点
    final AbstractChannelHandlerContext tail;
    private final Channel channel;//保存了连接channel，因为整个管道就这个channel
    
    //初始化方法
    protected DefaultChannelPipeline(Channel channel) {
        this.channel = ObjectUtil.checkNotNull(channel, "channel");
        succeededFuture = new SucceededChannelFuture(channel, null);
        voidPromise =  new VoidChannelPromise(channel, true);

        tail = new TailContext(this);//内部类，做一些必须的操作
        head = new HeadContext(this);

        head.next = tail;
        tail.prev = head;
    }
    
    //将handler添加到责任链的方法，通过将channel包装进context内，连接到链表
    @Override
    public final ChannelPipeline addFirst(EventExecutorGroup group, String name, ChannelHandler handler) {
        final AbstractChannelHandlerContext newCtx;
        synchronized (this) {
            checkMultiplicity(handler);
            
            name = filterName(name, handler);
            
            //添加handler到链表，通过新建context节点（将handler保存在context中）
            newCtx = newContext(group, name, handler);
            //链表添加操作
            addFirst0(newCtx);

            if (!registered) {
                newCtx.setAddPending();
                callHandlerCallbackLater(newCtx, true);
                return this;
            }

            EventExecutor executor = newCtx.executor();
            if (!executor.inEventLoop()) {
                callHandlerAddedInEventLoop(newCtx, executor);
                return this;
            }
        }
        //handler添加成功事件
        callHandlerAdded0(newCtx);//添加成功之后，通知该handler并调用其handlerAdded方法。属于观察者模式
        return this;
    }
    //还有很多链表操作，remove，addLast等
    
    //其他调用该pipline，将责任从头传递下去的方法
    @Override
    public final ChannelPipeline fireChannelRead(Object msg) {
        AbstractChannelHandlerContext.invokeChannelRead(head, msg);//责任方法
        return this;
    }
}
```

```java
//链表节点
public interface ChannelHandlerContext extends AttributeMap, ChannelInboundInvoker, ChannelOutboundInvoker {

}
//Channel、pipline、channelHandler模块间交互上下文类，双向链表节点
//应该类似于tomcat中servletContext，是一种握手型的交互式
abstract class AbstractChannelHandlerContext implements ChannelHandlerContext, ResourceLeakHint {
    volatile AbstractChannelHandlerContext next;
    volatile AbstractChannelHandlerContext prev;
    private final DefaultChannelPipeline pipeline;//保存了所在的pipline
    final EventExecutor executor;//
    
    //责任传递
    static void invokeChannelRead(final AbstractChannelHandlerContext next, Object msg) {
        //当然这里还没有传递到下一个
        final Object m = next.pipeline.touch(ObjectUtil.checkNotNull(msg, "msg"), next);
        EventExecutor executor = next.executor();
        if (executor.inEventLoop()) {
            next.invokeChannelRead(m);//传递到next
        } else {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    next.invokeChannelRead(m);//传递到next
                }
            });
        }
    }
    
    //next调用的invokeChannelRead方法
    private void invokeChannelRead(Object msg) {
        if (invokeHandler()) {
            //判断是否已调用{ @link ChannelHandler＃channelRead（ChannelHandlerContext,Object）}等各种方法
            try {
                ((ChannelInboundHandler) handler()).channelRead(this, msg);//调用绑定的handler的channelRead方法
                //之后就需要在channelRead方法中再调用 fireChannelRead方法来将责任传递下去。
                //ctx.fireChannelRead(msg);
            } catch (Throwable t) {
                invokeExceptionCaught(t);
            }
        } else {
            //如果已调用，直接转发到下一个handler处理
            fireChannelRead(msg);
        }
    }
    
    @Override
    public ChannelHandlerContext fireChannelRead(final Object msg) {
        //findContextInBound即查找下一个责任节点 context 调用 handler 
        invokeChannelRead(findContextInbound(MASK_CHANNEL_READ), msg);
        return this;
    }
    
    private AbstractChannelHandlerContext findContextInbound(int mask) {
        AbstractChannelHandlerContext ctx = this;
        EventExecutor currentExecutor = executor();
        do {
            ctx = ctx.next;//循环查找下一个
        } while (skipContext(ctx, currentExecutor, mask, MASK_ONLY_INBOUND));//通过mask判断是否是需要的inbound handler
        return ctx;
    }
}


//子类保存了handler
final class DefaultChannelHandlerContext extends AbstractChannelHandlerContext {

    private final ChannelHandler handler;
}
```

| 管道/上下文                                                |
| ---------------------------------------------------------- |
| 管道pipline                                                |
| 通道上下文ChannelContext                                   |
| 底层实现为 具有头节点、尾结点的双向链表                    |
| Context的核心方法invokeChannelRead -> fireChannelRead      |
| AbstractChannelHandlerContext.invokeChannelRead(head, msg) |

