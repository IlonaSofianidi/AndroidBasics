package androidlabs.com.androidbasics;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AssetsTest extends AppCompatActivity {
    private TextView textView;

    public static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line + "\n");
        }
        is.close();
        return sb.toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assets_test);
        textView = findViewById(R.id.assets_textView);
        textView.setText(getStringFromAssetsFile(this, "test.txt"));
    }

    private String getStringFromAssetsFile(Activity activity, String fileName) {
        AssetManager assetManager = activity.getAssets();
        InputStream inputStream = null;
        String test_textFromFile = null;
        try {
            inputStream = assetManager.open(fileName);
            try {
                test_textFromFile = convertStreamToString(inputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception: ", e.toString());
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return test_textFromFile;
    }
}
