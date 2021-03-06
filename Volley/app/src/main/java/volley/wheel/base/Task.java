package volley.wheel.base;

import android.util.Log;

import volley.wheel.handle.HttpHandleCustom;
import volley.wheel.handle.HurResponse;
import volley.wheel.invoke.Volley;

/**
 * Created by Administrator on 2017/11/30.
 */

public class Task implements Runnable {
    private Request mRequest;

    public Task(Request mRequest) {
        this.mRequest = mRequest;
    }

    @Override
    public void run() {
        if (mRequest == null) return;
        /**
         * 1，先去获取访问网络
         * **/

        HurResponse hurResponse = HttpHandleCustom.getInstance().getResponse(mRequest);
        Log.i(Volley.TAG, "我是任务中心 - 获取到了服务器端返回的信息");
        /**
         * 2，获取到返回值交给数据处理中心（每个自定定义自己的处理的中心）
         * */
        if (hurResponse != null) {
            mRequest.parse(hurResponse);
        }

    }


}
