package volley.wheel.base.core;

import java.util.PriorityQueue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import volley.wheel.base.Task;

/**
 * Created by Administrator on 2017/11/30.
 */

//进行线程发送处理处理
public class CorePoolImp {
    //定义一个队列
    private static PriorityQueue<Runnable> priorityQueue ;
    //定义一个线程池
    private static ThreadPoolExecutor threadPoolExecutor;

    private static RejectedExecutionHandler rejectedExecutionHandler = new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
            priorityQueue.add(runnable);
        }
    };

    static {
        priorityQueue = new PriorityQueue<>();
        threadPoolExecutor = new ThreadPoolExecutor(4,10,10, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10), rejectedExecutionHandler);
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    //拿到task的任务
                    Runnable runnable = priorityQueue.poll();
                    //执行task的run
                    runnable.run();
                }
            }
        });
    }

    public static void execute(Task task) {
        if (task == null) throw new NullPointerException("Task is Null");
        priorityQueue.add(task);
    }

}
