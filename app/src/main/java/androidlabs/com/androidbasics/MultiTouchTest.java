package androidlabs.com.androidbasics;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class MultiTouchTest extends AppCompatActivity implements View.OnTouchListener {
    StringBuilder stringBuilder = new StringBuilder();
    float[] x = new float[10];
    float[] y = new float[10];
    boolean[] touched = new boolean[10];
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textView = new TextView(this);
        textView.setText(R.string.touch_multi_view);
        setContentView(textView);
        textView.setOnTouchListener(this);


    }

    private void updateTextView() {
        stringBuilder.setLength(0);
        for (int i = 0; i < 10; i++) {
            stringBuilder.append(touched[i]);
            stringBuilder.append(". ");
            stringBuilder.append(x[i]);
            stringBuilder.append(". ");
            stringBuilder.append(y[i]);
            stringBuilder.append("\n");

        }
        
        textView.setText(stringBuilder.toString());
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction() & MotionEvent.ACTION_MASK;
        int pointerIndex = (motionEvent.getAction() & MotionEvent.ACTION_POINTER_ID_MASK) >> MotionEvent.ACTION_POINTER_ID_SHIFT;

        int pointerId = motionEvent.getPointerId(pointerIndex);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                touched[pointerId] = false;
                x[pointerId] = (int) motionEvent.getX(pointerIndex);
                y[pointerId] = (int) motionEvent.getY(pointerIndex);
                break;


            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL:
                touched[pointerId] = false;
                x[pointerId] = (int) motionEvent.getX(pointerIndex);
                y[pointerId] = (int) motionEvent.getY(pointerIndex);
                break;
            case MotionEvent.ACTION_MOVE:
                int pointerCount = motionEvent.getPointerCount();

                for (int i = 0; i < pointerCount; i++) {
                    pointerIndex = 0;
                    pointerId = motionEvent.getPointerId(pointerIndex);
                    x[pointerId] = (int) motionEvent.getX(pointerIndex);
                    y[pointerId] = (int) motionEvent.getY(pointerIndex);
                }
                break;


        }
        updateTextView();
        return true;
    }
}
