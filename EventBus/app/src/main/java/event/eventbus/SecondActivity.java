package event.eventbus;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import event.eventbus.eventbusbase.EventBus;
import event.eventbus.eventbusbase.SubscriberModel;
import event.eventbus.eventbusbase.ThreadModel;

public class SecondActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

    }

    public void testEventBus(View view) {
        EventBus.getInstance().post(new String("Hello,EventBus!"));
    }

}
