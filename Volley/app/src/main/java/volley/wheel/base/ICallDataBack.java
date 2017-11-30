package volley.wheel.base;

/**
 * Created by Administrator on 2017/11/30.
 */

public interface ICallDataBack<T> {
    public void onSuccess(T data);
    public void onFailed(int Code);
}
