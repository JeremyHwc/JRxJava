# 序言
    对于RxJava而言，之前的学习一直比较零碎，没有系统的学习过，最近项目上线以后，可以利用这段的时间沉
    淀沉淀自己。以后自己的学习记录库，都会以“J”+“具体内容”，比如，该库用于学习记录RxJava,就叫做
    JRxJava。
# 第1、2章 课程介绍—课程整体内容介绍
##    1.响应式变成思想
        响应式变成思想概述：
            响应式变成是一种面向  *数据流*  和  变化传播  的 编程范式。
            数据流：只能以事先规定好的顺序被读取一次的数据的一个序列；
            变化传播：类似观察者模式，变化了要通知别人；
            编程范式：计算机编程的基本风格或典范模式。
            
        流水线：
            数据流-->在履带上运送的要加工的物品
            变化传播-->把前一个环节的加工结果传到下一个环节
            编程范式-->每种物品的加工方式不同，流水线就不同
            
        响应式编程思想的实现 --- RxJava
            
        关键名词解释
    
##    2.RxJava源码分析（1和2）
        RxJava基本元素 -----> Operator操作符变换原理 -----> Scheduler线程变换原理 -----> 整体变换compose和 Transformer原理
        .简单介绍+回顾        .操作符简介                   .线程变换简介                .整体变换简介
        .源码分析             .源码剖析核心操作符lift       .剖析subscribeOn原理         .剖析Transformer原理
        .实战基本元素         .实战操作符                   .实战subscribeOn             .实战模仿整体变换
                                                            .剖析observeOn原理
                                                            .实战observeOn
        学习流程：
            抛出思想，阐述理论原理 ---->
            现实案例，解释落地思想 ---->
            从简单代码示例入手，剖析源码实现，和源码设计思想 ---->
            项目案例实战，加深理解。
            
##    3.代码实战
        RxJava + Retrofit        ---->        RxJava + MVP              ---->       RxJava + Retrofit + MVP
        
        .利用CallAdapter进行适配              .MVP模式概念和分层思想                .一个接口数据依赖于另一个接口
        .将返回值转换成Observable对象         .RxJava + Retrofit作为M层提供数据     .不用RxBinding
                                                                                    .如何实现防抖动
                                                                                    .如何实现进行下一个请求，取消上个请求
##    4.学习收获
        深入了解响应式编程思想
        学会如何将响应式编程思想用于实践
        懂得如何使用代码将理论实际化
        掌握一定的代码分析能力                          
        

## 第3章 RxJava基本元素
### 3-1 简单介绍及回顾RxJava
    RxJava简介：
        A libarary for composing **asynchronous** and **eventbased** programs by using 
    observable sequences.(异步的，RxJava是一个异步的库，基于回调的；基于事件的，事件分发的库，消息传递的库)
    
    RxJava代码实例：
        
    RxJava1基本元素：
        Observable ---> 观察得到的---被观察者，通过Observable创建一个可观察的序列（create方法），通过subscribe()去注册一个观察者
        Observer ---> 用于接受数据的---观察者，作为Observable的subscribe方法的参数
        Subscription ---> 订阅，用于描述被观察者和观察者之间的关系，用于取消订阅和获取当前的订阅状态，
        OnSubscribe ---> 当订阅时会出发此接口调用，在Observable内部，实际作用是向订阅者发射数据
        Subscriber ---> 实现了Observer和Subscription，只有自己才能阻止自己
        
    RxJava1基本元素源码分析：
    
    流程简述：Observable通过create()方法传入Observable.OnSubscribe对象构造一个Observable对象，然后Observable对象通过subscribe()方法
                      调用Observable.OnSubscribe的call方法。
                      
            疑问：在call方法里面调用subscriber的onNext()发送事件，Observer是怎么收到的？
            解答：subscriber是进行封装过的Observer，call()方法里面调用了subscriber的onNext()，onCompleted()
                    方法，实际就是调用了Observer的onNext()，onCompleted()方法。不过为什么要这么写还需要斟酌一下。
            
            现实中的案例 --- 打电话
            
            UML图
            Observable<T>
            
    背压概念：
        异步环境下产生的问题
        发送和处理速度不统一
        是一种流速控制解决策略
        
        生产者和消费者
        
        实例分析：
            一个装满的快递箱，蜂巢快递
            
    RxJava2的基本元素：
    
        RxJava2基本元素源码分析（无背压）
        
            Observable
                观察得到的 --- 被观察者，不支持背压
                通过Observable创建一个可观察的序列（create方法）
                通过subscribe()去注册一个观察者
                
            Observer
                用于接受数据 --- 观察者
                作为Observable的subscribe方法的参数
                
            Disposable（类似于RxJava1中的Subscription，和其作用相当）
                用于取消订阅和获取当前的订阅状态
            
            ObservableOnSubscribe
                当订阅时会出发此接口调用
                在Observable内部，实际作用是向观察者发射数据
                
            Emitter
                一个发射数据的接口，和Observer的方法类似
                本质是对observer和Subscriber的包装
        
        流程简述：
                @Override
                protected void subscribeActual(Observer<? super T> observer) {
                    CreateEmitter<T> parent = new CreateEmitter<T>(observer);
                    observer.onSubscribe(parent);
            
                    try {
                        source.subscribe(parent);
                    } catch (Throwable ex) {
                        Exceptions.throwIfFatal(ex);
                        parent.onError(ex);
                    }
                }
        
            Observable 通过 create() 方法，传入 ObservableOnSubscribe 对象构建Observable对象，这里会构造出ObservableCreate对象，
            Observable 通过 subscribe()方法订阅Observer，进而调用ObservableCreate对象里面的 subscribeActual()方法，
            在这个方法里面会构造出CreateEmitter()，其实现了Disposable和ObservableEmitter接口，进而调用了Observer的onSubscribe()方法和
            ObservableOnSubscribe的subscribe()方法，就开始了事件流程。本质和RxJava1里面是一样的，最终都是通过observer调用onNext，onError
            onComplete()等方法
            
### 3-6 RxJava2基本元素源码分析(有背压)
    RxJava2 有背压时的基本元素
        Flowable:
            易流动的 ---> 被观察者，支持背压；
            通过Flowable创建一个可观察的序列（create方法）；
            通过subscribe去注册一个观察者。
            
        Subscriber:
            一个单独接口，和Observer的方法类似；
            作为Flowable的subscribe方法的参数。
        Subscription:
            订阅，和RxJava1的有所不同；
            支持背压，有用于背压的request方法。
        FlowableOnSubscribe:
            当订阅时会触发此接口调用；
            在Flowable内部，实际作用是向观察者发射数据。
        Emitter:
            一个发射数据的接口，和Observer的方法类似；
            本质是对Observer和Subscriber的包装。
        
    Flowable 和 Observable 的实现方式及原理是一样的，只是Flowable里面多了一个背压策略。
    
### 实战基本元素
    RxJava1：
    
    五大元素.仿
    
        RxJava1 元素实战：
            Observable ---> Caller
                            打电话的人（被观察者） ---> 发数据的一方
                            通过create()方法创建
                            通过call方法去打给接电话的人（观察者）
            Observer ---> Callee
                            接电话的人（观察者）---> 接受数据的一方
                            作为call的call方法的参数
            Subscription ---> Calling
                            描述打电话这件事
                            用于取消挂掉电话和获取当前的是否在打电话
            OnSubscribe ---> OnCall
                            当打电话时会出发此接口调用
                            作用于caller，向接电话的人发送通话内容
            Subscriber ---> Receiver  
                            实现了Callee和Calling；
                            接电话的人挂掉电话
    
    RxJava2：
        无背压：
            Observable ---> Caller
            Observer ---> Callee
            Disposable ---> Release
            ObservableOnSubscribe ---> OnCall
            Emitter ---> Emitter
    
### 章节回顾
    响应式变成思想概念
        响应式编程是一种面向 数据流 和 变化传播 的 编程范式
        数据流是只能以事先规定好的顺序被读取一次的数据的一个序列
        变化传播类似于观察者模式，变化了要通知别人
        编程范式是指计算机编程的基本风格或典范模式，如面向对象、过程编程都是编程范式
        
        如何体现：
            响应式，反应式 --- 基于注册回调的方式运行
            数据流 --- 通过onNext方法来发射数据
            编程范式 --- 后面讲到
            变化传播 --- 后面讲到
            
    RxJava基本元素
    
    基本元素是如何体现响应式编程思想的
    
    案例演练：苹果汁流水线
        1.将苹果一个一个放上履带
        
# 第4章 Operator操作符变换—源码解析与案例实践    
    操作符：
        将发出的数据进行处理并再发送
    RxJava1操作符：
        （1）Func1接口
        （2）Operator接口
    RxJava2操作符：
        （1）Function接口
        （2）AbstractObservableWithUpstream抽象类
    变换的原理
        lift操作符 --- 变换的原理
        
    Operator操作符变换原理    
    RxJava1源码分析变换的原理 --- 操作符lift
    
## 4-2 RxJava1操作符源码分析
    lisft操作符 接收原OnSubscribe和当前的Operater
        （1）接收原OnSubscribe和当前的Operator；
        （2）创建一个新的OnSubscribe并返回新的Observable；
        （3）用新的Subscriber包裹旧的Subscriber；
        （4）在新的Subscriber里做完变换再传给旧的Subscriber；
        类似于代理机制
        现实中的案例 ---- 寄快递
    
## 4-3 RxJava2操作符源码分析
    AbstractObservableWithUpstream抽象类(无背压版)
        (1)继承此类
        (2)利用其subscribeActual方法
        (3)用原Observable去subscribe变换后的Observer
    AbstractFlowableWithUpstream抽象类(有背压版)
        (1)继承此类
        (2)利用其subscribeActual方法
        (3)用原Flowable去subscribe变换后的Subscriber 
        
    Operator接口
        (1)实现此接口
        (2)在subscribeActual方法中做变换
        (3)用于扩展自定义操作符
    分析
        RxJava1和RxJava2的实现方式在本质上没有什么区别，类似于代理机制。
        现实中的案例 --- 寄快递
## 4-4 实战操作符RxJava1

## 4-7 章节回顾
    （1）响应式编程思想概念
        响应式编程是一种面向 数据流 和 变化传播 的 编程范式
    （2）Operator操作符变换原理 
        
    （3）Operator操作符是如何体现响应式编程思想的
        变化传播 --- 通过操作符实现变化，并能向下传播
    
# 第5章 Scheduler线程变换—源码解析与案例实践
## 5-1 线程变换简介
    让代码可以再不同的线程执行
    subscribeOn()----订阅时的线程
    observeOn()---- 接收时的线程
    Scheduler ---- 实际做线程变换的
    
    RxJava1线程变换
        （1）Scheduler调度者
        （2）Operator操作符接口
        （3）lift核心操作符
        
    RxJava1线程变换
        （1）Scheduler调度者
        （2）AbstractObservableWithUpstream抽象类
        
    分析
        现实中的案例 --- 下载电影看
        
## 5-2 Scheduler源码分析(RxJava1)
    1.Scheduler调度者源码分析（RxJava1）
        (1)Scheduler抽象类
        (2)Worker --- 真正做线程调度的类
        (3)Action0 --- 在线程中执行的操作
        (4)schedule --- 实际做线程调度的方法，入参为Action0
        具体流程：
            传入不同Scheduler来使用不同的线程
            用Scheduler创建Worker来使用真正的线程池
            传入具体可操作Action0
            通过schedule方法来实现调度
        
        Android中的Scheduler
            通过Handler和Looper来实现执行在主线程    
## 5-3 Scheduler调度者源码分析(RxJava2)
    1.Scheduler调度者源码分析（RxJava2）
        （1）Scheduler抽象类
        （2）Worker --- 真正做线程调度的类
        （3）Runnable --- 在线程中执行的操作
        （4）schedule --- 做线程调度的方法，入参为Runnable
        
        传入不同Scheduler来使用不同的线程
        用Scheduler创建Worker来使用真正的线程池
        传入具体操作Runnable
        通过schedule方法来实现调度
        
## 5-6 subscribeOn原理分析(RxJava1)
### subscribeOn原理
    subscribeOn 用于指定 subscribe() 时所发生的线程
    （1）通过OnSubscribe来做文章
    （2）利用Scheduler将发出动作放到线程中执行
    分析：
        类似于代理机制
        现实中的案例 --- 类似于找了一个中介
        
        从源码角度可以看出，内部线程调度是通过 ObservableSubscribeOn来实现的。
        ObservableSubscribeOn 的核心源码在 subscribeActual 方法中，通过代理的
        方式使用 SubscribeOnObserver 包装 Observer 后，设置 Disposable 来将 
        subscribe 切换到 Scheduler 线程中。
## 5-7 subscribeOn原理分析(RxJava2无背压)
    （1）继承AbstractObservableWithUpstream
    （2）实现subscribeActual方法
    （3）利用Scheduler将发出动作放到线程中执行
    
    线程切换需要注意的：
        RxJava 内置的线程调度器的确可以让我们的线程切换得心应手，但其中也有些需要注意的地方。
        
        .简单地说，subscribeOn() 指定的就是发射事件的线程，observerOn 指定的就是订阅者接收事件的线程。
        .多次指定发射事件的线程只有第一次指定的有效，也就是说多次调用 subscribeOn() 只有第一次的有效，其余的会被忽略。
        .但多次指定订阅者接收线程是可以的，也就是说每调用一次 observerOn()，下游的线程就会切换一次。
        
## 5-8 subscribeOn原理分析(RxJava2有背压) 

# 第6章 整体变换compose和Transformer原理
    整体变换简介
        （1）将一坨变换整合起来放在一起
        （2）用于固定的变换场景
        代码示例：
            以下代码是RxJava2
            Observable
                                    .create(new ObservableOnSubscribe<String>() {
                                        @Override
                                        public void subscribe(ObservableEmitter<String> e) throws Exception {
                                            if (!e.isDisposed()) {
                                                e.onNext("test");
                                                System.out.println("currentThread:" + Thread.currentThread());
                                                e.onComplete();
                                            }
                                        }
                                    })
                                    .compose(new ObservableTransformer<String, String>() {
                                        @Override
                                        public ObservableSource<String> apply(Observable<String> upstream) {
                                            return upstream
                                                    .subscribeOn(Schedulers.newThread())
                                                    .observeOn(AndroidSchedulers.mainThread());
                                        }
                                    })
                                    .subscribe(new Observer<String>() {
            
                                        @Override
                                        public void onSubscribe(Disposable d) {
            
                                        }
            
                                        @Override
                                        public void onNext(String value) {
                                            System.out.println("currentThread:" + Thread.currentThread());
                                            System.out.println("onNext:" + value);
                                        }
            
                                        @Override
                                        public void onError(Throwable e) {
            
                                        }
            
                                        @Override
                                        public void onComplete() {
            
                                        }
                                    });
                                    
            Flowable
                                    .create(new FlowableOnSubscribe<String>() {
                                        @Override
                                        public void subscribe(FlowableEmitter<String> e) throws Exception {
                                            if (!e.isCancelled()) {
                                                e.onNext("test");
                                                System.out.println("currentThread:" + Thread.currentThread());
                                                e.onComplete();
                                            }
                                        }
                                    }, BackpressureStrategy.DROP)
            
                                    .compose(new FlowableTransformer<String, String>() {
                                        @Override
                                        public Publisher<String> apply(Flowable<String> upstream) {
                                            return upstream.
                                                    subscribeOn(Schedulers.newThread()).
                                                    observeOn(AndroidSchedulers.mainThread());
                                        }
                                    })
                                    .subscribe(new Subscriber<String>() {
                                        @Override
                                        public void onSubscribe(Subscription s) {
                                            s.request(Long.MAX_VALUE);
                                        }
            
                                        @Override
                                        public void onNext(String s) {
                                            System.out.println("currentThread:" + Thread.currentThread());
                                            System.out.println("onNext:" + s);
                                        }
            
                                        @Override
                                        public void onError(Throwable t) {
            
                                        }
            
                                        @Override
                                        public void onComplete() {
            
                                        }
                                    });                        
        现实中的案例：
            transformer类似于小学班长收作业交给老师
            
    整体变换Transformer原理
        RxJava1:
            Transformer接口
            （1）继承自Func1接口，泛型参数是两个Observable
            （2）为compose方法的入参
            （3）传入一个Observable返回一个Observable
            
            简单来说就是，observable1.compose(transformer);在transformer里面的call(observable1)方法里
            面对observable1进行一系列的变换，从而返回一个新的observable2,因此称作整体变换
            
        RxJava2:
            ObservableTransformer和FlowableTransformer
            （1）有一个apply方法
            （2）传入一个Observable返回一个新的Observable
            （3）为compose方法的入参
            
    本章回顾
        （1）响应式编程思想概念
        （2）整体变换原理
        （3）整体变换是如何体现响应式编程思想的
            变化---通过Transformer实现整体变换
            传播--- 变换后并能向下传播
        
# 第7章 RxJava+Retrofit+MVP综合案例
    RxJava + Retrofit
        (1)利用CallAdapter进行适配 
        (2)将返回值转换成Observable对象
    MVP模式
        M:提供数据
        V:视图层
        P:负责业务逻辑
        （1）MVP模式的概念和分层思想
            简单描述：V -> P -> M , M -> P -> V
        （2）RxJava + Retrofit 作为M层提供数据
        
    完整案例：
        1.一个接口的数据依赖于另外一个接口
            NetworkService
                            .getInterface()
                            .pre()
                            .subscribeOn(Schedulers.io())
                            .map(new Function<String, Info>() {
                                @Override
                                public Info apply(String s) throws Exception {
                                    return new Gson().fromJson(s, Info.class);
                                }
                            })
                            .flatMap(new Function<Info, ObservableSource<String>>() {
                                @Override
                                public ObservableSource<String> apply(Info info) throws Exception {
                                    return NetworkService.getInterface().doSomething(info.name);
                                }
                            })
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<String>() {
                                @Override
                                public void onSubscribe(Disposable d) {
            
                                }
            
                                @Override
                                public void onNext(String value) {
                                    mView.update(value + System.currentTimeMillis());
                                }
            
                                @Override
                                public void onError(Throwable e) {
            
                                }
            
                                @Override
                                public void onComplete() {
            
                                }
                            });
        2.RxJava结合Retrofit结合MVP完整案例
            （1）不用RxBinding,如何实现防抖动
            （2）不用RxBinding,如何实现进行下一个请求取消上一个请求
                if (mEmitter == null) {
                            //防抖动
                            Observable.
                                    create(new ObservableOnSubscribe<Object>() {
                                        @Override
                                        public void subscribe(ObservableEmitter<Object> e) throws Exception {
                                            mEmitter = e;
                                        }
                                    }).
                                    throttleFirst(1500, TimeUnit.MILLISECONDS).
                                    subscribe(new Consumer<Object>() {
                                        @Override
                                        public void accept(Object s) throws Exception {
                                            mView.update("就不让疯狂点击" + System.currentTimeMillis());
                                        }
                                    });
                            //进行下一个请求取消上一个请求
                            Observable.
                                    create(new ObservableOnSubscribe<Object>() {
                                        @Override
                                        public void subscribe(ObservableEmitter<Object> e) throws Exception {
                                            mEmitter = e;
                                        }
                                    }).
                                    switchMap(new Function<Object, ObservableSource<Integer>>() {
                                        @Override
                                        public ObservableSource<Integer> apply(Object o) throws Exception {
                                            return NetworkService.network();
                                        }
                                    }).
                                    subscribe(new Consumer<Integer>() {
                                        @Override
                                        public void accept(Integer i) throws Exception {
                                            mView.update("当前的计数是:" + i);
                                        }
                                    });
                        }
                
                        if (!mEmitter.isDisposed()) {
                            mEmitter.onNext("");
                        }