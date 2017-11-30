package volley.wheel.base.baseimpl;

import java.net.URL;

import volley.wheel.base.ICallDataBack;
import volley.wheel.base.Request;
import volley.wheel.handle.HurResponse;

/**
 * Created by Administrator on 2017/11/30.
 */

public class StringQuest<T> extends Request<T> {
    public StringQuest(URL url, T data, ICallDataBack<T> iCallDataBack) {
        super(url, data, iCallDataBack);
    }

    @Override
   public void parse(HurResponse hurResponse) {

        byte [] data = hurResponse.getData();
        //1，进行转换到自己泛型的类
        //dosomething
        //2，callback返回处理
        ICallDataBack callDataBack =  getiCallDataBack();
        if (callDataBack != null) {
            callDataBack.onSuccess(null);
        }
    }


}
