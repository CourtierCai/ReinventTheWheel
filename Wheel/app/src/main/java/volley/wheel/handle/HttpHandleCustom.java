package volley.wheel.handle;

import volley.wheel.base.Request;

/**
 * Created by Administrator on 2017/11/30.
 */

public class HttpHandleCustom implements IHttpHandle{
    private HttpHandleCustom() {

    }
    private static class Instance{
       public static    HttpHandleCustom i = new HttpHandleCustom();
    }

    public static HttpHandleCustom getInstance() {
        return Instance.i;
    }
    @Override
    public HurResponse getResponse(Request<?> request) {
        //dosomething，可以做一些网络访问(HttpClient)拿到inputStream之后传递给HurResponse。

        return new HurResponse(null);
    }
}
