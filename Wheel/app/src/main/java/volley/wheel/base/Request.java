package volley.wheel.base;

import java.net.URL;

import volley.wheel.handle.HurResponse;

/**
 * Created by Administrator on 2017/11/30.
 */

public abstract class Request<T> {
      private URL url;
      private T data;
      private ICallDataBack<T> iCallDataBack;

    public Request(URL url, T data, ICallDataBack<T> iCallDataBack) {
        this.url = url;
        this.data = data;
        this.iCallDataBack = iCallDataBack;
    }

    public URL getUrl() {
        return url;
    }

    public T getData() {
        return data;
    }

    public ICallDataBack<T> getiCallDataBack() {
        return iCallDataBack;
    }

    public abstract void parse(HurResponse hurResponse);

}
