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
    RxJava简介
        A libarary for composing **asynchronous** and **eventbased** programs by using 
    observable sequences.(异步的，RxJava是一个异步的库，基于回调的；基于事件的，事件分发的库，消息传递的库)
    RxJava代码实例
        
    RxJava基本元素
        Observable
        Observer
        Subscription
        OnSubscribe
        Subscriber
    
    
    
        