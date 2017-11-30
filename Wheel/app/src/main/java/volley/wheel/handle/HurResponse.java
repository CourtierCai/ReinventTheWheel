package volley.wheel.handle;

import java.io.InputStream;

import volley.wheel.base.Request;

/**
 * Created by Administrator on 2017/11/30.
 */

//模拟返回值
public class HurResponse {
    public static final String SIMULATE = "Simulate";
    private InputStream inputStream;


    public HurResponse(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public byte[] getData() {
        //拿到inputStream来获取data
        return null;
    }


}
