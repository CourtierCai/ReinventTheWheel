package event.eventbus.eventbusbase;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import event.eventbus.BuildConfig;

/**
 * Created by Administrator on 2017/11/28.
 */

public class EventBus {
    public static class EventInstance {
        private static final EventBus eventBus = new EventBus();
    }


    private static HashMap<Object,CopyOnWriteArrayList> map;
    private Handler handler;
    private ExecutorService executorService;
    private EventBus(){
        handler = new Handler(Looper.getMainLooper());
        map = new HashMap<>();
        executorService = Executors.newCachedThreadPool();
    }

    public static EventBus getInstance(){
        return EventInstance.eventBus;
    }

    public void registe(Object cls) {
          if (cls == null) {
             throw new NullPointerException("Cls is Null,Not allow");
          }
          if (map.get(cls) == null) {
              CopyOnWriteArrayList<Subscriber> subscriberMethods = findSubscriberMethods(cls.getClass());
              map.put(cls, subscriberMethods);
          }
    }

    public void unregiste(Object cls){
       if (map.get(cls) != null) {
           map.remove(cls);
       }
    }

    private static CopyOnWriteArrayList<Subscriber> findSubscriberMethods(Class<?> subscriberClass){
        //防止多线程，CopyOnWriteArrayList 新加并且创建一个
        CopyOnWriteArrayList<Subscriber> subscribers = new CopyOnWriteArrayList<Subscriber>();
        for (Class cls = subscriberClass ; cls != null; cls = cls.getSuperclass()) {
            /**
             * 获取所有的eventbus的东东要有注解
             * 1，要有注解
             * 2，不能包括系统
            **/
            Method[] methods = cls.getDeclaredMethods();
            for (Method method: methods) {
                //不能包括系统
                if (method.getName().startsWith("java") || method.getName().startsWith("javax") || method.getName().startsWith("android")) {
                    continue;
                }
                //获取注解的下面的参数类型
                SubscriberModel subscriberModel =  method.getAnnotation(SubscriberModel.class);
                //如果这个注解是空的我们也不要了，因为不是我们期望的，跟其他版本判断方法开头不同，之前的傻傻的。
                if (subscriberModel == null) continue;

                //这个时候我们新建我们的对象
                /**
                 * 获取参数
                 * */
                Class [] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length != 1) {
                    throw new IllegalArgumentException("Error，So mush parameterTypes");
                }

                Subscriber subscriber = new Subscriber(method, parameterTypes[0], subscriberModel);
                if (BuildConfig.DEBUG) {
                    //打印看下
                    Log.i("subscriber====>", method.getName() + "  " + subscriberModel.threadModle());
                }
                subscribers.add(subscriber);
            }

        }

        return subscribers;
    }


    //找到所有被订阅者
    public void  post(final Object promulgator) {
        if (promulgator == null) {
            throw  new NullPointerException("Error，Param is null");
        }
        Set<Map.Entry<Object,CopyOnWriteArrayList>> sets = map.entrySet();
        for (final Map.Entry entry : sets) {
            CopyOnWriteArrayList<Subscriber> localOnList = (CopyOnWriteArrayList<Subscriber>)entry.getValue();
            for (final Subscriber sub : localOnList) {
                 if (sub.getCls().isAssignableFrom(promulgator.getClass())) {
                     SubscriberModel model = sub.getSubscriberModel();
                     hanleSubscriberModel(promulgator, entry, sub, model);
                 }
            }
        }
    }

    private void hanleSubscriberModel(final Object promulgator, final Map.Entry entry, final Subscriber sub, SubscriberModel model) {
        if (model.threadModle() == ThreadModel.THREAD_NORMAL) {
            invoke(sub.getMethod(), entry.getKey() ,promulgator);
        } else if (model.threadModle() == ThreadModel.THREAD_MAIN) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                //直接调用
                invoke(sub.getMethod(), entry.getKey(), promulgator);
            } else {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        invoke(sub.getMethod(), entry.getKey(), promulgator);
                    }
                });
            }
        } else if (model.threadModle() == ThreadModel.THREAD_BACKGROUN) {
            if (Looper.myLooper() != Looper.getMainLooper()) {
                invoke(sub.getMethod(), entry.getKey(), promulgator);
            } else {
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        invoke(sub.getMethod(), entry.getKey() ,promulgator);
                    }
                });
            }
        }
    }

    /**
     * 1，方法：一般是你要的方法（被订阅者的）。
     * 2，实例：被订阅者真实的对象。
     * 3，参数：每一个参数的订阅者传进来的哦。
     * */
    private void invoke(Method m, Object instance, Object param) {
        try {
            m.invoke(instance,param);
        } catch (Throwable b) {
           Log.i("Error===>", "Error", b);

        }
    }

}
