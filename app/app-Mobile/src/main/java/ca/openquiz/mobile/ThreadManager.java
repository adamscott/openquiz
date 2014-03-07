package ca.openquiz.mobile;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class ThreadManager {
	
	private static ThreadManager instance = null;
	
    private final BlockingQueue<Runnable> workQueue;
    private final ThreadPoolExecutor threadPool;
    
    private static int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    private static final int KEEP_ALIVE_TIME = 1;
    private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
    
    private ThreadManager()
    {
    	workQueue = new LinkedBlockingQueue<Runnable>();
    	
    	threadPool = new ThreadPoolExecutor(NUMBER_OF_CORES, NUMBER_OF_CORES,
                KEEP_ALIVE_TIME, KEEP_ALIVE_TIME_UNIT, workQueue);
    }
	
	public static ThreadManager getInstance()
	{
		if(instance == null)
			instance = new ThreadManager();

		return instance;
	}

	public ThreadPoolExecutor getCreateNewGameThreadPool() {
		return threadPool;
	}
	
}
