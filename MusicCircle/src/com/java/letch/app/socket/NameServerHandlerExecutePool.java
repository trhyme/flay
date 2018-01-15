package com.java.letch.app.socket;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/***
 * 创建固定的线程池和任务队列
 * @author Administrator
 * 2017-12-13
 */
public class NameServerHandlerExecutePool{
	private ExecutorService executor;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public NameServerHandlerExecutePool(int maxPoolSize, int queueSize) { 
		executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), 
		maxPoolSize, 120L, TimeUnit.SECONDS, new ArrayBlockingQueue(queueSize)); 
	} 
	
	public void execute(Runnable task) { 
		executor.execute(task); 
	} 
}
