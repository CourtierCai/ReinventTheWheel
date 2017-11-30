package volley.wheel;

import android.app.Activity;

import android.os.Bundle;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

import volley.wheel.base.ICallDataBack;
import volley.wheel.base.baseimpl.StringRequest;
import volley.wheel.invoke.Volley;

public class MainActivity extends Activity {
    private static int  z = 0;
    public static final String TAG = MainActivity.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            //性能测试
            for (int i = 0 ; i < 50 ; i++) {
                StringRequest<String> stringStringRequest = new StringRequest<>(new URL("http://test.com"), String.class, new ICallDataBack<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Log.i(TAG, "Count：" + ++z + "");
                    }

                    @Override
                    public void onFailed(int Code) {

                    }
                });

                Volley.execute(stringStringRequest);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
