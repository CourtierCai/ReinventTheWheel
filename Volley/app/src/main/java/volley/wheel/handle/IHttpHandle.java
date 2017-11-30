package volley.wheel.handle;

import volley.wheel.base.Request;

/**
 * Created by Administrator on 2017/11/30.
 */

public interface IHttpHandle {
    HurResponse getResponse(Request<?> request);
}
