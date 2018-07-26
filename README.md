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
        Flowable
        Subscriber
        Subscription
        FlowableOnSubscribe
        Emitter
        
    Flowable 和 Observable 的实现方式及原理是一样的，只是Flowable里面多了一个背压策略。
    
        
    
    
    
    
        