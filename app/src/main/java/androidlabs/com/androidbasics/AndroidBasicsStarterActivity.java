package androidlabs.com.androidbasics;


import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AndroidBasicsStarterActivity extends ListActivity {
    String[] activity_tests = {"LifeCycleTest", "SingleTouchTest", "MultiTouchTest",
             "AssetsTest",
            "DatabaseStorageTest",
            "GoogleMapActivity"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, activity_tests);
        setListAdapter(adapter);

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String activity_test_name = activity_tests[position];
        try {
            Class class_name = Class.forName("androidlabs.com.androidbasics." + activity_test_name);
            Intent intent = new Intent(this, class_name);
            startActivity(intent);


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
