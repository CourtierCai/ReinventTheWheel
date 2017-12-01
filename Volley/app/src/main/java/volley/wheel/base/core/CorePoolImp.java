package volley.wheel.base.core;

import android.util.Log;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import volley.wheel.base.Task;
import volley.wheel.invoke.Volley;

/**
 * Created by Administrator on 2017/11/30.
 */

//进行线程发送处理处理
public class CorePoolImp {
    //定义一个队列
    private static LinkedBlockingDeque<Runnable> linkedBlockingDeque;
    //定义一个线程池
    private static ThreadPoolExecutor threadPoolExecutor;

    private static RejectedExecutionHandler rejectedExecutionHandler = new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
            Log.i(CorePoolImp.class.getName(), "rejectedExecution");
            linkedBlockingDeque.add(runnable);
        }
    };

    static {
        linkedBlockingDeque = new LinkedBlockingDeque<>();
        //1，这里其实可以开5多个线程去做这个事情去执行runnable，让整个系统跑得更快一些。就跟volley一样
        //2，直接在runnable提交--》这个可能是性能问题，假设我现在5个了，继续execute会一直的拒绝加入重复工作
        threadPoolExecutor = new ThreadPoolExecutor(5, 10, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10), rejectedExecutionHandler);
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                while (true) {

                    //拿到task的任务
                    Runnable runnable = null;
                    try {
                        runnable = linkedBlockingDeque.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.i("CorePool", threadPoolExecutor.getCorePoolSize() + "");
                    Log.i(Volley.TAG, "我是核心池中心 - 我拿到了任务");
                    if (runnable == null) {
                        Log.i(Volley.TAG, "我是核心池中心 - 我没有拿到了任务");
                        continue;
                    }
                    //执行task的run
                    if (threadPoolExecutor != null){
                        threadPoolExecutor.execute(runnable);
                    }

//                    runnable.run();
                }
            }
        });
    }

    public static void execute(Task task) {
        if (task == null) throw new NullPointerException("Task is Null");
        if (linkedBlockingDeque != null) {
            linkedBlockingDeque.add(task);
        }
    }

    public static  void  clear() {
        if (linkedBlockingDeque != null) {
            linkedBlockingDeque.clear();
        }
        if (threadPoolExecutor != null) {
            threadPoolExecutor.shutdown();
        }

    }

}
