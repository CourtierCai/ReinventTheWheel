package volley.wheel.invoke;

import android.util.Log;

import volley.wheel.base.Request;
import volley.wheel.base.Task;
import volley.wheel.base.core.CorePoolImp;

/**
 * Created by Administrator on 2017/11/30.
 */

public class Volley {
    public static final String TAG = Volley.class.getName();
    public static void execute(Request request) {
        Log.i(TAG, "执行了任务");
        Task task = new Task(request);
        CorePoolImp.execute(task);
    }


    public static void exit() {
        CorePoolImp.clear();
    }

}
