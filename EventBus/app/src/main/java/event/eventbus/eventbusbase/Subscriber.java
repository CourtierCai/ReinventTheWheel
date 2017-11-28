package event.eventbus.eventbusbase;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/11/28.
 */

public class Subscriber {
    //当前的方法是什么？
    private Method method;
    //Class当前的参数是什么？
    private Class<?> cls;
    //注解的类型
    private SubscriberModel subscriberModel;

    public Subscriber(Method method, Class<?> cls, SubscriberModel subscriberModel) {
        this.method = method;
        this.cls = cls;
        this.subscriberModel = subscriberModel;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Class<?> getCls() {
        return cls;
    }

    public void setCls(Class<?> cls) {
        this.cls = cls;
    }

    public SubscriberModel getSubscriberModel() {
        return subscriberModel;
    }

    public void setSubscriberModel(SubscriberModel subscriberModel) {
        this.subscriberModel = subscriberModel;
    }
}
