package volley.wheel.base.baseimpl;

import android.util.Log;

import java.net.URL;

import volley.wheel.base.ICallDataBack;
import volley.wheel.base.Request;
import volley.wheel.handle.HurResponse;
import volley.wheel.invoke.Volley;

/**
 * Created by Administrator on 2017/11/30.
 */

public class StringRequest<T> extends Request<T> {
    public StringRequest(URL url, Class<T> data, ICallDataBack<T> iCallDataBack) {
        super(url, data, iCallDataBack);
    }

    @Override
   public void parse(HurResponse hurResponse) {
        Log.i(Volley.TAG, "我是数据处理中心 - 我正在处理对应的data的数据");
        byte [] data = hurResponse.getData();
        //1，进行转换到自己泛型的类
        //dosomething
        //2，callback返回处理
        ICallDataBack callDataBack =  getiCallDataBack();
        if (callDataBack != null) {
            callDataBack.onSuccess(null);
            Log.i(Volley.TAG, "我正在回调获取到服务器的值");
        }
    }


}
