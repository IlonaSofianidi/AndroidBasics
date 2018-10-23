package androidlabs.com.androidbasics;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class GoogleMapActivity extends ListActivity {
    private String[] subjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_google_map);
       subjects =  getResources().getStringArray(R.array.subjects_course);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, subjects);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String selectedItem = (String) l.getItemAtPosition(position);
        Intent intent = new Intent(this,GoogleMapTest.class);
        switch (selectedItem) {
            case "Компютерні мережі":
                intent.putExtra(GoogleMapTest.EXTRA_LATITUDE,49.835213);
                intent.putExtra(GoogleMapTest.EXTRA_LONGINTUDE,24.008512);
                intent.putExtra(GoogleMapTest.EXTRA_LOCATION,"5 корпус НУЛП");
                break;
            case "Компютерна логіка":
                intent.putExtra(GoogleMapTest.EXTRA_LATITUDE,49.836472);
                intent.putExtra(GoogleMapTest.EXTRA_LONGINTUDE,24.013581);
                intent.putExtra(GoogleMapTest.EXTRA_LOCATION,"3 корпус НУЛП");
                break;
            case "Вища математика":
                intent.putExtra(GoogleMapTest.EXTRA_LATITUDE,49.835463);
                intent.putExtra(GoogleMapTest.EXTRA_LONGINTUDE, 24.010230);
                intent.putExtra(GoogleMapTest.EXTRA_LOCATION,"1 корпус НУЛП");
                break;
            case "Системне програмування":
                intent.putExtra(GoogleMapTest.EXTRA_LATITUDE,49.835463);
                intent.putExtra(GoogleMapTest.EXTRA_LONGINTUDE, 24.010230);
                intent.putExtra(GoogleMapTest.EXTRA_LOCATION,"1 корпус НУЛП");
                break;
            case "Кросс-платформені засоби програмування":
                intent.putExtra(GoogleMapTest.EXTRA_LATITUDE,49.835213);
                intent.putExtra(GoogleMapTest.EXTRA_LONGINTUDE,24.008512);
                intent.putExtra(GoogleMapTest.EXTRA_LOCATION,"5 корпус НУЛП");
                break;
            case "Мережні операційні системи":
                intent.putExtra(GoogleMapTest.EXTRA_LATITUDE,49.835213);
                intent.putExtra(GoogleMapTest.EXTRA_LONGINTUDE,24.008512);
                intent.putExtra(GoogleMapTest.EXTRA_LOCATION,"5 корпус НУЛП");

                break;

        }
        startActivity(intent);


    }
}
