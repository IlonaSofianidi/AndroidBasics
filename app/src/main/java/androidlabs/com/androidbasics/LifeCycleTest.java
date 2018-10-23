package androidlabs.com.androidbasics;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class LifeCycleTest extends AppCompatActivity {
    StringBuilder stringBuilder = new StringBuilder();
    private TextView textView;

    private void log(String text) {
        Log.d("LifeCycleTest: ", text);
        stringBuilder.append(text);
        stringBuilder.append('\n');

        textView.setText(stringBuilder.toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_life_cycle_test);

        textView = findViewById(R.id.lifecycle_textView);
        textView.setText(stringBuilder.toString());

        log("created");
    }


    @Override
    protected void onPause() {
        super.onPause();
        log("paused");

        if (isFinishing()) {
            log("finishing");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        log("resume");
    }
}
