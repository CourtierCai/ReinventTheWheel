package event.eventbus.eventbusbase;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Administrator on 2017/11/28.
 */
//用于方法上和运行时候解析
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SubscriberModel {
    ThreadModel threadModle() default ThreadModel.THREAD_NORMAL;
}
