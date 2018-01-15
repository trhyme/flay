package com.java.letch.service;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadPoolExecutor;

import javax.annotation.Resource;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

/***
 * 线程管理器
 * @author Administrator
 *
 */
@Repository(value="ThreadTestTwoManger")
public class ThreadTestTwoManger implements BeanFactoryAware{

	//线程池
	@Resource(name="taskExecutor")
	private ThreadPoolTaskExecutor threadPool;
		
	
	//用于从IOC里取对象
	private BeanFactory factory;
	
	// 消息缓冲队列
    Queue<Object> msgQueue = new LinkedList<Object>();

    //用于储存在队列中的订单,防止重复提交
    Map<String, Object> cacheMap = new ConcurrentHashMap<>();

    //由于超出线程范围和队列容量而使执行被阻塞时所使用的处理程序
    final RejectedExecutionHandler handler = new RejectedExecutionHandler() {
    	 @Override
         public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
             System.out.println("太忙了,把该订单交给调度线程池逐一处理:" + (ThreadTestService.class));
             msgQueue.offer(new ThreadTestService());
         }
	};
	

    // 调度线程池。此线程池支持定时以及周期性执行任务的需求。
    final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);

	 // 查看是否有待定请求，如果有，则创建一个新的AccessDBThread，并添加到线程池中
    final ScheduledFuture taskHandler = scheduler.scheduleAtFixedRate(new Runnable() {
        @Override
        public void run() {
            if (!msgQueue.isEmpty()) {
                if (threadPool.getMaxPoolSize() < 10) {
                    System.out.print("调度：");
                    String orderId = (String) msgQueue.poll();
                    ThreadTestService accessDBThread = (ThreadTestService) factory.getBean("ThreadTestService");
                    accessDBThread.setOrderID(orderId);
                    threadPool.execute(accessDBThread);
                }
                // while (msgQueue.peek() != null) {
                // }
            }
        }
    }, 0, 1, TimeUnit.SECONDS);
	
	//终止订单线程池+调度线程池
    public void shutdown() {
        //true表示如果定时任务在执行，立即中止，false则等待任务结束后再停止
        System.out.println(taskHandler.cancel(false));
        scheduler.shutdown();
        threadPool.shutdown();
    }


    public Queue<Object> getMsgQueue() {
        return msgQueue;
    }


    //将任务加入订单线程池
    public void processOrders(String orderId) {
        if (cacheMap.get(orderId) == null) {
            cacheMap.put(orderId,new Object());
        }else{
        	System.out.println(cacheMap.size());
        	cacheMap.put(orderId,new Object());
        }
        ThreadTestService accessDBThread = (ThreadTestService) factory.getBean("ThreadTestService");
        accessDBThread.setOrderID(orderId);
        threadPool.execute(accessDBThread);
    }


    //BeanFactoryAware
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        factory = beanFactory;
    }
	
}
