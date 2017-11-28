package event.eventbus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import event.eventbus.eventbusbase.EventBus;
import event.eventbus.eventbusbase.SubscriberModel;
import event.eventbus.eventbusbase.ThreadModel;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getInstance().registe(this);
    }


    @SubscriberModel(threadModle = ThreadModel.THREAD_MAIN)
    public void testEventBus(String testString) {
        Log.i("MainActivity===>", testString);
        //收到消息了
        Toast.makeText(this, testString ,Toast.LENGTH_LONG).show();
    }

    public void jumpBtn(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getInstance().unregiste(this);
    }



}
