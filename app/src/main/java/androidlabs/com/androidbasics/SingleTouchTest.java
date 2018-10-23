package androidlabs.com.androidbasics;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class SingleTouchTest extends AppCompatActivity implements View.OnTouchListener {
    StringBuilder stringBuilder = new StringBuilder();
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        textView = new TextView(this);
        textView.setText(R.string.touch_view);
        textView.setOnTouchListener(this);
        setContentView(textView);

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        stringBuilder.setLength(0);
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                stringBuilder.append("down. ");
                break;
            case MotionEvent.ACTION_MOVE:
                stringBuilder.append("move");
                break;
            case MotionEvent.ACTION_CANCEL:
                stringBuilder.append("cancel. ");
                break;
            case MotionEvent.ACTION_UP:
                stringBuilder.append("up. ");
                break;
        }
        stringBuilder.append(motionEvent.getX());
        stringBuilder.append(". ");
        stringBuilder.append(motionEvent.getY());
        String text = stringBuilder.toString();
        Log.d("TouchTest", text);
        textView.setText(text);
        return true;
    }
}
